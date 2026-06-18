package model;

public class Income extends Transaction {

    private String source;      
    private boolean isReseller;
    private String reference;   

    public Income(int userId, double amount, String description, int paymentMethodId, String paymentMethodName, String source, boolean isReseller) {
        super(userId, amount, description, paymentMethodId, paymentMethodName);
        this.source = source;
        this.isReseller = isReseller;
        this.reference = "INC-" + transactionId;
    }

    @Override
    public double getAmount() {
        return Math.abs(amount);
    }

    @Override
    public String getSummary() {
        return String.format("[PEMASUKAN #%d] %s | Rp%.0f | %s | Via: %s | %s", transactionId, description, getAmount(), source, paymentMethodName, formatDate());
    }

    public String getSource() { 
        return source; 
    }
    
    public boolean isReseller() { 
        return isReseller; 
    }
    
    public String getReference() { 
        return reference; 
    }

    @Override
    public String toString() {
        return getSummary();
    }
}