package exception;

public class InvalidTransactionException extends BentoException {
    private String fieldName;

    public InvalidTransactionException(String fieldName, String message) {
        super(message, "BENTO-001");
        this.fieldName = fieldName;
    }

    public String getFieldName() { 
        return fieldName; 
    }

    @Override
    public String toString() {
        return super.toString() + " | Field: " + fieldName;
    }
}
