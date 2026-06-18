package Interfacee;

public interface ITransactionable {
    void save();
    void update();
    void delete();
    double getAmount();
    String getSummary();
}

