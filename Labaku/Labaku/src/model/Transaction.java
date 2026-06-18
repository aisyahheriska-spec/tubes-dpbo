package model;

import Interfacee.ITransactionable;
import exception.InvalidTransactionException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaction implements ITransactionable {
    protected int transactionId;
    protected int userId;
    protected LocalDateTime date;
    protected double amount;
    protected String description;
    protected int paymentMethodId;
    protected String paymentMethodName;

    private static int idCounter = 1;

    public Transaction(int userId, double amount, String description, int paymentMethodId, String paymentMethodName) {
        if (amount <= 0) {
            throw new InvalidTransactionException("amount", "Jumlah transaksi harus lebih dari 0, nilai yang dimasukkan: " + amount);
        }
        if (description == null || description.isBlank()) {
            throw new InvalidTransactionException("description", "Deskripsi transaksi tidak boleh kosong");
        }
        this.transactionId = idCounter++;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.date = LocalDateTime.now();
    }

    @Override
    public abstract double getAmount();

    @Override
    public abstract String getSummary();

    @Override
    public void save() {
        System.out.println(" Transaksi disimpan: " + getSummary());
    }

    @Override
    public void update() {
        System.out.println(" Transaksi diperbarui: #" + transactionId);
    }

    @Override
    public void delete() {
        System.out.println(" Transaksi dihapus: #" + transactionId);
    }

    public int getTransactionId() { 
        return transactionId; 
    }
    public int getUserId() { 
        return userId; 
    }
    public LocalDateTime getDate() { 
        return date; 
    }
    public String getDescription() { 
        return description; 
    }
    public int getPaymentMethodId() { 
        return paymentMethodId; 
    }
    public String getPaymentMethodName() { 
        return paymentMethodName; 
    }

    public String formatDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
