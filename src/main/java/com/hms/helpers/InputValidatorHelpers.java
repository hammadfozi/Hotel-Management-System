package com.hms.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidatorHelpers {

    /**
     * @return if request did not container SQL-Injection attacks
     */
    public static boolean isHtmlSafe(String check) {
        if (check == null) return true;

        Pattern pattern = Pattern.compile("[a-zA-Z0-9_]*");
        Matcher matcher = pattern.matcher(check);
        return !matcher.matches();
    }
}
