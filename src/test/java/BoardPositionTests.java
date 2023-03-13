import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardPositionTests {

    @Test
    void getPositionRow() {
        int row = 3;
        int column = 2;
        BoardPosition position = new BoardPosition(row, column);
        assertEquals(position.getRow(), row);
    }

    @Test
    void getPositionColumn() {
        int row = 4;
        int column = 8;
        BoardPosition position = new BoardPosition(row, column);
        assertEquals(position.getColumn(), column);
    }

    private static @NotNull Stream<Arguments> providePositionPairsForArePositionsAdjacent() {
        return Stream.of(
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(3, 4), true),
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(3, 2), true),
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(2, 3), true),
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(4, 3), true),
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(3, 1), false),
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(3, 5), false),
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(1, 3), false),
                Arguments.of(new BoardPosition(3, 3), new BoardPosition(5, 3), false)
        );
    }

    @ParameterizedTest
    @MethodSource("providePositionPairsForArePositionsAdjacent")
    void arePositionsAdjacent(@NotNull BoardPosition firstPosition, @NotNull BoardPosition secondPosition, boolean expectedAdjacent) {
        assertEquals(firstPosition.isPositionAdjacentTo(secondPosition), expectedAdjacent);
    }
}
