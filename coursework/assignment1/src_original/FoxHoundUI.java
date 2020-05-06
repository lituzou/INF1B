import java.util.Scanner;
import java.util.StringJoiner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all user interface related
 * functionality such as printing menus and displaying the game board.
 */
public class FoxHoundUI {

    /** Error message for null scanner */
    private static final String NULL_STDIN_MSG = "Given Scanner must not be null";
    /** Number of main menu entries. */
    private static final int MENU_ENTRIES = 4;
    /** Main menu display string. */
    private static final String MAIN_MENU =
        "\n1. Move\n2. Save\n3. Load\n4. Exit\n\nEnter 1 - 4:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;
    /** Menu entry to save the game*/
    public static final int MENU_SAVE = 2;
    /** Menu entry to load the game */
    public static final int MENU_LOAD = 3;
    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 4;

    /**
     * Print the basic display board according to the dimension and players' list
     * @param players list of players' coordinates
     * @param dimension
     * @throws NullPointerException if players is null
     * @throws IllegalArgumentException if any coordinates in players are invalid
     * @throws IllegalArgumentException if dimension is out of range 
     */
    public static void displayBoard(String[] players, int dimension) throws NullPointerException, IllegalArgumentException
    {
        FoxHoundUtils.checkInputPositionList(players, true);
        FoxHoundUtils.checkInputDimension(dimension, true);

        final boolean LEADING_ZERO = dimension > 9;
        //get board in char matrix
        char[][] board = boardMatrix(players, dimension, false);

        StringBuilder output = new StringBuilder();
        String columnLabelString  = columnLabel(LEADING_ZERO, dimension, false);
        //print first row label
        output.append(columnLabelString);
        output.append("\n");
        //print board rows
        output.append(boardRows(LEADING_ZERO, dimension, board, false));
        output.append("\n");
        //print last row label
        output.append(columnLabelString);

        //print output
        System.out.println(output.toString());
    }

    /**
     * Print the fancy display board according to the dimension and players' list
     * @param players list of players' coordinates
     * @param dimension
     * @throws NullPointerException if players is null
     * @throws IllegalArgumentException if any coordinates in players are invalid
     * @throws IllegalArgumentException if dimension is out of range 
     */
    public static void displayBoardFancy(String[] players, int dimension) throws NullPointerException, IllegalArgumentException
    {
        FoxHoundUtils.checkInputPositionList(players, true);
        FoxHoundUtils.checkInputDimension(dimension, true);

        final boolean LEADING_ZERO = dimension > 9;
        //get board in char matrix
        char[][] board = boardMatrix(players, dimension, true);

        StringBuilder output = new StringBuilder();
        String columnLabelString  = columnLabel(LEADING_ZERO, dimension, true);
        //print first row label
        output.append(columnLabelString);
        output.append("\n");
        //print board rows
        output.append(boardRows(LEADING_ZERO, dimension, board, true));
        output.append("\n");
        //print last row label
        output.append(columnLabelString);

        //print output
        System.out.println(output.toString());
    }

    /**
     * Component of board. 
     * Caution: check the input parameters at the calling code 
     * @param players list of players' coordinates
     * @param dimension
     * @param FANCY true if applicable to fancy display board, false otherwise
     * @return char matrix listing the figure at each coordinate 
     */
    private static char[][] boardMatrix (String[] players, int dimension, boolean FANCY)
    {
        char emptyChar = FANCY ? ' ' : '.';
        char[][] board = new char[dimension][dimension];
        //put dots to the board
        for (int i=0; i < dimension; i++) for (int j=0; j < dimension;j++) board[i][j] = emptyChar;
        //put locations on board
        for (int i=0; i < players.length; i++)
        {
            String player = players[i];
            int row = FoxHoundUtils.getRowNumber(player);
            int col = FoxHoundUtils.getColumnNumber(player);
            char field = i == players.length-1 ? FoxHoundUtils.FOX_FIELD : FoxHoundUtils.HOUND_FIELD;
            board[row][col] = field;
        }
        return board;
    }

    /**
     * Component of board. 
     * Caution: check the input parameters at the calling code 
     * @param LEADING_ZERO if label contains more than one digit (e.g. 01, 02, ...)
     * @param dimension 
     * @param FANCY true if applicable to fancy display board, false otherwise
     * @return Column row in string
     */
    private static String columnLabel (boolean LEADING_ZERO, int dimension, boolean FANCY)
    {
        String padding = FANCY ? (LEADING_ZERO ? "     " : "    ") : (LEADING_ZERO ? "   " : "  ");
        String delimiter = FANCY ? "   " : "";
        StringJoiner output = new StringJoiner(delimiter,padding,padding);
        for(int i = 0; i < dimension; i++)
        {
            char columnChar = FoxHoundUtils.getColumnChar(i);
            output.add(Character.toString(columnChar));
        }
        return output.toString();
    }

