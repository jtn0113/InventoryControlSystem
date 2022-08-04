package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Inventory class data
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * adds new part
     * @param part
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * adds new product
     * @param product
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * returns all parts
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * returns all products
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * deletes part
     * @param selectedPart
     * @return Boolean
     */
    public static Boolean deletePart(Part selectedPart) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the selected part. Continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return null;
        }
    }

    /**
     * deletes product
     * @param selectedProduct
     */
    public static void deleteProduct(Product selectedProduct) {
                allProducts.remove(selectedProduct);
    }

    /**
     * updates part
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
            allParts.set(index, selectedPart);
    }

    /**
     * returns all filtered parts
     * @return All Filtered Parts
     */
    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }

    /**
     * returns all filtered products
     * @return All Filtered Products
     */
    public static ObservableList<Product> getAllFilteredProducts() {
        return filteredProducts;
    }

    /**
     * Updates product
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        Product product = Inventory.lookupProduct(index);
        Inventory.deleteProduct(product);
        Inventory.addProduct(newProduct);
    }

    /**
     * Search for parts by id
     * @param partId
     * @return Part
     */
    public static Part lookupPart(int partId) {
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == partId) {
                Inventory.getAllFilteredParts().add(part);
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Results");
        alert.setContentText("No Parts Found");
        alert.showAndWait();
        return null;
    }

    /**
     * Search for products by id
     * @param productId
     * @return Product
     */
    public static Product lookupProduct(int productId) {
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == productId) {
                Inventory.getAllFilteredProducts().add(product);
                return product;
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Results");
        alert.setContentText("No Products Found");
        alert.show();
        return null;
    }

    /**
     * search for parts by name
     * @param partName
     * @return All Filtered Parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        if (!(Inventory.getAllFilteredParts().isEmpty())) {
            Inventory.getAllFilteredParts().clear();
        }
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(partName)) {
                Inventory.getAllFilteredParts().add(part);
            }
        }
        if (Inventory.getAllFilteredParts().isEmpty()) {
            lookupPart(Integer.parseInt(partName));
            if (Inventory.getAllFilteredParts().isEmpty()) {
                return Inventory.getAllParts();
            } else {
                return Inventory.getAllFilteredParts();
            }
        } else {
            return Inventory.getAllFilteredParts();
        }
    }

    /**
     * search for products by name
     * @param productName
     * @return All filtered products
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        if(!(Inventory.getAllFilteredProducts().isEmpty())) {
            Inventory.getAllFilteredProducts().clear();
        }
        for(Product product : Inventory.getAllProducts()) {
            if(product.getName().contains(productName)) {
                Inventory.getAllFilteredProducts().add(product);
            }
        }
        if(Inventory.getAllFilteredProducts().isEmpty()) {
            lookupProduct(Integer.parseInt(productName));
            if(Inventory.getAllFilteredProducts().isEmpty()) {
                return Inventory.getAllProducts();
            } else {
                return Inventory.getAllFilteredProducts();
            }
        } else {
            return Inventory.getAllFilteredProducts();
        }
    }

}

