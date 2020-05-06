/**
 * All the title prefix in order derived from book title.
 */
public enum TitlePrefix {
    A, B, C, D, E, F, G, H, I, J,
    K, L, M, N, O, P, Q, R, S, T,
    U, V, W, X, Y, Z,
    /**
     * It represents the digits (0-9)
     */
    DIGIT {
        /**
         * Display its corresponding group label
         * @return group label for DIGIT
         */
        @Override
        public String toString() {
            return "[0-9]";
        }
    };

    /**
     * Parse the title to relevant TitlePrefix object by fetching its first character.
     * If invalid title is passed, null is returned.
     *
     * @param value Book title
     * @return corresponding TitlePrefix object if title is in valid form, null otherwise
     */
    public static TitlePrefix getTitlePrefix(String value) {
        if (value == null || value.isBlank()) return null;

        char firstChar = Character.toUpperCase(value.charAt(0));
        if (Character.isAlphabetic(firstChar)) return TitlePrefix.valueOf(String.valueOf(firstChar));
        else if (Character.isDigit(firstChar)) return TitlePrefix.DIGIT;
        else return null;
    }

}
