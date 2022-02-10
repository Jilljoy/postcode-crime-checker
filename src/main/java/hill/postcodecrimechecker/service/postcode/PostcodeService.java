package hill.postcodecrimechecker.service.postcode;


import hill.postcodecrimechecker.Coords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@Component
public class PostcodeService {

    private static final Logger LOG = LoggerFactory.getLogger(PostcodeService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${url.postcode.postcodes}")
    private String postcodeServiceUrl;

    private PostcodeResponseData lookupPostcode(String postcode) {

        var uri = UriComponentsBuilder.fromHttpUrl(postcodeServiceUrl).build(postcode);

        LOG.info("Calling: {}", uri);
        try {
            return restTemplate.getForObject(uri, PostcodeResponseData.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @Cacheable(value = "postcodeLookups", key = "#postcode")
    public Coords getCoordsForPostcode(String postcode) {

        return Optional.of(lookupPostcode(postcode))
                .map(PostcodeResponseData::getResult)
                .filter(postcodeResultData -> postcodeResultData.getLongitude() != null && postcodeResultData.getLatitude() != null)
                .map(postcodeResultData -> new Coords(postcodeResultData.getLatitude(), postcodeResultData.getLongitude()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Could not retrieve coordinate information for postcode %S", postcode)));
    }

}
