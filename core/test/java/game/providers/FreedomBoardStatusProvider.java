package game.providers;

import com.badlogic.gdx.graphics.Color;
import it.units.sdm.project.board.Position;
import it.units.sdm.project.game.FreedomBoardStatusObserver;
import it.units.sdm.project.game.Move;
import it.units.sdm.project.game.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class FreedomBoardStatusProvider {

        public static @NotNull Stream<Arguments> provide4x4BoardWithLastMOvedPlayed() {
            Player whitePlayer = new Player(Color.WHITE, "white");
            Player blackPlayer = new Player(Color.BLACK, "black");
            return Stream.of(
                    Arguments.of(
                            " 4 W  B  W  B\n"
                                    + " 3 W  B  W  B\n"
                                    + " 2 W  B  W  B\n"
                                    + " 1 W  B  W  B\n"
                                    + "   A  B  C  D",
                            new Move(whitePlayer, Position.fromCoordinates(0,0)),
                            FreedomBoardStatusObserver.GameStatus.GAME_OVER
                    ),
                    Arguments.of(
                            " 4 W  B  W  B\n"
                                    + " 3 W  B  W  B\n"
                                    + " 2 W  B  -  B\n"
                                    + " 1 W  B  W  B\n"
                                    + "   A  B  C  D",
                            new Move(blackPlayer, Position.fromCoordinates(3,1)),
                            FreedomBoardStatusObserver.GameStatus.LAST_MOVE
                    ),
                    Arguments.of(
                            " 4 W  B  W  B\n"
                                    + " 3 W  B  W  B\n"
                                    + " 2 W  B  -  B\n"
                                    + " 1 W  -  W  B\n"
                                    + "   A  B  C  D",
                            new Move(whitePlayer, Position.fromCoordinates(3,0)),
                            FreedomBoardStatusObserver.GameStatus.FREEDOM
                    ),
                    Arguments.of(
                            " 4 -  B  W  B\n"
                                    + " 3 W  B  W  B\n"
                                    + " 2 W  B  -  B\n"
                                    + " 1 W  -  W  B\n"
                                    + "   A  B  C  D",
                            new Move(blackPlayer, Position.fromCoordinates(3,1)),
                            FreedomBoardStatusObserver.GameStatus.NO_FREEDOM
                    ),
                    Arguments.of(
                            " 4 -  -  -  -\n"
                                    + " 3 -  -  -  -\n"
                                    + " 2 -  -  -  -\n"
                                    + " 1 -  -  -  -\n"
                                    + "   A  B  C  D",
                            null,
                            FreedomBoardStatusObserver.GameStatus.FREEDOM
                    )

            );
        }

}
