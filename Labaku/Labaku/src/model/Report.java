package model;

import Interfacee.IExportable;
import java.time.LocalDate;

public abstract class Report implements IExportable {

    protected int reportId;
    protected String reportType;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected double totalIncome;
    protected double totalExpense;

    private static int reportCounter = 1;

    public Report(String reportType, LocalDate startDate, LocalDate endDate) {
        this.reportId = reportCounter++;
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalIncome = 0;
        this.totalExpense = 0;
    }

    public abstract void generate();

    public double getProfitLoss() {
        return totalIncome + totalExpense; 
    }

    @Override
    public void exportPDF(String filePath) {
        System.out.println("  [EXPORT] Laporan diekspor ke PDF: " + filePath);
        System.out.println("           " + generateReport());
    }

    @Override
    public void exportCSV(String filePath) {
        System.out.println("  [EXPORT] Laporan diekspor ke CSV: " + filePath);
        System.out.println("           " + generateReport());
    }

    public int getReportId() { 
        return reportId; 
    }
    public String getReportType() { 
        return reportType; 
    }
    public double getTotalIncome() { 
        return totalIncome; 
    }
    public double getTotalExpense() { 
        return Math.abs(totalExpense); 
    }
}
