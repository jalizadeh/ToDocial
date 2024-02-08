package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.service.impl.GymService;
import com.jalizadeh.todocial.service.impl.TodoService;
import com.jalizadeh.todocial.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PublicPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private GymService gymService;

    @Autowired
    private SettingsGeneralConfig settings;

    /**
     * If user is authenticated, he will be redirected to his public page accessing all info (even private),
     * but if it is an anonymous user, can access the public area of a registered user's page
     */
    @GetMapping("/@{username}")
    public String showPublicPage(ModelMap model, @PathVariable String username,
                                 RedirectAttributes redirectAttributes) {
        model.put("settings", settings);

        User currentUser = userService.GetAuthenticatedUser();
        User targetUser = userService.findByUsername(username);

        //if there is no mathcing account
        if (targetUser == null) {
            redirectAttributes.addFlashAttribute("exception","There is no account matching '" + username + "'");
            return "redirect:/error";
        }

        //if the user is anonymous
        if (userService.isUserAnonymous()) {
            model.put("user", targetUser);
            model.put("todos", todoService.findAllTodosByUserIdAndIsPublicTrue(targetUser.getId()));
            model.put("gym", gymService.findAllPlansByUserIdAndIsPublicTrue(targetUser.getId()));
            model.put("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + "(" + targetUser.getUsername() + ")");
            return "public-page";
        }


        User loggedinUser  =  userService.findByUsername(currentUser.getUsername());

        //the user is logged in and is checking another profile
        //but first check if current user is following the target or not
        if(!currentUser.getUsername().equals(username)) {
            List<String> listOfFollowings = loggedinUser.getFollowings().stream()
                    .map(u -> u.getUsername())
                    .collect(Collectors.toList());

            for (String following : listOfFollowings) {
                if(following.equals(targetUser.getUsername())) {
                    model.put("isfollowing", true);
                    break;
                }else {
                    model.put("isfollowing", false);
                }
            }


            model.put("user", targetUser);
            model.put("todos", todoService.findAllTodosByUserIdAndIsPublicTrue(targetUser.getId()));
            model.put("gym", gymService.findAllPlansByUserIdAndIsPublicTrue(targetUser.getId()));
            model.put("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + "(" + targetUser.getUsername() + ")");
            return "public-page";
        }


        model.put("user", loggedinUser);
        model.put("todos", todoService.findAllByUser(targetUser));
        model.put("gym", gymService.findAllPlansByUserId(targetUser.getId()));
        model.put("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + "(" + loggedinUser.getUsername() + ")");
        return "public-page";
    }

}
