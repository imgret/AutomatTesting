package forecastData;

import dataLoader.ForecastDataLoader;
import forecastDataProcessing.ForecastDataProcessor;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ForecastData {

    private String textData;
    private JSONObject jsonData;
    private ForecastDataProcessor dataProcessor;
    private ForecastType forecastType;

    public ForecastData(String fileName, ForecastType forecastType) throws JSONException, IOException {
        this.forecastType = forecastType;
        this.textData = new ForecastDataLoader().getTextDataFromFile(fileName);
        this.dataProcessor = new ForecastDataProcessor(this);
        this.jsonData = dataProcessor.getJsonData();
    }

    public void setForecastType(ForecastType forecastType) {
        this.forecastType = forecastType;
    }

    public void setTextData(String textData) throws JSONException {
        this.textData = textData;
        this.jsonData = dataProcessor.getJsonData();
    }

    public void setJsonData(JSONObject jsonData) {
        this.textData = jsonData.toString();
        this.jsonData = jsonData;
    }

    public ForecastType getForecastType() {
        return forecastType;
    }

    public String getTextData() {
        return textData;
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    public String getLocationLatitude() throws JSONException {
        return dataProcessor.getWeatherForecastLocationLatitude();
    }

    public String getLocationLongitude() throws JSONException {
        return dataProcessor.getWeatherForecastLocationLongitude();
    }

    public String getLocationCountry() throws JSONException {
        return dataProcessor.getWeatherForecastLocationCountry();
    }

    public String getLocationTown() throws JSONException {
        return dataProcessor.getWeatherForecastLocationTown();
    }

    public String getForecastDate() throws JSONException {
        return dataProcessor.getWeatherForecastDate();
    }

    public List<String> getFiveDaysForecastDates() throws JSONException {
        return dataProcessor.getFiveDaysForecastDates();
    }

    public String getMaximumTemperature() throws JSONException {
        return dataProcessor.getWeatherForecastMaximumTemperature();
    }

    public List<String> getFiveDaysMaximumTemperature() throws JSONException {
        return dataProcessor.getFiveDaysMaximumTemperature();
    }

    public String geMinimumTemperature() throws JSONException {
        return dataProcessor.getWeatherForecastMinimumTemperature();
    }

    public List<String> getFiveDaysMinimumTemperature() throws JSONException {
        return dataProcessor.getFiveDaysMinimumTemperature();
    }

    public String getAverageTemperature() throws JSONException {
        return dataProcessor.getWeatherForecastAverageTemperature();
    }

    public List<String> getFiveDaysAverageTemperature() throws JSONException {
        return dataProcessor.getFiveDaysAverageTemperature();
    }

    public String getCloudiness() throws JSONException {
        return dataProcessor.getWeatherForecastCloudiness();
    }

    public List<String> getFiveDaysCloudiness() throws JSONException {
        return dataProcessor.getFiveDaysCloudiness();
    }

    public String getWindDirection() throws JSONException {
        return dataProcessor.getWeatherForecastWindDirection();
    }

    public List<String> getFiveDaysWindDirections() throws JSONException {
        return dataProcessor.getFiveDaysWindDirections();
    }

    public String getHumidity() throws JSONException {
        return dataProcessor.getWeatherForecastHumidity();
    }

    public List<String> getFiveDaysHumidity() throws JSONException {
        return dataProcessor.getFiveDaysHumidity();
    }

}
