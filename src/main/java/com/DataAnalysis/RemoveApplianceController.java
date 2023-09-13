package com.DataAnalysis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveApplianceController implements Initializable {
    DatabaseManager databaseManager;
    final String TABLE_NAME_APPLIANCE_DATA = "ApplianceData";
    final String P_KEY_COL_NAME_APPLIANCE = "ApplianceID";
    @FXML private Button button_RemoveAppliance;
    @FXML private ListView<String> listView_Appliance;
    @FXML
    void handel_ButtonRemoveAppliance(ActionEvent event) {
        SelectionModel<String> selectionModel = listView_Appliance.getSelectionModel();
        String selectedText = selectionModel.getSelectedItem();
        int applianceID = Integer.parseInt(selectedText.substring(0, selectedText.indexOf(':')));
        databaseManager.removeDataRow(applianceID, P_KEY_COL_NAME_APPLIANCE, TABLE_NAME_APPLIANCE_DATA);


        int applainceCount = databaseManager.getRowCount(TABLE_NAME_APPLIANCE_DATA);
        ObservableList<String> items = FXCollections.observableArrayList();
        List<String> lstApplianceID = databaseManager.getDataFromColumn(TABLE_NAME_APPLIANCE_DATA, P_KEY_COL_NAME_APPLIANCE);
        List<String> lstApplianceAlias = databaseManager.getDataFromColumn(TABLE_NAME_APPLIANCE_DATA, "Alias");
        List<String> lstApplianceType = databaseManager.getDataFromColumn(TABLE_NAME_APPLIANCE_DATA, "Type");
        for (int i = 0; i < applainceCount; i++) {
            String dataLine = lstApplianceID.get(i) + ":\t\t" + lstApplianceAlias.get(i) + "-" + lstApplianceType.get(i);
            items.add(dataLine);
        }
        listView_Appliance.setItems(items);

        Stage stage = (Stage) button_RemoveAppliance.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseManager  = new DatabaseManager("jdbc:sqlite:/Users/matthewrivera/Projects/AMS/AMS_Database");

        int applainceCount = databaseManager.getRowCount(TABLE_NAME_APPLIANCE_DATA);
        ObservableList<String> items = FXCollections.observableArrayList();
        List<String> lstApplianceID = databaseManager.getDataFromColumn(TABLE_NAME_APPLIANCE_DATA, P_KEY_COL_NAME_APPLIANCE);
        List<String> lstApplianceAlias = databaseManager.getDataFromColumn(TABLE_NAME_APPLIANCE_DATA, "Alias");
        List<String> lstApplianceType = databaseManager.getDataFromColumn(TABLE_NAME_APPLIANCE_DATA, "Type");
        for (int i = 0; i < applainceCount; i++) {
            String dataLine = lstApplianceID.get(i) + ":\t\t" + lstApplianceAlias.get(i) + "-" + lstApplianceType.get(i);
            items.add(dataLine);
        }
        listView_Appliance.setItems(items);
    }
}
