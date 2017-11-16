package forecastFileWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ForecastFileWriter {

    private FileWriter fileWriter;

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public void openFile(String fileName) throws IOException {
        File textDataFile = new File(fileName);
        if (textDataFile.getParentFile() != null) {
            textDataFile.getParentFile().mkdirs();
        }
        fileWriter = new FileWriter(textDataFile);
    }

    public void writeToFile(String textData) throws IOException {
        fileWriter.write(textData);
    }

    public void closeFile() throws IOException {
        fileWriter.close();
    }
}
