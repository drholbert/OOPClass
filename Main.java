import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize Inventory and Catalog
        Inventory inventory = new Inventory();
        Catalog catalog = new Catalog(inventory);

        // Create a Customer
        Customer customer = new Customer("C001", "John Doe", true, 0);

        // Create a Transaction
        Transaction transaction = new Transaction(customer);

        // Initialize Scanner for user input
        Scanner scanner = new Scanner(System.in);
        String productName;
        int quantity;

        // Display the products menu
        System.out.println("\nWelcome to WildBucks! Here’s our menu:");
        catalog.displayProducts();

        // Loop to allow the user to add items to the transaction
        while (true) {
            System.out.print("\nEnter the product name to add to your transaction (or type 'done' to finish): ");
            productName = scanner.nextLine();

            // Exit the loop if user is done
            if (productName.equalsIgnoreCase("done")) {
                break;
            }

            // Check if the product exists in the inventory
            Product product = inventory.getProductByName(productName);
            if (product == null) {
                System.out.println("Product not found. Please enter a valid product name from the menu.");
                catalog.displayProducts(); // Display the menu again for reference
                continue;
            }

            // Ask for the quantity
            System.out.print("Enter the quantity: ");
            while (true) {
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                    if (quantity <= 0) {
                        System.out.print("Quantity must be greater than zero. Enter quantity again: ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a numeric quantity: ");
                }
            }

            // Add item to transaction
            transaction.addItem(product, quantity);
        }

        // Finalize the transaction
        transaction.finalizeTransaction();

        // Print the receipt
        transaction.printReceipt();

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        catalog.displayProducts();

        // Display customer's loyalty points
        System.out.println("Customer's Loyalty Points: " + customer.getLoyaltyPoints());

        // Close the scanner
        scanner.close();
    }
}
