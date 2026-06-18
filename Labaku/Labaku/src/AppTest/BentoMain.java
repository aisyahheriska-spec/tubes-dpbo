package AppTest;

import collection.TransactionManager;
import exception.*;
import Interfacee.*;
import model.*;
import GUI.MainFrame;
import GUI.LoginDialog;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class BentoMain {

    public static void main(String[] args) {
        System.out.println(" INTERFACE");
        System.out.println("  ITransactionable, IExportable, ISaveable<T>");
        System.out.println("  diimplementasikan oleh Transaction, Report, TransactionManager\n");

        System.out.println(" INHERITANCE");
        System.out.println("  Transaction (abstract) ← Income, Expense");
        System.out.println("  Report (abstract)      ← DailyReport");
        System.out.println("  BentoException         ← InvalidTransactionException, AuthenticationException");
        System.out.println("  JFrame                 ← MainFrame");
        System.out.println("  JDialog                ← LoginDialog\n");

        System.out.println(" POLYMORPHISM — getAmount() & getSummary() di-override");

        Transaction t1 = new Income(1, 150000, "Penjualan bento siang", 1, "Tunai",
            "Penjualan Langsung", false);
        Transaction t2 = new Expense(1, 45000, "Beli bahan baku ayam", 1, "Tunai",
            "Bahan Baku", true);
        Transaction t3 = new Income(1, 80000, "Penjualan QRIS malam", 2, "QRIS",
            "Penjualan Langsung", false);
        Transaction t4 = new Expense(1, 20000, "Isi token listrik", 1, "Tunai",
            "Operasional", true);

        Transaction[] txArray = {t1, t2, t3, t4};
        for (Transaction t : txArray) {
            System.out.println("  getAmount() = " + t.getAmount() + " | " + t.getClass().getSimpleName());
        }
        System.out.println();

        System.out.println("COLLECTION & ARRAY");
        TransactionManager manager = new TransactionManager();

        for (Transaction t : txArray) {
            manager.addTransaction(t);
        }

        System.out.println("\n  [ArrayList] Hasil pencarian 'bento':");
        ArrayList<Transaction> hasil = manager.search("bento");
        for (Transaction t : hasil) {
            System.out.println("    → " + t.getSummary());
        }

        System.out.println("\n  [ArrayList] Filter metode 'QRIS':");
        ArrayList<Transaction> qris = manager.filterByPayment("QRIS");
        for (Transaction t : qris) {
            System.out.println("    → " + t.getSummary());
        }

        manager.printRekapMetode();

        manager.printWeeklySummary();

        System.out.println();

        System.out.println("EXCEPTION HANDLING");

        System.out.println(" amount = 0 → harus lempar InvalidTransactionException");
        try {
            Transaction invalid = new Income(1, 0, "Test invalid", 1, "Tunai",
                "Penjualan Langsung", false);
        } catch (InvalidTransactionException ex) {
            System.out.println("  ✓ Ditangkap: " + ex);
        }

        System.out.println(" deskripsi kosong → harus lempar InvalidTransactionException");
        try {
            Transaction invalid2 = new Income(1, 50000, " ", 1, "Tunai",
                "Penjualan Langsung", false);
        } catch (InvalidTransactionException ex) {
            System.out.println("  ✓ Ditangkap: " + ex);
        }

        System.out.println(" password salah → harus lempar AuthenticationException");
        try {
            model.User user = new model.User( 1, "Pemilik", "owner@bento.com", "bento123", "Owner");
            user.login("owner@bento.com", "salah");
        } catch (AuthenticationException ex) {
            System.out.println("  ✓ Ditangkap: " + ex);
        }
        System.out.println();

        System.out.println(" Polymorphism pada Report.generate()");
        DailyReport laporan = new DailyReport(LocalDate.now());
        for (Transaction t : txArray) laporan.addTransaction(t);
        laporan.generate(); 

        laporan.exportPDF("bento_" + LocalDate.now() + ".pdf");
        laporan.exportCSV("bento_" + LocalDate.now() + ".csv");

        manager.saveToFile("data/transaksi.dat");

        System.out.println(" GUI — Membuka aplikasi Swing BENTO...");
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);

            if (login.isLoginSuccess()) {
                MainFrame frame = new MainFrame(manager);
                frame.setVisible(true);
            } else {
                System.out.println("  Login dibatalkan.");
                System.exit(0);
            }
        });
    }
}
