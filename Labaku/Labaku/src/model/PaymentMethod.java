package model;

public class PaymentMethod {
    private int methodId;
    private String name;
    private String type; 
    private boolean isActive;

    public PaymentMethod(int methodId, String name, String type) {
        this.methodId = methodId;
        this.name = name;
        this.type = type;
        this.isActive = true;
    }

    public int getMethodId(){ 
        return methodId; 
    }
    public String getName() { 
        return name; 
    }
    public String getType() { 
        return type; 
    }
    public boolean isActive() { 
        return isActive; 
    }

    @Override
    public String toString() {
        return "[" + methodId + "] " + name + " (" + type + ")";
    }
}
