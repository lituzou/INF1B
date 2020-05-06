/**
 * All available group type
 */
public enum GroupCmdType {
    TITLE, AUTHOR;

    /**
     * Parse group type from String to its relevant GroupCmdType object.
     * If invalid line is passed, null is returned
     *
     * @param type type in String
     * @return corresponding GroupCmdType object if type is in valid form, null otherwise
     */
    public static GroupCmdType getGroupCmdType(String type) {
        try {
            return GroupCmdType.valueOf(type);
        } catch (Exception e) {
            return null;
        }
    }
}
