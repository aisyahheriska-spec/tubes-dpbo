package Interfacee;

import java.util.ArrayList;

public interface ISaveable<T> {
    void saveToFile(String path);
    ArrayList<T> loadFromFile(String path);
    boolean validateData();
}
