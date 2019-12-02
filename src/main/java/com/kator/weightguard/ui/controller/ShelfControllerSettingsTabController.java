package com.kator.weightguard.ui.controller;

import com.kator.weightguard.ui.entity.*;
import com.kator.weightguard.ui.enums.UserOperations;
import com.kator.weightguard.ui.server.request.RequestTask;
import com.kator.weightguard.ui.service.*;
import com.kator.weightguard.ui.user.UserGroupsPermissionsHolder;
import com.kator.weightguard.ui.utils.TaskRunner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kator.weightguard.ui.strings.Message.NO_CONTENT_IN_SHELF_SETTINGS_TABLE;

/**
 * Created by prats on 3/12/18.
 */
public class ShelfControllerSettingsTabController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AuthenticationService authService;

    private ShelfService shelfService;

    private EndpointService endpointService;

    private ShelfSettingService shelfSettingService;

    @FXML
    private ChoiceBox<String> shelfControllerIdChoiceBox;

    @FXML
    private TableView<ShelfSetting> controllerSettingTab;

    @FXML
    private TableColumn<ShelfSetting, String> shelfIdCol;

    @FXML
    private TableColumn<ShelfSetting, String> shelfTitleCol;

    @FXML
    private TableColumn<ShelfSetting, String> weightOnePositionCol;

    @FXML
    private TableColumn<ShelfSetting, String> weightDifferenseCol;

    @FXML
    private TableColumn<ShelfSetting, String> eventAlarmDifferenseCol;

    @FXML
    private PasswordField shelfSettingPassword;

    @FXML
    private Button clearPasswordButton;

    @FXML
    private Button shelfSettingSaveButton;

    private List<Endpoint> endpointList = new ArrayList<>();
    private List<ShelfSetting> shelfSettingList = new ArrayList<>();
    private List<ShelfSetting> shelfSettingListBuf = new ArrayList<>();
    private List<Shelf> shelfListByControllerId = new ArrayList<>();
    private List<ShelfSetting> controllerSettingTabList = new ArrayList<>();

    @FXML
    private void initialize(){
        shelfControllerIdChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
            (observableValue, value, newValue) -> {
                TaskRunner.run(new RequestTask(() -> {
                    if (((Integer) value >= 0) && ((Integer) newValue >= 0)) {
                        sendShelfSettingGetAllRequest();
                        Platform.runLater(() -> refreshShelfSettingTable());
                    }
                }));
            }
        );

        shelfSettingSaveButton.setOnAction((ActionEvent click) -> {
            try {
                authService.authenticate(UserOperations.canModifyShelfTab, shelfSettingPassword.getText());

                for (Endpoint item : endpointList) {
                    if (!shelfControllerIdChoiceBox.getValue().equals(item.getId())) continue;

                    controllerSettingTabList.clear();
                    controllerSettingTabList = controllerSettingTab.getItems();
                    int controllerSettingTabListSize = controllerSettingTabList.size();

                    if (controllerSettingTabListSize == 0) return;

                    TaskRunner.run(new RequestTask(() -> {
                        shelfSettingService.sendAll(item.getIp(), controllerSettingTabList);

                        for (int i = 0; i < controllerSettingTabListSize; i++) {
                            if (controllerSettingTabList.get(i).getTitle() != null)
                                shelfService.shelfUpdate(controllerSettingTabList.get(i).getId(), controllerSettingTabList.get(i).getTitle());
                        }
                        sendShelfSettingGetAllRequest();
                        Platform.runLater(() -> refreshShelfSettingTable());
                    }));
                }

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        });

        clearPasswordButton.setOnAction(click -> shelfSettingPassword.clear());

        initShelfSettingTableController();
    }

    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    public void setShelfService(ShelfService shelfService){
        this.shelfService = shelfService;
    }

    public void setShelfSettingService(ShelfSettingService shelfSettingService){
        this.shelfSettingService = shelfSettingService;
    }

    public void setEndpointService(EndpointService endpointService){
        this.endpointService = endpointService;
    }

    public void refreshEndpointChoiceBox() {
        log.trace(">> refreshEndpointList()");

        int endpointPreviousSize = getEndpointListSize();
        int endpointSelection = shelfControllerIdChoiceBox.getSelectionModel().getSelectedIndex();

        shelfControllerIdChoiceBox.getItems().clear();

        endpointList.forEach(item -> shelfControllerIdChoiceBox.getItems().add(item.getId()));

        if(endpointPreviousSize == endpointList.size()) {
            shelfControllerIdChoiceBox.getSelectionModel().select(endpointSelection);
        } else {
            shelfControllerIdChoiceBox.getSelectionModel().selectFirst();
            refreshShelfSettingTable();
        }
        if(controllerSettingTab.getItems().size() == 0) refreshShelfSettingTable();

        log.trace("<< refreshEndpointList()");
    }

    public void sendEndpointsGetAllRequest() {
        endpointList = endpointService.getAll();
    }

    public boolean endpointListShowing(){
        return shelfControllerIdChoiceBox.isShowing();
    }

    public int getEndpointListSize(){
        return shelfControllerIdChoiceBox.getItems().size();
    }

    public void initShelfSettingTableController() {
        controllerSettingTab.setEditable(true);
        controllerSettingTab.setPlaceholder(new Label(NO_CONTENT_IN_SHELF_SETTINGS_TABLE));

        setupColumn(shelfIdCol, "Id");
        setupColumn(shelfTitleCol, "Title");
        setupColumn(weightOnePositionCol, "WeightOfOnePosition");
        setupColumn(weightDifferenseCol, "WeightDifferense");
        setupColumn(eventAlarmDifferenseCol, "EventAlarmDifference");
    }

    public void setupColumn(TableColumn<ShelfSetting, String> tableColumn, String property){
        tableColumn.setCellValueFactory(new PropertyValueFactory<ShelfSetting, String>(property));
        tableColumn.setCellFactory(TextFieldTableCell.<ShelfSetting>forTableColumn());
        tableColumn.setOnEditCommit((TableColumn.CellEditEvent<ShelfSetting, String> event) -> {
            ShelfSetting setting = event.getTableView().getItems().get(event.getTablePosition().getRow());

            switch (tableColumn.getId()) {
                case "shelfIdCol":
                    setting.setId(event.getNewValue());
                    break;
                case "shelfTitleCol":
                    setting.setTitle(event.getNewValue());
                    break;
                case "weightOnePositionCol":
                    setting.setWeightOfOnePosition(event.getNewValue());
                    break;
                case "weightDifferenseCol":
                    setting.setWeightDifferense(event.getNewValue());
                    break;
                default:
                    setting.setEventAlarmDifference(event.getNewValue());
            }
        });
    }

    public void refreshShelfSettingTable(){
        log.trace(">> refreshShelfSettingTable()");

        controllerSettingTab.getItems().clear();

        int shelfSettingListSize = shelfSettingList.size();
        int shelfListByControllerIdSize = shelfListByControllerId.size();

        for (int i = 0; i < shelfSettingListSize; i++) {
            for (int i2 = 0; i2 < shelfListByControllerIdSize; i2++) {
                if (shelfSettingList.get(i).getId().equals(shelfListByControllerId.get(i2).getId())) {
                    shelfSettingList.get(i).setTitle(shelfListByControllerId.get(i2).getTitle());
                    break;
                } else if (i2 == shelfListByControllerIdSize - 1) {
                    shelfSettingList.get(i).setTitle("error");
                }
            }
        }

        ObservableList<ShelfSetting> SettObservableList = FXCollections.observableList(shelfSettingList);
        controllerSettingTab.setItems(SettObservableList);

        log.trace("<< refreshShelfSettingTable()");
    }

    public void sendShelfSettingGetAllRequest(){
        String selectedShelfID = shelfControllerIdChoiceBox.getValue();
        if(selectedShelfID == null) return;

        for(Endpoint item: endpointList){
            if(!selectedShelfID.equals(item.getId())) continue;
            shelfSettingListBuf = shelfSettingService.getAll(item.getIp());

            if(shelfControllerIdChoiceBox.getValue().equals(item.getId())) {
                shelfSettingList = shelfSettingListBuf;
                shelfListByControllerId = shelfService.getShelfByControllerId(item.getId());
            }
        }
    }
}
