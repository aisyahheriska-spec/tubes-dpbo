package exception;

import java.time.LocalDateTime;

public class BentoException extends RuntimeException {
    private String errorCode;
    private LocalDateTime timestamp;

    public BentoException(String message) {
        super(message);
        this.errorCode = "BENTO-000";
        this.timestamp = LocalDateTime.now();
    }

    public BentoException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

    public String getErrorCode() { 
        return errorCode; 
    }
    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }

    @Override
    public String toString() {
        return "[" + errorCode + " | " + timestamp + "] " + getMessage();
    }
}
