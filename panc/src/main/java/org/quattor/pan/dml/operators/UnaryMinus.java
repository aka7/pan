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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/operators/UnaryMinus.java $
 $Id: UnaryMinus.java 3515 2008-07-31 13:20:05Z loomis $
 */

package org.quattor.pan.dml.operators;

import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_ARGS_UMINUS;

import org.quattor.pan.dml.AbstractOperation;
import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.DoubleProperty;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.LongProperty;
import org.quattor.pan.dml.data.NumberProperty;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;
import org.quattor.pan.utils.MessageUtils;

/**
 * Implements a unary minus operation.
 * 
 * @author loomis
 * 
 */
final public class UnaryMinus extends AbstractOperation {

	private UnaryMinus(SourceRange sourceRange, Operation... operations) {
		super(sourceRange, operations);
		assert (operations.length == 1);
	}

	public static Operation newOperation(SourceRange sourceRange,
			Operation... ops) throws SyntaxException {

		assert (ops.length == 1);

		Operation result = null;

		// Attempt to optimize this operation.
		if (ops[0] instanceof Element) {

			try {
				NumberProperty a = (NumberProperty) ops[0];
				result = execute(a);
			} catch (ClassCastException cce) {
				throw new EvaluationException(MessageUtils
						.format(MSG_INVALID_ARGS_UMINUS), sourceRange);
			} catch (EvaluationException ee) {
				throw SyntaxException.create(sourceRange, ee);
			}

		} else {
			result = new UnaryMinus(sourceRange, ops);
		}

		return result;
	}

	@Override
	public Element execute(Context context) {

		try {
			Element[] args = calculateArgs(context);
			NumberProperty a = (NumberProperty) args[0];
			return execute(a);
		} catch (ClassCastException cce) {
			throw new EvaluationException(MessageUtils
					.format(MSG_INVALID_ARGS_UMINUS), sourceRange);
		}

	}

	private static Element execute(NumberProperty a) {

		Element result = null;

		if (a instanceof LongProperty) {
			long l1 = ((Long) a.getValue()).longValue();
			result = LongProperty.getInstance(-l1);

		} else if (a instanceof DoubleProperty) {
			double d1 = ((Double) a.getValue()).doubleValue();
			result = DoubleProperty.getInstance(-d1);
		}

		return result;
	}
}
