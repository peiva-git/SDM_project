package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTests {
    private final Player whitePlayer = new Player(Color.WHITE, "white");
    private final Player blackPlayer = new Player(Color.BLACK, "black");

    @Test
    void testColorGetter() {
        assertEquals(Color.WHITE, whitePlayer.getColor());
        assertEquals(Color.BLACK, blackPlayer.getColor());
    }

    @Test
    void testUsernameGetter() {
        assertEquals("white", whitePlayer.getUsername());
        assertEquals("black", blackPlayer.getUsername());
    }

    @Test
    void testToString() {
        assertEquals("white", whitePlayer.toString());
        assertEquals("black", blackPlayer.toString());
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(Color.WHITE), whitePlayer.hashCode());
        assertEquals(Objects.hash(Color.BLACK), blackPlayer.hashCode());
    }

    @ParameterizedTest
    @MethodSource("game.providers.PlayerProviders#providePlayerAndObjectAndWhetherEqual")
    void testEquals(@NotNull Player firstPlayer, @Nullable Object secondPlayer, boolean areEqual) {
        if (areEqual) {
            assertEquals(firstPlayer, secondPlayer);
        } else {
            assertNotEquals(firstPlayer, secondPlayer);
        }
    }

    @ParameterizedTest
    @MethodSource("game.providers.PlayerProviders#providePlayerColorsWithExceptionsForInvalidColors")
    void testColorValidity(@NotNull Color color, Class<Exception> expectedException) {
        if (expectedException == null) {
            assertDoesNotThrow(() -> new Player(color, "username"));
        } else {
            assertThrows(expectedException, () -> new Player(color, "username"));
        }
    }

}
