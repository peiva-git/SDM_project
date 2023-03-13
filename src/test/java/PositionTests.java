import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTests {

    @Test
    void getPositionRow() {
        int row = 3;
        int column = 2;
        Position position = new Position(row, column);
        assertEquals(position.getRow(), row);
    }

    @Test
    void getPositionColumn() {
        int row = 4;
        int column = 8;
        Position position = new Position(row, column);
        assertEquals(position.getColumn(), column);
    }

    private static @NotNull Stream<Arguments> providePositionPairsForArePositionsAdjacent() {
        return Stream.of(
                Arguments.of(new Position(3, 3), new Position(3, 4), true),
                Arguments.of(new Position(3, 3), new Position(3, 2), true),
                Arguments.of(new Position(3, 3), new Position(2, 3), true),
                Arguments.of(new Position(3, 3), new Position(4, 3), true),
                Arguments.of(new Position(3, 3), new Position(3, 1), false),
                Arguments.of(new Position(3, 3), new Position(3, 5), false),
                Arguments.of(new Position(3, 3), new Position(1, 3), false),
                Arguments.of(new Position(3, 3), new Position(5, 3), false)
        );
    }

    @ParameterizedTest
    @MethodSource("providePositionPairsForArePositionsAdjacent")
    void arePositionsAdjacent(@NotNull Position firstPosition,@NotNull Position secondPosition, boolean expectedAdjacent) {
        assertEquals(firstPosition.isPositionAdjacentTo(secondPosition), expectedAdjacent);
    }
}
