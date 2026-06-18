package model;

public class Expense extends Transaction {

    private String category;      
    private boolean isOperational;
    private String receiptNumber;

    public Expense(int userId, double amount, String description, int paymentMethodId, String paymentMethodName, String category, boolean isOperational) {
        super(userId, amount, description, paymentMethodId, paymentMethodName);
        this.category = category;
        this.isOperational = isOperational;
        this.receiptNumber = "EXP-" + transactionId;
    }

    @Override
    public double getAmount() {
        return -Math.abs(amount); 
    }

    @Override
    public String getSummary() {
        return String.format("[PENGELUARAN #%d] %s | Rp%.0f | Kategori: %s | Via: %s | %s", transactionId, description, Math.abs(amount), category, paymentMethodName, formatDate());
    }

    public String getCategory() { 
        return category; 
    }
    
    public boolean isOperational() { 
        return isOperational; 
    }
    public String getReceiptNumber() { 
        return receiptNumber; 
    }

    @Override
    public String toString() {
        return getSummary();
    }
}