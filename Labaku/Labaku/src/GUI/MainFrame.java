package GUI;

import collection.TransactionManager;
import exception.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private TransactionManager manager;
    private DailyReport dailyReport;

    private JTabbedPane tabbedPane;

    private JTextField txtIncomeDesc, txtIncomeAmount;
    private JComboBox<String> cmbIncomeSource, cmbIncomePayment;
    private JCheckBox chkReseller;
    private JTable tblIncome;
    private DefaultTableModel modelIncome;

    private JTextField txtExpenseDesc, txtExpenseAmount;
    private JComboBox<String> cmbExpenseCategory, cmbExpensePayment;
    private JTable tblExpense;
    private DefaultTableModel modelExpense;

    private JLabel lblTotalIncome, lblTotalExpense, lblProfitLoss;
    private JTextArea txtLaporan;

    public MainFrame(TransactionManager manager) {
        this.manager = manager;
        this.dailyReport = new DailyReport(LocalDate.now());
        initUI();
    }

    private void initUI() {
        setTitle("BENTO - Sistem Pencatatan Keuangan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(88, 56, 150));
        JLabel lblTitle = new JLabel("  BENTO — Bekal Murah Serba 10.000");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📊 Dashboard",    buildDashboardPanel());
        tabbedPane.addTab("💰 Pemasukan",    buildIncomePanel());
        tabbedPane.addTab("💸 Pengeluaran",  buildExpensePanel());
        tabbedPane.addTab("📄 Laporan",      buildReportPanel());

        add(header, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel buildDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 12, 0));

        lblTotalIncome  = new JLabel("Rp 0", SwingConstants.CENTER);
        lblTotalExpense = new JLabel("Rp 0", SwingConstants.CENTER);
        lblProfitLoss   = new JLabel("Rp 0", SwingConstants.CENTER);

        cardPanel.add(makeCard("Total Pemasukan",  lblTotalIncome,  new Color(39, 174, 96)));
        cardPanel.add(makeCard("Total Pengeluaran", lblTotalExpense, new Color(192, 57, 43)));
        cardPanel.add(makeCard("Laba / Rugi",       lblProfitLoss,   new Color(41, 128, 185)));

        JButton btnRefresh = new JButton("Refresh Dashboard");
        btnRefresh.addActionListener(e -> refreshDashboard());

        panel.add(cardPanel, BorderLayout.NORTH);
        panel.add(btnRefresh, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel makeCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));
        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        valueLabel.setForeground(Color.WHITE);
        card.add(lbl, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildIncomePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        txtIncomeDesc    = new JTextField();
        txtIncomeAmount  = new JTextField();
        cmbIncomeSource  = new JComboBox<>(new String[]{"Penjualan Langsung","Reseller","Modal"});
        cmbIncomePayment = new JComboBox<>(new String[]{"Tunai","QRIS","Transfer"});
        chkReseller      = new JCheckBox("Tandai sebagai Reseller");

        form.add(new JLabel("Deskripsi:")); form.add(txtIncomeDesc);
        form.add(new JLabel("Jumlah (Rp):")); form.add(txtIncomeAmount);
        form.add(new JLabel("Sumber:")); form.add(cmbIncomeSource);
        form.add(new JLabel("Metode Bayar:")); form.add(cmbIncomePayment);
        form.add(new JLabel("")); form.add(chkReseller);

        JButton btnSimpan = new JButton("Simpan Pemasukan");
        btnSimpan.setBackground(new Color(39, 174, 96));
        btnSimpan.setForeground(Color.WHITE);

        btnSimpan.addActionListener(e -> saveIncome());
        form.add(btnSimpan); form.add(new JLabel(""));

        modelIncome = new DefaultTableModel( new String[]{"#","Deskripsi","Jumlah","Sumber","Metode","Waktu"}, 0);
        tblIncome = new JTable(modelIncome);
        JScrollPane scroll = new JScrollPane(tblIncome);

        panel.add(form, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildExpensePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        txtExpenseDesc     = new JTextField();
        txtExpenseAmount   = new JTextField();
        cmbExpenseCategory = new JComboBox<>(new String[]{"Bahan Baku","Operasional","Gaji","Lain-lain"});
        cmbExpensePayment  = new JComboBox<>(new String[]{"Tunai","QRIS","Transfer"});

        form.add(new JLabel("Deskripsi:")); form.add(txtExpenseDesc);
        form.add(new JLabel("Jumlah (Rp):")); form.add(txtExpenseAmount);
        form.add(new JLabel("Kategori:")); form.add(cmbExpenseCategory);
        form.add(new JLabel("Metode Bayar:")); form.add(cmbExpensePayment);

        JButton btnSimpan = new JButton("Simpan Pengeluaran");
        btnSimpan.setBackground(new Color(192, 57, 43));
        btnSimpan.setForeground(Color.WHITE);

        btnSimpan.addActionListener(e -> saveExpense());
        form.add(btnSimpan); form.add(new JLabel(""));

        modelExpense = new DefaultTableModel( new String[]{"#","Deskripsi","Jumlah","Kategori","Metode","Waktu"}, 0);
        tblExpense = new JTable(modelExpense);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblExpense), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        txtLaporan = new JTextArea();
        txtLaporan.setEditable(false);
        txtLaporan.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnGenerate = new JButton("Generate Laporan Harian");
        JButton btnExportPDF = new JButton("Export PDF");
        JButton btnExportCSV = new JButton("Export CSV");

        btnGenerate.addActionListener(e -> {
            dailyReport.generate();
            txtLaporan.setText(
                "=== LAPORAN HARIAN BENTO ===\n" +
                "Total Pemasukan  : Rp" + String.format("%.0f", dailyReport.getTotalIncome()) + "\n" +
                "Total Pengeluaran: Rp" + String.format("%.0f", dailyReport.getTotalExpense()) + "\n" +
                "Laba / Rugi      : Rp" + String.format("%.0f", dailyReport.getProfitLoss()) + "\n\n" +
                "Rincian:\n"
            );
            for (Transaction t : dailyReport.getTransactions()) {
                txtLaporan.append("  " + t.getSummary() + "\n");
            }
        });

        btnExportPDF.addActionListener(e -> {
            dailyReport.exportPDF("laporan_bento_" + LocalDate.now() + ".pdf");
            JOptionPane.showMessageDialog(this, "Laporan berhasil diekspor ke PDF!");
        });

        btnExportCSV.addActionListener(e -> {
            dailyReport.exportCSV("laporan_bento_" + LocalDate.now() + ".csv");
            JOptionPane.showMessageDialog(this, "Laporan berhasil diekspor ke CSV!");
        });

        btnPanel.add(btnGenerate);
        btnPanel.add(btnExportPDF);
        btnPanel.add(btnExportCSV);

        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtLaporan), BorderLayout.CENTER);
        return panel;
    }

    private void saveIncome() {
        try {
            String desc   = txtIncomeDesc.getText();
            double amount = Double.parseDouble(txtIncomeAmount.getText());
            String source = (String) cmbIncomeSource.getSelectedItem();
            String metode = (String) cmbIncomePayment.getSelectedItem();
            int methodId  = cmbIncomePayment.getSelectedIndex() + 1;
            boolean reseller = chkReseller.isSelected();

            Income income = new Income(1, amount, desc, methodId, metode, source, reseller);
            manager.addTransaction(income);
            dailyReport.addTransaction(income);

            modelIncome.addRow(new Object[]{
                income.getTransactionId(), desc,
                "Rp" + String.format("%.0f", amount),
                source, metode, income.formatDate()
            });

            txtIncomeDesc.setText("");
            txtIncomeAmount.setText("");
            refreshDashboard();
            JOptionPane.showMessageDialog(this, "Pemasukan berhasil dicatat!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);

        } catch (InvalidTransactionException ex) {
            JOptionPane.showMessageDialog(this,"Data tidak valid: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveExpense() {
        try {
            String desc     = txtExpenseDesc.getText();
            double amount   = Double.parseDouble(txtExpenseAmount.getText());
            String category = (String) cmbExpenseCategory.getSelectedItem();
            String metode   = (String) cmbExpensePayment.getSelectedItem();
            int methodId    = cmbExpensePayment.getSelectedIndex() + 1;

            Expense expense = new Expense(1, amount, desc, methodId, metode, category, true);
            manager.addTransaction(expense);
            dailyReport.addTransaction(expense);

            modelExpense.addRow(new Object[]{ expense.getTransactionId(), desc, "Rp" + String.format("%.0f", amount), category, metode, expense.formatDate()});

            txtExpenseDesc.setText("");
            txtExpenseAmount.setText("");
            refreshDashboard();
            JOptionPane.showMessageDialog(this, "Pengeluaran berhasil dicatat!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);

        } catch (InvalidTransactionException ex) {
            JOptionPane.showMessageDialog(this, "Data tidak valid: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshDashboard() {
        double income  = manager.getTotalIncome();
        double expense = manager.getTotalExpense();
        double profit  = income - expense;

        lblTotalIncome.setText("Rp " + String.format("%,.0f", income));
        lblTotalExpense.setText("Rp " + String.format("%,.0f", expense));
        lblProfitLoss.setText("Rp " + String.format("%,.0f", profit));
        lblProfitLoss.setForeground(profit >= 0 ? Color.WHITE : new Color(255, 200, 200));
    }
}