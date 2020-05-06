import java.nio.file.Path;
import java.util.Scanner;

/** 
 * The Main class of the fox hound program.
 * 
 * It contains the main game loop where main menu interactions
 * are processed and handler functions are called.
  */
public class FoxHoundGame {

    /** 
     * This scanner can be used by the program to read from
     * the standard input. 
     * 
     * Every scanner should be closed after its use, however, if you do
     * that for StdIn, it will close the underlying input stream as well
     * which makes it difficult to read from StdIn again later during the
     * program.
     * 
     * Therefore, it is advisable to create only one Scanner for StdIn 
     * over the course of a program and only close it when the program
     * exits. Additionally, it reduces complexity. 
     */
    private static final Scanner STDIN_SCAN = new Scanner(System.in);
    /** Error msg if file is not successfully saved */
    private static final String SAVE_ERR_MSG = "ERROR: Saving file failed.";
    /** Error msg if file is not successfully loaded */
    private static final String LOAD_ERR_MSG = "ERROR: Loading file failed.";
    /** Error msg when entering invalid positions of origin and destination */
    private static final String POSITION_ERR_MSG = "ERROR: positions of origin and destination are invalid.";
    /** Fox winning msg */
    private static final String FOX_WIN_MSG = "The Fox wins!";
    /** Hound winning msg */
    private static final String HOUND_WIN_MSG = "The Hound wins!";
    
    /**
     * Swap between fox and hounds to determine the next
     * figure to move.
     * 
     * @param currentTurn last figure to be moved
     * @return next figure to be moved
     */
    private static char swapPlayers(char currentTurn) {
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    /**
     * The main loop of the game. Interactions with the main
     * menu are interpreted and executed here.
     * 
     * @param dimension the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     */
    private static void gameLoop(int dimension, String[] players) {

        // start each game with the Fox
        char turn = FoxHoundUtils.FOX_FIELD;
        boolean exit = false;
        while(!exit) {
            System.out.println("\n#################################");
            FoxHoundUI.displayBoard(players, dimension);

            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);
            
            // handle menu choice
            switch(choice) {
                case FoxHoundUI.MENU_MOVE:
                    //Ensure the loop to begin
                    boolean validInput = false;
                    //Initialise origin and destination
                    String origin = "";
                    String destination = "";
                    //Infinite loop until valid position is secured
                    while (!validInput)
                    {
                        //Get position
                        String[] positions = FoxHoundUI.positionQuery(dimension, STDIN_SCAN);
                        origin = positions[0];
                        destination = positions[1];
                        //Check if it is a valid move. If yes, escape from the loop. Otherwise, print error message
                        if(FoxHoundUtils.isValidMove(dimension, players, turn, origin, destination)) validInput = true;
                        else System.err.println(POSITION_ERR_MSG);
                    }
                    //Update coordinate, knowing that position is valid
                    FoxHoundUtils.updatePositions(players, origin, destination);
                    //Swap player
                    turn = swapPlayers(turn);
                    break;
                
                case FoxHoundUI.MENU_SAVE:
                    //Receive path
                    Path savePath = FoxHoundUI.fileQuery(STDIN_SCAN);
                    //Attempt to save the file, if failed, print error message
                    if(!FoxHoundIO.saveGame(players, turn, savePath)) System.err.println(SAVE_ERR_MSG);
                    break;

                case FoxHoundUI.MENU_LOAD:
                    //Receive path
                    Path loadPath = FoxHoundUI.fileQuery(STDIN_SCAN);
                    //Try to load the file and get the next figure. If valid file is not found, '#' is returned
                    char nextFigure = FoxHoundIO.loadGame(players, loadPath);
                    if(!FoxHoundUtils.checkInputFigure(nextFigure, false)) System.err.println(LOAD_ERR_MSG);
                    else turn = nextFigure;
                    break;

                case FoxHoundUI.MENU_EXIT:
                    exit = true;
                    break;

                default:
                    System.err.println("ERROR: invalid menu choice: " + choice);
            }

            //decide if any player win
            //check fox first, then check hound
            if(FoxHoundUtils.isFoxWin(players[players.length - 1]))
            {
                System.out.println(FOX_WIN_MSG);
                exit = true;
            }
            else if(FoxHoundUtils.isHoundWin(players, dimension))
            {
                System.out.println(HOUND_WIN_MSG);
                exit = true;
            }
        }
    }

    /**
     * Entry method for the Fox and Hound game. 
     * 
     * The dimensions of the game board can be passed in as
     * optional command line argument.
     * 
     * If no argument is passed, a default dimension of 
     * {@value FoxHoundUtils#DEFAULT_DIM} is used. 
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be
     * board dimensions.
     */
    public static void main(String[] args) {
        //Initialise dimension
        int dimension;
        try 
        {
            //Attempt to get dimention fron args[0]
            int inputDimension = Integer.parseInt(args[0]);
            FoxHoundUtils.checkInputDimension(inputDimension, true);
            dimension = inputDimension;
        } 
        catch (Exception e) 
        {
            //Use default dimension if exception is catched
            dimension = FoxHoundUtils.DEFAULT_DIM;
        }

        //Initialise the board given the dimension
        String[] players = FoxHoundUtils.initialisePositions(dimension);
        //Start the game loop
        gameLoop(dimension, players);

        // Close the scanner reading the standard input stream       
        STDIN_SCAN.close();
    }
}
