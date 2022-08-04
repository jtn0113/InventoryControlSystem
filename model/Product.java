package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int max;
    private int min;

    public Product(int id, String name, double price, int stock, int max, int min) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.max = max;
        this.min = min;
    }

    /**
     * returns id
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * sets id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns name
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns price
     * @return Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * returns stock
     * @return Stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets stock
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * returns max
     * @return Max
     */
    public int getMax() {
        return max;
    }

    /**
     * sets max
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * returns min
     * @return Min
     */
    public int getMin() {
        return min;
    }

    /**
     * sets min
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * adds associated part
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deletes associated part
     * @param selectedAssociatedPart
     * @return Boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * returns associated parts
     * @return All Associated Parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
