package it.units.sdm.project.exceptions;

/**
 * Thrown to indicate that an invalid {@link it.units.sdm.project.board.Board} {@link it.units.sdm.project.board.Position} has been
 * passed.
 */
public class InvalidPositionException extends RuntimeException {
    /**
     * Constructs an {@link InvalidPositionException} with the specified detail message.
     * @param message The detail message
     */
    public InvalidPositionException(String message) {
        super(message);
    }
}
