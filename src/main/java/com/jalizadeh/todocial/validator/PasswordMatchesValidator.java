package com.jalizadeh.todocial.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jalizadeh.todocial.model.User;

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