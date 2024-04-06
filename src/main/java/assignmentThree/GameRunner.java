package assignmentThree;

public class GameRunner {

    private final State GAME_STATE; // State object of the game
    private Guess latestGuess; // Most recent guess
    private static boolean goodGuess; // Boolean of latestGuess validity

    /**
     * Default constructor for the GameRunner
     */
    public GameRunner() {

        GAME_STATE = new State(); // Create game state
        latestGuess = new Guess(); // Blank guess
        goodGuess = false; // Default to false
    }

    /**
     * Instantiates a new guess object and checks its validity before passing it to the State object
     * @param newWord String with which to instantiate a guess
     * @return boolean of guess validity
     */
    protected boolean makeGuess(String newWord) {

        latestGuess = new Guess(newWord); // New guess with this string
        goodGuess = validGuess(); // Checks if the guess is valid
        if (goodGuess) {
            GAME_STATE.updateState(latestGuess); // Updates the game state if the guess is permitted
        }
        return goodGuess; // Return guess validity
    }

    /**
     * Checks if latestGuess is valid
     * @return boolean of guess validity
     */
    private boolean validGuess() {return InputChecker.goodInput(latestGuess,GAME_STATE);}

    /**
     * Checks if latestGuess produced a winning state
     * @return boolean of winning state
     */
    protected boolean winningGuess() {return GAME_STATE.checkWin();}

    /**
     * Counts the number of guesses remaining from the State object
     * @return integer of remaining guesses
     */
    protected int guessesLeft() {return State.maxGuesses() - GAME_STATE.guessesUsed();}

    /**
     * Counts the number of guesses used from the State object
     * @return integer of used guesses
     */
    protected int guessesUsed() {return GAME_STATE.guessesUsed();}

    /**
     * Gets letter states of the most recent guess from the State object
     * @return integer array of letter states
     */
    protected int[] getBoardRow() {return GAME_STATE.getLetterStates();}

    /**
     * Checks if the game has been lost from the State object
     * @return boolean of game loss
     */
    protected boolean gameLost() {return GAME_STATE.checkLoss();}

    /**
     * Calculates the game score of this GameRunner
     * @return integer of game score
     */
    protected int score() {

        int score = 0;
        for (int status : GAME_STATE.getLetterStates()) {
            switch (status) {
                case 1 -> score++; // Add 1 to score if this letter is in wrong place but in word
                case 2 -> score += 2; // Add 2 to score if letter is in word in right position
                default -> {}
            }
        }
        score += this.guessesLeft(); // Add one point for each remaining guess
        return score;
    }

    /**
     * Fetch the secret word if the user loses the game
     * @return String of the secret word
     */
    protected String loserReveal() {
        return GAME_STATE.consolation();
    }
}
