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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/functions/Base64DecodeTest.java $
 $Id: Base64DecodeTest.java 1042 2006-11-28 10:04:35Z loomis $
 */

package org.quattor.pan.dml.functions;

import org.junit.Test;
import org.quattor.pan.dml.data.LongProperty;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.exceptions.SyntaxException;

public class DebugSuppressedTest extends BuiltInFunctionTestUtils {

	@Test
	public void checkGetInstance() {
		checkClassRequirements(Debug.class);
	}

	@Test(expected = SyntaxException.class)
	public void checkTooFewArguments() throws SyntaxException {
		DebugSuppressed.getInstance(null);
	}

	@Test(expected = SyntaxException.class)
	public void checkInvalidArgument() throws SyntaxException {
		DebugSuppressed.getInstance(null, LongProperty.getInstance(0L));
	}

}
