package projecta3;

import java.util.Arrays;

public class Guess {
    private String secretWord;
    private String newGuess;
    private final int[] letterStatus;
    private final String DEFAULT_WORD = "START";

    private enum LetterStates
    {
        NO_MATCH,
        IN_WORD,
        EXACT_MATCH;
    }

    public Guess() {
        secretWord = new String(DEFAULT_WORD);
        letterStatus = new int[secretWord.length()];
        for (int i = 0; i < secretWord.length();i++)
        {
            letterStatus[i] = LetterStates.NO_MATCH.ordinal();
        }

    }

    public Guess(String newGuess, String secretWord)
    {
        this.secretWord = secretWord;
        this.newGuess = newGuess;
        letterStatus = new int[secretWord.length()];
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.contains(String.valueOf(newGuess.charAt(i)))) {
                letterStatus[i] = LetterStates.IN_WORD.ordinal();
                if (secretWord.charAt(i) == newGuess.charAt(i))
                    letterStatus[i] = LetterStates.EXACT_MATCH.ordinal();
            }
        }
    }

    public Guess(Guess that) {
        this.secretWord = that.secretWord;
        this.newGuess = that.newGuess;
        this.letterStatus = that.letterStatus;
    }

    public boolean hasWin()
    {
        boolean winState = true;
        for (int i = 0; i < secretWord.length(); i++)
        {
            if (getLetterStatus()[i] < LetterStates.EXACT_MATCH.ordinal())
                winState = false;
        }
        return winState;
    }

    public int[] getLetterStatus()
    {
        return letterStatus;
    }

    @Override
    public String toString() {
        return "State{" +
                "secretWord='" + secretWord + '\'' +
                ", letterStatus=" + Arrays.toString(letterStatus) +
                '}';
    }
}
