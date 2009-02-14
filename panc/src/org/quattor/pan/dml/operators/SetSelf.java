/*
 Copyright (c) 2006 Charles A. Loomis, Jr, Cedric Duprilot, and
 Centre National de la Recherche Scientifique (CNRS).

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/operators/SetValue.java $
 $Id: SetValue.java 2712 2008-01-13 20:49:58Z loomis $
 */

package org.quattor.pan.dml.operators;

import static org.quattor.pan.utils.MessageUtils.MSG_ILLEGAL_SELF_REF;
import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_COMPILE_TIME_OPERATION;
import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_EXECUTE_METHOD_CALLED;

import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.HashResource;
import org.quattor.pan.dml.data.ListResource;
import org.quattor.pan.dml.data.Undef;
import org.quattor.pan.exceptions.CompilerError;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.InvalidTermException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.template.Context;
import org.quattor.pan.template.SourceRange;
import org.quattor.pan.utils.Term;

/**
 * Implements a special operation to allow a result to be assigned to a
 * variable. This is generated by the compiler, but is not explicitly visible in
 * the pan language.
 * 
 * @author loomis
 * 
 */
public class SetSelf extends SetValue {

	private static final long serialVersionUID = 426484166958731379L;

	public SetSelf(SourceRange sourceRange, Operation... operations)
			throws SyntaxException {
		super(sourceRange, "SELF", operations);

		// Ensure that the user is not trying to set the SELF variable directly.
		// It is still legal to set children of SELF.
		if (operations.length == 0) {
			throw SyntaxException.create(sourceRange, MSG_ILLEGAL_SELF_REF);
		}
	}

	@Override
	public Element execute(Context context) {

		// This operation is intended to only be called from an Assign
		// operation. If this is called, then incorrect code was generated by
		// the compiler.
		throw CompilerError
				.create(MSG_INVALID_EXECUTE_METHOD_CALLED, "SetSelf");
	}

	@Override
	public Element execute(Context context, Element result) {

		// Quickly check to see if this is a compile-time context. This function
		// cannot be evaluated in such a context.
		if (context.isCompileTimeContext()) {
			throw EvaluationException
					.create(MSG_INVALID_COMPILE_TIME_OPERATION);
		}

		// Create an array containing the terms for dereferencing.
		Term[] terms = null;
		try {
			terms = calculateTerms(context);
		} catch (EvaluationException ee) {
			throw ee.addExceptionInfo(sourceRange, context);
		}

		// Duplicate the result only if necessary. Within a DML block,
		// duplicating the element is only necessary if the value we are going
		// to set has parents. This is to avoid any possibility of creating
		// cyclic data structures. This requirement essentially comes down to
		// checking if the number of terms is positive.
		Element dupResult = result;
		if (result != null) {
			dupResult = result.duplicate();
		}

		// Check that SELF isn't fixed. This is true for a validation call.
		if (context.isSelfFinal()) {
			EvaluationException ee = new EvaluationException(
					"cannot modify SELF from validation function");
			throw ee.addExceptionInfo(sourceRange, context);
		}

		// Now set the value. May throw an exception if this is a global
		// variable.
		try {
			Element self = context.getSelf();

			if (self.isProtected()) {
				self = self.writableCopy();
				context.resetSelf(self);
			}

			// If the value does not exist, create a resource of the correct
			// type and insert into variable table.
			if (self instanceof Undef) {

				Term term = terms[0];
				if (term.isKey()) {
					self = new HashResource();
				} else {
					self = new ListResource();
				}
				context.resetSelf(self);

			}

			// Recursively descend to set the value.
			try {
				self.rput(terms, 0, result);
			} catch (InvalidTermException ite) {
				throw new EvaluationException(ite.formatVariableMessage("SELF",
						terms));
			}

		} catch (EvaluationException ee) {
			throw ee.addExceptionInfo(sourceRange, context);
		}

		// Push the result onto the data stack.
		return dupResult;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + identifier + ","
				+ ops.length + ")";
	}

}
