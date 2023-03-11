import exceptions.InvalidSizeOfBoardException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BoardTests {

    @ParameterizedTest
    @MethodSource("provideWrongBoardInSize")
    void testBoardSizeValidity(int numberOfRows, int numberOfColumns, Class<Exception> expectedException) {
        if (expectedException != null) {
            Assertions.assertThrows(expectedException, () -> new Board(numberOfRows, numberOfColumns));
        } else {
            Assertions.assertDoesNotThrow(() -> new Board(numberOfRows, numberOfColumns));
        }
    }

    private static @NotNull Stream<Arguments> provideWrongBoardInSize() {
        return Stream.of(
                Arguments.of(2, 3, InvalidSizeOfBoardException.class),
                Arguments.of(4, 3, InvalidSizeOfBoardException.class),
                Arguments.of(11, 11, InvalidSizeOfBoardException.class),
                Arguments.of(8, 3, InvalidSizeOfBoardException.class),
                Arguments.of(45, 10, InvalidSizeOfBoardException.class),
                Arguments.of(12, 12, InvalidSizeOfBoardException.class),
                Arguments.of(10, 10, null),
                Arguments.of(8, 8, null)
        );
    }

}
