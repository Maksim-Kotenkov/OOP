package ru.nsu.kotenkov.snake;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private Canvas playgroundCanvas;

    @FXML
    private TextField foodNumberField;

    @FXML
    private TextField victoryScoreField;

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    public Button getStartButton() {
        return startButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Canvas getPlaygroundCanvas() {
        return playgroundCanvas;
    }

    public TextField getFoodNumberField() {
        return foodNumberField;
    }

    public TextField getVictoryScoreField() {
        return victoryScoreField;
    }
}
