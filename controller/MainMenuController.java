package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the main screen
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    public void showScene(ActionEvent event, String url) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private TableColumn<Part, Integer> mainPartIdCol;

    @FXML
    private TableColumn<Part, Integer> mainPartInvCol;

    @FXML
    private TableColumn<Part, String> mainPartNameCol;

    @FXML
    private TableColumn<Part, Double> mainPartPriceCol;

    @FXML
    public TableView<Part> mainPartTableView;

    @FXML
    private TableColumn<Product, Integer> mainProdIdCol;

    @FXML
    private TableColumn<Product, Integer> mainProdInvCol;

    @FXML
    private TableColumn<Product, String> mainProdNameCol;

    @FXML
    private TableColumn<Product, Double> mainProdPriceCol;

    @FXML
    private TableView<Product> mainProductTableView;

    @FXML
    private TextField mainSearchPartTxt;

    @FXML
    private TextField mainSearchProductTxt;

    /**
     * Opens add part form
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        showScene(event,"/view/AddPartsForm.fxml");
    }

    /**
     * Opens add product form
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        showScene(event, "/view/AddProductsForm.fxml");
    }

    /**
     * Deletes selected part
     * @param event
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part selectedPart = mainPartTableView.getSelectionModel().getSelectedItem();
        if(!(selectedPart == null)) {
            Inventory.deletePart(selectedPart);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
    }

    /**
     * Deletes selected product
     * @param event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = mainProductTableView.getSelectionModel().getSelectedItem();
        if(!(selectedProduct == null)) {
            if (selectedProduct.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the selected product. Continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Associated parts found");
                alert.setContentText("Products with associated parts cannot be deleted");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        }
    }

    /**
     * Opens modify part form
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartsForm.fxml"));
            loader.load();

            ModifyPartsFormController MPController = loader.getController();
            MPController.sendPart(mainPartTableView.getSelectionModel().getSelectedItem(), mainPartTableView.getSelectionModel().getSelectedIndex());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        }

    }

    /**
     * Opens modify product form
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductsForm.fxml"));
            loader.load();

            ModifyProductsFormController MPController = loader.getController();
            MPController.sendProduct(mainProductTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No product selected");
            alert.setContentText("Please select a product");
            alert.showAndWait();
        }
    }

    /**
     * Closes application
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Searches parts for user input
     * @param event
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        try {
            ObservableList<Part> parts = Inventory.lookupPart(mainSearchPartTxt.getText());
            mainPartTableView.setItems(parts);
            mainSearchPartTxt.setText("");
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No results");
            alert.setContentText("No parts found");
            alert.showAndWait();
        }



    }

    /**
     * Searches products for user input
     * @param event
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) {
        try {
            ObservableList<Product> products = Inventory.lookupProduct(mainSearchProductTxt.getText());
            mainProductTableView.setItems(products);
            mainSearchProductTxt.setText("");
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No results");
            alert.setContentText("No products found");
            alert.showAndWait();
        }
    }

    /**
     * Initialize method
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set parts table observable list
        mainPartTableView.setItems(Inventory.getAllParts());
        mainPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set products table observable list
        mainProductTableView.setItems(Inventory.getAllProducts());
        mainProdIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
