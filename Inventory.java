import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;
    private static final String FILE_PATH = "OOPClass\\inventory.csv";

    // Constructor
    public Inventory() {
        this.products = new HashMap<>();
        loadProductsFromFile();
    }

    // Method to load products from CSV file
    private void loadProductsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Parse product details from the CSV line
                String category = values[0];
                String name = values[1];
                double price = Double.parseDouble(values[2]);
                int inventory = Integer.parseInt(values[3]);

                Product product = new Product(name, category, price, inventory);
                products.put(name, product);
            }
        } catch (IOException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
        }
    }

    // Method to add a product
    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    // Method to get a product by name
    public Product getProductByName(String name) {
        return products.get(name);
    }

    // Method to get all products
    public Map<String, Product> getAllProducts() {
        return products;
    }
}
