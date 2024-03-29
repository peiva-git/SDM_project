package board.gui.providers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.board.gui.GuiBoard;
import it.units.sdm.project.board.gui.GuiStone;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class GuiBoardProviders {
    public static @NotNull Stream<Arguments> provideTileCoordinatesAndExpectedPositionFor8x8Board() {
        return Stream.of(
                Arguments.of(0, 0, Position.fromCoordinates(7, 0)),
                Arguments.of(7, 7, Position.fromCoordinates(0, 7)),
                Arguments.of(4, 5, Position.fromCoordinates(3, 5))
        );
    }

    public static void fillBoardWithWhiteGuiStones(@NotNull GuiBoard<GuiStone> board, TextureRegion whiteCheckerImage) {
        for (Position position : board.getPositions()) {
            board.putPiece(new GuiStone(Color.WHITE, whiteCheckerImage), position);
        }
    }
}
