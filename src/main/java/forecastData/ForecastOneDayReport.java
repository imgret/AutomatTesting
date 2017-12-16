package forecastData;

import java.util.List;

public class ForecastOneDayReport {

    public final List<String> dates;
    public final List<String> maxTemperatures;
    public final List<String> minTemperatures;

    public ForecastOneDayReport(List<String> dates, List<String> maxTemperatures, List<String> minTemperatures) {
        this.dates = dates;
        this.maxTemperatures = maxTemperatures;
        this.minTemperatures = minTemperatures;
    }
}
