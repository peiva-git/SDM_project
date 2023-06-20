package game.providers;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ColorProviders {

    public static @NotNull Stream<Arguments> provideColors() {
        return Stream.of(
                Arguments.of(
                        Color.WHITE,
                        null
                ),
                Arguments.of(
                        Color.BLACK,
                        null
                ),
                Arguments.of(
                        Color.GREEN,
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        Color.BLUE,
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        Color.YELLOW,
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        Color.RED,
                        IllegalArgumentException.class
                )
        );
    }

}
