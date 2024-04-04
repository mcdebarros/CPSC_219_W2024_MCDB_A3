package projecta3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import static java.lang.StringTemplate.STR;

public class WordGuesserController {

    GameRunner myGame;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField guessField;
    @FXML
    private GridPane wordGrid;

    private Label[][] cellLabels; // 2D array to store labels for each cell

    public WordGuesserController()
    {
        myGame = new GameRunner();
        statusLabel = new Label();
        guessField = new TextField();
        wordGrid = new GridPane();

        // Set grid lines visible
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

    public WordGuesserController(GameRunner myGame)
    {
        this.myGame = myGame;
        statusLabel = new Label();
        guessField = new TextField();
    }

    @FXML
    protected void onGuessButtonClick() {
        String currentWord = guessField.getText();
        Guess currentGuess = myGame.processInput(currentWord);
        char[] guessChars = new char[currentWord.length()];
        for (int i = 0; i < 5; i++) {
            guessChars[i] = currentWord.charAt(i);
        }

        //TODO
        // Implement win criteria
        // Clean up game board

        if (myGame.wasGoodGuess()) {
            guessField.clear();
            for (int i = 0; i < currentGuess.getLetterStatus().length; i++) {
                Label label = new Label(String.valueOf(guessChars[i]));
                wordGrid.add(label, i, (myGame.gameState.guessesUsed()) - 1);
                wordGrid.setStyle("-fx-alignment: center;");
                if (currentGuess.getLetterStatus()[i] == 0) {
                    label.setStyle("-fx-background-color: red;");
                } else if (currentGuess.getLetterStatus()[i] == 1) {
                    label.setStyle("-fx-background-color: yellow;");
                } else {
                    label.setStyle("-fx-background-color: green;");
                }
            }
            if (!myGame.gameState.checkWin()) {
            } else {
                int inWord = 0;
                int correctPos = 0;
                for (int status : currentGuess.getLetterStatus()) {
                    if (status == 1) {
                        inWord++;
                    } else if (status == 2) {
                        correctPos++;
                    }
                }
                statusLabel.setText(STR."""
                        Correct letters in wrong position:\s\{inWord}
                        Correct letters in correct position:\s\{correctPos}
                        Guesses Remaining:\s\{6 - myGame.gameState.guessesUsed()}
                        """);
            }
        } else {
            statusLabel.setText("Uhoh");
        }
    }
}