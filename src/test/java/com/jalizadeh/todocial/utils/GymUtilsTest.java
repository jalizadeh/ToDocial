package com.jalizadeh.todocial.utils;

import com.jalizadeh.todocial.model.gym.dto.GymWorkoutLogSetRep_DTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Gym Utils Tests")
class GymUtilsTest {

    @Test
    void workoutLogNoteParser_EmptyInput() {
        assertNull(GymUtils.workoutLogNoteParser(""));
        assertNull(GymUtils.workoutLogNoteParser("   "));
    }

    @Test
    void workoutLogNoteParser_BasicInput1() {
        //3 sets of 10(kg) with 12 (default) reps
        List<GymWorkoutLogSetRep_DTO> gymWorkoutLogSetRepDtos = GymUtils.workoutLogNoteParser("10,10,10");

        assertEquals(3, gymWorkoutLogSetRepDtos.size());
        gymWorkoutLogSetRepDtos.forEach(r -> {
            assertEquals(10.0, r.getWeight());
            assertEquals(12, r.getRep());
        });
    }

    @Test
    void workoutLogNoteParser_BasicInput2() {
        //4 sets of 10(kg) with 12 (default) reps
        List<GymWorkoutLogSetRep_DTO> gymWorkoutLogSetRepDtos = GymUtils.workoutLogNoteParser("10x3,10:12");

        assertEquals(4, gymWorkoutLogSetRepDtos.size());
        gymWorkoutLogSetRepDtos.forEach(r -> {
            assertEquals(10.0, r.getWeight());
            assertEquals(12, r.getRep());
        });
    }

    @Test
    void workoutLogNoteParser_BasicInput3() {
        //3 sets of 10(kg) with 12 (default) reps
        List<GymWorkoutLogSetRep_DTO> gymWorkoutLogSetRepDtos = GymUtils.workoutLogNoteParser("10:12-12-12");

        assertEquals(3, gymWorkoutLogSetRepDtos.size());
        gymWorkoutLogSetRepDtos.forEach(r -> {
            assertEquals(10.0, r.getWeight());
            assertEquals(12, r.getRep());
        });
    }

    @Test
    void parseRawInput_BasicReplacement() {
        assertEquals("7,7,7", GymUtils.parseRawInput("7%2C7%2C7"));
        assertEquals("7:10-10-10", GymUtils.parseRawInput("7%3A10-10-10"));
        assertEquals("6,7:10-10", GymUtils.parseRawInput("6%2C7%3A10-10"));
    }

    @Test
    void parseRawInput_EmptyInput() {
        assertEquals("", GymUtils.parseRawInput(""));
    }

    @Test
    void parseRawInput_NoReplacement(){
        assertEquals("6,7:10-10", GymUtils.parseRawInput("6,7:10-10"));
    }

    @Test
    void parseRawInput_MultipleReplacement(){
        assertEquals("6,7:10,8:8", GymUtils.parseRawInput("6,7:10,8:8"));
    }

    @Test
    void parseRawInput_NullInput(){
        assertNull(GymUtils.parseRawInput(null));
    }
}