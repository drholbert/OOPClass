import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Transaction {
    private static int transactionCounter = 1000;

    private String transactionId;
    private Customer customer;
    private Map<Product, Integer> items; // Product and quantity
    private double totalAmount;
    private LocalDateTime timestamp;

    // Constructor
    public Transaction(Customer customer) {
        this.transactionId = "T" + (++transactionCounter);
        this.customer = customer;
        this.items = new HashMap<>();
        this.totalAmount = 0.0;
        this.timestamp = LocalDateTime.now();
    }

    // Method to add item to the transaction
    public void addItem(Product product, int quantity) {
        try {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero.");
            }
            if (product.getInventory() >= quantity) {
                product.reduceStock(quantity);
                items.merge(product, quantity, Integer::sum);
                totalAmount += product.getPrice() * quantity;
                System.out.println("Added " + quantity + " x " + product.getName() + " to the transaction.");
            } else {
                throw new IllegalStateException("Insufficient stock for product: " + product.getName());
            }
        } catch (Exception e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    // Method to remove item from the transaction
    public void removeItem(Product product, int quantity) {
        if (items.containsKey(product)) {
            int currentQuantity = items.get(product);
            if (quantity >= currentQuantity) {
                items.remove(product);
                totalAmount -= product.getPrice() * currentQuantity;
                product.increaseStock(currentQuantity);
                System.out.println("Removed all quantities of " + product.getName() + " from the transaction.");
            } else {
                items.put(product, currentQuantity - quantity);
                totalAmount -= product.getPrice() * quantity;
                product.increaseStock(quantity);
                System.out.println("Reduced " + quantity + " quantities of " + product.getName() + " from the transaction.");
            }
        } else {
            System.out.println("Product not found in the transaction.");
        }
    }

    // Method to finalize the transaction
    public void finalizeTransaction() {
        double finalAmount = customer.applyDiscount(totalAmount);
        customer.addLoyaltyPoints((int) finalAmount / 10); // 1 point per $10 spent
        System.out.printf("Loyalty points added: %d%n", (int) finalAmount / 10);
    }

    // Method to print the receipt
    public void printReceipt() {
        System.out.println("\n===== WildBucks Receipt =====");
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Date: " + timestamp);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Items Purchased:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;
            System.out.printf("%-10s %-20s x%d @ $%.2f each - $%.2f%n",
                    product.getCategory(),
                    product.getName(),
                    quantity,
                    product.getPrice(),
                    itemTotal);
        }
        System.out.printf("Subtotal: $%.2f%n", totalAmount);
        double finalAmount = customer.applyDiscount(totalAmount);
        if (finalAmount < totalAmount) {
            System.out.printf("Member Discount Applied: -$%.2f%n", totalAmount - finalAmount);
        }
        System.out.printf("Total Amount Due: $%.2f%n", finalAmount);
        System.out.println("=============================\n");
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction ID: ").append(transactionId).append("\n");
        sb.append("Date: ").append(timestamp).append("\n");
        sb.append("Customer: ").append(customer.getName()).append("\n");
        sb.append("Items Purchased:\n");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;
            sb.append(String.format("- %-10s %-20s x%d @ $%.2f each - $%.2f\n",
                    product.getCategory(),
                    product.getName(),
                    quantity,
                    product.getPrice(),
                    itemTotal));
        }
        sb.append(String.format("Subtotal: $%.2f\n", totalAmount));

        double finalAmount = customer.applyDiscount(totalAmount);
        if (finalAmount < totalAmount) {
            sb.append(String.format("Member Discount Applied: -$%.2f\n", totalAmount - finalAmount));
        }
        sb.append(String.format("Total Amount Due: $%.2f\n", finalAmount));
        return sb.toString();
    }
}
