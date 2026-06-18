package exception;

public class AuthenticationException extends BentoException {
    public AuthenticationException(String message) {
        super(message, "BENTO-002");
    }
}
