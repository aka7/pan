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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/functions/Substr.java $
 $Id: Substr.java 2618 2007-12-08 16:32:02Z loomis $
 */

package org.quattor.pan.dml.functions;

import static org.quattor.pan.utils.MessageUtils.MSG_2_OR_3_ARGS;
import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_KEY_OR_INDEX;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.ListResource;
import org.quattor.pan.dml.data.LongProperty;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.exceptions.CompilerError;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.InvalidTermException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;
import org.quattor.pan.utils.Term;
import org.quattor.pan.utils.TermFactory;

/**
 * Replace occurrences of a regular expression with a given string.
 * 
 * @author loomis
 * 
 */
final public class Split extends BuiltInFunction {

	private Split(SourceRange sourceRange, Operation... operations)
			throws SyntaxException {
		super("split", sourceRange, operations);
	}

	public static Operation getInstance(SourceRange sourceRange,
			Operation... operations) throws SyntaxException {

		// There must be two or three arguments.
		if (operations.length < 2 || operations.length > 3) {
			throw SyntaxException.create(sourceRange, MSG_2_OR_3_ARGS, "split");
		}

		return new Split(sourceRange, operations);
	}

	@Override
	public Element execute(Context context) {

		assert (ops.length == 2 || ops.length == 3);

		// Extract the first argument. This must be a string value and also a
		// valid regular expression.
		Pattern regex = null;
		try {

			String re = ((StringProperty) ops[0].execute(context)).getValue();
			regex = Pattern.compile(re);

		} catch (ClassCastException cce) {
			throw new EvaluationException(
					"first argument to split() must be a regular expression string",
					getSourceRange(), context);
		} catch (PatternSyntaxException pse) {
			throw new EvaluationException("invalid regular expression: "
					+ pse.getLocalizedMessage(), getSourceRange(), context);
		}

		// If there are only two arguments, then the default limit value is
		// zero. If there are three arguments, extract the value.
		int limit = 0;
		if (ops.length == 3) {
			try {

				limit = ((LongProperty) ops[1].execute(context)).getValue()
						.intValue();

			} catch (ClassCastException cce) {
				throw new EvaluationException(
						"second argument to split() must be a long value",
						getSourceRange(), context);
			}
		}

		// Finally get the target string.
		String target = null;
		try {

			int index = (ops.length == 2) ? 1 : 2;
			target = ((StringProperty) ops[index].execute(context)).getValue();

		} catch (ClassCastException cce) {
			throw new EvaluationException(
					"last argument to split() must be a string",
					getSourceRange(), context);
		}

		// Create the list of strings to return.
		String[] results = regex.split(target, limit);
		ListResource list = new ListResource();
		long index = 0L;
		for (String s : results) {
			Term t = TermFactory.create(index++);
			try {
				list.put(t, StringProperty.getInstance(s));
			} catch (InvalidTermException ite) {
				// This exception should never be encountered because the
				// terms created with the correct type.
				throw CompilerError.create(MSG_INVALID_KEY_OR_INDEX);
			}
		}
		return list;
	}
}
