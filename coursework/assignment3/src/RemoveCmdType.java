/**
 * All available remove types
 */
public enum RemoveCmdType {
    TITLE, AUTHOR;

    /**
     * Parse remove type from String to its relevant RemoveCmdType object.
     * If invalid line is passed, null is returned
     *
     * @param type type in String
     * @return corresponding RemoveCmdType object if type is in valid form, null otherwise
     */
    public static RemoveCmdType getRemoveCmdType(String type) {
        try {
            return RemoveCmdType.valueOf(type);
        } catch (Exception e) {
            return null;
        }
    }
}
