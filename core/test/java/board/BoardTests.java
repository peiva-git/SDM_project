package board;

import it.units.sdm.project.board.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static utility.BoardUtils.fillBoardWithWhiteStones;

class BoardTests {

    private final int numberOfRows = 8;
    private final int numberOfColumns = 8;
    private final Board<Piece> board = new MapBoard<>(numberOfRows, numberOfColumns);

    @BeforeEach
    void clearBoard() {
        board.clearBoard();
    }

    @ParameterizedTest
    @MethodSource("board.providers.MapBoardProviders#provideStartingPositionAndAdjacent8x8BoardPositionsWithExceptionForInvalidStartingPosition")
    void testGetAdjacentPositions(Position position, Set<Position> adjacentPositions, Class<Exception> expectedException) {
        if (expectedException != null) {
            assertThrows(expectedException, () -> board.getAdjacentPositions(position));
        } else {
            assertEquals(adjacentPositions, board.getAdjacentPositions(position));
        }
    }

    @ParameterizedTest
    @MethodSource("board.providers.MapBoardProviders#providePositionsFor8x8BoardWithExceptionForInvalidPositions")
    void testAreAdjacentCellsOccupiedByFillingEmptyBoardAndThenRemovingOnePiece(int row, int column, Class<Exception> expectedException) {
        if (expectedException == null) {
            fillBoardWithWhiteStones(board);
            assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            board.clearCell(Position.fromCoordinates(row, column));
            assertTrue(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
            assertFalse(board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column + 1)));
        } else {
            assertThrows(expectedException, () -> board.areAdjacentCellsOccupied(Position.fromCoordinates(row, column)));
        }
    }

    @Test
    void testClearBoardAndIsCellOccupiedByFillingBoardAndThenRemovingAllThePieces() {
        fillBoardWithWhiteStones(board);
        boolean areAllPositionsOccupied = board.getPositions().stream().allMatch(board::isCellOccupied);
        assertTrue(areAllPositionsOccupied);
        board.clearBoard();
        boolean areAllPositionsFree = board.getPositions().stream().noneMatch(board::isCellOccupied);
        assertTrue(areAllPositionsFree);
    }

    @Test
    void testGetNumberOfFreeCellsByFillingBoardAndThenRemovingPieces() {
        fillBoardWithWhiteStones(board);
        assertEquals(0, board.getNumberOfFreeCells());
        board.clearCell(Position.fromCoordinates(0, 0));
        assertEquals(1, board.getNumberOfFreeCells());
        board.clearCell(Position.fromCoordinates(0, 1));
        assertEquals(2, board.getNumberOfFreeCells());
    }

}
