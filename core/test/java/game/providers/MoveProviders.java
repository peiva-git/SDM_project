package game.providers;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class MoveProviders {

    public static @NotNull Stream<Arguments> provideMoveWithCandidateObjectAndWhetherEqual() {
        Player whitePlayer = new Player(Color.WHITE, "white");
        Player blackPlayer = new Player(Color.BLACK, "black");
        Position firstPosition = Position.fromCoordinates(0, 0);
        Position secondPosition = Position.fromCoordinates(1, 1);
        Move move = new Move(whitePlayer, firstPosition);
        return Stream.of(
                Arguments.of(move, new Move(blackPlayer, firstPosition), false),
                Arguments.of(move, new Move(whitePlayer, firstPosition), true),
                Arguments.of(move, new Move(blackPlayer, secondPosition), false),
                Arguments.of(move, new Move(whitePlayer, secondPosition), false),
                Arguments.of(move, move, true),
                Arguments.of(move, null, false),
                Arguments.of(move, new Object(), false)
        );
    }

}
