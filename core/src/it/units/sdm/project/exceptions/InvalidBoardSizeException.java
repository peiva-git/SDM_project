package it.units.sdm.project.exceptions;

/**
 * Thrown to indicate that an invalid {@link it.units.sdm.project.board.Board}
 * size has been passed
 */
public class InvalidBoardSizeException extends RuntimeException {
    /**
     * Constructs an {@link InvalidBoardSizeException} with the specified detail message.
     * @param message The detail message
     */
    public InvalidBoardSizeException(String message) {
        super(message);
    }
}
