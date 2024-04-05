package assignmentThree;

import java.util.ArrayList;
import java.util.Random;

public class State {

    private final ArrayList<String> allGuesses;
    private final ArrayList<String> GAME_DICTIONARY;
    private final String SECRET_WORD;
    private WinState guessResult;
    private static final int NUM_CHARS = 5;
    private static final int MAX_GUESSES = 6;
    private int[] letterMatch;
    private final char[] SECRET_CHARS;

    private enum WinState {
        WIN,
        NO_WIN,
        LOSE
    }

    private enum LetterState {
        NONE,
        IN,
        EXACT
    }

    protected State() {
        allGuesses = new ArrayList<>();
        GAME_DICTIONARY = Guess.getGameDictionary();
        SECRET_WORD = pickSecret(GAME_DICTIONARY);
        guessResult = WinState.NO_WIN;
        SECRET_CHARS = parseSecret();
        letterMatch = new int[NUM_CHARS];
        for (int i = 0; i < NUM_CHARS;i++) {
            letterMatch[i] = LetterState.NONE.ordinal();
        }
    }

    private State(State that) {
        this.allGuesses = that.allGuesses;
        this.SECRET_WORD = that.SECRET_WORD;
        this.GAME_DICTIONARY = that.GAME_DICTIONARY;
        this.guessResult = that.guessResult;
        this.letterMatch = that.letterMatch;
        this.SECRET_CHARS = that.SECRET_CHARS;
    }

   protected void updateState(Guess guess) {

       allGuesses.add(guess.getGuessString());
        if (guess.getGuessString().equals(SECRET_WORD)) {
            guessResult = WinState.WIN;
        } else if (allGuesses.size() == MAX_GUESSES) {
            guessResult = WinState.LOSE;
       }
        letterMatch = setLetterStates(guess.getGuessString());
   }

   protected String checkWin() {
        State dupe = new State(this);
        switch (dupe.guessResult) {
            case WIN -> {return "WIN";}
            case LOSE -> {return "LOSE";}
            case NO_WIN -> {return "NO WIN";}
            default -> {return "NO_WIN";}
        }
   }

   protected int guessesUsed() {
        State dupe = new State(this);
        return dupe.allGuesses.size();
   }

   private String pickSecret(ArrayList<String> dictionary) {

       Random rng = new Random(); // rng for picking secret word
       return dictionary.get(rng.nextInt(dictionary.size() - 1));
   }

   private char[] parseSecret() {

        char[] secretArray = new char[NUM_CHARS];
        for (int i = 0; i < NUM_CHARS; i++) {
            secretArray[i] = SECRET_WORD.charAt(i);
        }
        return secretArray;
   }

   private int[] setLetterStates(String word) {

        int[] states = new int[NUM_CHARS];
        for (int i = 0; i < NUM_CHARS; i++) {
            if (SECRET_WORD.contains(String.valueOf(word.charAt(i)))) {
                states[i] = LetterState.IN.ordinal();
                if (SECRET_WORD.charAt(i) == word.charAt(i)) {
                    states[i] = LetterState.EXACT.ordinal();
                }
            } else {
                states[i] = LetterState.NONE.ordinal();
            }
        }
        return states;
   }

   public static int maxGuesses() {
        return MAX_GUESSES;
   }

   protected ArrayList<String> getAllGuesses() {
        State dupe = new State(this);
        return dupe.allGuesses;
   }

   protected static int getLength() {return NUM_CHARS;}

    protected int[] getLetterStates() {

        State dupe = new State(this);
        return dupe.letterMatch;
    }

    protected String consolation() {
        return SECRET_WORD;
    }
}
