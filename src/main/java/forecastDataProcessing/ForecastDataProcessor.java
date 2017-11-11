package forecastDataProcessing;

import forecastData.ForecastData;
import forecastData.ForecastType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class ForecastDataProcessor {

    private ForecastData forecastData;

    public ForecastDataProcessor(ForecastData forecastData) {
        this.forecastData = forecastData;
    }

    public JSONObject getJsonData() throws JSONException {
        return new JSONObject(forecastData.getTextData());
    }

    public String getWeatherForecastLocationLatitude() throws JSONException {
        if (forecastData.getForecastType() == ForecastType.CURRENT_WEATHER) {
            JSONObject coordinatesFromJson = forecastData.getJsonData().getJSONObject("coord");
            String latitude = coordinatesFromJson.getString("lat");
            return latitude;
        } else {
            JSONObject coordinatesFromJson = forecastData.getJsonData().getJSONObject("city").getJSONObject("coord");
            String latitude = coordinatesFromJson.getString("lat");
            return latitude;
        }
    }

    public String getWeatherForecastLocationLongitude() throws JSONException {
        if (forecastData.getForecastType() == ForecastType.CURRENT_WEATHER) {
            JSONObject coordinatesFromJson = forecastData.getJsonData().getJSONObject("coord");
            String longitude = coordinatesFromJson.getString("lon");
            return longitude;
        } else {
            JSONObject coordinatesFromJson = forecastData.getJsonData().getJSONObject("city").getJSONObject("coord");
            String longitude = coordinatesFromJson.getString("lon");
            return longitude;
        }
    }

    public String getWeatherForecastLocationCountry() throws JSONException {
        if (forecastData.getForecastType() == ForecastType.CURRENT_WEATHER) {
            JSONObject countryFromJson = forecastData.getJsonData().getJSONObject("sys");
            String country = countryFromJson.getString("country");
            return country;
        } else {
            JSONObject countryFromJson = forecastData.getJsonData().getJSONObject("city");
            String country = countryFromJson.getString("country");
            return country;
        }
    }

    public String getWeatherForecastLocationTown() throws JSONException {
        if (forecastData.getForecastType() == ForecastType.CURRENT_WEATHER) {
            String town = forecastData.getJsonData().getString("name");
            return town;
        } else {
            JSONObject townFromJson = forecastData.getJsonData().getJSONObject("city");
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

    public String getWeatherForecastDate() throws JSONException {
        String dateInSeconds = forecastData.getJsonData().getString("dt");
        String formattedDate = formatDateFromSeconds(dateInSeconds);
        return formattedDate;
    }

    public List<String> getFiveDaysForecastDates() throws JSONException {
        List<String> forecastDatesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastData.getJsonData().getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            String forecastDate = listOfForecastsForFiveDays.getJSONObject(i).getString("dt");
            forecastDatesForFiveDays.add(formatDateFromSeconds(forecastDate));
        }
        return forecastDatesForFiveDays;
    }

    public String getWeatherForecastMaximumTemperature() throws JSONException {
        JSONObject mainFromJson = forecastData.getJsonData().getJSONObject("main");
        String maxTemperature = mainFromJson.getString("temp_max");
        return maxTemperature;
    }

    public List<String> getFiveDaysMaximumTemperature() throws JSONException {
        List<String> maxTemperaturesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastData.getJsonData().getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            maxTemperaturesForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp_max"));
        }
        return maxTemperaturesForFiveDays;
    }

    public String getWeatherForecastMinimumTemperature() throws JSONException {
        JSONObject mainFromJson = forecastData.getJsonData().getJSONObject("main");
        String minTemperature = mainFromJson.getString("temp_min");
        return minTemperature;
    }

    public List<String> getFiveDaysMinimumTemperature() throws JSONException {
        List<String> minTemperaturesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastData.getJsonData().getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            minTemperaturesForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp_min"));
        }
        return minTemperaturesForFiveDays;
    }

    public String getWeatherForecastAverageTemperature() throws JSONException {
        JSONObject mainFromJson = forecastData.getJsonData().getJSONObject("main");
        String averageTemperature = mainFromJson.getString("temp");
        return averageTemperature;
    }

    public List<String> getFiveDaysAverageTemperature() throws JSONException {
        List<String> averageTemperaturesForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastData.getJsonData().getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            averageTemperaturesForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp"));
        }
        return averageTemperaturesForFiveDays;
    }

    public String getWeatherForecastCloudiness() throws JSONException {
        JSONObject cloudsFromJson = forecastData.getJsonData().getJSONObject("clouds");
        String clouds = cloudsFromJson.getString("all");
        return clouds;
    }

    public List<String> getFiveDaysCloudiness() throws JSONException {
        List<String> cloudinessForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastData.getJsonData().getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            cloudinessForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("clouds").getString("all"));
        }
        return cloudinessForFiveDays;
    }

    public String getWeatherForecastWindDirection() throws JSONException {
        JSONObject windFromJson = forecastData.getJsonData().getJSONObject("wind");
        String windDirection = windFromJson.getString("deg");
        return windDirection;
    }

    public List<String> getFiveDaysWindDirections() throws JSONException {
        List<String> windDirectionsForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastData.getJsonData().getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            windDirectionsForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("wind").getString("deg"));
        }
        return windDirectionsForFiveDays;
    }

    public String getWeatherForecastHumidity() throws JSONException {
        JSONObject mainFromJson = forecastData.getJsonData().getJSONObject("main");
        String humidity = mainFromJson.getString("humidity");
        return humidity;
    }

    public List<String> getFiveDaysHumidity() throws JSONException {
        List<String> humidityForFiveDays = new LinkedList<>();
        JSONArray listOfForecastsForFiveDays = forecastData.getJsonData().getJSONArray("list");
        for (int i = 0; i < listOfForecastsForFiveDays.length(); i++) {
            humidityForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("humidity"));
        }
        return humidityForFiveDays;
    }


}
