package model;

public class InventoryItem {
    private String partNumber;
    private int testNumber;
    private int serialNumber;
    private int quantity;
    private String allocation;
    private int purchaseOrder;
    private String description;
    private String location;

    public InventoryItem(String partNumber, int testNumber, int serialNumber, int quantity, String allocation, int purchaseOrder, String description, String location) {
        this.partNumber = partNumber;
        this.testNumber = testNumber;
        this.serialNumber = serialNumber;
        this.quantity = quantity;
        this.allocation = allocation;
        this.purchaseOrder = purchaseOrder;
        this.description = description;
        this.location = location;
    }

    public InventoryItem() {};

    public String getPartNumber() {
        return partNumber;
    }
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getTestNumber() {
        return testNumber;
    }
    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAllocation() {
        return allocation;
    }
    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    public int getPurchaseOrder() {
        return purchaseOrder;
    }
    public void setPurchaseOrder(int purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

}
