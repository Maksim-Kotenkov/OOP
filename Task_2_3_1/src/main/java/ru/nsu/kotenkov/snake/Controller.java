package ru.nsu.kotenkov.snake;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.nsu.kotenkov.snake.logic.StageUpdate;


/**
 * JavaFX controller to set up scene objects and let the program get them.
 */
public class Controller implements Initializable {
    private StageUpdate updater;

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
    private Button startButton;

    @FXML
    private Button resetButton;

    /**
     * Controller already created without StageUpdater at the startup.
     * (after Controller was created, because fxml loads at startup)
     * But we want to set up StageUpdater that doesn't exist at the startup.
     *
     * @param updater initialized object
     */
    public void setUpdater(StageUpdate updater) {
        this.updater = updater;
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

        startButton.setOnAction(event -> updater.pressContinueButton());
        resetButton.setOnAction(event -> updater.pressResetButton());
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public Button getStartButton() {
        return startButton;
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public Button getResetButton() {
        return resetButton;
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
    public TextField getHeightCellsField() {
        return heightCellsField;
    }
}
