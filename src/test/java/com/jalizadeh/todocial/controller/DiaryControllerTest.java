package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.controller.DiaryController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Diary controller tests")
@Tag("unit_test")
class DiaryControllerTest {

    @Autowired
    DiaryController diaryController;

    @Test
    void showDiary() {
        String viewName = diaryController.showDiary();
        assertEquals("diary/diary", viewName);
    }
}