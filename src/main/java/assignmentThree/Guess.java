package assignmentThree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Guess{

    private final String newGuess;
    private final int guessLength;
    private static final ArrayList<String> GAME_DICTIONARY = makeDictionary();

    protected Guess(String newGuess) {

        this.newGuess = newGuess.toLowerCase();
        guessLength = newGuess.length();
    }

    protected Guess() {

        newGuess = null;
        guessLength = 0;
    }

    private Guess(Guess that) {

        this.newGuess = that.newGuess;
        this.guessLength = that.guessLength;
    }

    @Override
    public String toString() {

        Guess dupe = new Guess(this);
        return STR."Guess: \{dupe.newGuess}";
    }

    private static ArrayList<String> makeDictionary() {

        ArrayList<String> dictionary = new ArrayList<>();
        try {
            FileReader fReader = new FileReader("sgb-words.txt");
            BufferedReader bReader = new BufferedReader(fReader);

            String currentLine;
            while((currentLine = bReader.readLine()) != null) {
                dictionary.add(currentLine.strip());
            }
        }
        catch(IOException ioE) {
            System.err.println("Could not write game dictionary from sgb-words. Check file and retry.");
        }
        return dictionary;
    }

    protected String getGuessString() {

        Guess dupe = new Guess(this);
        return dupe.newGuess;
    }

    protected static ArrayList<String> getGameDictionary() {return GAME_DICTIONARY;}

    protected int getLength() {

        Guess dupe = new Guess(this);
        return dupe.guessLength;
    }
}
