package board.gui.providers;

import it.units.sdm.project.board.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class GuiBoardProviders {
    public static @NotNull Stream<Arguments> provideTileCoordinatesAndExpectedPositionFor8x8Board() {
        return Stream.of(
                Arguments.of(0, 0, Position.fromCoordinates(7, 0)),
                Arguments.of(7, 7, Position.fromCoordinates(0, 7)),
                Arguments.of(4, 5, Position.fromCoordinates(3, 5))
        );
    }
}
