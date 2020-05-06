import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * LIST command used to display all loaded books in a given template
 */
public class ListCmd extends LibraryCommand {

    /**
     * The list type for presenting the library
     */
    private ListCmdType argumentType;

    /**
     * Create a LIST command that can be used to print book list.
     *
     * @param argumentInput template of book list
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * Execute the LIST command, this prints all the loaded book in a specified template.
     * Correct argument type is expected to be loaded.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if library data or argument type is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null");
        Objects.requireNonNull(argumentType, "Given list type must not be null");

        List<BookEntry> bookEntries = data.getBookData();
        int bookCount = bookEntries.size();

        if (bookCount == 0) {
            System.out.println("The library has no book entries.");
            return;
        }

        final String firstLine = String.format("%d books in library:\n", bookCount);
        StringJoiner sj = new StringJoiner("\n", firstLine, "");

        for (BookEntry bookEntry : bookEntries) {
            switch (argumentType) {
                case shortList: {
                    sj.add(bookEntry.getTitle());
                    break;
                }
                case longList: {
                    sj.add(bookEntry.toString() + "\n");
                    break;
                }
                default: {
                    System.err.println(String.format("ERROR: List type %s is not supported", argumentType));
                    return;
                }
            }
        }
        System.out.println(sj.toString());

    }

    /**
     * Parse the input argument to its corresponding list template given in ListCmdType.
     * It is then loaded in argumentType for execution.
     * If no template is founded, argumentType is unchanged.
     *
     * @param argumentInput argument input for this command
     * @return true if corresponding list template is founded, false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        ListCmdType type = ListCmdType.getListCmdType(argumentInput);
        if (type == null) return false;
        this.argumentType = type;
        return true;
    }


}