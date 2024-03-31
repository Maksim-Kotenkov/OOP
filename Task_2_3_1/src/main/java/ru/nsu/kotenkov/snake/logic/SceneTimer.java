package ru.nsu.kotenkov.snake.logic;


import javafx.animation.AnimationTimer;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.ToWaitException;


/**
 * A class to handle updates of the canvas.
 */
public class SceneTimer extends AnimationTimer {
    private long lastUpdate;
    private boolean started;
    private final StageUpdate updater;

    /**
     * We need updater to update the canvas.
     *
     * @param updater stage updater
     */
    public SceneTimer(StageUpdate updater) {
        this.updater = updater;
        try {
            updater.reset(false, false);
        } catch (ToWaitException e) {
            // here the exception should set started to false for waiting
            started = false;
        }
    }

    /**
     * Basic parameter to update canvas.
     *
     * @param now time from the start
     */
    @Override
    public void handle(long now) {
        // if the game is running
        if (started) {
            // check the time from prev update and compare to what we want
            long elapsedMilliseconds = (now - lastUpdate) / 1_000_000;
            long toSleep = Math.max(Playground.basicFrameDelay
                            - (updater.getSnake().getLength() * Playground.speedIncrease),
                    Playground.minFrameDelay);

            if (elapsedMilliseconds < toSleep) {
                return;
            }

            try {
                updater.run();
            } catch (ToWaitException e) {
                started = false;
            }

            lastUpdate = now;
        }
    }

    /**
     * Callback method for button.
     */
    public void pressStartButton() {
        if (!started) {
            try {
                updater.reset(false, false);
            } catch (ToWaitException e) {
                // here the exception should not set started to false
                started = true;
            }
        }
    }

    /**
     * Callback method for button.
     */
    public void pressResetButton() {
        if (started) {
            try {
                updater.reset(false, false);
            } catch (ToWaitException e) {
                // here the exception should set started to false for waiting
                started = false;
            }
        }
    }
}
