package board.gui.providers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kotcrab.vis.ui.VisUI;
import it.units.sdm.project.board.Piece;
import it.units.sdm.project.board.gui.GuiStone;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class GuiStoneProviders {
    private static final String WHITE_CHECKER_IMAGE_NAME = "white_checker";
    private static final String BLACK_CHECKER_IMAGE_NAME = "black_checker";
    public static @NotNull Stream<Arguments> provideGuiStoneAndObjectAndWhetherEqual() {
        Piece stone = new GuiStone(Color.WHITE, VisUI.getSkin().get(WHITE_CHECKER_IMAGE_NAME, TextureRegion.class));
        return Stream.of(
                Arguments.of(stone, new GuiStone(Color.WHITE, VisUI.getSkin().getRegion(WHITE_CHECKER_IMAGE_NAME)), true),
                Arguments.of(stone, new GuiStone(Color.WHITE, VisUI.getSkin().getRegion(BLACK_CHECKER_IMAGE_NAME)), false),
                Arguments.of(stone, new GuiStone(Color.BLACK, VisUI.getSkin().getRegion(WHITE_CHECKER_IMAGE_NAME)), false),
                Arguments.of(stone, new GuiStone(Color.BLACK, VisUI.getSkin().getRegion(BLACK_CHECKER_IMAGE_NAME)), false),
                Arguments.of(stone, stone, true),
                Arguments.of(stone, null, false),
                Arguments.of(stone, new Object(), false)
        );
    }

    public static @NotNull Stream<Arguments> provideAllImageColorCombinations() {
        return Stream.of(
                Arguments.of(WHITE_CHECKER_IMAGE_NAME, Color.WHITE),
                Arguments.of(WHITE_CHECKER_IMAGE_NAME, Color.BLACK),
                Arguments.of(BLACK_CHECKER_IMAGE_NAME, Color.WHITE),
                Arguments.of(BLACK_CHECKER_IMAGE_NAME, Color.BLACK)
        );
    }

}
