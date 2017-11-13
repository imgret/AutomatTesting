package forecastDataProcessing;

import forecastData.ForecastData;
import forecastData.ForecastType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestForecastDataProcessor {

    private ForecastData mockedData;
    private ForecastDataProcessor forecastProcessing;

    @Before
    public void starter() throws IOException, JSONException {
        mockedData = mock(ForecastData.class);
        forecastProcessing = new ForecastDataProcessor(mockedData);
    }

    @Test
    public void testTextDataToJsonData() throws JSONException {
        when(mockedData.getTextData()).thenReturn("{\"coord\":{\"lon\":24.75,\"lat\":59.44}}");
        JSONObject expectedJsonData = forecastProcessing.getJsonData();
        JSONObject actualJsonData = new JSONObject("{\"coord\":{\"lon\":24.75,\"lat\":59.44}}");
        assertEquals(expectedJsonData.toString(), actualJsonData.toString());
    }

    @Test
    public void testGetLatitudeFromCurrentForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.CURRENT_WEATHER);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"coord\":{\"lat\":59.44}}"));
        String latitude = forecastProcessing.getWeatherForecastLocationLatitude();
        assertEquals("59.44", latitude);
    }

    @Test
    public void testGetLatitudeFromFiveDaysForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.FIVE_DAY_FORECAST);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"city\":{\"coord\":{\"lat\":48.8534}}}"));
        String latitude = forecastProcessing.getWeatherForecastLocationLatitude();
        assertEquals("48.8534", latitude);
    }

    @Test
    public void testGetLongitudeFromCurrentForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.CURRENT_WEATHER);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"coord\":{\"lon\":24.75}}"));
        String longitude = forecastProcessing.getWeatherForecastLocationLongitude();
        assertEquals("24.75", longitude);
    }

    @Test
    public void testGetLongitudeFromFiveDaysForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.FIVE_DAY_FORECAST);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"city\":{\"coord\":{\"lon\":2.3488}}}"));
        String longitude = forecastProcessing.getWeatherForecastLocationLongitude();
        assertEquals("2.3488", longitude);
    }

    @Test
    public void testGetForecastLocationCountryFromCurrentForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.CURRENT_WEATHER);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"sys\":{\"country\":\"EE\"}}"));
        String country = forecastProcessing.getWeatherForecastLocationCountry();
        assertEquals("EE", country);
    }

    @Test
    public void testGetForecastLocationCountryFromFiveDaysForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.FIVE_DAY_FORECAST);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"city\":{\"country\":\"FR\"}}"));
        String country = forecastProcessing.getWeatherForecastLocationCountry();
        assertEquals("FR", country);
    }

    @Test
    public void testGetForecastLocationTownFromCurrentForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.CURRENT_WEATHER);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"name\":\"Tallinn\"}"));
        String town = forecastProcessing.getWeatherForecastLocationTown();
        assertEquals("Tallinn", town);
    }

    @Test
    public void testGetForecastLocationTownFromFiveDaysForecast() throws JSONException {
        when(mockedData.getForecastType()).thenReturn(ForecastType.FIVE_DAY_FORECAST);
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"city\":{\"name\":\"Paris\"}}"));
        String town = forecastProcessing.getWeatherForecastLocationTown();
        assertEquals("Paris", town);
    }

    @Test
    public void testGetForecastDateFromCurrentForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"dt\":1510399200}"));
        String date = forecastProcessing.getWeatherForecastDate();
        assertEquals("11:20:00 11.11.2017", date);
    }

    @Test
    public void testGetForecastDatesFromFiveDaysForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject(
                "{\"list\":[{\"dt\":1510358400},{\"dt\":1510369200}]}"));
        List<String> actualDates = forecastProcessing.getFiveDaysForecastDates();
        List<String> expectedDates = new LinkedList<>(Arrays.asList("00:00:00 11.11.2017", "03:00:00 11.11.2017"));
        assertEquals(expectedDates, actualDates);
    }

    @Test
    public void testGetMaximumTemperatureFromCurrentForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"main\":{\"temp_max\":5}}"));
        String maxTemperature = forecastProcessing.getWeatherForecastMaximumTemperature();
        assertEquals("5", maxTemperature);
    }

    @Test
    public void testGetMaximumTemperatureFromFiveDaysForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject(
                "{\"list\":[{\"main\":{\"temp_max\":7.69}},{\"main\":{\"temp_max\":6.97}}]}"));
        List<String> actualMaxTemperatures = forecastProcessing.getFiveDaysMaximumTemperature();
        List<String> expectedMaxTemperatures = new LinkedList<>(Arrays.asList("7.69", "6.97"));
        assertEquals(expectedMaxTemperatures, actualMaxTemperatures);
    }

    @Test
    public void testGetMinimumTemperatureFromCurrentForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"main\":{\"temp_min\":5}}"));
        String minTemperature = forecastProcessing.getWeatherForecastMinimumTemperature();
        assertEquals("5", minTemperature);
    }

    @Test
    public void testGetMinimumTemperatureFromFiveDaysForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject(
                "{\"list\":[{\"main\":{\"temp_min\":7.44}},{\"main\":{\"temp_min\":6.78}}]}"));
        List<String> actualMinTemperatures = forecastProcessing.getFiveDaysMinimumTemperature();
        List<String> expectedMinTemperatures = new LinkedList<>(Arrays.asList("7.44", "6.78"));
        assertEquals(expectedMinTemperatures, actualMinTemperatures);
    }

    @Test
    public void testGetAverageTemperatureFromCurrentForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"main\":{\"temp\":5}}"));
        String averageTemperature = forecastProcessing.getWeatherForecastAverageTemperature();
        assertEquals("5", averageTemperature);
    }

    @Test
    public void testGetAverageTemperatureFromFiveDaysForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject(
                "{\"list\":[{\"main\":{\"temp\":7.44}},{\"main\":{\"temp\":6.78}}]}"));
        List<String> actualAverageTemperatures = forecastProcessing.getFiveDaysAverageTemperature();
        List<String> expectedAverageTemperatures = new LinkedList<>(Arrays.asList("7.44", "6.78"));
        assertEquals(expectedAverageTemperatures, actualAverageTemperatures);
    }

    @Test
    public void testGetCloudinessFromCurrentForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"clouds\":{\"all\":90}}"));
        String cloudiness = forecastProcessing.getWeatherForecastCloudiness();
        assertEquals("90", cloudiness);
    }

    @Test
    public void testGetCloudinessFromFiveDaysForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject(
                "{\"list\":[{\"clouds\":{\"all\":88}},{\"clouds\":{\"all\":80}}]}"));
        List<String> actualCloudiness = forecastProcessing.getFiveDaysCloudiness();
        List<String> expectedCloudiness = new LinkedList<>(Arrays.asList("88", "80"));
        assertEquals(expectedCloudiness, actualCloudiness);
    }

    @Test
    public void testGetWindDirectionFromCurrentForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"wind\":{\"deg\":160}}"));
        String windDirection = forecastProcessing.getWeatherForecastWindDirection();
        assertEquals("160", windDirection);
    }

    @Test
    public void testGetWindDirectionFromFiveDaysForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject(
                "{\"list\":[{\"wind\":{\"deg\":271.502}},{\"wind\":{\"deg\":252}}]}"));
        List<String> actualWindDirection = forecastProcessing.getFiveDaysWindDirections();
        List<String> expectedWindDirection = new LinkedList<>(Arrays.asList("271.502", "252"));
        assertEquals(expectedWindDirection, actualWindDirection);
    }

    @Test
    public void testGetHumidityFromCurrentForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject("{\"main\":{\"humidity\":93}}"));
        String humidity = forecastProcessing.getWeatherForecastHumidity();
        assertEquals("93", humidity);
    }

    @Test
    public void testGetHumidityFromFiveDaysForecast() throws JSONException {
        when(mockedData.getJsonData()).thenReturn(new JSONObject(
                "{\"list\":[{\"main\":{\"humidity\":96}},{\"main\":{\"humidity\":96}}]}"));
        List<String> actualHumidity = forecastProcessing.getFiveDaysHumidity();
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