    /**
     * Component of board. 
     * Caution: check the input parameters at the calling code 
     * @param LEADING_ZERO if label contains more than one digit (e.g. 01, 02, ...)
     * @param dimension
     * @param board
     * @param FANCY true if applicable to fancy display board, false otherwise
     * @return
     */
    private static String boardRows(boolean LEADING_ZERO, int dimension, char[][] board, boolean FANCY)
    {
        StringJoiner output;
        //StringJoiner initialisation
        if(FANCY)
        {
            String fancyPadding = LEADING_ZERO ? "   " : "  ";
            StringJoiner fancyBorderJoiner = new StringJoiner("|", "\n"+fancyPadding + "|", "|" + fancyPadding + "\n");
            for(int i = 0; i < dimension; i++) fancyBorderJoiner.add("===");
            String fancyRow = fancyBorderJoiner.toString();
            output = new StringJoiner(fancyRow, fancyRow, fancyRow);
        }
        else
        {
            output = new StringJoiner("\n", "\n", "\n");
        }

        //row builder
        
        for (int i = 0; i < dimension; i++) {
            char[] row = board[i];
            
            String rowString;
            if(FANCY)
            {
                StringJoiner rowStringJoiner = new StringJoiner(" | ", "| ", " |");
                for (char c : row) {
                    rowStringJoiner.add(Character.toString(c));
                }
                rowString = rowStringJoiner.toString();
            }
            else
            {
                rowString = new String(row);
            }
            String rowLabel = (i+1<10 && LEADING_ZERO) ? ("0" + Integer.toString(i+1)) : (Integer.toString(i+1));
            output.add(String.format("%s %s %s", rowLabel, rowString, rowLabel));
        }
        return output.toString();
    }


    /**
     * Print the main menu and query the user for an entry selection.
     * @param figureToMove the figure type that has the next move
     * @param stdin a Scanner object to read user input from
     * @return a number representing the menu entry selected by the user
     * @throws IllegalArgumentException if the given figure type is invalid
     * @throws NullPointerException if the given Scanner is null
     */
    public static int mainMenuQuery(char figureToMove, Scanner stdin) throws IllegalArgumentException, NullPointerException
    {
        Objects.requireNonNull(stdin, NULL_STDIN_MSG);
        if (figureToMove != FoxHoundUtils.FOX_FIELD 
         && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }

        String nextFigure = 
            figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";

        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }

            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }

            stdin.nextLine(); // throw away the rest of the line
        }

        return input;
    }

    /**
     * Ask the coordinate from standard input
     * @param dimension
     * @param stdin Scanner
     * @return list containing two coordinates {origin, destination}
     * @throws IllegalArgumentException if dimension is out of range 
     * @throws NullPointerException if stdin is null
     */
    public static String[] positionQuery(int dimension, Scanner stdin) throws NullPointerException, IllegalArgumentException
    {
        //Check input dimension
        FoxHoundUtils.checkInputDimension(dimension, true);
        //Check standard input
        Objects.requireNonNull(stdin, NULL_STDIN_MSG);
        
        boolean exit = false;
        String[] positions = {};

        while(!exit)
        {
            //Print description
            System.out.println("Provide origin and destination coordinates.");
            System.out.printf("Enter two positions between %s-%s:\n",FoxHoundUtils.getPositionString(0,0), FoxHoundUtils.getPositionString(dimension-1, dimension-1));
            //Receive and process input
            String input = stdin.nextLine();
            positions = input.toUpperCase().split(" ");
            //Basic input validation
            boolean validationResult = true;
            if(positions.length != 2) validationResult = false;
            else if (!FoxHoundUtils.checkInputPosition(positions[0], false)) validationResult = false;
            else if (!FoxHoundUtils.checkInputPosition(positions[1], false)) validationResult = false;

            System.out.println();
            if(validationResult) exit = true;
            else System.err.println("ERROR: Please enter valid coordinate pair separated by space.\n");
        }

        return positions;
    }

    /**
     * Ask the path for saving or loading game 
     * @param stdin standard input 
     * @return the path that user wishes to save or load (However, the path is not checked)
     * @throws NullPointerException if standard input is null
     */
    public static Path fileQuery(Scanner stdin) throws NullPointerException
    {
        Objects.requireNonNull(stdin, NULL_STDIN_MSG);
        final String FILE_QUERY_MSG = "Enter file path:";
        System.out.println(FILE_QUERY_MSG);
        String inputPath = stdin.nextLine();
        Path path = Paths.get(inputPath);
        return path;
    }
}







