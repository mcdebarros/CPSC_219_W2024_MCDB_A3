package assignmentThree;

import java.util.ArrayList;
import java.util.Random;

/**
 * State object which stores information on game progression
 */
public class State {

    private final ArrayList<String> allGuesses; // String array of all used guesses
    private final ArrayList<String> GAME_DICTIONARY; // Dictionary of valid guesses
    private final String SECRET_WORD;
    private GuessResult guessResult; // Result of latest guess
    private static final int NUM_CHARS = 5; // This game's word length
    private static final int MAX_GUESSES = 6;
    private int[] letterMatch; // Array of letter statuses
    private final char[] SECRET_CHARS; // Character array of secret word content

    /**
     * Default state constructor
     */
    protected State() {
        allGuesses = new ArrayList<>();
        GAME_DICTIONARY = Guess.getGameDictionary();
        SECRET_WORD = pickSecret(GAME_DICTIONARY);
        guessResult = GuessResult.NO_WIN;
        SECRET_CHARS = parseSecret();
        letterMatch = new int[NUM_CHARS];
        for (int i = 0; i < NUM_CHARS;i++) {
            letterMatch[i] = LetterState.NONE.ordinal(); // Initializes the letterMatch array as a zero-array based on LetterState ordering
        }
    }

    /**
     * State copy constructor to guarantee state encapsulation in getter and check methods
     * @param that State to dupe
     */
    private State(State that) {
        this.allGuesses = that.allGuesses;
        this.SECRET_WORD = that.SECRET_WORD;
        this.GAME_DICTIONARY = that.GAME_DICTIONARY;
        this.guessResult = that.guessResult;
        this.letterMatch = that.letterMatch;
        this.SECRET_CHARS = that.SECRET_CHARS;
    }

    /**
     * Update method for the State object
     * @param guess Guess with which the state is updated
     */
    protected void updateState(Guess guess) {

       allGuesses.add(guess.getGuessString()); // Adds the guess to list of used guesses (it has been checked in GameRunner)
        if (guess.getGuessString().equals(SECRET_WORD)) { // Win if the guess is the secret word
            guessResult = GuessResult.WIN;
        } else if (allGuesses.size() == MAX_GUESSES) { // Lose if the guess is not the secret word and this is the 6th valid guess
            guessResult = GuessResult.LOSE;
       }
        letterMatch = setLetterStates(guess.getGuessString()); // Assess the letter states for this guess
    }

    /**
     * Check if the game has been won
     * @return Boolean of game victory
     */
    protected boolean checkWin() {

        State dupe = new State(this);
        return dupe.guessResult.equals(GuessResult.WIN);
    }

    /**
     * Check if the game has been lost
     * @return Boolean of your failure
     */
   protected boolean checkLoss() {

        State dupe = new State(this);
        return dupe.guessResult.equals(GuessResult.LOSE);
   }

    /**
     * Count the number of guesses used based on the size of the allGuesses array
     * @return int of number of guesses used
     */
   protected int guessesUsed() {

        State dupe = new State(this);
        return dupe.allGuesses.size();
   }

    /**
     * Picks a secret word at random from the game dictionary
     * @param dictionary ArrayList of valid word strings
     * @return the secret word string
     */
   private String pickSecret(ArrayList<String> dictionary) {

       Random rng = new Random(); // rng for picking secret word
       return dictionary.get(rng.nextInt(dictionary.size() - 1)); // The rng chooses a secret word from a random index of the ArrayList within its size
   }

    /**
     * Parses the secret word into a character array
     * @return character array of the secret word
     */
   private char[] parseSecret() {

        char[] secretArray = new char[NUM_CHARS];
        for (int i = 0; i < NUM_CHARS; i++) {
            secretArray[i] = SECRET_WORD.charAt(i);
        }
        return secretArray;
   }

    /**
     * Assigns letter states based on position and correctness criteria of the Wordle game
     * @param word Guess string for which to assign states
     * @return integer array of letter states
     */
   private int[] setLetterStates(String word) {

        int[] states = new int[NUM_CHARS];
        for (int i = 0; i < NUM_CHARS; i++) {
            if (SECRET_WORD.contains(String.valueOf(word.charAt(i)))) {
                states[i] = LetterState.IN.ordinal(); // Set this entry to 1 if the character is in the secret word
                if (SECRET_WORD.charAt(i) == word.charAt(i)) {
                    states[i] = LetterState.EXACT.ordinal(); // Set to 2 if the character is also in the same position as in the secret word
                }
            } else {
                states[i] = LetterState.NONE.ordinal(); // Set this position to 0 if the letter is not in the secret word
            }
        }
        return states;
   }

    /**
     * Fetch the maximum number of guesses for this implementation of the state object
     * @return integer of maximum allowable valid guesses
     */
   public static int maxGuesses() {return MAX_GUESSES;}

    /**
     * Fetches all guesses used in this iteration of the State
     * @return ArrayList of all guesses made
     */
   protected ArrayList<String> getAllGuesses() {

        State dupe = new State(this);
        return dupe.allGuesses;
   }

    /**
     * Fetches the word length for this iteration of the State object
     * @return integer of the Guess and Secret word length
     */
   protected static int getLength() {return NUM_CHARS;}

    /**
     * Fetches the integer array of letter state integers for the most recent guess
     * @return integer array of most recently assigned letter states
     */
    protected int[] getLetterStates() {

        State dupe = new State(this);
        return dupe.letterMatch;
    }

    /**
     * Fetch the secret word if the user loses
     * @return String of the secret word ONLY if the game has been lost
     */
    protected String consolation() {if (this.checkWin()) {return SECRET_WORD;} else {return null;}}
}
