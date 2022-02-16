package hill.postcodecrimechecker.service;

import hill.postcodecrimechecker.Coords;
import hill.postcodecrimechecker.service.crimeslocation.CrimesLocationResponse;
import hill.postcodecrimechecker.service.crimeslocation.CrimesLocationService;
import hill.postcodecrimechecker.service.postcode.PostcodeService;
import hill.postcodecrimechecker.validation.DateValidationException;
import hill.postcodecrimechecker.validation.PostcodeValidationException;
import org.easymock.EasyMockExtension;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(EasyMockExtension.class)
class CrimeStatisticsServiceTest extends EasyMockSupport {

    @Mock
    private PostcodeService mockPostcodeService;

    @Mock
    private CrimesLocationService mockCrimesLocationService;

    private CrimeStatisticsService testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new CrimeStatisticsService(mockPostcodeService, mockCrimesLocationService);
    }

    @AfterEach
    void tearDown() {
        verifyAll();
    }

    @Test
    public void testInvalidPostcode() {

        replayAll();

        assertThrows(PostcodeValidationException.class, () -> testSubject.getCrimeStatistics("", "2022-01"));
    }

    @Test
    public void testInvalidDate() {

        replayAll();

        assertThrows(DateValidationException.class, () -> testSubject.getCrimeStatistics("M14JR", "2022"));
    }

    @Test
    public void testSuccess() throws PostcodeValidationException, DateValidationException {

        var postcode = "M14JR";
        var date = "2022-01";

        var coords = new Coords(15, 30);
        expect(mockPostcodeService.getCoordsForPostcode(same(postcode))).andReturn(coords);

        var expectedResponse = new CrimesLocationResponse[1];
        expect(mockCrimesLocationService.getCrimesAtLocations(same(date), eq(coords.getLatitude()), eq(coords.getLongitude())))
                .andReturn(expectedResponse);

        replayAll();

        var actualResponse = testSubject.getCrimeStatistics(postcode, date);

        assertSame(expectedResponse, actualResponse);
    }
}