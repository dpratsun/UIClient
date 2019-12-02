package com.kator.weightguard.ui.controller;

/**
 * Created by prats on 3/8/18.
 */
import com.kator.weightguard.ui.entity.Alarm;
import com.kator.weightguard.ui.entity.Shelf;
import com.kator.weightguard.ui.entity.ShelfError;
import com.kator.weightguard.ui.enums.UserOperations;
import com.kator.weightguard.ui.media.SoundPlayer;
import com.kator.weightguard.ui.media.SystemSoundPlayer;
import com.kator.weightguard.ui.server.request.RequestTask;
import com.kator.weightguard.ui.service.*;
import com.kator.weightguard.ui.service.impl.AuthenticationServiceImpl;
import com.kator.weightguard.ui.strings.Caption;
import com.kator.weightguard.ui.strings.Message;
import com.kator.weightguard.ui.uielement.ShelfAlarmResetButton;
import com.kator.weightguard.ui.utils.AlertGenerator;
import com.kator.weightguard.ui.utils.FileUtils;
import com.kator.weightguard.ui.utils.FindUtils;
import com.kator.weightguard.ui.utils.TaskRunner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static com.kator.weightguard.ui.strings.Message.NO_CONTENT_IN_ALARM_TABLE;
import static com.kator.weightguard.ui.strings.Message.NO_CONTENT_IN_SHELF_ERRORS_TABLE;

