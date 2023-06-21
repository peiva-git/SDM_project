import com.beust.jcommander.ParameterException;
import it.units.sdm.project.BoardSizeValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardSizeValidationTests {
    private final BoardSizeValidator validator = new BoardSizeValidator();

    @ParameterizedTest
    @ValueSource(strings = {"2", "5", "26"})
    void testBoardSizeValidationWithCorrectValues(String inputValue) {
        assertDoesNotThrow(() -> validator.validate("Board size", inputValue));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "27", "text"})
    void testBoardSizeValidatorWithIncorrectValues(String inputValue) {
        assertThrows(ParameterException.class, () -> validator.validate("Board size", inputValue));
    }
}
