package com.jalizadeh.todocial.web.model.gym.types;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

/**
 * Document: https://www.baeldung.com/jpa-persisting-enums-in-jpa
 */

@Converter(autoApply = true)
public class GymMainGoalConverter implements AttributeConverter<GymMainGoal, String> {

    @Override
    public String convertToDatabaseColumn(GymMainGoal mainGoal) {
        if(mainGoal == null)
            return null;

        return mainGoal.getCode();
    }

    @Override
    public GymMainGoal convertToEntityAttribute(String code) {
        if(code == null)
            return null;

        return Stream.of(GymMainGoal.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
