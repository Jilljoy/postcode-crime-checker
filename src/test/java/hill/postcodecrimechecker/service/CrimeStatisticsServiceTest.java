package hill.postcodecrimechecker.service;

import hill.postcodecrimechecker.Coords;
import hill.postcodecrimechecker.service.crimeslocation.CrimesLocationLocationData;
import hill.postcodecrimechecker.service.crimeslocation.CrimesLocationResponse;
import hill.postcodecrimechecker.service.crimeslocation.CrimesLocationService;
import hill.postcodecrimechecker.service.postcode.PostcodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CrimeStatisticsServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrimesLocationService mockCrimesLocationService;

    @MockBean
    private PostcodeService mockPostcodeService;

    @Test
    void testSuccessfulCall() throws Exception {

        var coords = new Coords(15, 30);
        var postcode = "DN227AF";
        var date = "2022-02";

        Mockito.when(mockPostcodeService.getCoordsForPostcode(postcode)).thenReturn(coords);

        var crimesLocationResponses = new CrimesLocationResponse[1];
        crimesLocationResponses[0] = new CrimesLocationResponse();
        crimesLocationResponses[0].setCategory("aCategory");
        crimesLocationResponses[0].setId("anId");
        crimesLocationResponses[0].setPersistentId("somePersistentId");
        var crimesLocationLocationData = new CrimesLocationLocationData();
        crimesLocationLocationData.setLatitude(15.0);
        crimesLocationLocationData.setLongitude(30.0);
        crimesLocationResponses[0].setLocation(crimesLocationLocationData);

        Mockito.when(mockCrimesLocationService.getCrimesAtLocations(date, 15, 30)).thenReturn(crimesLocationResponses);

        mockMvc.perform(get("/crimeStatistics")
                        .queryParam("date", date)
                        .queryParam("postcode", postcode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].category").value("aCategory"))
                .andExpect(jsonPath("$[0].id").value("anId"))
                .andExpect(jsonPath("$[0].persistent_id").value("somePersistentId"))
                .andExpect(jsonPath("$[0].location.latitude").value(15.0))
                .andExpect(jsonPath("$[0].location.longitude").value(30.0));

        Mockito.verify(mockPostcodeService, Mockito.times(1)).getCoordsForPostcode(postcode);
        Mockito.verify(mockCrimesLocationService, Mockito.times(1)).getCrimesAtLocations(date, 15, 30);
    }

    @Test
    void testInvalidPostcode() throws Exception {

        var invalidPostcode = "!@!";
        var date = "2022-02";

        mockMvc.perform(get("/crimeStatistics")
                        .queryParam("date", date)
                        .queryParam("postcode", invalidPostcode))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Provided postcode is invalid."));
    }

    @Test
    void testInvalidDate() throws Exception {

        var postcode = "M16TH";
        var invalidDate = "2022/02";

        mockMvc.perform(get("/crimeStatistics")
                        .queryParam("date", invalidDate)
                        .queryParam("postcode", postcode))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Provided date is invalid."));

    }
}