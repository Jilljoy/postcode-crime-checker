package hill.postcodecrimechecker.validation;

import java.util.regex.Pattern;

public class DateValidator {

    /**
     * Regex pattern to check whether a date is in the correct format YYYY-MM.
     */
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}");

    public static boolean isValidDate(String postcode) {

        if (postcode == null || postcode.isBlank()) {
            return false;
        }

        return DATE_PATTERN.matcher(postcode).find();
    }
}
