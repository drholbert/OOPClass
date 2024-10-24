import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private int loyaltyPoints;
    private List<Transactions> orderHistory;

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.loyaltyPoints = 0;
        this.orderHistory = new ArrayList<>();
    }

    public void addPoints(int points) {
        loyaltyPoints += points;
    }

    public double applyDiscount(double totalPrice) {
        double discount = Math.min(loyaltyPoints * 0.01, totalPrice);  // 1 point = $0.01
        loyaltyPoints = 0;  // Reset points after applying the discount
        return totalPrice - discount;
    }

    public List<Transactions> getOrderHistory() {
        return orderHistory;
    }

    public Transactions placeOrder(Product product, int quantity, int transactionId) {
        if (product.isAvailable(quantity)) {
            product.reduceStock(quantity);
            double totalPrice = product.getPrice() * quantity;
            Transactions transaction = new Transactions();
            orderHistory.add(transaction);
            addPoints((int) (totalPrice / 10));  // For every $10 spent, get 1 point
            return transaction;
        } else {
            throw new IllegalArgumentException("Product out of stock");
        }
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public String getName() {
        return name;
    }
}