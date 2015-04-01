package util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * ReadCSV.java
 *
 *
 */
public class ReadCSV {

    private String filePath; // Path to File

    /**
     * Prepares the file to be read
     * @param filePath - Path to the file
     */
    public ReadCSV(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Parses the CSV and stores it into a HashMap, using one column as a Key, and another as a Value
     * @param colKey - Column to be used as Key
     * @param colVal - Column to be used as Value
     * @return - HashMap containing data using the inputted Key-Value pairs
     */
    public HashMap<String, String> parse(int colKey, int colVal) {
        BufferedReader br = null;
        String line = "";
        HashMap<String, String> result = new HashMap<String, String>();

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Regex to split the values

                try {
                    result.put(values[colKey], values[colVal]);
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException occurred");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {

                }
            }
        }
        return result;
    }
}
