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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/AbstractOperationTestUtils.java $
 $Id: AbstractOperationTestUtils.java 3600 2008-08-17 14:48:32Z loomis $
 */

package org.quattor.pan.dml;

import org.quattor.pan.dml.data.Element;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.CompileTimeContext;

public class AbstractOperationTestUtils {

	public Element runDml(Operation op) throws SyntaxException {
		return op.execute(new CompileTimeContext());
	}

}
