package game.providers;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PlayerProviders {

    public static @NotNull Stream<Arguments> providePlayerColorsWithExceptionsForInvalidColors() {
        return Stream.of(
                Arguments.of(Color.WHITE, null),
                Arguments.of(Color.BLACK, null),
                Arguments.of(Color.GREEN, IllegalArgumentException.class),
                Arguments.of(null, IllegalArgumentException.class)
        );
    }

    public static @NotNull Stream<Arguments> providePlayerAndObjectAndWhetherEqual() {
        Player player = new Player(Color.WHITE, "username");
        return Stream.of(
                Arguments.of(player, new Player(Color.WHITE, "username"), true),
                Arguments.of(player, new Player(Color.WHITE, "white"), true),
                Arguments.of(player, new Player(Color.BLACK, "username"), false),
                Arguments.of(player, new Player(Color.BLACK, "black"), false),
                Arguments.of(player, null, false),
                Arguments.of(player, player, true),
                Arguments.of(player, new Object(), false)
        );
    }

}
