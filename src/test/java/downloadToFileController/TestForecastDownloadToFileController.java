package downloadToFileController;

import forecastData.*;
import org.json.JSONException;
import repositoryOperator.forcastFileReader.ForecastFileReader;
import repositoryOperator.forecastFileWriter.ForecastFileWriter;
import http.HttpUtility;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class TestForecastDownloadToFileController {

    private HttpUtility httpUtilityMock;
    private ForecastFileWriter fileWriterMock;
    private ForecastFileReader fileReaderMock;
    private ForecastDownloadToFileController downloadToFileController;
    private ForecastReportCreator reportCreatorMock;


    @Before
    public void starter() throws IOException {
        httpUtilityMock = mock(HttpUtility.class);
        fileWriterMock = mock(ForecastFileWriter.class);
        fileReaderMock = mock(ForecastFileReader.class);
        reportCreatorMock = mock(ForecastReportCreator.class);
        downloadToFileController = new ForecastDownloadToFileController(httpUtilityMock, fileWriterMock, fileReaderMock, reportCreatorMock);
    }

    @Test
    public void testForecastDownloadFromUrlUsingMock() throws IOException {
        String url = "some url";
        downloadToFileController.downloadForecastFromUrl(url);
        verify(httpUtilityMock).createHttpUrlConnection(url);
        verify(httpUtilityMock).downloadWeatherForecastText();
        verify(httpUtilityMock).closeHttpUrlConnection();
    }

    @Test
    public void testForecastWritingToFileUsingMock() throws IOException {
        String outputFileName = "output.txt";
        String forecastText = "some forecast";
        downloadToFileController.writeForecastToFile(outputFileName, forecastText);
        verify(fileWriterMock).openFile(outputFileName);
        verify(fileWriterMock).writeToFile(forecastText);
        verify(fileWriterMock).closeFile();
    }

    @Test
    public void testGettingTownsFromInputFileReturnsCorrectSetOfTowns() throws IOException {
        when(fileReaderMock.readFromFile()).thenReturn("Tartu\nHapsalu\nGoliet");
        Set<String> actualTowns = downloadToFileController.readTownsForForecastFromInputFile();
        Set<String> expectedTowns = new HashSet<>(Arrays.asList("Tartu", "Hapsalu", "Goliet"));
        assertEquals(expectedTowns, actualTowns);
    }

    @Test
    public void testGettingTownsFromInputFileCallsAllFileReaderMethods() throws IOException {
        when(fileReaderMock.readFromFile()).thenReturn("Tartu\nHapsalu\nGoliet");
        downloadToFileController.readTownsForForecastFromInputFile();
        verify(fileReaderMock).openFile("input.txt");
        verify(fileReaderMock).readFromFile();
        verify(fileReaderMock).closeFile();
    }

    @Test
    public void testDownloadCurrentForecastForGivenTown() throws IOException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("downloaded forecast text");
        downloadToFileController.downloadToFileCurrentForecastForGivenTown("Tallinn");
        verify(fileWriterMock).openFile("Tallinn.txt");
        verify(fileWriterMock).writeToFile("downloaded forecast text");
    }

    @Test
    public void testDownloadFiveDaysForecastForGivenTown() throws IOException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("Test value for five days forecast");
        downloadToFileController.downloadToFileCurrentForecastForGivenTown("Moscow");
        verify(fileWriterMock).openFile("Moscow.txt");
        verify(fileWriterMock).writeToFile("Test value for five days forecast");
    }

    @Test
    public void testDownloadFullForecastReportForGivenTownWasCreatedUrlForCurrentForecast() throws IOException, JSONException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("{}");
        downloadToFileController.downloadToFileFullForecastReportForGivenTown("Moscow");
        verify(httpUtilityMock).createDownloadUrlUsingForecastTypeAndTown(ForecastType.CURRENT_WEATHER, "Moscow");
    }

    @Test
    public void testDownloadFullForecastReportForGivenTownWasCreatedUrlForFiveDaysForecast() throws IOException, JSONException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("{}");
        downloadToFileController.downloadToFileFullForecastReportForGivenTown("Moscow");
        verify(httpUtilityMock).createDownloadUrlUsingForecastTypeAndTown(ForecastType.FIVE_DAY_FORECAST, "Moscow");
    }

    @Test
    public void testDownloadFullForecastReportForGivenTownTwoTimesWasCalledDownloadMethod() throws IOException, JSONException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("{}");
        downloadToFileController.downloadToFileFullForecastReportForGivenTown("Moscow");
        verify(httpUtilityMock, times(2)).downloadWeatherForecastText();
    }

    @Test
    public void testDownloadFullForecastReportForGivenTownTwoTimesWasCalledGetFullReportJsonTextMethod() throws IOException, JSONException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("{}");
        downloadToFileController = spy(downloadToFileController);
        downloadToFileController.downloadToFileFullForecastReportForGivenTown("Moscow");
        verify(downloadToFileController).getFullReportJsonFromForecastFullText(anyString(), anyString(), anyString());
    }

    @Test
    public void testDownloadFullForecastReportForGivenTownTwoTimesWasCalledWriteToFileMethod() throws IOException, JSONException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("{}");
        downloadToFileController.downloadToFileFullForecastReportForGivenTown("Moscow");
        verify(fileWriterMock).writeToFile(anyString());
    }

    @Test
    public void testDownloadCurrentForecastForTownsFromInputFileWasCalledTwoTimes() throws IOException {
        when(fileReaderMock.readFromFile()).thenReturn("Paris\nHelsinki");
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn(anyString());
        downloadToFileController.downloadToFileCurrentForecastForTownsFromInputFile();
        verify(fileWriterMock, times(2))
                .writeToFile(anyString());
    }

    @Test
    public void testDownloadCurrentForecastForTownsFromInputFileWriteToFileCorrectText() throws IOException {
        when(fileReaderMock.readFromFile()).thenReturn("Paris\nHelsinki");
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("test content");
        downloadToFileController.downloadToFileCurrentForecastForTownsFromInputFile();
        verify(fileWriterMock, times(2))
                .writeToFile("test content");
    }

    @Test
    public void testDownloadFiveDaysForecastForTownsFromInputFileWasCalledTwoTimes() throws IOException {
        when(fileReaderMock.readFromFile()).thenReturn("Paris\nHelsinki");
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn(anyString());
        downloadToFileController.downloadToFileCurrentForecastForTownsFromInputFile();
        verify(fileWriterMock, times(2))
                .writeToFile(anyString());
    }

    @Test
    public void testDownloadFiveDaysForecastForTownsFromInputFileWriteToFileCorrectText() throws IOException {
        when(fileReaderMock.readFromFile()).thenReturn("Paris\nHelsinki");
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("test content");
        downloadToFileController.downloadToFileFiveDayForecastForTownsFromInputFile();
        verify(fileWriterMock, times(2))
                .writeToFile("test content");
    }

    @Test
    public void testDownloadToFileFullForecastForTownsFromInputFileCalledWriteToFileMethodTwoTimes() throws IOException, JSONException {
        when(fileReaderMock.readFromFile()).thenReturn("Paris\nHelsinki");
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("{}");
        downloadToFileController.downloadToFileFullForecastForTownsFromInputFile();
        verify(fileWriterMock, times(2))
                .writeToFile(anyString());
    }

    @Test
    public void testGetFullReportJsonFromForecastFullText() throws IOException, JSONException {
        when(reportCreatorMock.createForecastFullReport(anyString(), any(), any())).thenReturn(
                new ForecastFullReport("Town", "cord",
                        new ForecastCurrentReport("0"),
                        new ForecastThreeDaysReport(new ForecastOneDayReport[]{
                                new ForecastOneDayReport(
                                        Arrays.asList("", "", "", "", "", "", "", ""),
                                        Arrays.asList("", "", "", "", "", "", "", ""),
                                        Arrays.asList("", "", "", "", "", "", "", "")
                                ),
                                new ForecastOneDayReport(
                                        Arrays.asList("", "", "", "", "", "", "", ""),
                                        Arrays.asList("", "", "", "", "", "", "", ""),
                                        Arrays.asList("", "", "", "", "", "", "", "")
                                ),
                                new ForecastOneDayReport(
                                        Arrays.asList("", "", "", "", "", "", "", ""),
                                        Arrays.asList("", "", "", "", "", "", "", ""),
                                        Arrays.asList("", "", "", "", "", "", "", "")
                                )
                        })
                ));
        String actualFullReportJson = downloadToFileController.getFullReportJsonFromForecastFullText(
                "Town", "{}", "{}");
        String expectedFullReportJson = "{\"town\":\"Town\",\"coordinates\":\"cord\",\"currentReport\":{\"averageTemperature\":\"0\"}," +
                "\"threeDaysReport\":{\"oneDayReports\":[{\"dates\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]," +
                "\"maxTemperatures\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"minTemperatures\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]}," +
                "{\"dates\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"maxTemperatures\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]," +
                "\"minTemperatures\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]},{\"dates\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]," +
                "\"maxTemperatures\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"minTemperatures\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]}]}}";
        assertEquals(expectedFullReportJson, actualFullReportJson);
    }
}
