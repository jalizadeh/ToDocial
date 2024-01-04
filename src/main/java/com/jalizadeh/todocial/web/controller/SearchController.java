package com.jalizadeh.todocial.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class SearchController {

    @GetMapping("/search")
    public void search(ModelMap model, @RequestParam Map<String, String> queryParams){
        StringBuilder result = new StringBuilder("Search parameters:\n");

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
    }

}
