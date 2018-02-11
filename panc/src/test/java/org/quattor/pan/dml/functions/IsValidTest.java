package org.quattor.pan.dml.functions;

import org.junit.Test;
import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.dml.operators.NestedVariable;
import org.quattor.pan.dml.operators.SelfSimpleVariable;
import org.quattor.pan.dml.operators.SimpleVariable;
import org.quattor.pan.dml.operators.Variable;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.BuildContext;
import org.quattor.pan.ttemplate.CompileTimeContext;
import org.quattor.pan.ttemplate.Context;

/**
 * Created by iliclaey.
 */
public class IsValidTest extends BuiltInFunctionTestUtils {

    @Test
    public void checkGetInstance() {
        checkClassRequirements(IsValid.class);
    }

    @Test(expected = SyntaxException.class)
    public void testTooFewArguments() throws SyntaxException {
        IsValid.getInstance(null);
    }

    @Test(expected = SyntaxException.class)
    public void testTooManyArguments() throws SyntaxException {
        IsValid.getInstance(null, SimpleVariable.getInstance(null, "type"),
                null, StringProperty.getInstance("message"));
    }

    @Test(expected = SyntaxException.class)
    public void testWrongFirstArgument() throws SyntaxException {
        IsValid.getInstance(null, StringProperty.getInstance("Not ok"),
                StringProperty.getInstance("message"));
    }

    @Test(expected = EvaluationException.class)
    public void testCompileTimeContext() throws SyntaxException {

        Context context = new CompileTimeContext();
        Operation dml = IsValid.getInstance(null, SimpleVariable.getInstance(null, "type"),
                StringProperty.getInstance("message"));

        context.executeDmlBlock(dml);
    }

    @Test(expected = EvaluationException.class)
    public void testWrongVariableType() throws SyntaxException {

        Context context = new BuildContext();
        NestedVariable nv = (NestedVariable) NestedVariable.getInstance(null, "type",
                StringProperty.getInstance("message"));

        Operation dml = IsValid.getInstance(null, nv, StringProperty.getInstance("message"));

        context.executeDmlBlock(dml);
    }

    @Test(expected = EvaluationException.class)
    public void testNoFullTypeDefined() throws SyntaxException {

        Context context = new BuildContext();
        Operation dml = IsValid.getInstance(null, SimpleVariable.getInstance(null, "type"),
                StringProperty.getInstance("message"));

        context.executeDmlBlock(dml);
    }

}
