package model;

// Encapsulation is used to protect menu item data.
public class MenuItem {

    private int itemId;
    private String itemName;
    private String category;
    private double price;
    private boolean available;

    public MenuItem(int itemId, String itemName, String category, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayItem() {
        System.out.println(itemId + ". " + itemName + " | " + category + " | £" + price + " | Available: " + available);
    }
}