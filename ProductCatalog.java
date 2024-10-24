import java.util.HashMap;

import java.util.Map;



// Class managing the collection of products

public class ProductCatalog {

    // HashMap with incremented ID as key and Product object as value

    private HashMap<Integer, Product> products;

    private int nextProductId;



    // Constructor

    public ProductCatalog() {

        products = new HashMap<>();

        nextProductId = 1;

    }



    // Add a new product to the catalog

    public void addProduct(String name, double price, int inventory) {

        products.put(nextProductId, new Product(name, price, inventory));

        nextProductId++;

    }



    // Display all products in the catalog

    public void getMenu() {

        System.out.println("Product Catalog:");

        for (Map.Entry<Integer, Product> entry : products.entrySet()) {

            int id = entry.getKey();

            Product p = entry.getValue();

            System.out.println("ID: " + id + ", Name: " + p.getName() +

                               ", Price: $" + p.getPrice() +

                               ", Inventory: " + p.getInventory());

        }

    }



    // Purchase a product by reducing its inventory

    public boolean buyProduct(int productId, int quantity) {

        if (products.containsKey(productId)) {

            Product p = products.get(productId);

            if (p.getInventory() >= quantity) {

                p.setInventory(p.getInventory() - quantity);

                System.out.println("Purchased " + quantity + " of " + p.getName());

                return true;

            } else {

                System.out.println("Insufficient inventory for product ID " + productId);

                return false;

            }

        } else {

            System.out.println("Product ID " + productId + " not found");

            return false;

        }

    }



    // Check if a product is present in the catalog

    public boolean isProductPresent(int productId) {

        return products.containsKey(productId);

    }



    // Get the price of a specific product

    public double getPrice(int productId) {

        if (products.containsKey(productId)) {

            return products.get(productId).getPrice();

        } else {

            System.out.println("Product ID " + productId + " not found");

            return -1; // Indicate that the product was not found

        }

    }

}