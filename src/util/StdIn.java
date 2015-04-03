package util;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StdIn {
    private StdIn() { }

    private static Scanner scanner;
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE = Locale.US;
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
    private static final Pattern EMPTY_PATTERN = Pattern.compile("");
    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

    public static boolean isEmpty() {
        return !scanner.hasNext();
    }

    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public static boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public static String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (Exception e) {
            line = null;
        }
        return line;
    }

    public static char readChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        String ch = scanner.next();
        assert (ch.length() == 1) : "Internal StdIn error";
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return ch.charAt(0);
    }

    public static String readAll() {
        if (!scanner.hasNextLine()) {
            return "";
        }

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public static String readString() {
        return scanner.next();
    }

    public static int readInt() {
        return scanner.nextInt();
    }

    public static double readDouble() {
        return scanner.nextDouble();
    }

    public static float readFloat() {
        return scanner.nextFloat();
    }

    public static long readLong() {
        return scanner.nextLong();
    }

    public static short readShort() {
        return scanner.nextShort();
    }

    public static byte readByte() {
        return scanner.nextByte();
    }

    public static boolean readBoolean() {
        String s = readString();
        if (s.equalsIgnoreCase("true")) {
            return true;
        } else if (s.equalsIgnoreCase("false")) {
            return false;
        } else if (s.equalsIgnoreCase("1")) {
            return true;
        } else if (s.equalsIgnoreCase("0")) {
            return false;
        }
        throw new InputMismatchException();
    }

    public static String[] readAllStrings() {
        // we could use readAll.trim().split(), but that's not consistent
        // because trim() uses characters 0x00..0x20 as whitespace
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0)
            return tokens;

        // don't include first token if it is leading whitespace
        String[] decapitokens = new String[tokens.length-1];
        for (int i = 0; i < tokens.length - 1; i++)
            decapitokens[i] = tokens[i+1];
        return decapitokens;
    }

    /**
     * Reads all remaining lines from standard input and returns them as an array of strings.
     * @return all remaining lines on standard input, as an array of strings
     */
    public static String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[0]);
    }

    /**
     * Reads all remaining tokens from standard input, parses them as integers, and returns
     * them as an array of integers.
     * @return all remaining integers on standard input, as an array
     * @throws InputMismatchException if any token cannot be parsed as an <tt>int</tt>
     */
    public static int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
    }

    /**
     * Reads all remaining tokens from standard input, parses them as doubles, and returns
     * them as an array of doubles.
     * @return all remaining doubles on standard input, as an array
     * @throws InputMismatchException if any token cannot be parsed as a <tt>double</tt>
     */
    public static double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }

    /*** end: section (2 of 2) of code duplicated from In to StdIn */


    // do this once when StdIn is initialized
    static {
        resync();
    }

    /**
     * If StdIn changes, use this to reinitialize the scanner.
     */
    private static void resync() {
        setScanner(new Scanner(new java.io.BufferedInputStream(System.in), CHARSET_NAME));
    }

    private static void setScanner(Scanner scanner) {
        StdIn.scanner = scanner;
        StdIn.scanner.useLocale(LOCALE);
    }

    /**
     * Reads all remaining tokens, parses them as integers, and returns
     * them as an array of integers.
     * @return all remaining integers, as an array
     * @throws InputMismatchException if any token cannot be parsed as an <tt>int</tt>
     * @deprecated For more consistency, use {@link #readAllInts()}
     */
    public static int[] readInts() {
        return readAllInts();
    }

    /**
     * Reads all remaining tokens, parses them as doubles, and returns
     * them as an array of doubles.
     * @return all remaining doubles, as an array
     * @throws InputMismatchException if any token cannot be parsed as a <tt>double</tt>
     * @deprecated For more consistency, use {@link #readAllDoubles()}
     */
    public static double[] readDoubles() {
        return readAllDoubles();
    }

    /**
     * Reads all remaining tokens and returns them as an array of strings.
     * @return all remaining tokens, as an array of strings
     * @deprecated For more consistency, use {@link #readAllStrings()}
     */
    public static String[] readStrings() {
        return readAllStrings();
    }

}
