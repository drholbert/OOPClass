class Product {

    private String name;

    private double price;

    private int inventory;



    // Constructor

    public Product(String name, double price, int inventory) {

        this.name = name;

        this.price = price;

        this.inventory = inventory;

    }



    // Getters and setters

    public String getName() { return name; }

    public double getPrice() { return price; }

    public int getInventory() { return inventory; }

    public void setInventory(int inventory) { this.inventory = inventory; }
    public Boolean isAvailable(int p){return true;}
    public void reduceStock(int num){}
    public int getProductId(){return 0;}

}