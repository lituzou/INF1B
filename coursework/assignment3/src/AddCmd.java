import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * ADD command used to load the existing files to the library
 */
public class AddCmd extends LibraryCommand {

    private String dataFileUrl;

    /**
     * Create an ADD command
     *
     * @param argumentInput argument input is expected to have valid file extension
     * @throws IllegalArgumentException if the argument is invalid
     * @throws NullPointerException     if the argument is null
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    /**
     * Execute the ADD command, this adds the book entries from the given files.
     * The file path is expected to be loaded before execution.
     * If invalid path is given or the file is not formatted well, error message will be displayed to console.
     * If error is encountered during execution, the library will not be changed.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException library data is null or file path is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null");
        Objects.requireNonNull(dataFileUrl, "Given file Url must not be null");

        Path dataFile = Paths.get(dataFileUrl);
        data.loadData(dataFile);

    }

    /**
     * Check whether the file path has the allowed file extension.
     * Note this method will not check the validity of the file path.
     * Currently, only one extension ".csv" is supported. If you want to allow more extensions please add them to AllowedFileExtension enum.
     *
     * @param argumentInput argument input for this command
     * @return true if argument ends with supported file extension, false otherwise
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (AllowedFileExtension.getAllowedFileExtension(argumentInput) == null) return false;
        this.dataFileUrl = argumentInput;
        return true;
    }

}