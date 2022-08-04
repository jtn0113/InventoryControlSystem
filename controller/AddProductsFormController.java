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
 * Controller for add products form
 */
public class AddProductsFormController implements Initializable {

    Stage stage;
    Parent scene;
    Product product = new Product(0, "test name", 5, 5, 5, 5);

    /**
     * Method to return to main menu
     * @param event
     * @throws IOException
     */
    public void toMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private TableColumn<Part, Integer> addProdAddPartId;

    @FXML
    private TableColumn<Part, Integer> addProdAddPartInv;

    @FXML
    private TableColumn<Part, String> addProdAddPartName;

    @FXML
    private TableColumn<Part, Double> addProdAddPartPrice;

    @FXML
    private TableView<Part> addProdAddPartTableView;

    @FXML
    private TableColumn<Part, Integer> addProdAssociatedPartsId;

    @FXML
    private TableColumn<Part, Integer> addProdAssociatedPartsInv;

    @FXML
    private TableColumn<Part, String> addProdAssociatedPartsName;

    @FXML
    private TableColumn<Part, Double> addProdAssociatedPartsPrice;

    @FXML
    private TableView<Part> addProdAssociatedPartsTableView;

    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPartSearchTxt;

    @FXML
    private TextField addProductPriceTxt;

    /**
     * Adds associated part to product
     * @param event
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) {
        if(!(addProdAddPartTableView.getSelectionModel().getSelectedItem() == null)) {
            Part associatedPart = addProdAddPartTableView.getSelectionModel().getSelectedItem();
            product.addAssociatedPart(associatedPart);
            addProdAssociatedPartsTableView.setItems(product.getAllAssociatedParts());
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
        if(!(addProdAssociatedPartsTableView.getSelectionModel().getSelectedItem() == null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                product.deleteAssociatedPart(addProdAssociatedPartsTableView.getSelectionModel().getSelectedItem());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No selection");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        }
    }

    /**
     * LOGICAL ERROR
     * A logical error that I had in my code:
     *     In my add products and modify products controllers, I was using a static method to save the product.
     *     This was causing bugs on my products and associated parts.
     *     When I saved a product, it would update the data for all my products.
     *     I corrected this by using instance methods instead.
     * Saves product
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        int idCounter = 999;
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() > idCounter) {
                idCounter = product.getId();
            }
        }
        int id = ++idCounter;
        try {
            int stock = Integer.parseInt(addProductInvTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());

            if ((max >= min) && (stock >= min) && (stock <= max)) {
                product.setId(id);
                product.setName(addProductNameTxt.getText());
                product.setPrice(Double.parseDouble(addProductPriceTxt.getText()));
                product.setStock(Integer.parseInt(addProductInvTxt.getText()));
                product.setMax(Integer.parseInt(addProductMaxTxt.getText()));
                product.setMin(Integer.parseInt(addProductMinTxt.getText()));

                Inventory.addProduct(product);
                toMain(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setContentText("Maximum must be greater than or equal to minimum. Inv must be between min and max.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please use correct data types in text fields" + "\n" + "Name: String" + "\n" + "Inv: Integer" + "\n" + "Price: Double" + "\n" + "Max: Integer" + "\n" + "Min: Integer");
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
     * Searches parts for user input
     * @param event
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        ObservableList<Part> parts = Inventory.lookupPart(addProductPartSearchTxt.getText());
        addProdAddPartTableView.setItems(parts);
        addProductPartSearchTxt.setText("");
    }

    /**
     * Initialize and build tables
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set available parts table observable list
        addProdAddPartTableView.setItems(Inventory.getAllParts());
        addProdAddPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdAddPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdAddPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdAddPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProdAssociatedPartsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdAssociatedPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdAssociatedPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdAssociatedPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
