public class Customer {
    private String customerId;
    private String name;
    private boolean isMember;
    private int loyaltyPoints;

    // Constructor
    public Customer(String customerId, String name, boolean isMember, int loyaltyPoints) {
        this.customerId = customerId;
        this.name = name;
        this.isMember = isMember;
        this.loyaltyPoints = 0;
    }

    /* GETTERS AND SETTERS */
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isMember() {
        return isMember;
    }
    public void setMember(boolean isMember) {
        this.isMember = isMember;
    }
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    // Method to add loyalty points
    public void addLoyaltyPoints(int points) {
        loyaltyPoints += points;
    }

    // Method to apply discount if customer is a member
    public double applyDiscount(double totalAmount) {
        if (isMember) {
            return totalAmount * 0.9; // give 10% discount
        }
        return totalAmount;
    }
}
