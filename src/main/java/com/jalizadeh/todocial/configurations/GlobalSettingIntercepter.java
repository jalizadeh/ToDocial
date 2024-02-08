package com.jalizadeh.todocial.configurations;

import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GlobalSettingIntercepter extends HandlerInterceptorAdapter {

    @Autowired
    private SettingsGeneralConfig settings;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        modelAndView.addObject("settings", settings);
    }

}
