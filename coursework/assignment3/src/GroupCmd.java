import java.util.*;

/**
 * GROUP command for printing loaded books in several groups
 */
public class GroupCmd extends LibraryCommand {

    /**
     * Padding for group label
     */
    public final String LABEL_PADDING = "## ";

    /**
     * Padding for individual group item
     */
    public final String ITEM_PADDING = "\t";

    /**
     * The type of grouping
     */
    private GroupCmdType argumentType;

    /**
     * Create a GROUP command that is used to print books in groups
     *
     * @param argumentInput type of grouping
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    /**
     * It groups all loaded books in the library by a specified type and print all of them in order.
     * Correct group type is expected to be loaded as argumentType before execution.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if library data or argumentType is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null");
        Objects.requireNonNull(argumentType, "Given group type must not be null");

        if (data.getBookData().isEmpty()) {
            System.out.println("The library has no book entries.");
            return;
        }

        StringJoiner sj = new StringJoiner("\n");
        sj.add("Grouped data by " + this.argumentType.toString());

        switch (argumentType) {
            case TITLE: {
                //Map all entries to their corresponding title
                HashMap<BookEntry, TitlePrefix> bookTitlePrefixMap = new HashMap<>();
                for (BookEntry entry : data.getBookData()) {
                    bookTitlePrefixMap.put(entry, entry.getTitlePrefix());
                }
                TreeMap<TitlePrefix, List<BookEntry>> groupedBooks = ListOperationUtils.groupByAttribute(bookTitlePrefixMap);
                addGroupBlocks(sj, groupedBooks);
                break;
            }
            case AUTHOR: {
                //Map all entries to their corresponding authors
                HashMap<BookEntry, List<String>> bookAuthorsMap = new HashMap<>();
                for (BookEntry entry : data.getBookData()) {
                    bookAuthorsMap.put(entry, Arrays.asList(entry.getAuthors()));
                }
                TreeMap<String, List<BookEntry>> groupedBooks = ListOperationUtils.groupByManyAttributes(bookAuthorsMap);
                addGroupBlocks(sj, groupedBooks);
                break;
            }
            default: {
                System.err.println(String.format("ERROR: Group type %s is not supported", argumentType));
                return;
            }

        }
        System.out.println(sj.toString());
    }

    /**
     * Parse group type in String to its corresponding GroupCmdType and store it in argumentType for execution.
     * If invalid group type is passed, argumentType will not be changed.
     *
     * @param argumentInput argument input for this command
     * @return true if corresponding group type is founded, false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        GroupCmdType type = GroupCmdType.getGroupCmdType(argumentInput);
        if (type == null) return false;
        this.argumentType = type;
        return true;
    }

    /**
     * Add a group block to a StringJoiner object
     *
     * @param sj           StringJoiner object
     * @param groupedBooks grouped books by group labels
     * @param <GroupLabel> type of group labels
     * @throws NullPointerException StringJoiner or grouped books is null
     */
    private <GroupLabel> void addGroupBlocks(StringJoiner sj, AbstractMap<GroupLabel, List<BookEntry>> groupedBooks) {
        Objects.requireNonNull(sj, "Given StringJoiner object must not be null");
        Objects.requireNonNull(groupedBooks, "Given book groups must not be null");

        for (GroupLabel groupLabel : groupedBooks.keySet()) {
            final String groupHeader = LABEL_PADDING + groupLabel.toString();
            StringJoiner blockSJ = new StringJoiner("\n" + ITEM_PADDING);
            blockSJ.add(groupHeader);
            for (BookEntry book : groupedBooks.get(groupLabel)) {
                blockSJ.add(book.getTitle());
            }
            sj.add(blockSJ.toString());
        }
    }


}
