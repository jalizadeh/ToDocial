package com.jalizadeh.todocial.utils;

import org.owasp.encoder.Encode;

public class DataUtils {

    public static String sanitizeQuery(String query) {
        // Use OWASP Java Encoder to HTML encode the user input
        return Encode.forHtml(query);
    }
}
