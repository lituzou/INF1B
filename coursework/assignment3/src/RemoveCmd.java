import java.util.*;

/**
 * REMOVE command for removing books from the library
 */
public class RemoveCmd extends LibraryCommand {

    /**
     * Delimiter of arguments
     */
    public final String ARGUMENT_DELIMITER = " ";

    /**
     * The type of books to be remove (e.g. AUTHOR, TITLE)
     */
    private RemoveCmdType argumentType;

    /**
     * The value of type to be removed
     */
    private String argumentValue;

    /**
     * Create a REMOVE command for removing books
     *
     * @param argumentInput REMOVE parameters separated by a space
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    /**
     * It removes all the books with a given value by a specified attribute (e.g. AUTHOR or TITLE).
     * Correct removal type is expected to be loaded as argumentType before execution.
     * Removal value is expected to be loaded as argumentValue before execution.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if library data, argumentType or argumentValue is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(this.argumentType, "Given remove type must not be null");
        Objects.requireNonNull(this.argumentValue, "Given remove value must not be null");
        Objects.requireNonNull(data, "Given books must not be null");

        String response;
        List<BookEntry> bookEntries = data.getBookData();
        switch (this.argumentType) {
            case TITLE: {
                //Map all entries to their title
                HashMap<BookEntry, String> bookTitleMap = new HashMap<>();
                for (BookEntry bookEntry : bookEntries) {
                    bookTitleMap.put(bookEntry, bookEntry.getTitle());
                }
                int removeCount = ListOperationUtils.removeOneByAttribute(bookEntries, bookTitleMap, this.argumentValue);
                response = (removeCount == 0) ?
                        String.format("%s: not found.", this.argumentValue) :
                        String.format("%s: removed successfully.", this.argumentValue);
                break;
            }
            case AUTHOR: {
                //Map all entries against their hasAuthor() value
                HashMap<BookEntry, Boolean> bookHasAuthorMap = new HashMap<>();
                for (BookEntry bookEntry : bookEntries) {
                    bookHasAuthorMap.put(bookEntry, bookEntry.hasAuthor(this.argumentValue));
                }
                int removeCount = ListOperationUtils.removeManyByAttribute(bookEntries, bookHasAuthorMap, Boolean.TRUE);
                response = String.format("%d books removed for author: %s", removeCount, this.argumentValue);
                break;
            }
            default: {
                System.err.println(String.format("ERROR: Remove type %s is not supported", argumentType));
                return;
            }
        }
        System.out.println(response);
    }

    /**
     * Parse removal type in String to its corresponding RemoveCmdType and store it in argumentType for execution.
     * Load removal value in String to argumentValue for execution.
     * If either invalid remove type is passed or empty remove value is passed, nothing will be loaded.
     *
     * @param argumentInput argument input for this command
     * @return true if both removal type and value are valid and loaded, false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput == null) return false;
        final int DELIMITER_INDEX = argumentInput.indexOf(ARGUMENT_DELIMITER);
        if (DELIMITER_INDEX == -1) return false;

        try {
            String[] args = {
                    argumentInput.substring(0, DELIMITER_INDEX), // String before delimiter
                    argumentInput.substring(DELIMITER_INDEX + ARGUMENT_DELIMITER.length()) //String after delimiter
            };

            RemoveCmdType inputType = RemoveCmdType.getRemoveCmdType(args[0]);
            String inputValue = args[1];
            if (inputType == null || inputValue.isBlank()) return false;

            this.argumentType = inputType;
            this.argumentValue = inputValue;
            return true;

        } catch (IndexOutOfBoundsException e) {
            return false; // This means the input argument does not follow the input format
        }

    }

}
