import com.beust.jcommander.ParameterException;
import it.units.sdm.project.BoardSizeValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class BoardSizeValidationTests {

    private final BoardSizeValidator validator = new BoardSizeValidator();

    @ParameterizedTest
    @ValueSource(strings = {"2", "5", "26"})
    void testBoardSizeValidationWithCorrectValues(String inputValue) {
        Assertions.assertDoesNotThrow(() -> validator.validate("White player username", inputValue));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "27", "text"})
    void testBoardSizeValidatorWithIncorrectValues(String inputValue) {
        Assertions.assertThrows(ParameterException.class, () -> validator.validate("White player username", inputValue));
    }
}
