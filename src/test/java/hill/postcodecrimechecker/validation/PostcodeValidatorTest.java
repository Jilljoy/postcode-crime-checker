package hill.postcodecrimechecker.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostcodeValidatorTest {

    @Test
    void testNullPostcode() {

        var response = PostcodeValidator.isValidPostcode(null);

        assertFalse(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void testBlankPostcode(String postcodeToValidate) {

        var response = PostcodeValidator.isValidPostcode(postcodeToValidate);

        assertFalse(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678", "DN111111", "@E3 1EP", "#####"})
    void testInvalidPostcode(String postcodeToValidate) {

        var response = PostcodeValidator.isValidPostcode(postcodeToValidate);

        assertFalse(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A1 2BC", "A12 3BC", "AB1 2CD"})
    void testValidPostcode(String postcodeToValidate) {

        var response = PostcodeValidator.isValidPostcode(postcodeToValidate);

        assertTrue(response);
    }
}