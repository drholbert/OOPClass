import java.util.HashMap;
import java.util.Map;

public class Transactions {
    private ProductCatalog productCatalog;
    private Map<Integer, Customer> customers;
    private Map<Integer, TransactionRecord> transactions;
    private int transactionId;
    
    public Transactions(){
        
    }

    public Transactions(ProductCatalog productCatalog, Map<Integer, Customer> customers) {
        this.productCatalog = productCatalog;
        this.customers = customers;
        this.transactions = new HashMap<>();
        this.transactionId = 1;
    }

    // Calculate total price of a product based on product ID and quantity
    public double calculatePrice(int productId, int quantity) {
        double price = productCatalog.getPrice(productId);
        return (price >= 0) ? price * quantity : -1;
    }

    // Apply loyalty points discount for the customer
    public double applyDiscount(int customerId, double totalPrice) {
        Customer customer = customers.get(customerId);
        return customer.applyDiscount(totalPrice);  // Uses Customer class's applyDiscount method
    }

    // Process a transaction
    public String processTransaction(int customerId, int productId, int quantity, double paidAmount, boolean usePoints) {
        // Check if the product exists
        if (!productCatalog.isProductPresent(productId)) {
            return "Product not available.";
        }

        // Calculate total price
        double totalPrice = calculatePrice(productId, quantity);
        if (totalPrice < 0) {
            return "Invalid product ID.";
        }

        // If using points, apply discount
        if (usePoints) {
            totalPrice = applyDiscount(customerId, totalPrice);
        }

        // Check if the customer has sufficient funds
        if (paidAmount < totalPrice) {
            return "Insufficient payment.";
        }

        // Record the transaction
        double change = paidAmount - totalPrice;
        Customer customer = customers.get(customerId);
        productCatalog.buyProduct(productId, quantity);  // Reduces inventory for the product
        customer.addPoints((int) (totalPrice / 10));  // For every $10 spent, the customer earns 1 point
        TransactionRecord transaction = new TransactionRecord(transactionId++, customerId, productId, quantity, totalPrice, paidAmount, change);
        transactions.put(transaction.getTransactionId(), transaction);

        return "Transaction successful! Change: $" + String.format("%.2f", change) + " | Final Price: $" + String.format("%.2f", totalPrice);
    }

    // Record of each transaction
    private static class TransactionRecord {
        private int transactionId;
        private int customerId;
        private int productId;
        private int quantity;
        private double totalPrice;
        private double paidAmount;
        private double change;

        public TransactionRecord(int transactionId, int customerId, int productId, int quantity, double totalPrice, double paidAmount, double change) {
            this.transactionId = transactionId;
            this.customerId = customerId;
            this.productId = productId;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
            this.paidAmount = paidAmount;
            this.change = change;
        }

        public int getTransactionId() {
            return transactionId;
        }
    }

    // Main method to test the Transactions class
    public static void main(String[] args) {
        // Initialize product catalog
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.addProduct("Book A", 19.99, 50);
        productCatalog.addProduct("Book B", 29.99, 30);

        // Initialize customers
        Map<Integer, Customer> customers = new HashMap<>();
        customers.put(1, new Customer(1, "Alice", "alice@example.com"));
        customers.put(2, new Customer(2, "Bob", "bob@example.com"));

        // Initialize transaction system
        Transactions transactions = new Transactions(productCatalog, customers);

        // Process a transaction for customer 1
        String result = transactions.processTransaction(1, 1, 2, 40, true);  // Customer 1 buys 2 of product 1, paying $40 and using loyalty points
        System.out.println(result);  // Expected Output: Transaction successful! Change: $0.02 | Final Price: $39.98
    }
}