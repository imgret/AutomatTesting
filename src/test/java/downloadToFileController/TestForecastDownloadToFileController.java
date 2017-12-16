package downloadToFileController;

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


    @Before
    public void starter() throws IOException {
        httpUtilityMock = mock(HttpUtility.class);
        fileWriterMock = mock(ForecastFileWriter.class);
        fileReaderMock = mock(ForecastFileReader.class);
        downloadToFileController = new ForecastDownloadToFileController(httpUtilityMock, fileWriterMock, fileReaderMock);
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
}
