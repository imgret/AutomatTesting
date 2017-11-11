package dataLoader;

import java.io.*;

public class ForecastDataLoader {

    public String getTextDataFromFile(String fileName) throws IOException {
        String fileContent;
        StringBuilder fileContentStringBuilder = new StringBuilder("");
        File forecastFile = new File(fileName);
        FileReader fileReader = new FileReader(forecastFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String nextLine;
        while ((nextLine = bufferedReader.readLine()) != null) {
            fileContentStringBuilder.append(nextLine);
            fileContentStringBuilder.append("\n");
        }
        fileContentStringBuilder.deleteCharAt(fileContentStringBuilder.length() - 1);
        bufferedReader.close();
        fileReader.close();
        fileContent = fileContentStringBuilder.toString();
        return fileContent;
    }
}
