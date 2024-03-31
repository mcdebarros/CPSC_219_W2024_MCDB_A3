package projecta3;

import java.util.ArrayList;
import java.util.Random;


public class State {
    ArrayList <Guess> allGuesses;
    ArrayList <String> gameDictionary;
    String secretWord;
    GuessResult lastResult;


    public State()
    {
        allGuesses = new ArrayList<Guess>();
        gameDictionary = new ArrayList<String>();
        secretWord = "NULLS";
        lastResult = GuessResult.NONE;
    }

    public State(ArrayList<String> validWords)
    {
        allGuesses = new ArrayList<Guess>(); // List of used guesses
        gameDictionary = validWords; // List of valid words
        Random rng = new Random(); // rng for picking secret word
        secretWord = (String)gameDictionary.get(rng.nextInt(gameDictionary.size() - 1));

        lastResult = GuessResult.NONE; // No guess result for initial guess.

        // for testing purposes only
        System.out.println(secretWord);
    }


   public Guess makeGuess(String newWord)
    {
        Guess newGuess;
        if (this.gameDictionary.contains(newWord))
        {
            newGuess = new Guess(newWord, this.secretWord);
            this.lastResult = GuessResult.VALID;
            if (newGuess.hasWin())
            {
                this.lastResult = GuessResult.MATCH;
            }
        }
        else if (newWord.length() >= this.secretWord.length())
        {
            newGuess = new Guess(newWord.substring(0,this.secretWord.length()), this.secretWord);
        }
        else
        {
            String paddedWord = String.format("%1$5s",newWord);
            newGuess = new Guess(paddedWord, this.secretWord);
            this.lastResult = GuessResult.INVALID;
        }

        allGuesses.add(newGuess);

        return newGuess;
   }

   public boolean checkWin(int maxTurns)
   {
       boolean hasWin = false;

       if ((allGuesses.size() - 1 <= maxTurns) && (this.lastResult == GuessResult.MATCH))
       {
           hasWin = true;
       }

       return true;
   }

    public GuessResult getLastResult(String guess) {

        Guess dupe = new Guess(guess);

    }
}
