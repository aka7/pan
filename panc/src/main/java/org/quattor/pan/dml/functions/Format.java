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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/functions/Format.java $
 $Id: Format.java 2799 2008-01-26 17:10:23Z loomis $
 */

package org.quattor.pan.dml.functions;

import static org.quattor.pan.utils.MessageUtils.MSG_FIRST_STRING_ARG_REQ;

import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;

/**
 * Produces a formatted string based on the given format specification and
 * arguments.
 * 
 * @author loomis
 * 
 */
final public class Format extends Formatter {

    private Format(SourceRange sourceRange, Operation... operations)
            throws SyntaxException {
        super("format", sourceRange, operations);

        // There must be at least one argument.
        if (operations.length == 0) {
            throw SyntaxException.create(sourceRange, MSG_FIRST_STRING_ARG_REQ,
                    name);
        }
    }

    public static Operation getInstance(SourceRange sourceRange,
            Operation... operations) throws SyntaxException {
        return new Format(sourceRange, operations);
    }

    @Override
    public Element execute(Context context) {

        assert (ops.length > 0);

        StringProperty result = format(context);

        // Return the value. It should never be null if we reach this part of
        // the code.
        assert (result != null);
        return result;

    }
}
