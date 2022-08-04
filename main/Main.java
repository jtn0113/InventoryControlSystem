package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * Creates application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 897, 464));
        primaryStage.show();
    }

    /**
     * Loads application
     * @param args
     */
    public static void main(String[] args) {
        //Create test data for parts table
        InHouse part1 = new InHouse(1, "Brakes", 15, 10, 1, 20, 100);
        InHouse part2 = new InHouse(2, "Wheel", 11.00, 16, 1, 20, 101);
        Outsourced part3 = new Outsourced(3, "Seat", 15.00, 10, 1, 20, "Mongoose");
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        // Create test data for products table
        Product product1 = new Product(1000, "Giant Bike", 299.99, 5, 10, 1);
        Product product2 = new Product(1001, "Tricycle", 99.99, 1, 1, 0);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch(args);
    }
}