package assignmentThree;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import static java.lang.StringTemplate.STR;

public class WordGuesserController {

    @SuppressWarnings("all")
    private GameRunner myGame; // GameRunner object
    @FXML @SuppressWarnings("all")
    private TextField guessField; // Input of guesses
    @FXML @SuppressWarnings("all")
    private GridPane wordGrid; // Game board
    @FXML @SuppressWarnings("all")
    private Label status; // Tracks guesses remaining

    /**
     * Default constructor for Controller
     */
    public WordGuesserController() {
        myGame = new GameRunner();
        guessField = new TextField();
        wordGrid = new GridPane();
        status = new Label(STR."Guesses left: \{State.maxGuesses()}");
        wordGrid.setStyle("-fx-grid-lines-visible: true;");

        for (int i = 0; i < wordGrid.getRowCount(); i++) { // Populate the grid with labels
            for (int j = 0; j < wordGrid.getColumnCount(); j++) {
                Label label = new Label();
                label.setStyle("-fx-alignment: center;");
                wordGrid.add(label, i, j);
            }
        }
    }

    /**
     * Constructor with externally built game
     * @param myGame Instance of the GameRunner
     */
    public WordGuesserController(GameRunner myGame) {
        this.myGame = myGame;
        guessField = new TextField();
        wordGrid = new GridPane();
        status = new Label(STR."Guesses left: \{State.maxGuesses()}");
        wordGrid.setStyle("-fx-grid-lines-visible: true;");

        for (int i = 0; i < wordGrid.getRowCount(); i++)
        {
            for (int j = 0; j < wordGrid.getColumnCount(); j++)
            {
                Label label = new Label();
                label.setStyle("-fx-alignment: center;");
                wordGrid.add(label, i, j);
            }
        }
    }

    /**
     * Action for clicking the guess button
     */
    @FXML @SuppressWarnings("all")
    protected void onGuessButtonClick() {

        String currentWord = guessField.getText(); // Get the newest guess String
        boolean guessAccepted = myGame.makeGuess(currentWord); // Checks if the guess was valid

        if (guessAccepted) { // Case if good guess
            guessField.clear(); // Clear the guess field
            status.setText(STR."Guesses left: \{myGame.guessesLeft()}"); // Update the guess tracker label
            for (int i = 0; i < State.getLength(); i++) { // Update the game board
                Label label = new Label(String.valueOf(currentWord.charAt(i)).toUpperCase()); // Create label for guess character
                label.setStyle(STR."-fx-background-color: \{cellColor(myGame.getBoardRow()[i])}; -fx-font-weight: bold; -fx-alignment: center;"); // Set the color of the cell based on correct position
                label.setPrefWidth(100);
                label.setPrefHeight(30);
                wordGrid.add(label, i, 5 - myGame.guessesLeft()); // Add the label to the grid
            }
            if (myGame.winningGuess()) { // Case if this guess won the game
                Alert gameWon = new Alert(Alert.AlertType.INFORMATION); // Pop up to congratualte user
                gameWon.setTitle("Winner Winner Chicken Dinner!"); // Set the title
                gameWon.setHeaderText(null); // Skip the header
                if (myGame.guessesLeft() == 5) { // Bonus if the user guessed in EXACTLY one guess
                    gameWon.setContentText("Wow, a perfect game, worth 17 points! The odds of this are 1/5757; are you sure you didn't cheat?");
                } else { // Case if more than one guess used
                    gameWon.setContentText(String.format("Congratulations! You've won the game in %d guesses! Your score was %d points.", myGame.guessesUsed(), myGame.score()));
                }
                gameWon.showAndWait(); // Show the pop-up
                Platform.exit(); // Close the application
            } else if (myGame.gameLost()) { // Same as above, but for losing game
                Alert gameLost = new Alert(Alert.AlertType.INFORMATION);
                gameLost.setTitle("You suck!");
                gameLost.setHeaderText(null);
                gameLost.setContentText(STR."Wow, you really blew that one! Better luck next time! The secret word was \{myGame.loserReveal()}. Your score was \{myGame.score()} points.");
                gameLost.showAndWait();
                Platform.exit(); // Close the application
            }
        } else { // Warn the player that the guess was invalid but do not penalize a guess
            Alert badInput = new Alert(Alert.AlertType.INFORMATION);
            badInput.setTitle("That didn't make sense!");
            badInput.setHeaderText(null);
            badInput.setContentText("Something's fishy with the guess you made. Check it and try again! Hit the help button for game instructions!");
            badInput.showAndWait();
            badInput.close();
        }
    }

