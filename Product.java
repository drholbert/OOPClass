public class Product {
    private String productId; // Optional or autogenerated if needed
    private String category;
    private String name;
    private double price;
    private int inventory;

    public Product(String name, String category, double price, int inventory) {
        // Generate a unique product ID if not provided, or handle it separately
        this.productId = generateUniqueId(); 
        this.category = category;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }

    private String generateUniqueId() {
        // Generate a unique ID based on timestamp or some other method (e.g., UUID)
        return "PROD-" + System.currentTimeMillis();
    }

    /* GETTERS AND SETTERS */
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getInventory() {
        return inventory;
    }
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public boolean reduceStock(int quantity) {
        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than 0");
            return false;
        } 
        else if (inventory == 0) {
            System.out.printf("Error: Sorry, %s is out of stock\n", this.name);
            return false;
        } 
        else if (quantity > inventory) {
            System.out.printf("Error: Not enough stock of %s\n", this.name);
            return false;
        }
        inventory -= quantity;
        System.out.printf("Success: %d items of %s removed from stock\n", quantity, this.name);
        return true;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than 0");
        } else {
            inventory += quantity;
            System.out.printf("Success: %d items of %s added to stock\n", quantity, this.name);
        }
    }
}
