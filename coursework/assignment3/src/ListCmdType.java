/**
 * All available list types.
 * <p>
 * Note: since short and long are not allowed, every list type must end with "List".
 * For example, shortList, longList
 */
public enum ListCmdType {
    shortList,
    longList;

    /**
     * Parse the type from String to its relevant ListCmdType object.
     * If empty line is passed, default type short_list is returned.
     * If invalid line is passed, null is returned.
     *
     * @param type type in String
     * @return corresponding ListCmdType object if type is in valid form, null otherwise
     */
    public static ListCmdType getListCmdType(String type) {
        try {
            if (type.isEmpty()) return shortList;
            return ListCmdType.valueOf(type + "List");

        } catch (Exception e) {
            return null;
        }
    }
}
