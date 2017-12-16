package forecastData;

public class ForecastFullReport {

    public final String town;
    public final String coordinates;
    public final ForecastCurrentReport currentReport;
    public final ForecastThreeDaysReport threeDaysReport;

    public ForecastFullReport(String town, String coordinates,
                              ForecastCurrentReport currentReport, ForecastThreeDaysReport threeDaysReport) {
        this.town = town;
        this.coordinates = coordinates;
        this.currentReport = currentReport;
        this.threeDaysReport = threeDaysReport;
    }
}
