package exceptions;

public class InvalidSizeOfBoard extends RuntimeException {
    public InvalidSizeOfBoard(String message) {
        super(message);
    }
}
