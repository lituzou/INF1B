/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
public class FoxHoundUtils {

    // ATTENTION: Changing the following given constants can 
    // negatively affect the outcome of the auto grading!

    /** Default dimension of the game board in case none is specified. */
    public static final int DEFAULT_DIM = 8;
    /** Minimum possible dimension of the game board. */
    public static final int MIN_DIM = 4;
    /** Maximum possible dimension of the game board. */
    public static final int MAX_DIM = 26;
    /** Minimum possible length of coordinate */
    public static final int MIN_POSITION_LENGTH = 2;
    /** Maximum possible length of coordinate */
    public static final int MAX_POSITION_LENGTH = 3;

    /** Symbol to represent a hound figure. */
    public static final char HOUND_FIELD = 'H';
    /** Symbol to represent the fox figure. */
    public static final char FOX_FIELD = 'F';

    // HINT Write your own constants here to improve code readability ...

    /**
     * Initialise the board given the dimension 
     * @param dimension
     * @return positions of hounds and fox in a String array form 
     * @throws IllegalArgumentException if dimension is out of the given range 
     */
    public static String[] initialisePositions(int dimension) throws IllegalArgumentException
    {

        //Handle the input dimension
        checkInputDimension(dimension, true);

        //Known constants
        final int HOUND_NUM = dimension / 2;
        final int INIT_FIRST_HOUND_COL = 1;
        final int INIT_HOUND_ROW = 0;

        final int INIT_FOX_COL = dimension / 2;
        final int INIT_FOX_ROW = dimension - 1;

        //Create an instance of String Array containing the positions of hounds and fox
        String[] players = new String[HOUND_NUM+1];

        for (int i = 0; i < HOUND_NUM; i++)
        {
            int columnNumber = INIT_FIRST_HOUND_COL + i*2;
            String houndPosition = getPositionString(columnNumber, INIT_HOUND_ROW);
            players[i] = houndPosition;
        }

        String foxPosition = getPositionString(INIT_FOX_COL, INIT_FOX_ROW);
        players[HOUND_NUM] = foxPosition;

        return players;



    }

    /**
     * Get zero-based column number from the player position
     * @param player position coordinate 
     * @return zero-based column number
     * @throws IllegalArgumentException if player position is not valid
     */
    public static int getColumnNumber(String player) throws IllegalArgumentException
    {
        checkInputPosition(player, true);
        int number = player.charAt(0) - (int)'A';
        return number;
    }

    /**
     * Convert the zero-based column number to its corresponding column label
     * @param columnNumber zero-based column number
     * @return Column Character starting from A
     * @throws IllegalArgumentException if the columm number is negative
     */
    public static char getColumnChar(int columnNumber) throws IllegalArgumentException
    {
        checkInputColumnNumber(columnNumber, true);
        char columnChar = (char)('A' + columnNumber);
        return columnChar;
    }


    /**
     * Get zero-based row number from the player position
     * @param player position coordinate 
     * @return zero-based row number
     * @throws IllegalArgumentException if player position is not valid
     */
    public static int getRowNumber(String player) throws IllegalArgumentException
    {
        checkInputPosition(player, true);
        int number = Integer.parseInt(player.substring(1)) - 1;
        return number;
    }

    /**
     * public method for creating position string using column number and row number (all zero-based)
     * @param columnNumber zero-based column number
     * @param rowNumber zero-based row number
     * @return Position string of the player
     * @throws IllegalArgumentException if either column or row number is negative
     */
    public static String getPositionString(int columnNumber, int rowNumber) throws IllegalArgumentException
    {
        checkInputColumnNumber(columnNumber, true);
        checkInputRowNumber(rowNumber, true);
        return getPositionStringNoThrows(columnNumber, rowNumber);
    }

    /**
     * Internal method for creating position string using column number and row number (all zero-based). This method does not throw exception
     * @param columnNumber zero-based column number
     * @param rowNumber zero-based row number
     * @return Position string of the player
     */
    private static String getPositionStringNoThrows(int columnNumber, int rowNumber)
    {
        char columnChar = (char)('A' + columnNumber);
        String location = String.format("%c%d", columnChar, rowNumber + 1);
        return location;
    }

