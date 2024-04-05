package assignmentThree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WordGuesserApp extends Application {

    GameRunner myGame = new GameRunner();

    public WordGuesserApp() {}

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordGuesserApp.class.getResource("word-guesser.fxml"));
        fxmlLoader.setController(new WordGuesserController(myGame));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("NotWordle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}