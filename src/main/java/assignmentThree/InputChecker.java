package assignmentThree;

/**
 * Utility class to check guess validity
 */
public class InputChecker {

    /**
     * Amalgamates all validity criteria
     * @param guess Guess to assert validity
     * @param state Game state from which to reference guesses already used
     * @return boolean of guess validity
     */
    protected static boolean goodInput(Guess guess, State state) {

        return (onlyAlpha(guess) && correctLength(guess) && inList(guess) && notNull(guess) && notEmpty(guess) && notGuessed(guess,state));
    }

    /**
     * Checks to see if all entries in the guess string are alphabetic
     * @param guess Guess to assert validity
     * @return boolean of guess's alphabetic content
     */
    private static boolean onlyAlpha(Guess guess) {
        boolean onlyAlpha = true;
        for (Character letter : guess.getGuessString().toCharArray()) {
            if (!Character.isLetter(letter)) {
                return false;
            }
        }
        return onlyAlpha;
    }

    /**
     * Checks if the guess is exactly 5 characters
     * @param guess guess to check length
     * @return boolean of valid guess length
     */
    private static boolean correctLength(Guess guess) {return guess.getLength() == State.getLength();}

    /**
     * Checks that the guess is in the list of permitted words
     * @param guess Guess to search for in dictionary
     * @return boolean of presence of guess in dictionary
     */
    private static boolean inList(Guess guess) {return Guess.getGameDictionary().contains(guess.getGuessString());}

    /**
     * Checks if the guess string is not null
     * @param guess Guess to check for null pointer
     * @return Boolean of null guess
     */
    private static boolean notNull(Guess guess) {return guess.getGuessString() != null;}

    /**
     * Checks if the guess string is not empty
     * @param guess Guess to check content of
     * @return Boolean of guess emptiness
     */
    private static boolean notEmpty(Guess guess) {return !guess.getGuessString().isEmpty();}

    /**
     * Checks if the guess has been guessed before
     * @param guess Guess to check repetition
     * @param state State containing array of previous guesses
     * @return boolean of guess repetition
     */
    private static boolean notGuessed(Guess guess, State state) {return !state.getAllGuesses().contains(guess.getGuessString());}
}
