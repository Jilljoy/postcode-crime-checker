package hill.postcodecrimechecker.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

public class PostcodeValidator {

    /**
     * Regex pattern to check whether a postcode is valid.
     */
    private static final Pattern POSTCODE_PATTERN = Pattern.compile("^[A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}$");

    public static boolean isValidPostcode(String postcode) {

        if (postcode == null || postcode.isBlank()) {
            return false;
        }

        return POSTCODE_PATTERN.matcher(postcode).find();
    }
}
