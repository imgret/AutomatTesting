package http;

public enum ForecastType {
    CURRENT_WEATHER("weather"),
    FIVE_DAY_FORECAST("forecast");

    private String forecastType;

    ForecastType(String forecastType) {
        this.forecastType = forecastType;
    }

    public String getForecastType() {
        return forecastType;
    }
}
