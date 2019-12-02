package com.kator.weightguard.ui.controller;

import com.kator.weightguard.ui.strings.Caption;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * Created by prats on 4/27/18.
 */
public class MediaController {

    private PasswordController passwordController;
    private BorderPane borderPane;
    private MediaViewController mediaViewController;

    MediaController(BorderPane borderPane) {
        this.borderPane = borderPane;
        passwordController = new PasswordController();

        this.borderPane.setBottom(passwordController);
    }

    public String getPassword() {
        return passwordController.passwordField.getText();
    }

    public void playVideoFile(final MediaPlayer mediaPlayer) {
        if (mediaViewController != null) {
            mediaViewController.mp.dispose();
        }
        mediaViewController = new MediaViewController(mediaPlayer);
    }

    private class PasswordController extends HBox {
        private PasswordField passwordField;

        public PasswordController() {
            super(30);
            setAlignment(Pos.CENTER);
            setPadding(new Insets(10));

            Label passwordLabel = new Label(Caption.LABEL_CAPTION_PASSWORD);
            getChildren().add(passwordLabel);

            passwordField = new PasswordField();
            getChildren().add(passwordField);

            Button buttonClearPassword = new Button(Caption.BUTTON_CAPTION_CLEAR_PASSWORD);
            buttonClearPassword.setOnAction(event -> {
                passwordField.clear();
                try {
                    if (mediaViewController != null) {
                        mediaViewController.mp.dispose();
                        mediaViewController = null;
                    }
                    borderPane.setTop(null);
                    borderPane.setCenter(null);
                } catch (Exception e){}
            });
            getChildren().add(buttonClearPassword);
        }
    }

    public class MediaViewController extends Pane {

        private MediaPlayer mp;
        private MediaView mediaView;
        private final boolean repeat = false;
        private boolean stopRequested = false;
        private boolean atEndOfMedia = false;
        private Duration duration;
        private Slider timeSlider;
        private Label playTime;
        private HBox mediaBar;

        public MediaViewController(final MediaPlayer mp) {
            this.mp = mp;
            mediaView = new MediaView(mp);
            Pane mvPane = new Pane();
            mediaView.fitWidthProperty().bind(mvPane.widthProperty());

            mvPane.getChildren().add(mediaView);
            borderPane.setTop(mvPane);

            mediaBar = new HBox();
            mediaBar.setAlignment(Pos.CENTER);
            mediaBar.setPadding(new Insets(5, 10, 5, 10));
            BorderPane.setAlignment(mediaBar, Pos.CENTER);

            final Button playButton = new Button(Caption.MEDIA_PLAYER_BUTTON_CAPTION_PLAY);
            playButton.setPrefWidth(80);

            playButton.setOnAction(e -> {
                MediaPlayer.Status status = mp.getStatus();

                if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                    // don't do anything in these states
                    return;
                }

                if (status == MediaPlayer.Status.PAUSED
                        || status == MediaPlayer.Status.READY
                        || status == MediaPlayer.Status.STOPPED) {
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    mp.play();
                } else {
                    mp.pause();
                }
            });

            mp.currentTimeProperty().addListener(ov -> updateValues());

            mp.setOnPlaying(() -> {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setText(Caption.MEDIA_PLAYER_BUTTON_CAPTION_PAUSE);
                }
            });

            mp.setOnPaused(() -> {
                playButton.setText(Caption.MEDIA_PLAYER_BUTTON_CAPTION_PLAY);
            });

            mp.setOnReady(() -> {
                duration = mp.getMedia().getDuration();
                updateValues();
            });

            mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
            mp.setOnEndOfMedia(() -> {
                if (!repeat) {
                    playButton.setText(Caption.MEDIA_PLAYER_BUTTON_CAPTION_PLAY);
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            });

            mediaBar.getChildren().add(playButton);
            Label spacer = new Label("   ");
            mediaBar.getChildren().add(spacer);

            Label timeLabel = new Label("Zeit: ");
            mediaBar.getChildren().add(timeLabel);

            timeSlider = new Slider();
            HBox.setHgrow(timeSlider, Priority.ALWAYS);
            timeSlider.setMinWidth(50);
            timeSlider.setMaxWidth(Double.MAX_VALUE);
            timeSlider.valueProperty().addListener(ov -> {
                if (timeSlider.isValueChanging()) {
                    mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            });
            mediaBar.getChildren().add(timeSlider);

            playTime = new Label();
            playTime.setPrefWidth(130);
            playTime.setMinWidth(50);
            mediaBar.getChildren().add(playTime);

            borderPane.setCenter(mediaBar);
        }

        public void stop() {
            mp.stop();
        }

        protected void updateValues() {
            if (playTime != null && timeSlider != null) {
                Duration currentTime = mp.getCurrentTime();
                String text = formatTime(currentTime, duration);
                Platform.runLater(() -> {
                    playTime.setText(text);
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                });
            }
        }

        private String formatTime(Duration elapsed, Duration duration) {
            int intElapsed = (int) Math.floor(elapsed.toSeconds());
            int elapsedHours = intElapsed / (60 * 60);
            if (elapsedHours > 0) {
                intElapsed -= elapsedHours * 60 * 60;
            }
            int elapsedMinutes = intElapsed / 60;
            int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                    - elapsedMinutes * 60;

            if (duration.greaterThan(Duration.ZERO)) {
                int intDuration = (int) Math.floor(duration.toSeconds());
                int durationHours = intDuration / (60 * 60);
                if (durationHours > 0) {
                    intDuration -= durationHours * 60 * 60;
                }
                int durationMinutes = intDuration / 60;
                int durationSeconds = intDuration - durationHours * 60 * 60
                        - durationMinutes * 60;
                if (durationHours > 0) {
                    return String.format("%d:%02d:%02d/%d:%02d:%02d",
                            elapsedHours, elapsedMinutes, elapsedSeconds,
                            durationHours, durationMinutes, durationSeconds);
                } else {
                    return String.format("%02d:%02d/%02d:%02d",
                            elapsedMinutes, elapsedSeconds, durationMinutes,
                            durationSeconds);
                }
            } else {
                if (elapsedHours > 0) {
                    return String.format("%d:%02d:%02d", elapsedHours,
                            elapsedMinutes, elapsedSeconds);
                } else {
                    return String.format("%02d:%02d", elapsedMinutes,
                            elapsedSeconds);
                }
            }
        }
    }
}
