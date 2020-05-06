import java.nio.file.Path;
import java.util.StringJoiner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output related operations
 * such as saving and loading a game.
 */
public class FoxHoundIO {


    private static final int MAX_PLAYERS_IN_DIM_8 = 5;

    /**
     * Save the game to a file (8-dim only)
     * @param players
     * @param nextFigure
     * @param filePath
     * @return true if game is saved to a file, false otherwise
     * @throws NullPointerException Path object is null
     * @throws IllegalArgumentException the dimension is not equal to 8, figure is illegal, or players list is illegal
     */
    public static boolean saveGame(String[] players, char nextFigure, Path filePath)
            throws NullPointerException, IllegalArgumentException {
        if (players.length != MAX_PLAYERS_IN_DIM_8) throw new IllegalArgumentException("Player list given does not imply dimension of 8");
        FoxHoundUtils.checkInputPositionList(players, true);
        FoxHoundUtils.checkInputFigure(nextFigure, true);
        if (filePath == null)
            throw new NullPointerException("File path is not defined");

        File gameFile = filePath.toFile();
        try {
            gameFile.createNewFile();
            FileWriter gameFileWriter = new FileWriter(gameFile);
            StringJoiner stringJoiner = new StringJoiner(" ");
            stringJoiner.add(Character.toString(nextFigure));
            for (String player : players) {
                stringJoiner.add(player);
            }
            gameFileWriter.write(stringJoiner.toString());
            gameFileWriter.close();

        } catch (IOException e) {
            System.err.println("Failed to create the file: unexpected errors");
            return false;
        }
        return true;
    }

    /**
     * Load game from a file (8-dim)
     * @param players
     * @param filePath
     * @return return the next figure of the loaded game. (If '#' is returned, it means the loading error)
     * @throws IllegalArgumentException if the playerd dimension is not equal to 8
     * @throws NullPointerException File path is null
     */
    public static char loadGame(String[] players, Path filePath) throws IllegalArgumentException, NullPointerException
    {
        if (filePath == null) throw new NullPointerException("File path is not defined");
        if (players.length != MAX_PLAYERS_IN_DIM_8) throw new IllegalArgumentException("Player list given does not imply dimension of 8");


        try {
            final Scanner gameFileReader = new Scanner(filePath);
            if(gameFileReader.hasNextLine())
            {
                //load file
                String data = gameFileReader.nextLine();
                gameFileReader.close();
                String[] dataList = data.split(" ");

                //valiadate loaded figure
                char loadedFigure = dataList[0].charAt(0);
                if(!FoxHoundUtils.checkInputFigure(loadedFigure, false)) return '#';

                //validate loaded data
                String[] loadedPlayers = new String[dataList.length-1];
                for (int i = 1; i < dataList.length; i++) {
                    loadedPlayers[i-1] = dataList[i];
                }
                if (loadedPlayers.length != MAX_PLAYERS_IN_DIM_8 || !FoxHoundUtils.checkInputPositionList(loadedPlayers,false)) return '#';
                

                //Write to given players
                for (int i = 0; i < loadedPlayers.length; i++) {
                    players[i] = loadedPlayers[i];
                }
                
                return loadedFigure;
            }
            else
            {
                gameFileReader.close();
                System.err.println("Failed to read the file: no content");
                return '#';
            }

        } catch (IOException e) {
            System.err.println("Failed to read the file: invalid path");
            return '#';
        }
    }

}
