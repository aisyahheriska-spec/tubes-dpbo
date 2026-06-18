package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DailyReport extends Report {

    private ArrayList<Transaction> transactions;

    public DailyReport(LocalDate date) {
        super("Harian", date, date);
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        if (t instanceof Income) {
            totalIncome += t.getAmount();
        } else if (t instanceof Expense) {
            totalExpense += t.getAmount(); 
        }
    }

    @Override
    public void generate() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("       LAPORAN HARIAN BENTO - " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("=".repeat(55));

        System.out.println("\n--- RINCIAN TRANSAKSI ---");
        for (Transaction t : transactions) {
            System.out.println("  " + t.getSummary());
        }

        System.out.println("\n--- REKAP ---");
        System.out.printf("  Total Pemasukan  : Rp%.0f%n", totalIncome);
        System.out.printf("  Total Pengeluaran: Rp%.0f%n", Math.abs(totalExpense));
        System.out.printf("  Laba / Rugi      : Rp%.0f%n", getProfitLoss());
        System.out.println("  Jumlah Transaksi : " + transactions.size());
        System.out.println("=".repeat(55));
    }

    @Override
    public String generateReport() {
        return String.format("Laporan Harian %s | Pemasukan: Rp%.0f | Pengeluaran: Rp%.0f | Laba/Rugi: Rp%.0f", startDate, totalIncome, Math.abs(totalExpense), getProfitLoss());
    }

    public ArrayList<Transaction> filterByType(String type) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (type.equalsIgnoreCase("income") && t instanceof Income) {
                result.add(t);
            } else if (type.equalsIgnoreCase("expense") && t instanceof Expense) {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Transaction> getTransactions() { 
        return transactions; 
    }
}
