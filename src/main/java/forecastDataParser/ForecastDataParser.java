package forecastDataParser;

import forecastData.ForecastType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class ForecastDataParser {

    private ForecastType forecastType;
    private JSONObject forecastJsonData;

    public ForecastDataParser(ForecastType forecastType, String forecastTextData) throws JSONException {
        this.forecastType = forecastType;
        this.forecastJsonData = new JSONObject(forecastTextData);
    }

    public void setForecastJsonData(JSONObject forecastJsonData) {
        this.forecastJsonData = forecastJsonData;
    }

    public void setForecastType(ForecastType forecastType) {
        this.forecastType = forecastType;
    }

    public JSONObject getJsonData() throws JSONException {
        return forecastJsonData;
    }

    public String getWeatherForecastLocationLatitude() throws JSONException {
        if (forecastType == ForecastType.CURRENT_WEATHER) {
            JSONObject coordinatesFromJson = forecastJsonData.getJSONObject("coord");
            String latitude = coordinatesFromJson.getString("lat");
            return latitude;
        } else {
            JSONObject coordinatesFromJson = forecastJsonData.getJSONObject("city").getJSONObject("coord");
            String latitude = coordinatesFromJson.getString("lat");
            return latitude;
        }
    }

    public String getWeatherForecastLocationLongitude() throws JSONException {
        if (forecastType == ForecastType.CURRENT_WEATHER) {
            JSONObject coordinatesFromJson = forecastJsonData.getJSONObject("coord");
            String longitude = coordinatesFromJson.getString("lon");
            return longitude;
        } else {
            JSONObject coordinatesFromJson = forecastJsonData.getJSONObject("city").getJSONObject("coord");
            String longitude = coordinatesFromJson.getString("lon");
            return longitude;
        }
    }

    public String getWeatherForecastGEOTypeCoordinates() throws JSONException {
        String latitude = getWeatherForecastLocationLatitude();
        String longitude = getWeatherForecastLocationLongitude();

        return String.format("%s:%s", latitude, longitude);
    }

    public String getWeatherForecastLocationCountry() throws JSONException {
        if (forecastType == ForecastType.CURRENT_WEATHER) {
            JSONObject countryFromJson = forecastJsonData.getJSONObject("sys");
            String country = countryFromJson.getString("country");
            return country;
        } else {
            JSONObject countryFromJson = forecastJsonData.getJSONObject("city");
            String country = countryFromJson.getString("country");
            return country;
        }
    }

    public String getWeatherForecastLocationTown() throws JSONException {
        if (forecastType == ForecastType.CURRENT_WEATHER) {
            String town = forecastJsonData.getString("name");
            return town;
        } else {
            JSONObject townFromJson = forecastJsonData.getJSONObject("city");
            String town = townFromJson.getString("name");
            return town;
        }
    }

    private String formatDateFromSeconds(String dateInSeconds) {
        Date date = new Date(Long.valueOf(dateInSeconds) * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public String getCurrentForecastDate() throws JSONException {
        String dateInSeconds = forecastJsonData.getString("dt");
        String formattedDate = formatDateFromSeconds(dateInSeconds);
        return formattedDate;
    }

    public List<String> getFiveDaysForecastDates() throws JSONException {
        List<String> forecastDatesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            String forecastDate = listOfForecastsForFiveDays.getJSONObject(i).getString("dt");
            forecastDatesForFiveDays.add(formatDateFromSeconds(forecastDate));
        }
        return forecastDatesForFiveDays;
    }

    public List<String> getThreeDaysForecastDates() throws JSONException {
        List<String> forecastDatesForThreeDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        int elementsAmount = listOfForecastsForFiveDays.length();
        if (elementsAmount > 24) {
            elementsAmount = 24;
        }
        for (int i = 0; i < elementsAmount; i++) {
            String forecastDate = listOfForecastsForFiveDays.getJSONObject(i).getString("dt");
            forecastDatesForThreeDays.add(formatDateFromSeconds(forecastDate));
        }
        return forecastDatesForThreeDays;
    }

    public String getCurrentForecastMaximumTemperature() throws JSONException {
        JSONObject mainFromJson = forecastJsonData.getJSONObject("main");
        String maxTemperature = mainFromJson.getString("temp_max");
        return maxTemperature;
    }

    public List<String> getFiveDaysMaximumTemperatures() throws JSONException {
        List<String> maxTemperaturesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            maxTemperaturesForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp_max"));
        }
        return maxTemperaturesForFiveDays;
    }

    public List<String> getThreeDaysMaximumTemperatures() throws JSONException {
        List<String> maxTemperaturesForThreeDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        int elementsAmount = listOfForecastsForFiveDays.length();
        if (elementsAmount > 24) {
            elementsAmount = 24;
        }
        for (int i = 0; i < elementsAmount; i++) {
            maxTemperaturesForThreeDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp_max"));
        }
        return maxTemperaturesForThreeDays;
    }

    public String getCurrentForecastMinimumTemperature() throws JSONException {
        JSONObject mainFromJson = forecastJsonData.getJSONObject("main");
        String minTemperature = mainFromJson.getString("temp_min");
        return minTemperature;
    }

    public List<String> getFiveDaysMinimumTemperatures() throws JSONException {
        List<String> minTemperaturesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            minTemperaturesForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp_min"));
        }
        return minTemperaturesForFiveDays;
    }

    public List<String> getThreeDaysMinimumTemperatures() throws JSONException {
        List<String> minTemperaturesForThreeDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        int elementsAmount = listOfForecastsForFiveDays.length();
        if (elementsAmount > 24) {
            elementsAmount = 24;
        }
        for (int i = 0; i < elementsAmount; i++) {
            minTemperaturesForThreeDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp_min"));
        }
        return minTemperaturesForThreeDays;
    }

    public String getCurrentForecastAverageTemperature() throws JSONException {
        JSONObject mainFromJson = forecastJsonData.getJSONObject("main");
        String averageTemperature = mainFromJson.getString("temp");
        return averageTemperature;
    }

    public List<String> getFiveDaysAverageTemperatures() throws JSONException {
        List<String> averageTemperaturesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            averageTemperaturesForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp"));
        }
        return averageTemperaturesForFiveDays;
    }

    public String getCurrentForecastCloudiness() throws JSONException {
        JSONObject cloudsFromJson = forecastJsonData.getJSONObject("clouds");
        String clouds = cloudsFromJson.getString("all");
        return clouds;
    }

    public List<String> getFiveDaysCloudiness() throws JSONException {
        List<String> cloudinessForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            cloudinessForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("clouds").getString("all"));
        }
        return cloudinessForFiveDays;
    }

    public String getCurrentForecastWindDirection() throws JSONException {
        JSONObject windFromJson = forecastJsonData.getJSONObject("wind");
        String windDirection = windFromJson.getString("deg");
        return windDirection;
    }

    public List<String> getFiveDaysWindDirections() throws JSONException {
        List<String> windDirectionsForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            windDirectionsForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("wind").getString("deg"));
        }
        return windDirectionsForFiveDays;
    }

    public String getCurrentForecastHumidity() throws JSONException {
        JSONObject mainFromJson = forecastJsonData.getJSONObject("main");
        String humidity = mainFromJson.getString("humidity");
        return humidity;
    }

    public List<String> getFiveDaysHumidity() throws JSONException {
        List<String> humidityForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastJsonData.getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            humidityForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("humidity"));
        }
        return humidityForFiveDays;
    }


}
