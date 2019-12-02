package com.kator.weightguard.ui;

import com.kator.weightguard.ui.config.AppConfig;
import com.kator.weightguard.ui.server.request.BasicRequest;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by prats on 3/8/18.
 */
@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {
    @Value("${ui.title:JavaFX приложение}")
    private String windowTitle;

    @Qualifier("mainView")
    @Autowired
    private AppConfig.ViewHolder view;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(windowTitle);
        Scene scene = new Scene(view.getView());
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/main.css").toExternalForm());
        stage.setScene(scene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.initStyle(StageStyle.UNDECORATED);

        //stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(Application.class, args);
    }
}
