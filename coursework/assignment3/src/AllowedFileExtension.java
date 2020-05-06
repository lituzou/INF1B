/**
 * List of all allowed file extensions
 */
public enum AllowedFileExtension {
    csv;

    /**
     * Get the allowed file extension for a given String file path.
     * If the file is not allowed, null is return.
     * @param path file path in String
     * @return AllowedFileExtension object for this file path
     */
    public static AllowedFileExtension getAllowedFileExtension(String path) {
        if (path == null) return null;
        for (AllowedFileExtension ext : AllowedFileExtension.values()) {
            if (path.endsWith("." + ext.toString())) {
                return ext;
            }
        }
        return null;
    }
}
