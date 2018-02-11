package org.quattor.pan.type;

import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.Resource;
import org.quattor.pan.dml.data.StringProperty;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.exceptions.ValidationException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;

import java.util.ArrayList;
import java.util.List;

import static org.quattor.pan.utils.MessageUtils.MSG_INVALID_CHOICE_TYPE;

/**
 * Created by iliclaey.
 *
 * The eventual value of the variable/path needs to be contained in the possible choices.
 */
public class ChoiceType extends AdvancedType {

    private List<Element> choices;

    /**
     * Constructor for ChoiceType.
     *
     * @param source      String describing the source containing this definition
     * @param sourceRange
     * @param choices     List of possible choices
     */
    public ChoiceType(String source, SourceRange sourceRange, List<Element> choices) {
        super(source, sourceRange, "string");
        this.choices = choices;
    }

    public Object validate(final Context context, final Element self) {

        // Check if the value is indeed a StringProperty.
        FullType type = context.getFullType(identifier);
        type.validate(context, self);

        System.out.println(this.toString());

        // Check whether the value is one of the possible choices.
        boolean found = choices.contains(self);

        // If the element is not a possible choice, throw an error.
        if (!found) {
            throw ValidationException.create(MSG_INVALID_CHOICE_TYPE, self.toString());
        }

        return null;
    }

    @Override
    public Element setDefaults(Context context, Element self) throws EvaluationException {
        // ChoiceType itself can't have a default value. The FullType containing the ChoiceType might.
        return null;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<String>();

        for (Element e : choices) {
            list.add(((StringProperty) e).getValue());
        }

        return "choice(" + String.join(", ", list) + ")";
    }
}
