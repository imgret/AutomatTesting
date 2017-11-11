import downloadController.ForecastDownloadController;

import java.io.IOException;

public class ForecastController {

    public static void main(String... args) throws IOException {
        ForecastDownloadController downloadController = new ForecastDownloadController();

        // downloadController.downloadCurrentForecastForGivenTown("Tallinn");

        downloadController.addTownToInputFileUsingConsole();
        downloadController.downloadFiveDayForecastForTownFromInputFile();


    }
}
