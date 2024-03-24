package ru.nsu.kotenkov.snake;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.nsu.kotenkov.snake.logic.StageUpdate;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private StageUpdate updater;

    public void setUpdater(StageUpdate updater) {
        this.updater = updater;
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playgroundCanvas.setFocusTraversable(true);

        startButton.setOnAction(event -> updater.pressContinueButton());
        resetButton.setOnAction(event -> updater.pressResetButton());
    }
}
