package medium.knight;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    public void should_board_position_knight_on_correct_line() {
        // Given
        Player.Board board = new Player.Board(1, 2);

        // When
        board.knightAt(1,0);

        // Then
        assertThat(board.toString()).isEqualTo(".\nK");
    }

    @Test
    public void should_board_position_knight_on_correct_column() {
        // Given
        Player.Board board = new Player.Board(2, 1);

        // When
        board.knightAt(0,1);

        // Then
        assertThat(board.toString()).isEqualTo(".K");
    }

    @Test
    public void should_board_position_knight_on_correct_line_and_column() {
        // Given
        Player.Board board = new Player.Board(3, 3);

        // When
        board.knightAt(1,1);

        // Then
        assertThat(board.toString()).isEqualTo("...\n.K.\n...");
    }

}