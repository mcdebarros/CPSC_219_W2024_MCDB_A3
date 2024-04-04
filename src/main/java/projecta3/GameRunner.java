package projecta3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameRunner {

    protected final State gameState;

    @SuppressWarnings("unchecked")
    public GameRunner() {

        final ArrayList<String> validWords = new ArrayList<>(); // Empty list of valid words
        try
        { // Populate list from word file
            FileReader fReader = new FileReader("sgb-words.txt");
            BufferedReader bReader = new BufferedReader(fReader);

            String currentLine = null;
            while((currentLine = bReader.readLine()) != null)
            {
                validWords.add(currentLine.strip());
            }
        }
        catch(IOException ioE)
        {
            System.err.println("There seems to be an issue with sgb-words");
        }
        gameState = new State((ArrayList<String>) validWords.clone()); // Create game state
    }

    public Guess processInput(String newWord)
    {
        Guess newestGuess;
        if((newWord == null))
        {
            newestGuess = new Guess();
        }
        assert newWord != null;
        newestGuess = gameState.makeGuess(newWord.toLowerCase());

        return newestGuess;
    }

    public boolean wasGoodGuess()
    {
        return gameState.getLastResult() != GuessResult.INVALID;
    }
}
