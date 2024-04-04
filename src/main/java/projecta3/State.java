package projecta3;

import java.util.ArrayList;
import java.util.Random;


public class State {
    private final ArrayList <Guess> allGuesses;
    private final ArrayList <String> gameDictionary;
    private final String secretWord;
    private GuessResult lastResult;


    public State()
    {
        allGuesses = new ArrayList<Guess>();
        gameDictionary = new ArrayList<>();
        secretWord = "NULLS";
        lastResult = GuessResult.NONE;
    }

    public State(ArrayList<String> validWords)
    {
        allGuesses = new ArrayList<Guess>(); // List of used guesses
        gameDictionary = validWords; // List of valid words
        Random rng = new Random(); // rng for picking secret word
        secretWord = gameDictionary.get(rng.nextInt(gameDictionary.size() - 1));

        lastResult = GuessResult.NONE; // No guess result for initial guess.

        // for testing purposes only
        System.out.println(secretWord);
    }

    public State(State that) {
        this.allGuesses = that.allGuesses;
        this.gameDictionary = that.gameDictionary;
        this.secretWord = that.secretWord;
        this.lastResult = that.lastResult;
    }

   public Guess makeGuess(String newWord) {
        Guess newGuess;
        if (this.gameDictionary.contains(newWord)) {
            newGuess = new Guess(newWord, this.secretWord);
            this.lastResult = GuessResult.VALID;
            if (newGuess.hasWin()) {
                this.lastResult = GuessResult.MATCH;
            }
        } else if (newWord.length() >= this.secretWord.length()) {
            newGuess = new Guess(newWord.substring(0,this.secretWord.length()), this.secretWord);
        } else {
            String paddedWord = String.format("%1$5s",newWord);
            newGuess = new Guess(paddedWord, this.secretWord);
            this.lastResult = GuessResult.INVALID;
        }
        allGuesses.add(newGuess);
        return newGuess;
   }

   public boolean checkWin() {
       int maxTurns = 6;
       return ((allGuesses.size() <= maxTurns) && (this.lastResult == GuessResult.MATCH));
   }

   public GuessResult getLastResult() {
        State dupe = new State(this);
        return dupe.lastResult;
    }

   public int guessesUsed() {
        return allGuesses.size();
   }
}
