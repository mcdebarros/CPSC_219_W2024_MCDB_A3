package tests;

import org.junit.jupiter.api.Test;
import projecta3.GameRunner;
import projecta3.Guess;
import projecta3.State;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class StateTests {

    GameRunner tester = new GameRunner();
    String secret = "LEMON";
    String wrong = "DONUT";

    @Test
    void test_getLastGuess() {

        Guess testGuess = tester.processInput(wrong);
        int[] expected;


    }
}