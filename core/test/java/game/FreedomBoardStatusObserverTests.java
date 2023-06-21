package game;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.game.FreedomBoardStatusObserver;
import it.units.sdm.project.game.Move;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;

import static board.providers.BoardProviders.parseBoardFromString;
import static org.junit.jupiter.api.Assertions.*;

public class FreedomBoardStatusObserverTests {

    @ParameterizedTest
    @MethodSource("game.providers.FreedomPointsCounterProviders#printedBoardsProvider")
    void testCurrentWinner(String printedBoard, int boardSize, int blackScore, int whiteScore) {
        FreedomBoardStatusObserver freedomBoardStatusObserver = new FreedomBoardStatusObserver(parseBoardFromString(printedBoard, boardSize));
        if(blackScore > whiteScore) {
            assertEquals(Color.BLACK, freedomBoardStatusObserver.getCurrentWinner());
        } else if (blackScore < whiteScore) {
            assertEquals(Color.WHITE, freedomBoardStatusObserver.getCurrentWinner());
        } else {
            assertNull(freedomBoardStatusObserver.getCurrentWinner());
        }
    }

    @ParameterizedTest
    @MethodSource("game.providers.FreedomBoardStatusObserverProvider#provide4x4BoardWithLastMovedPlayed")
    void testGetCurrentGameStatus(@NotNull String printedBoard, @Nullable Move lastMove, @NotNull FreedomBoardStatusObserver.GameStatus gameStatusExpected) {
        FreedomBoardStatusObserver freedomBoardStatusObserver = new FreedomBoardStatusObserver(parseBoardFromString(printedBoard, 4));
        assertEquals(gameStatusExpected, freedomBoardStatusObserver.getCurrentGameStatus(lastMove));
    }
}
