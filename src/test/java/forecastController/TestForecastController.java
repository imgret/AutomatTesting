package forecastController;

import forecastData.ForecastData;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestForecastController {

    private ForecastController forecastController;

    @Before
    public void starter() {
        forecastController = new ForecastController();
    }

    @Test
    public void testGettingCorrectMinimalAndMaximalTemperaturesForEachDayOfGivenForecast() throws JSONException {
        ForecastData mockedData = mock(ForecastData.class);
        when(mockedData.getFiveDaysForecastDates()).thenReturn(Arrays.asList(
                "00:00:00 31.12.2017", "03:00:00 31.12.2017", "06:00:00 31.12.2017", "09:00:00 31.12.2017",
                "12:00:00 31.12.2017", "15:00:00 31.12.2017", "18:00:00 31.12.2017", "21:00:00 31.12.2017",
                "00:00:00 32.12.2017", "03:00:00 32.12.2017", "06:00:00 32.12.2017", "09:00:00 32.12.2017",
                "12:00:00 32.12.2017", "15:00:00 32.12.2017", "18:00:00 32.12.2017", "21:00:00 32.12.2017"));
        when(mockedData.getFiveDaysMinimumTemperatures()).thenReturn(Arrays.asList(
                "3", "2", "1", "0", "0.1567", "-0.01", "-3.8999999", "-2",
                "-0.0", "-0.1", "2.6", "5.89", "6", "4.5", "2.2222", "-1.09"
        ));
        when(mockedData.getFiveDaysMaximumTemperatures()).thenReturn(Arrays.asList(
                "3", "2", "1", "0", "0.1567", "-0.01", "-3.8999999", "-2",
                "-0.0", "-0.1", "2.6", "5.89", "5", "4.5", "2.2222", "-1.09"
        ));
        List<String> actualMinAndMaxTemperatures = forecastController.getMinAndMaxTemperaturesForEachDayOfForecast(mockedData);
        List<String> expectedMinAndMaxTemperatures = Arrays.asList(
                "Minimum 31.12.2017 temperature: -3.90\nMaximum 31.12.2017 temperature: 3.00\n\n",
                "Minimum 32.12.2017 temperature: -1.09\nMaximum 32.12.2017 temperature: 5.89\n\n");
        assertEquals(expectedMinAndMaxTemperatures, actualMinAndMaxTemperatures);
    }

    @Test
    public void testGettingCorrectGEOCoordinates() throws JSONException {
        ForecastData mockedData = mock(ForecastData.class);
        when(mockedData.getLocationLatitude()).thenReturn("1.5678");
        when(mockedData.getLocationLongitude()).thenReturn("-30.00");
        String actualGEOCoordinates = forecastController.getGEOTypeCoordinates(mockedData);
        String expectedGEOCoordinates = "1.5678:-30.00";
        assertEquals(expectedGEOCoordinates, actualGEOCoordinates);
    }
}
