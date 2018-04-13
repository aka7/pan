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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/operators/Variable.java $
 $Id: Variable.java 1867 2007-06-17 17:01:45Z loomis $
 */

package org.quattor.pan.dml.operators;

import static org.quattor.pan.utils.MessageUtils.MSG_CANNOT_MODIFY_GLOBAL_VARIABLE_FROM_DML;
import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_IN_COMPILE_TIME_CONTEXT;

import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.ListResource;
import org.quattor.pan.dml.data.Null;
import org.quattor.pan.dml.data.Undef;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.InvalidTermException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;
import org.quattor.pan.utils.Term;

/**
 * Looks up a nested variable in the execution context. There must be at least
 * one index (operation).
 * 
 * @author loomis
 * 
 */
public class NestedListVariable extends ListVariable {

	public NestedListVariable(SourceRange sourceRange, String identifier,
			Operation... operations) {
		super(sourceRange, identifier, operations);
		assert (operations.length > 0);
	}

	@Override
	public Element execute(Context context) {

		// Create the array of terms to dereference the variable.
		Term[] terms = null;
		try {
			terms = calculateTerms(context);
		} catch (EvaluationException ee) {
			throw ee.addExceptionInfo(getSourceRange(), context);
		}

		// The value pulled out will be modified. Because of this the given
		// variable must be a local variable.
		Element result = context.getLocalVariable(identifier);

		if (result == null) {

			// This operation can only be used in a compile-time context if the
			// referenced variable already exists. If it doesn't it can lead to
			// incorrect compile-time values that rely on global variables.
			if (context.isCompileTimeContext()) {
				throw EvaluationException.create(sourceRange,
						MSG_INVALID_IN_COMPILE_TIME_CONTEXT, this.getClass()
								.getSimpleName());
			}

			// Before creating the given local variable, ensure that we are not
			// masking a global variable.
			if (context.getGlobalVariable(identifier) != null) {
				throw EvaluationException.create(sourceRange,
						MSG_CANNOT_MODIFY_GLOBAL_VARIABLE_FROM_DML, identifier);
			}

			// Ok. Create an empty list and a local variable with this name.
			result = new ListResource();
			context.setLocalVariable(identifier, result);

		} else if ((result instanceof Undef) || (result instanceof Null)) {

			// Create an empty list and set the local variable to this value.
			result = new ListResource();
			context.setLocalVariable(identifier, result);

		} else if (result.isProtected()) {

			// The given value is protected. Replace it with a writable copy.
			result = result.writableCopy();
			context.setLocalVariable(identifier, result);

		}

		// Lookup (and create if necessary) all of the children.
		try {
			result = result.rgetList(terms, 0);
		} catch (InvalidTermException e) {
			throw new EvaluationException(e.formatVariableMessage(identifier,
					terms));
		}

		// Send the result back to the caller.
		return result;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + identifier + ","
				+ ops.length + ")";
	}
}
