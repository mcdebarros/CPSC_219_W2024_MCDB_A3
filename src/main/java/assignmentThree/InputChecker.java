package assignmentThree;

public class InputChecker {

    protected static boolean goodInput(Guess guess, State state) {

        return (onlyAlpha(guess) && correctLength(guess) && inList(guess) && notNull(guess) && notEmpty(guess) && notGuessed(guess,state));
    }

    private static boolean onlyAlpha(Guess guess) {
        boolean onlyAlpha = true;
        for (Character letter : guess.getGuessString().toCharArray()) {
            if (!Character.isLetter(letter)) {
                return false;
            }
        }
        return onlyAlpha;
    }

    private static boolean correctLength(Guess guess) {return guess.getLength() == State.getLength();}

    private static boolean inList(Guess guess) {return Guess.getGameDictionary().contains(guess.getGuessString());}

    private static boolean notNull(Guess guess) {return guess.getGuessString() != null;}

    private static boolean notEmpty(Guess guess) {return !guess.getGuessString().isEmpty();}

    private static boolean notGuessed(Guess guess, State state) {return !state.getAllGuesses().contains(guess.getGuessString());}
}
