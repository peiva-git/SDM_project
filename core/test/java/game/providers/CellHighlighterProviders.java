package game.providers;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static it.units.sdm.project.board.gui.GuiBoard.DARK_TILE;
import static it.units.sdm.project.board.gui.GuiBoard.LIGHT_TILE;
import static it.units.sdm.project.game.gui.FreedomCellHighlighter.HIGHLIGHT_DARK_TILE;
import static it.units.sdm.project.game.gui.FreedomCellHighlighter.HIGHLIGHT_LIGHT_TILE;

public class CellHighlighterProviders {
    public static @NotNull Stream<Arguments> provideALightAndADarkDefaultAndHighlightedTilePair() {
        return Stream.of(
                Arguments.of(LIGHT_TILE, HIGHLIGHT_LIGHT_TILE),
                Arguments.of(DARK_TILE, HIGHLIGHT_DARK_TILE)
        );
    }
}
