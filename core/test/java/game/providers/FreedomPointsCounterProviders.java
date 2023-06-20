package game.providers;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class FreedomPointsCounterProviders {
    public static @NotNull Stream<Arguments> printedBoardsProvider() {
        return Stream.of(
                Arguments.of(" 4 W  B  W  B\n"
                                + " 3 W  B  W  B\n"
                                + " 2 W  B  W  B\n"
                                + " 1 W  B  W  B\n"
                                + "   A  B  C  D",
                        4, 2, 2
                ),
                Arguments.of(" 4 W  W  W  W\n"
                                + " 3 B  B  B  B\n"
                                + " 2 W  W  W  W\n"
                                + " 1 B  B  B  B\n"
                                + "   A  B  C  D",
                        4, 2, 2
                ),
                Arguments.of(" 4 W  -  -  B\n"
                                + " 3 -  W  B  -\n"
                                + " 2 -  B  W  -\n"
                                + " 1 B  -  -  W\n"
                                + "   A  B  C  D",
                        4, 1, 1
                ),
                Arguments.of(
                        " 8 W  W  W  W  W  W  W  W\n"
                                + " 7 W  B  W  W  W  W  W  W\n"
                                + " 6 W  B  B  W  B  W  B  B\n"
                                + " 5 W  B  B  W  B  W  B  W\n"
                                + " 4 W  W  B  W  B  W  B  W\n"
                                + " 3 B  W  W  W  B  W  B  W\n"
                                + " 2 B  W  W  W  W  W  W  W\n"
                                + " 1 B  W  W  B  W  W  W  W\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 2, 2
                ),
                Arguments.of(
                        " 8 W  W  W  W  W  W  W  W\n"
                                + " 7 W  B  W  W  W  W  W  W\n"
                                + " 6 W  B  B  W  B  W  B  B\n"
                                + " 5 W  B  B  W  B  W  B  W\n"
                                + " 4 W  W  B  W  B  W  B  W\n"
                                + " 3 B  W  W  B  B  W  B  W\n"
                                + " 2 B  W  W  W  B  W  W  W\n"
                                + " 1 B  W  W  W  W  W  W  W\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 2, 2
                ),
                Arguments.of(
                        " 8 W  W  W  W  W  W  W  W\n"
                                + " 7 W  B  W  W  W  W  W  W\n"
                                + " 6 W  B  B  W  B  W  B  B\n"
                                + " 5 W  B  B  W  B  W  B  W\n"
                                + " 4 W  W  B  B  B  W  B  W\n"
                                + " 3 B  -  W  B  B  W  B  W\n"
                                + " 2 B  W  W  W  B  W  W  W\n"
                                + " 1 B  W  W  W  W  W  W  W\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 3, 1
                ),
                Arguments.of(
                        " 8 -  -  -  -  -  -  -  -\n"
                                + " 7 -  -  -  -  -  -  -  -\n"
                                + " 6 -  -  -  -  W  -  -  -\n"
                                + " 5 -  -  -  W  B  B  -  -\n"
                                + " 4 -  -  W  B  W  B  -  -\n"
                                + " 3 -  -  -  -  -  W  B  -\n"
                                + " 2 -  -  -  -  -  -  W  B\n"
                                + " 1 -  -  -  -  -  -  -  -\n"
                                + "   A  B  C  D  E  F  G  H",
                        8, 1, 1)
        );
    }
}
