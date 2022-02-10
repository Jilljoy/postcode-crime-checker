package hill.postcodecrimechecker.service;

import hill.postcodecrimechecker.Coords;
import hill.postcodecrimechecker.service.crimeslocation.CrimesLocationResponse;
import hill.postcodecrimechecker.service.crimeslocation.CrimesLocationService;
import hill.postcodecrimechecker.service.postcode.PostcodeService;
import hill.postcodecrimechecker.validation.DateValidationException;
import hill.postcodecrimechecker.validation.DateValidator;
import hill.postcodecrimechecker.validation.PostcodeValidationException;
import hill.postcodecrimechecker.validation.PostcodeValidator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crimeStatistics")
public class CrimeStatisticsService {

    private final PostcodeService postcodeService;

    private final CrimesLocationService crimesLocationService;

    public CrimeStatisticsService(PostcodeService postcodeService, CrimesLocationService crimesLocationService) {
        this.postcodeService = postcodeService;
        this.crimesLocationService = crimesLocationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CrimesLocationResponse[] getCrimeStatistics(@RequestParam("postcode") String postcode, @RequestParam("date") String date) throws PostcodeValidationException, DateValidationException {

        if (!PostcodeValidator.isValidPostcode(postcode)) {
            throw new PostcodeValidationException();
        }

        if (!DateValidator.isValidDate(date)) {
            throw new DateValidationException();
        }

        final var coords = postcodeService.getCoordsForPostcode(postcode);

        return crimesLocationService.getCrimesAtLocations(date, coords.getLatitude(), coords.getLongitude());
    }
}
