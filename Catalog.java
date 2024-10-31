import java.util.Map;

public class Catalog {
    private Inventory inventory;

    public Catalog(Inventory inventory) {
        this.inventory = inventory;
    }

    // Display all products in the inventory
    public void displayProducts() {
        Map<String, Product> products = inventory.getAllProducts();

        System.out.println("\n╔═══════════════════════ WildBucks Menu ═══════════════════════════════╗");
        System.out.println("║ ID      │ Category   │ Name                │ Price     │ Stock      ║");
        System.out.println("╠═════════╪════════════╪═════════════════════╪═══════════╪════════════╣");

        for (Product product : products.values()) {
            System.out.printf("║ %-7s │ %-10s │ %-20s │ $%-8.2f │ %-10d ║\n",
                product.getProductId(),
                truncate(product.getCategory(), 10),
                truncate(product.getName(), 20),
                product.getPrice(),
                product.getInventory()
            );
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
    }

    // Truncate product name if it's too long
    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}
