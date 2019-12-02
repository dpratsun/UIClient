package com.kator.weightguard.ui.utils;

import javafx.scene.control.Alert;

/**
 * Created by prats on 4/23/18.
 */
public class AlertGenerator {
    public static void generateShowAndWait(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
