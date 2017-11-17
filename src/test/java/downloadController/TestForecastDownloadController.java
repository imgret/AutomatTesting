package downloadController;

import forecastInputFileUtility.InputFileUtility;
import http.HttpUtility;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestForecastDownloadController {

    private HttpUtility httpUtilityMock;
    private ForecastDownloadController downloadController;
    private InputFileUtility inputFileUtility;

    @Before
    public void starter() throws IOException {
        httpUtilityMock = mock(HttpUtility.class);
        downloadController = new ForecastDownloadController(httpUtilityMock);
        inputFileUtility = new InputFileUtility();
    }

    @Test
    public void testDownloadCurrentForecastForGivenTown() throws IOException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("Test value for current forecast");
        downloadController.downloadToFileCurrentForecastForGivenTown("Tallinn");
        String actualFileContent = Files.lines(Paths.get("Tallinn.txt")).collect(Collectors.joining());
        assertEquals("Test value for current forecast", actualFileContent);
        Files.deleteIfExists(Paths.get("Tallinn.txt"));
    }

    @Test
    public void testDownloadFiveDaysForecastForGivenTown() throws IOException {
        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("Test value for five days forecast");
        downloadController.downloadToFileCurrentForecastForGivenTown("Moscow");
        String actualFileContent = Files.lines(Paths.get("Moscow.txt")).collect(Collectors.joining());
        assertEquals("Test value for five days forecast", actualFileContent);
        Files.deleteIfExists(Paths.get("Moscow.txt"));
    }

    @Test
    public void testDownloadCurrentForecastForTownsFromInputFile() throws IOException {
        inputFileUtility.openInputFile();
        inputFileUtility.clearInputFile();
        inputFileUtility.addTownToInputFile("Paris");
        inputFileUtility.addTownToInputFile("Helsinki");
        inputFileUtility.closeInputFile();

        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("Test value for current forecast");
        downloadController.downloadToFileCurrentForecastForTownsFromInputFile();

        String actualParisContent = Files.lines(Paths.get("Paris.txt")).collect(Collectors.joining());
        String actualHelsinkiContent = Files.lines(Paths.get("Helsinki.txt")).collect(Collectors.joining());

        List<String> actualFilesContent = Arrays.asList(actualParisContent, actualHelsinkiContent);
        List<String> expectedFilesContent = Arrays.asList("Test value for current forecast", "Test value for current forecast");

        assertEquals(expectedFilesContent, actualFilesContent);
        Files.deleteIfExists(Paths.get("Paris.txt"));
        Files.deleteIfExists(Paths.get("Helsinki.txt"));
    }

    @Test
    public void testDownloadFiveDaysForecastForTownsFromInputFile() throws IOException {
        inputFileUtility.openInputFile();
        inputFileUtility.clearInputFile();
        inputFileUtility.addTownToInputFile("London");
        inputFileUtility.addTownToInputFile("York");
        inputFileUtility.closeInputFile();

        when(httpUtilityMock.downloadWeatherForecastText()).thenReturn("Test value for five days forecast");
        downloadController.downloadToFileFiveDayForecastForTownsFromInputFile();

        String actualParisContent = Files.lines(Paths.get("London.txt")).collect(Collectors.joining());
        String actualHelsinkiContent = Files.lines(Paths.get("York.txt")).collect(Collectors.joining());

        List<String> actualFilesContent = Arrays.asList(actualParisContent, actualHelsinkiContent);
        List<String> expectedFilesContent = Arrays.asList("Test value for five days forecast", "Test value for five days forecast");

        assertEquals(expectedFilesContent, actualFilesContent);
        Files.deleteIfExists(Paths.get("London.txt"));
        Files.deleteIfExists(Paths.get("York.txt"));
    }
}