    /**
     * Update the position of the board, you must check if the move is valid at the caller code using isValidMove method.
     * If the origin is not found, players array will not be touched
     * @param players list of coordinates 
     * @param origin 
     * @param destination 
     * @throws IllegalArgumentException if origin, destination or players array is not valid 
     * @throws NullPointerException if players array is null
     */
    public static void updatePositions(String[] players, String origin, String destination) 
        throws IllegalArgumentException, NullPointerException
    {
        //Handle input exceptions
        checkInputPositionList(players, true);
        checkInputPosition(origin, true);
        checkInputPosition(destination, true);
        //Find the origin in the array, then change this to destination
        for (int i = 0; i < players.length; i++) {
            if(players[i].equals(origin)) players[i] = destination;
        }
    }

    /**
     * Verify if fox is winning using its coordinate
     * @param foxPosition
     * @return true if fox wins, false otherwise 
     * @throws IllegalArgumentException if fox coordinate is invalid 
     */
    public static boolean isFoxWin(String foxPosition) throws IllegalArgumentException
    {
        checkInputPosition(foxPosition, true);
        return getRowNumber(foxPosition) == 0;
    }

    /**
     * Verify if hound is winning.
     * Caution: you must be sure that fox does not win yet at the calling code. You can verifiy using isFoxWin method before calling this method
     * @param players
     * @param dimension
     * @return true if hound wins, false otherwise 
     * @throws IllegalArgumentException if players array or dimension is invalid 
     * @throws NullPointerException if players array is null
     */
    public static boolean isHoundWin(String[] players, int dimension)
        throws IllegalArgumentException, NullPointerException
    {
        checkInputPositionList(players, true);
        checkInputDimension(dimension, true);
        String foxPosition = players[players.length - 1];
        int foxColumn = getColumnNumber(foxPosition);
        int foxRow = getRowNumber(foxPosition);
        String[] foxPossibleMove = {
            getPositionStringNoThrows(foxColumn + 1, foxRow + 1),
            getPositionStringNoThrows(foxColumn + 1, foxRow - 1),
            getPositionStringNoThrows(foxColumn - 1, foxRow + 1),
            getPositionStringNoThrows(foxColumn - 1, foxRow - 1)
        };
        for (String move : foxPossibleMove) {
            try {
                if(isValidMove(dimension, players, FOX_FIELD, foxPosition, move)) return false;
            } catch (Exception e) {
                continue;
            }
        }
        return true;
    }

    /**
     * Check if the next move is a valid move 
     * @param dimension
     * @param players list of players' coordinates
     * @param figure 
     * @param origin
     * @param destination
     * @return true if the move is valid, false otherwise
     * @throws NullPointerException list of players' coordinates is null
     * @throws IllegalArgumentException some of coordinates of players, origin, destination, figure and dimension are invalid 
     */
    public static boolean isValidMove(int dimension, String[] players, char figure, String origin, String destination)
        throws IllegalArgumentException, NullPointerException
    {
        //Handle exceptions 
        checkInputPositionList(players, true);
        checkInputFigure(figure, true);
        checkInputDimension(dimension, true);
        checkInputPosition(origin, true);
        checkInputPosition(destination, true);
        checkInputDimWithPositionList(players, dimension, true);

        //Check if the origin and destination are valid coordinates
        if (getColumnNumber(origin) >= dimension || getRowNumber(origin) >= dimension) return false;
        if (getColumnNumber(destination) >= dimension || getRowNumber(destination) >= dimension) return false;

        //Check if origin is in player list
        boolean containsOrigin = false;
        for (int i = 0; i < players.length; i++) {
            String player = players[i];
            if (origin.equals(player))
            {
                containsOrigin = true;
                //Check If figure matches the origin
                char originIdentity = (i == players.length - 1) ? FOX_FIELD : HOUND_FIELD;
                if (originIdentity != figure) return false;
            }
            //Check if the destination is not in player list
            if (destination.equals(player)) return false;
        }
        if (!containsOrigin) return false;


        //Check if the destination can be arrived from the origin
        int relativeColumnDistance = getColumnNumber(destination) - getColumnNumber(origin);
        int relativeRowDistance = getRowNumber(destination) - getRowNumber(origin);
        if(figure == FOX_FIELD && (Math.abs(relativeColumnDistance) != 1 || Math.abs(relativeRowDistance) != 1)) return false;
        if(figure == HOUND_FIELD && (Math.abs(relativeColumnDistance) != 1 || relativeRowDistance != 1)) return false;

        //All tests pass
        return true;
    }

