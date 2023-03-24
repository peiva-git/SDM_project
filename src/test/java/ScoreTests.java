import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreTests {

    @Test
    void testIncrementNumberOfFreedomLines() {
        Score score = new Score(new Player(Stone.Color.WHITE, "Mario", "Rossi"));
        Assertions.assertEquals(score.getNumberOfFreedomLines(), 0);
        score.incrementNumberOfFreedomLines();
        Assertions.assertEquals(score.getNumberOfFreedomLines(), 1);
    }

}
