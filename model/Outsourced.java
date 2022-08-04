package model;

/**
 * Outsourced class data
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * gets company name
     * @return Company Name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * sets company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