    /**
     * Action for clicking the help button
     */
    @FXML @SuppressWarnings("unused")
    protected void onHelpButtonClick() { // Help message with game instructions
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText(null);
        String HELP_MESSAGE = """
                Welcome to NotWordle!
                           
                The goal is to guess the secret word, chosen at random, in 6 guesses or less. After each guess, the grid will update with your guess. Red indicates that this letter is not in the secret word. Yellow indicates that this letter is in the secret word, but in the wrong position. Green indicates that this letter is in the secret word in this position; don't change it!
                           
                Your score will be calculated based on your last guess when the game is over. Green boxes are worth two points. Yellow boxes are worth one point. Red boxes are worth no points. Each remaining guess at the end of the game is worth one point.
                           
                Invalid guesses, such as those too long, too short, those previously guessed, or with invalid characters, will not be penalized; fix your guess and try again!""";
        helpAlert.setContentText(HELP_MESSAGE);
        helpAlert.showAndWait();
    }

    /**
     * Action for pressing the enter key
     * @param ae ActionEvent of the enter key
     */
    @FXML @SuppressWarnings("all")
    public void onEnter(ActionEvent ae) { // Same as button click, but with enter key

        String currentWord = guessField.getText();
        boolean guessAccepted = myGame.makeGuess(currentWord);

        if (guessAccepted) {
            guessField.clear();
            status.setText(STR."Guesses left: \{myGame.guessesLeft()}");
            for (int i = 0; i < State.getLength(); i++) {
                Label label = new Label(String.valueOf(currentWord.charAt(i)).toUpperCase());
                label.setStyle(STR."-fx-background-color: \{cellColor(myGame.getBoardRow()[i])}; -fx-font-weight: bold; -fx-alignment: center;");
                label.setPrefWidth(100); // Adjust this width as needed
                label.setPrefHeight(30); // Adjust this height as needed
                wordGrid.add(label, i, 5 - myGame.guessesLeft());
            }
            if (myGame.winningGuess()) {
                Alert gameWon = new Alert(Alert.AlertType.INFORMATION);
                gameWon.setTitle("Winner Winner Chicken Dinner!");
                gameWon.setHeaderText(null);
                if (myGame.guessesLeft() == 5) {
                    gameWon.setContentText("Wow, a perfect game, worth 17 points! The odds of this are 1/5757; are you sure you didn't cheat?");
                } else {
                    gameWon.setContentText(String.format("Congratulations! You've won the game in %d guesses! Your score was %d points.", myGame.guessesUsed(), myGame.score()));
                }
                gameWon.showAndWait();
                Platform.exit(); // Close the application
            } else if (myGame.gameLost()) {
                Alert gameLost = new Alert(Alert.AlertType.INFORMATION);
                gameLost.setTitle("You suck!");
                gameLost.setHeaderText(null);
                gameLost.setContentText(STR."Wow, you really blew that one! Better luck next time! The secret word was \{myGame.loserReveal()}. Your score was \{myGame.score()} points.");
                gameLost.showAndWait();
                Platform.exit(); // Close the application
            }
        } else {
            Alert badInput = new Alert(Alert.AlertType.INFORMATION);
            badInput.setTitle("That didn't make sense!");
            badInput.setHeaderText(null);
            badInput.setContentText("Something's fishy with the guess you made. Check it and try again! Hit the help button for game instructions!");
            badInput.showAndWait();
            badInput.close();
        }
    }

    /**
     * Colors board cells based on letterStates
     * @param status State of this entry
     * @return String of cell color
     */
    private String cellColor(int status) {
        return switch (status) {
            case 0 -> "red";
            case 1 -> "yellow";
            case 2 -> "green";
            default -> "white";
        };
    }
}