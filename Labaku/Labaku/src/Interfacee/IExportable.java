package Interfacee;

public interface IExportable {
    void exportPDF(String filePath);
    void exportCSV(String filePath);
    String generateReport();
}
