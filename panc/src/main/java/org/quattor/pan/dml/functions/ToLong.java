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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/functions/ToLong.java $
 $Id: ToLong.java 3552 2008-08-03 09:55:58Z loomis $
 */

package org.quattor.pan.dml.functions;

import static org.quattor.pan.utils.MessageUtils.MSG_ARG_NOT_PROPERTY;
import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_STRING_FOR_LONG;
import static org.quattor.pan.utils.MessageUtils.MSG_ONE_OR_TWO_ARGS_REQ;
import static org.quattor.pan.utils.MessageUtils.MSG_UNKNOWN_PROPERTY_TYPE;

import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.BooleanProperty;
import org.quattor.pan.dml.data.DoubleProperty;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.LongProperty;
import org.quattor.pan.dml.data.Property;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.exceptions.CompilerError;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;

/**
 * Convert the function's argument to a long value.
 * 
 * @author loomis
 * 
 */
final public class ToLong extends BuiltInFunction {

	private ToLong(SourceRange sourceRange, Operation... operations)
			throws SyntaxException {
		super("to_long", sourceRange, operations);
	}

	public static Operation getInstance(SourceRange sourceRange,
			Operation... operations) throws SyntaxException {

		if (operations.length == 1) {
			return new ToLong(sourceRange, operations);
		} else if (operations.length == 2) {
			return ToLongRadix.getInstance(sourceRange, operations);
		} else {
			throw SyntaxException.create(sourceRange, MSG_ONE_OR_TWO_ARGS_REQ,
					"to_long");
		}

	}

	@Override
	public Element execute(Context context) {

		assert (ops.length == 1);

		Element result = ops[0].execute(context);

		try {
			Element value = null;

			Property property = (Property) result;
			if (property instanceof BooleanProperty) {
				long x = ((BooleanProperty) property).getValue().booleanValue() ? 1L
						: 0L;
				value = LongProperty.getInstance(x);
			} else if (property instanceof DoubleProperty) {
				double x = ((DoubleProperty) property).getValue().doubleValue();
				value = LongProperty.getInstance(Math.round(x));
			} else if (property instanceof StringProperty) {
				String x = ((StringProperty) property).getValue();
				try {
					value = LongProperty.getInstance(Long.decode(x));
				} catch (NumberFormatException nfe) {
					throw EvaluationException.create(sourceRange, context,
							MSG_INVALID_STRING_FOR_LONG, x);
				}
			} else if (property instanceof LongProperty) {
				value = result;
			} else {
				throw CompilerError.create(MSG_UNKNOWN_PROPERTY_TYPE,
						property.getClass());
			}
			return value;
		} catch (ClassCastException cce) {
			throw EvaluationException.create(sourceRange, context,
					MSG_ARG_NOT_PROPERTY, result.getTypeAsString());
		}
	}

}
