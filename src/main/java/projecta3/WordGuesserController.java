package projecta3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class WordGuesserController {

    GameRunner myGame;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField guessField;
    @FXML
    private GridPane wordGrid;

    public WordGuesserController()
    {
        myGame = new GameRunner();
        statusLabel = new Label();
        guessField = new TextField();
        wordGrid = new GridPane();

        for (int i = 0; i < wordGrid.getRowCount(); i++)
        {
            for (int j = 0; j < wordGrid.getColumnCount(); j++)
            {
                wordGrid.add(new Label(), i, j);
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

        if(currentGuess.hasWin())
        {
            // make a pop up
        }

        if (myGame.wasGoodGuess(currentWord)) {
            guessField.clear();
            statusLabel.setText("Making a guess");
            for (int i = 0; i < currentGuess.getLetterStatus().length; i++) {
                Label label = new Label(String.valueOf(currentGuess.getLetterStatus()[i]));
                wordGrid.add(label,i,(myGame.gameState.guessesUsed()) - 1);
                label.setStyle("-fx-alignment: CENTER;");
            }
            if (myGame.gameState.checkWin()) {

            }
        } else {
            statusLabel.setText("Uhoh");
        }
    }
}
