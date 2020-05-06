import java.util.*;

/**
 * SEARCH command used to search all the books with given search criteria
 */
public class SearchCmd extends LibraryCommand {

    /**
     * The search criteria
     */
    private String searchArgument;

    /**
     * Create a SEARCH command to search through the entire library
     *
     * @param argumentInput search argument
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    /**
     * Execute the SEARCH command, this search through the entire library with given search criteria.
     * Valid search criteria is expected to be loaded as searchArgument before execution.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException     if library data or searchArgument is null
     * @throws IllegalArgumentException if searchArgument is blank or it contains a space
     */
    @Override
    public void execute(LibraryData data) {
        //Check every book against search criteria
        HashMap<BookEntry, Boolean> bookCriteriaMap = new HashMap<>();
        for (BookEntry bookEntry : data.getBookData()) {
            bookCriteriaMap.put(bookEntry, hasMatchCriteria(bookEntry));
        }
        //Filter out entries which fail the search criteria
        List<BookEntry> searchResult = ListOperationUtils.groupByAttribute(bookCriteriaMap).get(Boolean.TRUE);
        if (searchResult == null || searchResult.size() == 0) {
            System.out.println("No hits found for search term: " + this.searchArgument);
            return;
        }

        StringJoiner sj = new StringJoiner("\n");
        for (BookEntry entry : searchResult) {
            sj.add(entry.getTitle());
        }
        System.out.println(sj.toString());

    }

    /**
     * Validate and load the input search argument.
     * The argument is invalid if it is blank or it contains a space
     *
     * @param argumentInput argument input for this command
     * @return true if search argument is valid, false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput == null || argumentInput.isBlank() || argumentInput.contains(" ")) return false;
        this.searchArgument = argumentInput;
        return true;
    }

    /**
     * Determine if the book matches search criteria.
     *
     * @param book book entry
     * @return True if criteria is met, False otherwise
     */
    private boolean hasMatchCriteria(BookEntry book) {
        Objects.requireNonNull(book, "Given book entry must not be null");
        return book.getTitle().toLowerCase().contains(this.searchArgument.toLowerCase());
    }
}
