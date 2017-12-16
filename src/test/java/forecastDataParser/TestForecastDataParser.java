package forecastDataParser;

import forecastData.ForecastType;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestForecastDataParser {

    private ForecastDataParser forecastDataParser;

    @Before
    public void starter() throws IOException, JSONException {
    }

    @Test
    public void testGetLatitudeFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"coord\":{\"lat\":59.44}}");
        String latitude = forecastDataParser.getWeatherForecastLocationLatitude();
        assertEquals("59.44", latitude);
    }

    @Test
    public void testGetLatitudeFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"city\":{\"coord\":{\"lat\":48.8534}}}");
        String latitude = forecastDataParser.getWeatherForecastLocationLatitude();
        assertEquals("48.8534", latitude);
    }

    @Test
    public void testGetLongitudeFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"coord\":{\"lon\":24.75}}");
        String longitude = forecastDataParser.getWeatherForecastLocationLongitude();
        assertEquals("24.75", longitude);
    }

    @Test
    public void testGetLongitudeFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"city\":{\"coord\":{\"lon\":2.3488}}}");
        String longitude = forecastDataParser.getWeatherForecastLocationLongitude();
        assertEquals("2.3488", longitude);
    }

    @Test
    public void testGettingGEOCoordinatesInCorrectForm() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"city\":{\"coord\":{\"lon\":2.3488,\"lat\":48.8534}}}");
        String geoCoordinates = forecastDataParser.getWeatherForecastGEOTypeCoordinates();
        assertEquals("48.8534:2.3488", geoCoordinates);
    }

    @Test
    public void testGetForecastLocationCountryFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"sys\":{\"country\":\"EE\"}}");
        String country = forecastDataParser.getWeatherForecastLocationCountry();
        assertEquals("EE", country);
    }

    @Test
    public void testGetForecastLocationCountryFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"city\":{\"country\":\"FR\"}}");
        String country = forecastDataParser.getWeatherForecastLocationCountry();
        assertEquals("FR", country);
    }

    @Test
    public void testGetForecastLocationTownFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"name\":\"Tallinn\"}");
        String town = forecastDataParser.getWeatherForecastLocationTown();
        assertEquals("Tallinn", town);
    }

    @Test
    public void testGetForecastLocationTownFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"city\":{\"name\":\"Paris\"}}");
        String town = forecastDataParser.getWeatherForecastLocationTown();
        assertEquals("Paris", town);
    }

    @Test
    public void testGetForecastDateFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"dt\":1510399200}");
        String date = forecastDataParser.getCurrentForecastDate();
        assertEquals("11:20:00 11.11.2017", date);
    }

    @Test
    public void testGetForecastDatesFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"dt\":1510358400},{\"dt\":1510369200}]}");
        List<String> actualDates = forecastDataParser.getFiveDaysForecastDates();
        List<String> expectedDates = new LinkedList<>(Arrays.asList("00:00:00 11.11.2017", "03:00:00 11.11.2017"));
        assertEquals(expectedDates, actualDates);
    }

    @Test
    public void testGetForecastDatesForThreeDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"dt\":1510358400},{\"dt\":1510369200},{\"dt\":1510369200}]}");
        List<String> actualDates = forecastDataParser.getThreeDaysForecastDates();
        List<String> expectedDates = new LinkedList<>(Arrays.asList("00:00:00 11.11.2017", "03:00:00 11.11.2017", "03:00:00 11.11.2017"));
        assertEquals(expectedDates, actualDates);
    }

    @Test
    public void testGetMaximumTemperatureFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"main\":{\"temp_max\":5}}");
        String maxTemperature = forecastDataParser.getCurrentForecastMaximumTemperature();
        assertEquals("5", maxTemperature);
    }

    @Test
    public void testGetMaximumTemperatureFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"main\":{\"temp_max\":7.69}},{\"main\":{\"temp_max\":6.97}}]}");
        List<String> actualMaxTemperatures = forecastDataParser.getFiveDaysMaximumTemperatures();
        List<String> expectedMaxTemperatures = new LinkedList<>(Arrays.asList("7.69", "6.97"));
        assertEquals(expectedMaxTemperatures, actualMaxTemperatures);
    }

    @Test
    public void testGetMaximumTemperatureForThreeDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"main\":{\"temp_max\":7.69}},{\"main\":{\"temp_max\":6.97}},{\"main\":{\"temp_max\":7.44}}]}");
        List<String> actualMaxTemperatures = forecastDataParser.getThreeDaysMaximumTemperatures();
        List<String> expectedMaxTemperatures = new LinkedList<>(Arrays.asList("7.69", "6.97", "7.44"));
        assertEquals(expectedMaxTemperatures, actualMaxTemperatures);
    }

    @Test
    public void testGetMinimumTemperatureFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"main\":{\"temp_min\":5}}");
        String minTemperature = forecastDataParser.getCurrentForecastMinimumTemperature();
        assertEquals("5", minTemperature);
    }

    @Test
    public void testGetMinimumTemperatureFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"main\":{\"temp_min\":7.44}},{\"main\":{\"temp_min\":6.78}}]}");
        List<String> actualMinTemperatures = forecastDataParser.getFiveDaysMinimumTemperatures();
        List<String> expectedMinTemperatures = new LinkedList<>(Arrays.asList("7.44", "6.78"));
        assertEquals(expectedMinTemperatures, actualMinTemperatures);
    }

    @Test
    public void testGetMinimumTemperatureForThreeDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"main\":{\"temp_min\":7.44}},{\"main\":{\"temp_min\":6.78}},{\"main\":{\"temp_min\":6.97}}]}");
        List<String> actualMinTemperatures = forecastDataParser.getThreeDaysMinimumTemperatures();
        List<String> expectedMinTemperatures = new LinkedList<>(Arrays.asList("7.44", "6.78", "6.97"));
        assertEquals(expectedMinTemperatures, actualMinTemperatures);
    }

    @Test
    public void testGetAverageTemperatureFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"main\":{\"temp\":5}}");
        String averageTemperature = forecastDataParser.getCurrentForecastAverageTemperature();
        assertEquals("5", averageTemperature);
    }

    @Test
    public void testGetAverageTemperatureFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"main\":{\"temp\":7.44}},{\"main\":{\"temp\":6.78}}]}");
        List<String> actualAverageTemperatures = forecastDataParser.getFiveDaysAverageTemperatures();
        List<String> expectedAverageTemperatures = new LinkedList<>(Arrays.asList("7.44", "6.78"));
        assertEquals(expectedAverageTemperatures, actualAverageTemperatures);
    }

    @Test
    public void testGetCloudinessFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"clouds\":{\"all\":90}}");
        String cloudiness = forecastDataParser.getCurrentForecastCloudiness();
        assertEquals("90", cloudiness);
    }

    @Test
    public void testGetCloudinessFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"clouds\":{\"all\":88}},{\"clouds\":{\"all\":80}}]}");
        List<String> actualCloudiness = forecastDataParser.getFiveDaysCloudiness();
        List<String> expectedCloudiness = new LinkedList<>(Arrays.asList("88", "80"));
        assertEquals(expectedCloudiness, actualCloudiness);
    }

    @Test
    public void testGetWindDirectionFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"wind\":{\"deg\":160}}");
        String windDirection = forecastDataParser.getCurrentForecastWindDirection();
        assertEquals("160", windDirection);
    }

    @Test
    public void testGetWindDirectionFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"wind\":{\"deg\":271.502}},{\"wind\":{\"deg\":252}}]}");
        List<String> actualWindDirection = forecastDataParser.getFiveDaysWindDirections();
        List<String> expectedWindDirection = new LinkedList<>(Arrays.asList("271.502", "252"));
        assertEquals(expectedWindDirection, actualWindDirection);
    }

    @Test
    public void testGetHumidityFromCurrentForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.CURRENT_WEATHER,
                "{\"main\":{\"humidity\":93}}");
        String humidity = forecastDataParser.getCurrentForecastHumidity();
        assertEquals("93", humidity);
    }

    @Test
    public void testGetHumidityFromFiveDaysForecast() throws JSONException {
        forecastDataParser = new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST,
                "{\"list\":[{\"main\":{\"humidity\":96}},{\"main\":{\"humidity\":96}}]}");
        List<String> actualHumidity = forecastDataParser.getFiveDaysHumidity();
        List<String> expectedHumidity = new LinkedList<>(Arrays.asList("96", "96"));
        assertEquals(expectedHumidity, actualHumidity);
    }

    /*
    "{\"list\":[" +
    "{\"dt\":1510358400," +
    "\"main\":{\"temp\":7.44,\"temp_min\":7.44,\"temp_max\":7.69,\"pressure\":1024.05,\"sea_level\":1036.44,\"grnd_level\":1024.05,\"humidity\":96,\"temp_kf\":-0.25}," +
    "\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}]," +
    "\"clouds\":{\"all\":88}," +
    "\"wind\":{\"speed\":3.31,\"deg\":271.502}," +
    "\"rain\":{\"3h\":0.1675}," +
    "\"sys\":{\"pod\":\"n\"}," +
    "\"dt_txt\":\"2017-11-11 00:00:00\"}," +
    "{\"dt\":1510369200," +
    "\"main\":{\"temp\":6.78,\"temp_min\":6.78,\"temp_max\":6.97,\"pressure\":1022.5,\"sea_level\":1034.78,\"grnd_level\":1022.5,\"humidity\":96,\"temp_kf\":-0.19}," +
    "\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}]," +
    "\"clouds\":{\"all\":80}," +
    "\"wind\":{\"speed\":3.87,\"deg\":252}," +
    "\"rain\":{\"3h\":0.115}," +
    "\"sys\":{\"pod\":\"n\"}," +
    "\"dt_txt\":\"2017-11-11 03:00:00\"}}]}"
    */
}