public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShelfService shelfService;

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private ShelfSettingService shelfSettingService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ShelfErrorService shelfErrorService;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private VideoServerSettingsService videoServerSettingsService;

    @Autowired
    private WeightServerSettingsService weightServerSettingsService;

    @Autowired
    private UserService userService;

    @Value("${weightserverui.datetimeformat}")
    private String dateTimeFormat;

    @FXML
    private TitledPane alarmShelfsTitledPane;

    @FXML
    private TitledPane shelfErrorsTitledPane;

    @FXML
    private TitledPane statisticTitledPane;

    @FXML
    private Label controllersCountLabel;

    @FXML
    private Label shelfsCountLabel;

    @FXML
    private Label errorsCountLabel;

    @FXML
    private TableView<ShelfError> shelfErrorsTable;

    @FXML
    private TableColumn<ShelfError, String> errorShelfIdCol;

    @FXML
    private TableColumn<ShelfError, Integer> errorCodeCol;

    @FXML
    private TableColumn<ShelfError, Void> errorCheckedCol;

    private ShelfErrorTableController shelfErrorTableController;

    @FXML
    private TilePane alarmTilePane;

    private AlarmTilePaneController alarmTilePaneController;

    @FXML
    private TableView<Alarm> alarmTable;

    private AlarmTableController alarmTableController;

    @FXML
    private TableColumn<Alarm, String> dateCol;

    @FXML
    private TableColumn<Alarm, String> shelfCol;

    @FXML
    private TableColumn<Alarm, Void> playCol;

    @FXML
    private TableColumn<Alarm, Void> saveCol;

    @FXML
    private TableColumn<Alarm, Void> delCol;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ShelfControllerSettingsTabController shelfControllerSettingsTabPageController;

    @FXML
    private AdminTabController adminTabPageController;

    @FXML
    private VBox alarmVBox;

    @FXML
    private VBox mediaVBox;

    private MediaController mediaController;

    private List<String> alarmShelfList = Collections.synchronizedList(new ArrayList<>());
    private SoundPlayer soundPlayer = new SystemSoundPlayer();

    @PostConstruct
    public void init() {
        log.trace(">> init()");

        shelfControllerSettingsTabPageController.setShelfService(shelfService);
        shelfControllerSettingsTabPageController.setShelfSettingService(shelfSettingService);
        shelfControllerSettingsTabPageController.setEndpointService(endpointService);
        shelfControllerSettingsTabPageController.setAuthService(authenticationService);

        adminTabPageController.setAuthenticationService(authenticationService);
        adminTabPageController.setVideoServerSettingsService(videoServerSettingsService);
        adminTabPageController.setWeightServerSettingsService(weightServerSettingsService);

        alarmShelfsTitledPane.setCollapsible(false);
        shelfErrorsTitledPane.setCollapsible(false);
        statisticTitledPane.setCollapsible(false);

        HBox.setHgrow(alarmVBox, Priority.ALWAYS);
        HBox.setHgrow(mediaVBox, Priority.ALWAYS);

        alarmTilePaneController = new AlarmTilePaneController();
        shelfErrorTableController = new ShelfErrorTableController();
        alarmTableController  = new AlarmTableController();

        BorderPane mediaBorderPane = new BorderPane();
        mediaController = new MediaController(mediaBorderPane);

        mediaVBox.getChildren().add(mediaBorderPane);

        VBox.setVgrow(alarmTable, Priority.ALWAYS);

        log.trace("<< init()");
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    private void refreshAlarmTilePaneTask() {
        log.trace(">> refreshAlarmTilePaneTask()");

        TaskRunner.run(new RequestTask(() -> {
            alarmTilePaneController.sendGetShelfRequest();
            Platform.runLater(() -> {
                alarmTilePaneController.refreshAlarmTilePane();
            });
        }));

        log.trace("<< refreshAlarmTilePaneTask()");
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 2000)
    private void refreshAlarmTableTask() {
        log.trace(">> refreshAlarmTableTask()");

        TaskRunner.run(new RequestTask(() -> {
            alarmTableController.sendGetAlarmRequest();

            Platform.runLater(() -> {
                alarmTableController.refreshAlarmTable();
            });
        }));

        log.trace("<< refreshAlarmTableTask()");
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 3000)
    private void refreshShelfErrorTableTask() {
        log.trace(">> refreshShelfErrorTableTask()");

        TaskRunner.run(new RequestTask(() -> {
            shelfErrorTableController.sendShelfErrorGetAllRequest();

            Platform.runLater(() -> {
                shelfErrorTableController.refreshTableData();
            });
        }));

        log.trace("<< refreshShelfErrorTableTask()");
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 4000)
    private void refreshEndpointListTask() {
        log.trace(">> refreshEndpointListTask()");

        TaskRunner.run(new RequestTask(() -> {
            shelfControllerSettingsTabPageController.sendEndpointsGetAllRequest();
            shelfControllerSettingsTabPageController.sendShelfSettingGetAllRequest();

            if(shelfControllerSettingsTabPageController.endpointListShowing()) return;

            Platform.runLater(() -> {
                shelfControllerSettingsTabPageController.refreshEndpointChoiceBox();
                controllersCountLabel.setText(Integer.toString(shelfControllerSettingsTabPageController.getEndpointListSize()));
            });
        }));

        log.trace("<< refreshEndpointListTask()");
    }

    private class AlarmTilePaneController {
        private List<Shelf> shelfList = Collections.synchronizedList(new ArrayList<>());

        public AlarmTilePaneController() {
            alarmTilePane.setHgap(10);
            alarmTilePane.setVgap(10);
            alarmTilePane.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        }

        public void refreshAlarmTilePane() {
            log.trace(">> refreshAlarmTilePane()");

            alarmTilePane.getChildren().clear();
            shelfsCountLabel.setText(Integer.toString(shelfList.size()));

            synchronized (shelfList) {
                shelfList.forEach(item -> {
                    if (item.getStatus() == 2) {
                        playAlarmSound(item.getId());
                        addShelfResetButtonToAlarmTilePane(item.getId(), item.getTitle());
                    }
                });
                shelfList.clear();
            }

            log.trace("<< refreshAlarmTilePane()");
        }

        public void sendGetShelfRequest() {
            log.trace(">> sendGetShelfRequest()");
            try {
                shelfList = Collections.synchronizedList(shelfService.getAll());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.trace("<< sendGetShelfRequest()");
        }

        private void addShelfResetButtonToAlarmTilePane(String shelfId, String shelfTitle) {
            log.trace(">> addShelfResetButtonToAlarmTilePane()");

            AnchorPane anchorPane = new AnchorPane();
            ShelfAlarmResetButton button = new ShelfAlarmResetButton();
            button.setText(shelfTitle);
            button.setShelfId(shelfId);
            button.setMnemonicParsing(false);
            button.setOnAction(event -> {
                TaskRunner.run(new RequestTask(() -> {
                    ShelfAlarmResetButton actionBtn = (ShelfAlarmResetButton) event.getSource();
                    Boolean result = shelfService.resetShelf(actionBtn.getShelfId());

                    if (result) {
                        Platform.runLater(() -> {
                            alarmTilePane.getChildren().remove(actionBtn.getParent());
                            alarmShelfList.remove(actionBtn.getText());
                        });
                    }
                }));
            });

            button.getStyleClass().add("alarm-button");

            AnchorPane.setBottomAnchor(button, 0.0);
            AnchorPane.setTopAnchor(button, 0.0);
            AnchorPane.setRightAnchor(button, 0.0);
            AnchorPane.setLeftAnchor(button, 0.0);

            anchorPane.getChildren().add(button);

            alarmTilePane.getChildren().add(anchorPane);

            log.trace("<< addShelfResetButtonToAlarmTilePane()");
        }

        private void playAlarmSound(String shelfId) {
            if (alarmShelfList.contains(shelfId)) return;

            alarmShelfList.add(shelfId);
            soundPlayer.playAlarm();
        }
    }

    private class ShelfErrorTableController {
        private List<ShelfError> shelfErrorList = Collections.synchronizedList(new ArrayList<>());

        public ShelfErrorTableController() {
            log.trace(">> ShelfErrorTableController()");

            shelfErrorsTable.setPlaceholder(new Label(NO_CONTENT_IN_SHELF_ERRORS_TABLE));

            errorShelfIdCol.setCellValueFactory(new PropertyValueFactory<ShelfError, String>("shelfId"));
            errorCodeCol.setCellValueFactory(new PropertyValueFactory<ShelfError, Integer>("code"));

            setupCheckedColumnCellFactory();

            shelfErrorsTable.getColumns().forEach(tableColumn -> {
                tableColumn.prefWidthProperty().bind(shelfErrorsTable.widthProperty().divide(3));
                tableColumn.setSortable(false);
            });

            log.trace("<< ShelfErrorTableController()");
        }

        public void refreshTableData() {
            log.trace(">> ShelfErrorTableController::refreshTableData()");

            shelfErrorsTable.getItems().clear();
            errorsCountLabel.setText(Integer.toString(shelfErrorList.size()));
            shelfErrorsTable.setItems(FXCollections.observableList(shelfErrorList));

            log.trace("<< ShelfErrorTableController::refreshTableData()");
        }

        public void sendShelfErrorGetAllRequest() {
            log.trace(">> sendShelfErrorGetAllRequest()");
            try {
                shelfErrorList = Collections.synchronizedList(shelfErrorService.getAll());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.trace("<< sendShelfErrorGetAllRequest()");
        }

        public void setupCheckedColumnCellFactory() {
            errorCheckedCol.setCellFactory(new Callback<TableColumn<ShelfError, Void>, TableCell<ShelfError, Void>>() {
                @Override
                public TableCell<ShelfError, Void> call(TableColumn<ShelfError, Void> param) {
                    return new TableCell<ShelfError, Void>() {

                        private final Button btn = new Button(Caption.SHELF_ERROR_TABLE_BUTTON_CAPTION);

                        {
                            btn.setOnAction((ActionEvent event) -> {
                                ShelfError shelfError = getTableView().getItems().get(getIndex());
                                TaskRunner.run(new RequestTask(() -> {
                                    if (shelfErrorService.setChecked(shelfError.getId())) {
                                        Platform.runLater(() -> {
                                            shelfErrorsTable.getItems().remove(shelfError);
                                            errorsCountLabel.setText(Integer.toString(shelfErrorsTable.getItems().size()));
                                        });
                                    }
                                }));
                            });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btn);
                            }
                        }
                    };
                }
            });
        }
    }

    private class AlarmTableController {
        private List<Alarm> alarmList = Collections.synchronizedList(new ArrayList<>());
        private Integer playedAlarmId;

        public AlarmTableController() {
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            dateCol.setCellFactory(column -> new TableCell<Alarm, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
                            LocalDateTime localDateTime = LocalDateTime.parse(item, formatter);

                            formatter = DateTimeFormatter.ofPattern(dateTimeFormat, Locale.GERMAN);
                            setText(localDateTime.format(formatter));
                        } catch (DateTimeParseException e) {
                            setText(null);
                        }
                    }
                }
            });

            alarmTable.setPlaceholder(new Label(NO_CONTENT_IN_ALARM_TABLE));
            shelfCol.setCellValueFactory(new PropertyValueFactory<>("shelfTitle"));

            alarmTable.getColumns().forEach(alarmTableColumn -> {
                alarmTableColumn.prefWidthProperty().bind(alarmTable.widthProperty().divide(5));
                alarmTableColumn.setSortable(false);
                alarmTableColumn.resizableProperty().setValue(false);
            });

            setupPlayColumnCellFactory();
            setupSaveColumnCellFactory();
            setupDeleteColumnCellFactory();
        }

        public void refreshAlarmTable() {
            log.trace(">> refreshAlarmTable()");

            alarmTable.getItems().clear();
            alarmTable.setItems(FXCollections.observableList(alarmList));

            focusSelectedAlarmRowByAlarmId();

            log.trace("<< refreshAlarmTable()");
        }

        private void sendGetAlarmRequest() {
            log.trace(">> sendGetAlarmRequest()");
            try {
                alarmList = Collections.synchronizedList(alarmService.getAll());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.trace("<< sendGetAlarmRequest()");
        }

        public void setupPlayColumnCellFactory() {
            log.trace(">> setupPlayColumnCellFactry()");

            playCol.setCellFactory(new AlarmTableCellFactoryCallBack(
                    Caption.BUTTON_CAPTION_PLAY, alarm -> {

                try {
                    authenticationService.authenticate(UserOperations.canPlayVideo, mediaController.getPassword());

                    playedAlarmId = alarm.getId();

                    Platform.runLater(() -> { focusSelectedAlarmRowByAlarmId(); });

                    File f = new File(alarm.getFilePath());
                    if (f.exists()) {
                        System.out.println("file exists: " + f.toString().replace("\\", "/"));

                        Media media = new Media("file:/" + f.toString().replace("\\", "/"));
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.setAutoPlay(true);

                        mediaController.playVideoFile(mediaPlayer);
                    } else {
                        log.trace("setupPlayColumnCellFactry: Media file not exist.");
                    }
                } catch (Exception e) {
                    AlertGenerator.generateShowAndWait(e.getMessage(), Alert.AlertType.ERROR);
                }
            }));
            log.trace("<< setupPlayColumnCellFactry()");
        }

        public void setupSaveColumnCellFactory() {
            log.trace(">> setupSaveColumnCellFactry()");

            saveCol.setCellFactory(new AlarmTableCellFactoryCallBack(
                    Caption.BUTTON_CAPTION_SAVE, alarm -> {
                try {
                    authenticationService.authenticate(UserOperations.canSaveVideo, mediaController.getPassword());

                    saveAlarmVideoFileToUsbDrive(alarm.getFilePath());
                } catch (Exception e) {
                    AlertGenerator.generateShowAndWait(e.getMessage(), Alert.AlertType.ERROR);
                }
            }));
            log.trace("<< setupSaveColumnCellFactry()");
        }

        public void setupDeleteColumnCellFactory() {
            log.trace(">> setupDeleteColumnCellFactory()");

            delCol.setCellFactory(new AlarmTableCellFactoryCallBack(
                    Caption.BUTTON_CAPTION_DELETE, alarm -> {
                try {
                    authenticationService.authenticate(UserOperations.canDeleteVideo, mediaController.getPassword());

                    TaskRunner.run(new RequestTask(() -> {
                        if (videoService.deleteVideo(alarm.getVideoId())) {
                            Platform.runLater(() -> {
                                alarmTable.getItems().removeIf(row -> row.getVideoId() == alarm.getVideoId());
                            });
                        }
                    }));
                } catch (Exception e) {
                    AlertGenerator.generateShowAndWait(e.getMessage(), Alert.AlertType.ERROR);
                }
            }));
            log.trace("<< setupDeleteColumnCellFactory()");
        }

        private void saveAlarmVideoFileToUsbDrive(String file) {
            log.trace(">> saveAlarmVideoFileToUsbDrive()");

            USBDeviceDetectorManager driveDetector = new USBDeviceDetectorManager();

            if (driveDetector.getRemovableDevices().size() == 0) {
                AlertGenerator.generateShowAndWait(Message.INSERT_USB_DRIVE, Alert.AlertType.INFORMATION);
                log.trace("<< saveAlarmVideoFileToUsbDrive()");
                return;
            }

            USBStorageDevice usbStorageDevice = driveDetector.getRemovableDevices().get(0);
            File source = new File(file);
            if (source.exists()) {
                Alert alertProcess = new Alert(Alert.AlertType.INFORMATION);
                alertProcess.getButtonTypes().clear();
                alertProcess.setContentText(Message.PLEASE_WAIT_A_MOMENT);
                String usbRootPath = usbStorageDevice.getRootDirectory().toString().replace("\\", "/");
                Path filePath = Paths.get(file);
                File destination = new File(usbRootPath + "/" + filePath.getFileName().toString());
                new Thread(() -> {
                    Platform.runLater(()-> alertProcess.show());
                    String message = FileUtils.copy(source, destination);
                    Platform.runLater(()-> {
                        alertProcess.setResult(ButtonType.CLOSE);
                        alertProcess.close();
                    });
                    final String infoMessage = message;
                    Platform.runLater(() -> AlertGenerator.generateShowAndWait(infoMessage, Alert.AlertType.INFORMATION));
                }).start();
            } else {
                log.trace("saveAlarmVideoFileToUsbDrive: file not exist");
                AlertGenerator.generateShowAndWait(Message.FILE_NOT_EXIST, Alert.AlertType.INFORMATION);
            }

            log.trace("<< saveAlarmVideoFileToUsbDrive()");
        }

        public void focusSelectedAlarmRowByAlarmId() {
            if (mediaController.getPassword().equals("") || playedAlarmId == null) return;

            synchronized (alarmTable) {
                Alarm alarm = FindUtils.findByProperty(alarmTable.getItems(), item -> item.getId().equals(playedAlarmId));

                if (alarm == null) {
                    playedAlarmId = null;
                    return;
                }

                alarmTable.requestFocus();
                alarmTable.getSelectionModel().select(alarm);
                alarmTable.getFocusModel().focus(alarmTable.getSelectionModel().getSelectedIndex());
            }
        }
    }

    private class AlarmTableCellFactoryCallBack implements Callback<TableColumn<Alarm, Void>, TableCell<Alarm, Void>> {

        String buttonCaption;
        Consumer<Alarm> consumer;

        public AlarmTableCellFactoryCallBack(String buttonCaption, Consumer<Alarm> consumer) {
            this.buttonCaption = buttonCaption;
            this.consumer = consumer;
        }

        @Override
        public TableCell<Alarm, Void> call(TableColumn<Alarm, Void> param) {
            return new TableCell<Alarm, Void>() {

                private final Button btn = new Button(buttonCaption);
                {
                    btn.setOnAction((ActionEvent event) -> {
                        Alarm alarm = getTableView().getItems().get(getIndex());
                        consumer.accept(alarm);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
        }
    }
}