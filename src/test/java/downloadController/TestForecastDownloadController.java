package downloadController;

import forecastInputFileUtility.InputFileUtility;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class TestForecastDownloadController {

    private ForecastDownloadController downloadController;

    @Before
    public void starter() {
        downloadController = new ForecastDownloadController();
    }

    @Test
    public void testDownloadCurrentForecastForGivenTown() throws IOException {
        boolean isDownloaded = downloadController.downloadCurrentForecastForGivenTown("Tallinn");
        assertEquals(true, isDownloaded);
    }

    @Test
    public void testDownloadFiveDaysForecastForGivenTown() throws IOException {
        boolean isDownloaded = downloadController.downloadFiveDayForecastForGivenTown("Tallinn");
        assertEquals(true, isDownloaded);
    }

    @Test
    public void testDownloadCurrentForecastForTownsFromInputFile() throws IOException {
        InputFileUtility.clearInputFile();
        InputFileUtility.addTownToInputFile("Tallinn");
        InputFileUtility.addTownToInputFile("Helsinki");
        boolean isDownloaded = downloadController.downloadCurrentForecastForTownFromInputFile();
        assertEquals(true, isDownloaded);
    }

    @Test
    public void testDownloadFiveDaysForecastForTownsFromInputFile() throws IOException {
        InputFileUtility.clearInputFile();
        InputFileUtility.addTownToInputFile("Tallinn");
        InputFileUtility.addTownToInputFile("Helsinki");
        boolean isDownloaded = downloadController.downloadFiveDayForecastForTownFromInputFile();
        assertEquals(true, isDownloaded);
    }
}
