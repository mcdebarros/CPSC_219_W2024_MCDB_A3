package assignmentThree;

public class GameRunner {

    private final State GAME_STATE;
    private Guess latestGuess;
    private static boolean goodGuess;

    public GameRunner() {

        GAME_STATE = new State(); // Create game state
        latestGuess = new Guess();
        goodGuess = false;
    }

    protected boolean makeGuess(String newWord) {

        latestGuess = new Guess(newWord);
        goodGuess = validGuess();
        if (goodGuess) {
            GAME_STATE.updateState(latestGuess);
        }
        return goodGuess;
    }

    private boolean validGuess() {return InputChecker.goodInput(latestGuess,GAME_STATE);}

    protected boolean winningGuess() {return GAME_STATE.checkWin();}

    protected int guessesLeft() {return State.maxGuesses() - GAME_STATE.guessesUsed();}

    protected int guessesUsed() {return GAME_STATE.guessesUsed();}

    protected int[] getBoardRow() {return GAME_STATE.getLetterStates();}

    protected boolean gameLost() {return GAME_STATE.checkLoss();}

    protected int score() {

        int score = 0;
        for (int status : GAME_STATE.getLetterStates()) {
            switch (status) {
                case 1 -> score++;
                case 2 -> score += 2;
                default -> {}
            }
        }
        score += this.guessesLeft();
        return score;
    }

    protected String loserReveal() {
        return GAME_STATE.consolation();
    }
}
