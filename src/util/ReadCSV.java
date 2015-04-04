package util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ReadCSV.java
 *
 *
 */
public class ReadCSV {

    private static String filePath; // Path to File

    /**
     * Prepares the file to be read
     * @param filePath - Path to the file
     */
    public ReadCSV(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads a specific line in the file
     * @param lineNumber - Line number to read
     * @return - String of selected line
     */
    public static String readLine(int lineNumber) {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < lineNumber; i++) {
                br.readLine();
            }

            line = br.readLine();
        } catch (IOException e) {

        }
        return line;
    }

    /**
     * Returns a range of lines
     * @param lo - the lower bound
     * @param hi - the upper bound
     * @return - String array of lines in range
     */
    public static String[] readRange(int lo, int hi) {
        BufferedReader br = null;
        String[] output = new String[hi-lo];
        try {
            br = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < lo; i++) {
                br.readLine();
            }
            for (int i = lo; i < hi; i++) {
                output[i] = br.readLine();
            }
        } catch (IOException e) {

        }
        return output;
    }

    public static ArrayList<String> readColumn(int col) {
        BufferedReader br = null;
        ArrayList<String> output = new ArrayList<String>();
        String line = "";
        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine(); // This is to skip the first line which is just a description
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                output.add(values[col]);
            }
        } catch (IOException e) {

        }
        return output;
    }

    /**
     * Parses the CSV and stores it into a HashMap, using one column as a Key, and another as a Value
     * @param colKey - Column to be used as Key
     * @param colVal - Column to be used as Value
     * @return - HashMap containing data using the inputted Key-Value pairs
     */
    public static HashMap<String, String> parse(int colKey, int colVal) {
        BufferedReader br = null;
        String line = "";
        HashMap<String, String> result = new HashMap<String, String>(100);

        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine(); // This is to skip the first line which is just a description
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

    public static ArrayList<Tuple<String, String>> parseTuple(int colKey, int colVal) {
        BufferedReader br = null;
        String line = "";
        ArrayList<Tuple<String,String>> result = new ArrayList<Tuple<String,String>>();

        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine(); // This is to skip the first line which is just a description
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Regex to split the values

                try {
                    result.add(new Tuple(values[colKey], values[colVal]));
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
