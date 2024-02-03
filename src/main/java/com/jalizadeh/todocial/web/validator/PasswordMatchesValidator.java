package com.jalizadeh.todocial.web.validator;

import com.jalizadeh.todocial.web.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final User user = (User) obj;
        //System.err.println("Passwords: "+user.getPassword()+"/"+user.getMp());
        return user.getPassword().equals(user.getMp());
    }

}