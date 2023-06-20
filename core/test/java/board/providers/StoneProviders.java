package board.providers;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class StoneProviders {

    public static @NotNull Stream<Arguments> provideStoneColorsWithExceptionsForInvalidColors() {
        return Stream.of(
                Arguments.of(Color.WHITE, null),
                Arguments.of(Color.BLACK, null),
                Arguments.of(Color.GREEN, IllegalArgumentException.class)
        );
    }
}
