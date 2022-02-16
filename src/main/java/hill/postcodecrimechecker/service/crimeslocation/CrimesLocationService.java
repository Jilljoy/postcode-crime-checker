package hill.postcodecrimechecker.service.crimeslocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class CrimesLocationService {

    private static final Logger LOG = LoggerFactory.getLogger(CrimesLocationService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${url.police.api.crimesatlocation}")
    private String crimesLocationsUrl;

    public CrimesLocationResponse[] getCrimesAtLocations(String date, double lat, double lng) {

        var uri = UriComponentsBuilder.fromHttpUrl(crimesLocationsUrl)
                .queryParam("date", date)
                .queryParam("lat", lat)
                .queryParam("lng", lng)
                .build();

        LOG.info("Calling: {}", uri);
        try {
            return restTemplate.getForObject(uri.toUri(), CrimesLocationResponse[].class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}
