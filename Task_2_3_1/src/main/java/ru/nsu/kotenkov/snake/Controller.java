package ru.nsu.kotenkov.snake;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.nsu.kotenkov.snake.logic.SceneTimer;


/**
 * JavaFX controller to set up scene objects and let the program get them.
 */
public class Controller implements Initializable {
    private SceneTimer timer;

    @FXML
    private Canvas playgroundCanvas;

    @FXML
    private TextField foodNumberField;

    @FXML
    private TextField victoryScoreField;

    @FXML
    private TextField widthCellsField;

    @FXML
    private TextField heightCellsField;

    @FXML
    private TextField scoreField;

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    /**
     * Timer.
     *
     * @param timer new SceneTimer
     */
    public void setTimer(SceneTimer timer) {
        this.timer = timer;
    }

    /**
     * Setup.
     * Callback handlers for buttons.
     *
     * @param url idk
     * @param resourceBundle idk
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playgroundCanvas.setFocusTraversable(true);

        startButton.setOnAction(event -> timer.pressStartButton());
        resetButton.setOnAction(event -> timer.pressResetButton());
        scoreField.setText("2");
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public Canvas getPlaygroundCanvas() {
        return playgroundCanvas;
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public TextField getFoodNumberField() {
        return foodNumberField;
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public TextField getVictoryScoreField() {
        return victoryScoreField;
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public TextField getWidthCellsField() {
        return widthCellsField;
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public TextField getScoreField() {
        return scoreField;
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public TextField getHeightCellsField() {
        return heightCellsField;
    }
}
