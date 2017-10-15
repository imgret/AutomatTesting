package weatherForecastDataProcessing;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestWeatherForecastDataProcessing {

    private WeatherForecastDataProcessing forecastProcessing;

    @Before
    public void init() throws IOException, JSONException {
        forecastProcessing = new WeatherForecastDataProcessing("forecastData/test/currentTest.json");
    }

    @Test
    public void testWeatherForecastFileExtension(){
        String fileExtension = forecastProcessing.getWeatherForecastFileExtension();
        assertEquals("json", fileExtension);
    }

    @Test
    public void testContentOfWeatherForecastFile() throws IOException, JSONException {
        forecastProcessing = new WeatherForecastDataProcessing("forecastData/test/contentTest.json");
        String fileContent = forecastProcessing.getWeatherForecastFileContent();
        assertEquals("{\"cod\":\"400\",\"message\":\"Nothing to geocode\"}", fileContent);
    }

    @Test
    public void testWeatherForecastMaximumTemperature() throws JSONException {
        String maximumTemperature = forecastProcessing.getWeatherForecastMaximumTemperature();
        assertEquals("15", maximumTemperature);
    }

    @Test
    public void testWeatherForecastMinimumTemperature() throws JSONException {
        String minimumTemperature = forecastProcessing.getWeatherForecastMinimumTemperature();
        assertEquals("10", minimumTemperature);
    }

    @Test
    public void testFiveDaysTemperature() throws JSONException, IOException {
        forecastProcessing.setFileName("forecastData/test/5daysTest.json");
        List<String> temperaturesForFiveDays = forecastProcessing.getFiveDaysTemperature();
        String[] expectedTemperaturesArray = {"8.69", "8.11", "8.19", "8.82", "10.52", "12.38", "12.87", "12.81", "12.46", "11.19", "9.53", "8.68", "8.88", "11.32", "11.75", "9.81", "12.05", "13.55", "12.22", "11.62", "11.16", "11.71", "11.36", "10.72", "8.77", "6.93", "5.31", "4.33", "4.05", "7.94", "9.12", "8.61", "8.15", "8.76", "9.77", "10.07", "10.05", "11", "12.07", "11.68"};
        List<String> expectedTemperaturesList = Arrays.asList(expectedTemperaturesArray);
        assertEquals(expectedTemperaturesList, temperaturesForFiveDays);
    }
}
