package board.providers;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.Stone;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PieceProviders {

    public static @NotNull Stream<Arguments> provideStoneColorsWithExceptionsForInvalidColors() {
        return Stream.of(
                Arguments.of(Color.WHITE, null),
                Arguments.of(Color.BLACK, null),
                Arguments.of(Color.GREEN, IllegalArgumentException.class),
                Arguments.of(null, IllegalArgumentException.class)
        );
    }

    public static @NotNull Stream<Arguments> provideStoneAndObjectAndWhetherEqual() {
        Piece stone = new Stone(Color.WHITE);
        return Stream.of(
                Arguments.of(stone, new Stone(Color.WHITE), true),
                Arguments.of(stone, new Stone(Color.BLACK), false),
                Arguments.of(stone, null, false),
                Arguments.of(stone, stone, true),
                Arguments.of(stone, new Object(), false)
        );
    }
}
