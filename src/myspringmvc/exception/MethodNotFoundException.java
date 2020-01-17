package myspringmvc.exception;

public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(){}
    public MethodNotFoundException(String message) {
        super(message);
    }
}
