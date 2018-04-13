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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/functions/Base64Encode.java $
 $Id: Base64Encode.java 3515 2008-07-31 13:20:05Z loomis $
 */

package org.quattor.pan.dml.functions;

import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_ARGS_BASE64_ENCODE;
import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_NO_ARGS_BASE64_ENCODE;

import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;
import org.quattor.pan.utils.Base64;
import org.quattor.pan.utils.MessageUtils;

import java.nio.charset.Charset;

/**
 * Encodes a string into its Base64 representation.
 *
 * @author loomis
 *
 */
final public class Base64Encode extends BuiltInFunction {

	private Base64Encode(SourceRange sourceRange, Operation... operations)
			throws SyntaxException {
		super("base64_encode", sourceRange, operations);

		// Ensure that there is exactly one argument. Since the parser does
		// little argument checking for function calls, this explicit check is
		// needed.
		if (operations.length != 1) {
			throw SyntaxException.create(sourceRange,
					MSG_INVALID_NO_ARGS_BASE64_ENCODE);
		}
	}

	public static Operation getInstance(SourceRange sourceRange,
			Operation... operations) throws SyntaxException {
		return new Base64Encode(sourceRange, operations);
	}

	@Override
	public Element execute(Context context) {

		assert (ops.length == 1);

		Element result = ops[0].execute(context);
		try {
			String s = ((StringProperty) result).getValue();
			return StringProperty.getInstance(Base64.encodeBytes(s.getBytes(Charset.forName("UTF-8"))));
		} catch (ClassCastException cce) {
			throw new EvaluationException(MessageUtils
					.format(MSG_INVALID_ARGS_BASE64_ENCODE), sourceRange,
					context);
		}
	}

}
