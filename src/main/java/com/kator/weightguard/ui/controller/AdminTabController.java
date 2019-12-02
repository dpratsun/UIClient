package com.kator.weightguard.ui.controller;

import com.kator.weightguard.ui.entity.ServerSettings;
import com.kator.weightguard.ui.enums.UserOperations;
import com.kator.weightguard.ui.server.request.RequestTask;
import com.kator.weightguard.ui.service.AuthenticationService;
import com.kator.weightguard.ui.service.UserService;
import com.kator.weightguard.ui.service.VideoServerSettingsService;
import com.kator.weightguard.ui.service.WeightServerSettingsService;
import com.kator.weightguard.ui.strings.UserGroups;
import com.kator.weightguard.ui.utils.AlertGenerator;
import com.kator.weightguard.ui.utils.TaskRunner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.kator.weightguard.ui.strings.Message.PARAMETERS_HAVE_BEEN_SAVED;


public class AdminTabController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AuthenticationService authService;

    private VideoServerSettingsService videoServerSettingsService;

    private WeightServerSettingsService weightServerSettingsService;

    @FXML
    private ChoiceBox adminControllerChoiseBoxGroup;

    @FXML
    private PasswordField adminControllerAuthenticatePassword;

    @FXML
    private PasswordField adminControllerRegistrationPasswordField;

    @FXML
    private PasswordField adminControllerRegistrationConfirmPasswordField;

    @FXML
    private TextField adminControllerVideoRecordFolderTextField;

    @FXML
    private TextField adminControllerVideoRecordDurationTextField;

    @FXML
    private TextField adminControllerVideoViewDurationTextField;

    @FXML
    private TextField adminControllerVideoRecordDelayTextField;

    @FXML
    private TextField adminControllerVideoStorePeriodTextField;

    @FXML
    private TextField adminControllerVideoPartFrameWidthTextField;

    @FXML
    private TextField adminControllerVideoPartFrameHeightTextField;

    @FXML
    private Button adminControllerSaveButton;

    @FXML
    private Button adminControllerClearPasswordButton;

    @FXML
    private Button adminControllerReadVideoParamButton;

    @FXML
    private Button adminControllerSaveVideoParamButton;

    @FXML
    private VBox textFieldsContainerVBox;

    @FXML
    private void initialize() {
        fillChoiceBoxUserGroup();
        setupSaveButton();
        setupClearPasswordButton();
        setupReadVideoParamButton();
        setupSaveVideoParamButton();

        textFieldsContainerVBox.getChildren().forEach(anchorPaneNode -> {
            AnchorPane anchorPane = (AnchorPane) anchorPaneNode;
            ((TextField) anchorPane.getChildren().get(0)).clear();
        });
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authService = authenticationService;
    }

    public void setVideoServerSettingsService(VideoServerSettingsService videoServerSettingsService) {
        this.videoServerSettingsService = videoServerSettingsService;
    }

    public void setWeightServerSettingsService(WeightServerSettingsService weightServerSettingsService) {
        this.weightServerSettingsService = weightServerSettingsService;
    }

    private void setupSaveButton() {
        adminControllerSaveButton.setOnAction((ActionEvent click) -> {
            try {
                updatePassword();
                clearPasswordFields();
            } catch (Exception e){
                AlertGenerator.generateShowAndWait(e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void setupClearPasswordButton() {
        adminControllerClearPasswordButton.setOnAction(click -> {
            adminControllerAuthenticatePassword.clear();
            clearTextFieldsVideoConfiguration();
        });
    }

    private void setupReadVideoParamButton() {
        adminControllerReadVideoParamButton.setOnAction(click -> {
            try {
                fillTextFieldsVideoConfiguration();
            } catch (Exception e){
                AlertGenerator.generateShowAndWait(e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void setupSaveVideoParamButton() {
        adminControllerSaveVideoParamButton.setOnAction(click -> {
            try {
                saveVideoConfiguration();
            } catch (Exception e){
                AlertGenerator.generateShowAndWait(e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void fillChoiceBoxUserGroup() {
        adminControllerChoiseBoxGroup.getItems().add(UserGroups.USER_GROUP_SECURITY);
        adminControllerChoiseBoxGroup.getItems().add(UserGroups.USER_GROUP_CHEF);
        adminControllerChoiseBoxGroup.getItems().add(UserGroups.USER_GROUP_ADMINISTRATOR);
        adminControllerChoiseBoxGroup.getItems().add(UserGroups.USER_GROUP_SUPER);
        adminControllerChoiseBoxGroup.getSelectionModel().selectFirst();
    }

    private void clearPasswordFields() {
        adminControllerRegistrationPasswordField.clear();
        adminControllerRegistrationConfirmPasswordField.clear();
    }

    private void updatePassword() throws Exception {
        authService.authenticate(UserOperations.canModifyAdminTab, adminControllerAuthenticatePassword.getText());

        UserService userService = authService.getUserService();

        userService.checkPasswordWithConfirmPassword(adminControllerRegistrationPasswordField.getText(), adminControllerRegistrationConfirmPasswordField.getText());
        userService.changeUserPassword(adminControllerChoiseBoxGroup.getValue().toString(), adminControllerRegistrationPasswordField.getText());
    }

    private void fillTextFieldsVideoConfiguration() throws Exception {
        authService.authenticate(UserOperations.canModifyAdminTab, adminControllerAuthenticatePassword.getText());
        log.trace(">> fillTextFieldsVideoConfiguration()");

        ServerSettings videoServerSettings = videoServerSettingsService.getFromIniFile();
        ServerSettings weightServerSettings = weightServerSettingsService.getFromIniFile();

        adminControllerVideoRecordFolderTextField.setText(videoServerSettings.getVideoRecordFolder());
        adminControllerVideoRecordDurationTextField.setText(videoServerSettings.getVideoRecordDuration());
        adminControllerVideoViewDurationTextField.setText(videoServerSettings.getVideoViewDuration());
        adminControllerVideoRecordDelayTextField.setText(videoServerSettings.getVideoRecordDelay());
        adminControllerVideoStorePeriodTextField.setText(weightServerSettings.getVideoStorePeriod());
        adminControllerVideoPartFrameWidthTextField.setText(videoServerSettings.getVideoPartFrameWidht());
        adminControllerVideoPartFrameHeightTextField.setText(videoServerSettings.getVideoPartFrameHeight());

        log.trace("<< fillTextFieldsVideoConfiguration()");
    }

    private void saveVideoConfiguration() throws Exception {
        authService.authenticate(UserOperations.canModifyAdminTab, adminControllerAuthenticatePassword.getText());
        log.trace(">> saveVideoConfiguration()");

        ServerSettings newVideoServerSettings = fillNewVideoServerSettingsObject();
        ServerSettings newWeightServerSettings = fillNewWeightServerSettingsObject();

        TaskRunner.run(new RequestTask(() -> {
            try {
                videoServerSettingsService.sendToServer(newVideoServerSettings);
                weightServerSettingsService.sendToServer(newWeightServerSettings);
                Platform.runLater(() -> AlertGenerator.generateShowAndWait(PARAMETERS_HAVE_BEEN_SAVED, Alert.AlertType.INFORMATION));
            } catch (Exception e){
                Platform.runLater(() -> AlertGenerator.generateShowAndWait(e.getMessage(), Alert.AlertType.ERROR));
            }

        }));
        log.trace("<< saveVideoConfiguration()");
    }

    private ServerSettings fillNewVideoServerSettingsObject() {
        ServerSettings serverSettings = new ServerSettings();

        serverSettings.setVideoRecordFolder(adminControllerVideoRecordFolderTextField.getText());
        serverSettings.setVideoRecordDuration(adminControllerVideoRecordDurationTextField.getText());
        serverSettings.setVideoRecordDelay(adminControllerVideoRecordDelayTextField.getText());
        serverSettings.setVideoViewDuration(adminControllerVideoViewDurationTextField.getText());
        serverSettings.setVideoPartFrameWidht(adminControllerVideoPartFrameWidthTextField.getText());
        serverSettings.setVideoPartFrameHeight(adminControllerVideoPartFrameHeightTextField.getText());

        return serverSettings;
    }

    private ServerSettings fillNewWeightServerSettingsObject() {
        ServerSettings serverSettings = new ServerSettings();

        serverSettings.setVideoRecordFolder(adminControllerVideoRecordFolderTextField.getText());
        serverSettings.setVideoStorePeriod(adminControllerVideoStorePeriodTextField.getText());

        return serverSettings;
    }

    private void clearTextFieldsVideoConfiguration() {
        textFieldsContainerVBox.getChildren().forEach(anchorPaneNode -> {
            AnchorPane anchorPane = (AnchorPane) anchorPaneNode;
            ((TextField) anchorPane.getChildren().get(0)).clear();
        });
    }
}
