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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/template/ObjectContextTest.java $
 $Id: ObjectContextTest.java 3550 2008-08-02 14:54:26Z loomis $
 */

package org.quattor.pan.ttemplate;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.type.AliasType;
import org.quattor.pan.type.BaseType;
import org.quattor.pan.type.ConcretePrimitiveType;
import org.quattor.pan.type.FullType;

public class TypeMapTest {

	@Test
	public void checkNullForUndefinedFunction() {
		TypeMap map = new TypeMap();
		assertTrue(map.get("DUMMY") == null);
	}

	@Test
	public void checkBuiltInTypes() {
		TypeMap map = new TypeMap();
		assertTrue(map.get("boolean") != null);
		assertTrue(map.get("double") != null);
		assertTrue(map.get("element") != null);
		assertTrue(map.get("nlist") != null);
		assertTrue(map.get("dict") != null);
		assertTrue(map.get("list") != null);
		assertTrue(map.get("long") != null);
		assertTrue(map.get("number") != null);
		assertTrue(map.get("property") != null);
		assertTrue(map.get("resource") != null);
		assertTrue(map.get("string") != null);
	}

	@Test
	public void checkSimpleAssociation() throws SyntaxException {

		TypeMap map = new TypeMap();
		BaseType baseType = new ConcretePrimitiveType("dummy", Element.class);

		// Setup an argument list with two string values.
		String name = "DUMMY";
		FullType type = new FullType("source", null, baseType, null, null);

		// Put this into the function map.
		map.put(name, type, null, null);

		// Ensure that we get something out.
		FullType defn = map.get(name);
		assertTrue(defn == type);
	}

	@Test(expected = EvaluationException.class)
	public void replaceExistingDefn() throws SyntaxException {

		TypeMap map = new TypeMap();
		BaseType baseType = new ConcretePrimitiveType("dummy", Element.class);

		// Setup an argument list with two string values.
		String name = "DUMMY";
		FullType type = new FullType("source", null, baseType, null, null);

		// Try to define the same thing twice.
		map.put(name, type, null, null);
		map.put(name, type, null, null);
	}

	@Test(expected = EvaluationException.class)
	public void checkMissingSubtype() throws SyntaxException {

		TypeMap map = new TypeMap();
		BaseType baseType = new AliasType("source", null, "dummy", null);

		// Setup an argument list with two string values.
		String name = "DUMMY";
		FullType type = new FullType("source", null, baseType, null, null);

		// This should fail because the type "dummy" doesn't exist.
		map.put(name, type, null, null);
	}

}
