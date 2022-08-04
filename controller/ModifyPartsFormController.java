package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for modify products form
 */
public class ModifyPartsFormController implements Initializable {

    Stage stage;
    Parent scene;
    private int partIndex;

    @FXML
    private TextField modPartIdMachineTxt;

    @FXML
    private TextField modPartIdTxt;

    @FXML
    private RadioButton modPartInHouseRBtn;

    @FXML
    private TextField modPartInvTxt;

    @FXML
    private TextField modPartMaxTxt;

    @FXML
    private TextField modPartMinTxt;

    @FXML
    private TextField modPartNameTxt;

    @FXML
    private RadioButton modPartOutsourcedRBtn;

    @FXML
    private TextField modPartPriceTxt;

    @FXML
    private Label machineIdOrCompanyName;

    /**
     * Saves part
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException{
        try {
            int id = Integer.parseInt(modPartIdTxt.getText());
            String name = modPartNameTxt.getText();
            double price = Double.parseDouble(modPartPriceTxt.getText());
            int stock = Integer.parseInt(modPartInvTxt.getText());
            int min = Integer.parseInt(modPartMinTxt.getText());
            int max = Integer.parseInt(modPartMaxTxt.getText());
            if((max>=min) && (stock>=min) && (stock<=max)) {
                if (modPartInHouseRBtn.isSelected()) {
                    int machineId = Integer.parseInt(modPartIdMachineTxt.getText());
                    Inventory.updatePart(partIndex, new InHouse(id, name, price, stock, min, max, machineId));

                } else if (modPartOutsourcedRBtn.isSelected()) {
                    String companyName = modPartIdMachineTxt.getText();
                    Inventory.updatePart(partIndex, new Outsourced(id, name, price, stock, min, max, companyName));
                }
                toMain(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setContentText("Maximum must be greater than or equal to minimum. Inv must be between min and max.");
                alert.showAndWait();
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please use correct data types in text fields" +"\n"+ "Name: String" +"\n"+ "Inv: Integer" +"\n"+ "Price/Cost: Double" +"\n"+ "Max: Integer" +"\n"+ "Min: Integer" +"\n"+ "Machine ID: Integer OR Company Name: String");
            alert.showAndWait();
        }
    }

    /**
     * Returns to main screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionToMain(ActionEvent event) throws IOException {
        toMain(event);
    }

    /**
     * When inhouse radio button is selected, changes text field on inhouse
     * @param event
     */
    @FXML
    void onActionAddInHouse(ActionEvent event) {
        machineIdOrCompanyName.setText("Machine ID");
    }

    /**
     * When outsourced radio button is selected, changes text field on outsourced
     * @param event
     */
    @FXML
    void onActionAddOutsourced(ActionEvent event) {
        machineIdOrCompanyName.setText("Company Name");
    }

    /**
     * Received part data from main screen
     * @param part
     * @param selectedIndex
     */
    public void sendPart(Part part, int selectedIndex) {
        partIndex = selectedIndex;
        if (part instanceof InHouse) {
            modPartInHouseRBtn.setSelected(true);
            modPartIdMachineTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            modPartOutsourcedRBtn.setSelected(true);
            machineIdOrCompanyName.setText("Company Name");
            modPartIdMachineTxt.setText(((Outsourced) part).getCompanyName());
        }

        modPartIdTxt.setText(String.valueOf(part.getId()));
        modPartNameTxt.setText((part.getName()));
        modPartPriceTxt.setText(String.valueOf(part.getPrice()));
        modPartInvTxt.setText(String.valueOf(part.getStock()));
        modPartMinTxt.setText(String.valueOf(part.getMin()));
        modPartMaxTxt.setText(String.valueOf(part.getMax()));
    }

    /**
     * Method to return to main screen
     * @param event
     * @throws IOException
     */
    public void toMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initialize method
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
