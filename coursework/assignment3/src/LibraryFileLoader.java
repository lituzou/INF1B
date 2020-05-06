import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Line delimiter for library file
     */
    public final String LINE_DELIMITER = ",";

    /**
     * Author delimiter for author array string
     */
    public final String AUTHOR_DELIMITER = "-";

    /**
     * Total number of entries in each line of library file
     */
    public final int NUM_ENTRIES = 5;

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * <p>
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * <p>
     * NOTE: Individual line entries do not include line breaks at the
     * end of each line.
     */
    private List<String> fileContent;

    /**
     * Create a new loader. No file content has been loaded yet.
     */
    public LibraryFileLoader() {
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * <p>
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     *
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     *
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     *
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        List<BookEntry> books = new ArrayList<>();
        try {
            if (!contentLoaded()) {
                throw new NullPointerException("No content loaded before parsing.");
            }
            Iterator<String> contentIterator = this.fileContent.iterator();
            if (contentIterator.hasNext()) {
                contentIterator.next(); // Skip the column header
            } else {
                throw new IllegalArgumentException("File content does not include any lines");
            }

            while (contentIterator.hasNext()) {
                String line = contentIterator.next();
                BookEntry book = parseLine(line);
                books.add(book);

            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return new ArrayList<>();
        }
        return books;

    }

    /**
     * Parse a line into a BookEntry object
     *
     * @param line the line to be parsed
     * @return a BookEntry object representing all the infos given in the line
     * @throws IllegalArgumentException The infos in the line are not enough
     * @throws NullPointerException     Given line is null
     */
    private BookEntry parseLine(String line) {
        Objects.requireNonNull(line, "Given line must not be null");

        String[] entries = line.split(LINE_DELIMITER);
        // Check if the line contains exact number of entries
        if (entries.length != NUM_ENTRIES) {
            throw new IllegalArgumentException("A line does not have correct number of entries");
        }

        // Try to parse the infos to relevant type
        String title = entries[0];
        String[] authors = entries[1].split(AUTHOR_DELIMITER);
        float rating = Float.parseFloat(entries[2]);
        String ISBN = entries[3];
        int pages = Integer.parseInt(entries[4]);

        return new BookEntry(title, authors, rating, ISBN, pages);
    }
}