    /**
     * Check the input figure
     * @param figure
     * @param THROW_EXCEPTION true if you want to throw an exception (and crash your software), false otherwise
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if you allow to throw exception if error is found
     */
    public static boolean checkInputFigure(char figure, boolean THROW_EXCEPTION) throws IllegalArgumentException
    {
        if(figure != FOX_FIELD && figure != HOUND_FIELD)
        {
            if(THROW_EXCEPTION) throw new IllegalArgumentException("Figure is neither F nor H");
            return false;
        }
        return true;
    }

    /**
     * Check the dimension
     * @param dimension
     * @param THROW_EXCEPTION true if you want to throw an exception (and crash your software), false otherwise
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if you allow to throw exception if error is found
     */
    public static boolean checkInputDimension(int dimension, boolean THROW_EXCEPTION) throws IllegalArgumentException
    {
        if (!(dimension >= MIN_DIM && dimension <= MAX_DIM))
        {
            if (THROW_EXCEPTION) throw new IllegalArgumentException(String.format("Dimension out of range (%d-%d)", MIN_DIM, MAX_DIM));
            return false;
        }
        return true;
    }

    /**
     * Check the input position
     * @param position
     * @param THROW_EXCEPTION true if you want to throw an exception (and crash your software), false otherwise
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if you allow to throw exception if error is found
     */
    public static boolean checkInputPosition(String position, boolean THROW_EXCEPTION) throws IllegalArgumentException
    {
        if(position.length() < MIN_POSITION_LENGTH || position.length() > MAX_POSITION_LENGTH) 
        {
            if (THROW_EXCEPTION) throw new IllegalArgumentException(String.format("Coordinate should have length of %d-%d", MIN_POSITION_LENGTH, MAX_POSITION_LENGTH));
            return false;
        }
        if (!Character.isUpperCase(position.charAt(0)))
        {
            if (THROW_EXCEPTION) throw new IllegalArgumentException(String.format("Coordinate %s does not contain a valid column label",position));
            return false;
        }
        try {
            Integer.parseInt(position.substring(1));
        } catch (Exception e) {
            if (THROW_EXCEPTION) throw new IllegalArgumentException(String.format("Coordinate %s does not contain a valid column label",position));
            return false;
        }
        
        return true;
    }

    /**
     * Check the input players array
     * @param players players' coordinates
     * @param THROW_EXCEPTION true if you want to throw an exception (and crash your software), false otherwise
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if you allow to throw exception if error is found 
     * @throws NullPointerException if you allow to throw exception if error is found
     */
    public static boolean checkInputPositionList(String[] players, boolean THROW_EXCEPTION) 
        throws IllegalArgumentException, NullPointerException
    {
        if(players == null)
        {
            if(THROW_EXCEPTION) throw new NullPointerException("The player list is null");
            return false;
        }
        int expectedMaxDim = players.length  * 2 - 1;
        for (String player : players) {
            boolean isValid = checkInputPosition(player, THROW_EXCEPTION);
            if(!isValid) return false;
            int columnNumber = getColumnNumber(player);
            int rowNumber = getRowNumber(player);
            if(expectedMaxDim <= columnNumber || expectedMaxDim <= rowNumber) isValid = false;
            if(!isValid) return false;
        }

        return true;
    }

    public static boolean checkInputDimWithPositionList(String[] players, int dimension,boolean THROW_EXCEPTION)
        throws IllegalArgumentException, NullPointerException
    {
        if(!checkInputPositionList(players, THROW_EXCEPTION) || !checkInputDimension(dimension, THROW_EXCEPTION))
        {
            return false;
        }
        int expectedNumPlayer = dimension / 2 + 1;
        if(expectedNumPlayer != players.length)
        {
            if(THROW_EXCEPTION) throw new IllegalArgumentException("Players number does not match the dimension");
            return false;
        }
        return true;
        
    }

    /**
     * Check the input column number 
     * @param columnNumber
     * @param THROW_EXCEPTION true if you want to throw an exception (and crash your software), false otherwise
     * @return true if valid, false otherwise
     * @throws IllegalArgumentException if you allow to throw exception if error is found
     */
    private static boolean checkInputColumnNumber(int columnNumber, boolean THROW_EXCEPTION) throws IllegalArgumentException
    {
        if(columnNumber < 0)
        {
            if (THROW_EXCEPTION) throw new IllegalArgumentException("Column number should start from zero");
            return false;
        }
        return true;
    }

    private static boolean checkInputRowNumber(int rowNumber, boolean THROW_EXCEPTION) throws IllegalArgumentException
    {
        if(rowNumber < 0)
        {
            if (THROW_EXCEPTION) throw new IllegalArgumentException("Row number should start from zero");
            return false;
        }
        return true;

    }



    
}
