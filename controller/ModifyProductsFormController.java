package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for modify products form
 */
public class ModifyProductsFormController implements Initializable {

    Stage stage;
    Parent scene;
    private int productIndex;
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Product modProduct;

    @FXML
    private TableColumn<Part, Integer> modProdAddPartId;

    @FXML
    private TableColumn<Part, Integer> modProdAddPartInv;

    @FXML
    private TableColumn<Part, String> modProdAddPartName;

    @FXML
    private TableColumn<Part, Double> modProdAddPartPrice;

    @FXML
    private TableView<Part> modProdAddPartTableView;

    @FXML
    private TableColumn<Part, Integer> modProdAssociatedPartsId;

    @FXML
    private TableColumn<Part, Integer> modProdAssociatedPartsInv;

    @FXML
    private TableColumn<Part, String> modProdAssociatedPartsName;

    @FXML
    private TableColumn<Part, Double> modProdAssociatedPartsPrice;

    @FXML
    private TableView<Part> modProdAssociatedPartsTableView;

    @FXML
    private TextField modProductIdTxt;

    @FXML
    private TextField modProductInvTxt;

    @FXML
    private TextField modProductMaxTxt;

    @FXML
    private TextField modProductMinTxt;

    @FXML
    private TextField modProductNameTxt;

    @FXML
    private TextField modProductPartSearchTxt;

    @FXML
    private TextField modProductPriceTxt;

    /**
     * Adds associated part to product
     * @param event
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) {
        if(!(modProdAddPartTableView.getSelectionModel().getSelectedItem() == null)) {
            modProduct.addAssociatedPart(modProdAddPartTableView.getSelectionModel().getSelectedItem());
            modProdAssociatedPartsTableView.setItems(modProduct.getAllAssociatedParts());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No selection");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        }
    }

    /**
     * Removes associated part from product
     * @param event
     */
    @FXML
    void onActionRemovePartFromProduct(ActionEvent event) {
        if(!(modProdAssociatedPartsTableView.getSelectionModel().getSelectedItem() == null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                modProduct.deleteAssociatedPart(modProdAssociatedPartsTableView.getSelectionModel().getSelectedItem());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No selection");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        }
    }

    /**
     * Saves product
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int stock = Integer.parseInt(modProductInvTxt.getText());
            int min = Integer.parseInt(modProductMinTxt.getText());
            int max = Integer.parseInt(modProductMaxTxt.getText());

            if ((max>=min) && (stock>=min) && (stock<=max)) {
                modProduct.setId(Integer.parseInt(modProductIdTxt.getText()));
                modProduct.setName(modProductNameTxt.getText());
                modProduct.setPrice(Double.parseDouble(modProductPriceTxt.getText()));
                modProduct.setStock(Integer.parseInt(modProductInvTxt.getText()));
                modProduct.setMin(Integer.parseInt(modProductMinTxt.getText()));
                modProduct.setMax(Integer.parseInt(modProductMaxTxt.getText()));
                Inventory.updateProduct(Integer.parseInt(modProductIdTxt.getText()), modProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setContentText("Maximum must be greater than or equal to minimum. Inv must be between min and max.");
                alert.showAndWait();
            }

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please use correct data types in text fields" +"\n"+ "Name: String" +"\n"+ "Inv: Integer" +"\n"+ "Price: Double" +"\n"+ "Max: Integer" +"\n"+ "Min: Integer");
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
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Searches parts for user input
     * @param event
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        ObservableList<Part> parts = Inventory.lookupPart(modProductPartSearchTxt.getText());
        modProdAddPartTableView.setItems(parts);
        modProductPartSearchTxt.setText("");
    }

    /**
     * Receives product data from main screen
     * @param product
     */
    public void sendProduct(Product product) {
        modProduct = product;

        modProductIdTxt.setText(String.valueOf(modProduct.getId()));
        modProductNameTxt.setText((modProduct.getName()));
        modProductPriceTxt.setText(String.valueOf(modProduct.getPrice()));
        modProductInvTxt.setText(String.valueOf(modProduct.getStock()));
        modProductMinTxt.setText(String.valueOf(modProduct.getMin()));
        modProductMaxTxt.setText(String.valueOf(modProduct.getMax()));
        modProdAssociatedPartsTableView.setItems(modProduct.getAllAssociatedParts());
    }

    /**
     * Initialize method
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set available parts table observable list
        modProdAddPartTableView.setItems(Inventory.getAllParts());
        modProdAddPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdAddPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdAddPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdAddPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

//        modProdAssociatedPartsTableView.setItems(associatedParts);
        modProdAssociatedPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdAssociatedPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdAssociatedPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdAssociatedPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
