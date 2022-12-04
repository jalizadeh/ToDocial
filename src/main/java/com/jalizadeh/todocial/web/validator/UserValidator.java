package com.jalizadeh.todocial.web.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jalizadeh.todocial.web.model.User;

public class UserValidator implements Validator {

	//private static final int MINIMUM_PASSWORD_LENGTH = 6;
	
    @Override
    public boolean supports(final Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object obj, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "message.firstname", "Firstname is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "message.lastname", "Lastname is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password", "Password is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "message.username", "UserName is required.");
        
        /*
         * done in PasswordValidator
         * 
        User login = (User) target;
        if (login.getPassword() != null
              && login.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
           errors.rejectValue("password", "field.min.length",
                 new Object[]{Integer.valueOf(MINIMUM_PASSWORD_LENGTH)},
                 "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
        }*/
    }

}
