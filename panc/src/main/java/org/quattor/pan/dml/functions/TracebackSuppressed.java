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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/functions/Traceback.java $
 $Id: Traceback.java 3599 2008-08-17 13:57:57Z loomis $
 */

package org.quattor.pan.dml.functions;

import static org.quattor.pan.utils.MessageUtils.MSG_ONE_STRING_ARG_REQ;

import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.dml.data.Undef;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;

/**
 * Suppressed traceback call. This replaces the traceback with undef.
 * 
 * @author loomis
 * 
 */
final public class TracebackSuppressed extends BuiltInFunction {

	private TracebackSuppressed(SourceRange sourceRange,
			Operation... operations) throws SyntaxException {
		super("traceback", sourceRange, operations);
	}

	public static Operation getInstance(SourceRange sourceRange,
			Operation... operations) throws SyntaxException {

		// Ensure that there is exactly one argument. Since the parser does
		// little argument checking for function calls, this explicit check is
		// needed.
		if (operations.length != 1) {
			throw SyntaxException.create(sourceRange, MSG_ONE_STRING_ARG_REQ,
					"traceback");
		}
		if (operations[0] instanceof Element) {
			if (!(operations[0] instanceof StringProperty)) {
				throw SyntaxException.create(sourceRange,
						MSG_ONE_STRING_ARG_REQ, "traceback");
			}
		}

		return Undef.VALUE;
	}

	@Override
	public Element execute(Context context) {
		// This should never be called. All suppressed traceback calls are
		// replaced with an undef value.
		assert (false);
		return null;
	}

}
