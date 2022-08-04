package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for add parts form
 */
public class AddPartsFormController implements Initializable {

    Stage stage;
    Parent scene;

    public void toMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private RadioButton addPartInHouseRBtn;

    @FXML
    private RadioButton addPartOutsourcedRBtn;

    @FXML
    private TextField addPartIdTxt;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartMachineTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private Label machineIdOrCompanyName;

    /**
     * Saves part when button is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException{
        try {
            int idCounter = 0;
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() > idCounter) {
                    idCounter = part.getId();
                }
            }
            int id = ++idCounter;
            String name = addPartNameTxt.getText();
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int stock = Integer.parseInt(addPartInvTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            if((max>=min) && (stock>=min) && (stock<=max)) {
                if (addPartInHouseRBtn.isSelected()) {
                    int machineId = Integer.parseInt(addPartMachineTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } else if (addPartOutsourcedRBtn.isSelected()) {
                    String companyName = addPartMachineTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
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
     * Returns to main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionToMain(ActionEvent event) throws IOException {
        toMain(event);
    }

    /**
     * When radio button is clicked, shows outsourced text field
     * @param event
     */
    @FXML
    void onActionAddOutsourced(ActionEvent event) {
        machineIdOrCompanyName.setText("Company Name");
    }

    /**
     * When radio button is clicked, shows inhouse text field
     * @param event
     */
    @FXML
    void onActionAddInHouse(ActionEvent event) {
        machineIdOrCompanyName.setText("Machine ID");
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
