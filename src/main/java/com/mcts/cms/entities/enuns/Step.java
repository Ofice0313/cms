package com.mcts.cms.entities.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Step {

    DURBAN_SA,
    NAMAACHA,
    CIDADE_DE_MAPUTO

    ;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Step from(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number number) {
            int index = number.intValue();
            Step[] values = Step.values();
            if (index >= 0 && index < values.length) {
                return values[index];
            }
            throw new IllegalArgumentException("Invalid Step index: " + index);
        }

        String text = value.toString().trim();
        if (text.isEmpty()) {
            return null;
        }

        if (text.matches("\\d+")) {
            int index = Integer.parseInt(text);
            Step[] values = Step.values();
            if (index >= 0 && index < values.length) {
                return values[index];
            }
            throw new IllegalArgumentException("Invalid Step index: " + index);
        }

        return Step.valueOf(text.toUpperCase());
    }

}
