package repositoryOperator.forcastFileReader;

import java.io.*;
import java.util.Scanner;

public class ForecastFileReader {

    private Scanner scanner;

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void openFile(String fileName) throws FileNotFoundException {
        File forecastFile = new File(fileName);
        scanner = new Scanner(forecastFile);
    }

    public String readFromFile() throws IOException {
        StringBuilder fileContentStringBuilder = new StringBuilder("");

        while (scanner.hasNextLine()) {
            fileContentStringBuilder.append(scanner.nextLine());
            fileContentStringBuilder.append("\n");
        }
        if (fileContentStringBuilder.length() > 0) {
            fileContentStringBuilder.deleteCharAt(fileContentStringBuilder.length() - 1);
        }

        return fileContentStringBuilder.toString();
    }

    public void closeFile() {
        scanner.close();
    }
}
