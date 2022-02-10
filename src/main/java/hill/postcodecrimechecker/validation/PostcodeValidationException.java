package hill.postcodecrimechecker.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided postcode is invalid.")
public class PostcodeValidationException extends Exception {
}
