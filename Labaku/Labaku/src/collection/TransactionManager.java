package collection;

import model.*;
import exception.*;
import Interfacee.ISaveable;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionManager implements  ISaveable<Transaction> {
    private ArrayList<Transaction> allTransactions;
    private ArrayList<PaymentMethod> paymentMethods;
    private HashMap<String, Double> rekapPerMetode;

    private double[] incomePerDay;
    private double[] expensePerDay;
    private static final int HISTORY_DAYS = 7;

    public TransactionManager() {
        this.allTransactions = new ArrayList<>();
        this.paymentMethods  = new ArrayList<>();
        this.rekapPerMetode  = new HashMap<>();
        this.incomePerDay    = new double[HISTORY_DAYS];
        this.expensePerDay   = new double[HISTORY_DAYS];

        paymentMethods.add(new PaymentMethod(1, "Tunai",    "Cash"));
        paymentMethods.add(new PaymentMethod(2, "QRIS",     "QRIS"));
        paymentMethods.add(new PaymentMethod(3, "Transfer", "Transfer"));

        rekapPerMetode.put("Cash",     0.0);
        rekapPerMetode.put("QRIS",     0.0);
        rekapPerMetode.put("Transfer", 0.0);
    }

    public void addTransaction(Transaction t) {
        try {
            if (!validateData()) {
                throw new BentoException("TransactionManager belum siap");
            }
            allTransactions.add(t);

            String metode = t.getPaymentMethodName();
            double prev = rekapPerMetode.getOrDefault(metode, 0.0);
            rekapPerMetode.put(metode, prev + Math.abs(t.getAmount()));

            if (t instanceof Income) {
                incomePerDay[0] += t.getAmount();
            } else if (t instanceof Expense) {
                expensePerDay[0] += Math.abs(t.getAmount());
            }

            t.save(); 

        } catch (InvalidTransactionException e) {
            System.out.println("  [ERROR] Transaksi tidak valid: " + e);
        }
    }

    public ArrayList<Transaction> search(String keyword) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : allTransactions) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Transaction> filterByPayment(String metodeName) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : allTransactions) {
            if (t.getPaymentMethodName().equalsIgnoreCase(metodeName)) {
                result.add(t);
            }
        }
        return result;
    }

    public void printWeeklySummary() {
        System.out.println("\n--- REKAP 7 HARI (ARRAY) ---");
        System.out.printf("  %-6s %-14s %-14s%n", "Hari", "Pemasukan", "Pengeluaran");
        for (int i = 0; i < HISTORY_DAYS; i++) {
            String label = (i == 0) ? "Hari ini" : "H-" + i;
            System.out.printf("  %-6s Rp%-12.0f Rp%-12.0f%n",
                label, incomePerDay[i], expensePerDay[i]);
        }
    }

    public void printRekapMetode() {
        System.out.println("\n--- REKAP PER METODE PEMBAYARAN (HASHMAP) ---");
        for (HashMap.Entry<String, Double> entry : rekapPerMetode.entrySet()) {
            System.out.printf("  %-10s : Rp%.0f%n", entry.getKey(), entry.getValue());
        }
    }

    public double getTotalIncome() {
        double total = 0;
        for (Transaction t : allTransactions) {
            if (t instanceof Income) total += t.getAmount();
        }
        return total;
    }

    public double getTotalExpense() {
        double total = 0;
        for (Transaction t : allTransactions) {
            if (t instanceof Expense) total += Math.abs(t.getAmount());
        }
        return total;
    }

    public ArrayList<Transaction> getAllTransactions() { 
        return allTransactions; 
    }
    
    public ArrayList<PaymentMethod> getPaymentMethods() { 
        return paymentMethods; 
    }
    
    public HashMap<String, Double> getRekapPerMetode() { 
        return rekapPerMetode; 
    }

    @Override
    public void saveToFile(String path) {
        System.out.println("  [FILE] Data disimpan ke: " + path);
    }

    @Override
    public ArrayList<Transaction> loadFromFile(String path) {
        System.out.println("  [FILE] Data dimuat dari: " + path);
        return new ArrayList<>();
    }

    @Override
    public boolean validateData() {
        return allTransactions != null && paymentMethods != null;
    }
}