package game;

import it.units.sdm.project.game.FreedomBoardStatusObserver;
import it.units.sdm.project.game.Move;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static board.providers.BoardProviders.parseBoardFromString;
import static org.junit.jupiter.api.Assertions.*;

public class FreedomBoardStatusObserverTests {

    @ParameterizedTest
    @MethodSource("game.providers.FreedomBoardStatusObserverProvider#provide4x4BoardWithLastMovedPlayed")
    void testGetCurrentGameStatus(@NotNull String printedBoard, @Nullable Move lastMove, @NotNull FreedomBoardStatusObserver.GameStatus gameStatusExpected) {
        FreedomBoardStatusObserver freedomBoardStatusObserver = new FreedomBoardStatusObserver(parseBoardFromString(printedBoard, 4));
        assertEquals(gameStatusExpected, freedomBoardStatusObserver.getCurrentGameStatus(lastMove));
    }
}
