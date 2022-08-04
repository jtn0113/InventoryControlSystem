package model;

/**
 * InHouse class data
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * gets ID
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * sets ID
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
