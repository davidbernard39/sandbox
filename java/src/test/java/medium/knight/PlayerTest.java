package medium.knight;

import medium.knight.Player.Board;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    public void should_board_position_knight_on_correct_line() {
        // Given
        Board board = new Board(1, 2);

        // When
        board.knightAt(1,0);

        // Then
        assertThat(board.toString()).isEqualTo(".\nK");
    }

    @Test
    public void should_board_position_knight_on_correct_column() {
        // Given
        Board board = new Board(2, 1);

        // When
        board.knightAt(0,1);

        // Then
        assertThat(board.toString()).isEqualTo(".K");
    }

    @Test
    public void should_board_position_knight_on_correct_line_and_column() {
        // Given
        Board board = new Board(3, 3);

        // When
        board.knightAt(1,1);

        // Then
        assertThat(board.toString()).isEqualTo("...\n.K.\n...");
    }

    @Test
    public void should_move_up_to_upper_case_when_one_position_upper() {
        Board board = new Board(1, 2);
        board.knightAt(1,0);
        assertThat(board.move("U")).isEqualTo("0 0");
    }

    @Test
    public void should_move_up_to_case_up_when_two_position_upper() {
        Board board = new Board(1, 3);
        board.knightAt(2,0);
        assertThat(board.move("U")).isEqualTo("0 1");
    }

    @Test
    public void should_move_up_to_case_up_when_three_position_upper() {
        Board board = new Board(1, 4);
        board.knightAt(3,0);
        assertThat(board.move("U")).isEqualTo("0 1");
    }

    @Test
    public void should_move_up_to_case_up_when_four_position_upper() {
        Board board = new Board(1, 5);
        board.knightAt(4,0);
        assertThat(board.move("U")).isEqualTo("0 2");
    }

    @Test
    public void should_move_down_when_one_position_down() {
        Board board = new Board(1, 2);
        board.knightAt(0,0);
        assertThat(board.move("D")).isEqualTo("0 1");
    }

    @Test
    public void should_move_down_when_three_position_down() {
        Board board = new Board(1, 4);
        board.knightAt(0,0);
        assertThat(board.move("D")).isEqualTo("0 2");
    }

    @Test
    public void should_move_down_when_knight_in_the_middle() {
        Board board = new Board(1, 7);
        board.knightAt(3,0);
        assertThat(board.move("D")).isEqualTo("0 5");
    }

    @Test
    public void should_move_left_when_one_position_left() {
        Board board = new Board(2, 1);
        board.knightAt(0,1);
        assertThat(board.move("L")).isEqualTo("0 0");
    }

    @Test
    public void should_move_left_when_two_position_left() {
        Board board = new Board(3, 1);
        board.knightAt(0,2);
        assertThat(board.move("L")).isEqualTo("1 0");
    }

    @Test
    public void should_move_right_when_one_position_right() {
        Board board = new Board(2, 1);
        board.knightAt(0,0);
        assertThat(board.move("R")).isEqualTo("1 0");
    }

    @Test
    public void should_move_right_when_knight_in_the_middle() {
        Board board = new Board(7, 1);
        board.knightAt(0,3);
        assertThat(board.move("R")).isEqualTo("5 0");
    }

    @Test
    public void should_move_up_right_change_knight_position() {
        Board board = new Board(2, 2);
        board.knightAt(1,0);
        board.move("UR");
        assertThat(board.toString()).isEqualTo(".K\n..");
    }

    @Test
    public void should_move_down_then_up() {
        Board board = new Board(1, 6);
        board.knightAt(0,0);
        board.move("D");
        board.move("U");
        assertThat(board.move("D")).isEqualTo("0 2");
    }

    @Test
    public void should_move_up_then_down() {
        Board board = new Board(1, 8);
        board.knightAt(7,0);
        board.move("U");
        board.move("D");
        assertThat(board.move("U")).isEqualTo("0 4");
    }

    @Test
    public void should_move_right_then_left() {
        Board board = new Board(6, 1);
        board.knightAt(0,0);
        board.move("R");
        board.move("L");
        assertThat(board.move("R")).isEqualTo("2 0");
    }

    @Test
    public void should_move_left_then_right() {
        Board board = new Board(8, 1);
        board.knightAt(0,7);
        System.out.println(board);
        System.out.println();
        board.move("L");
        System.out.println(board);
        System.out.println();
        board.move("R");
        System.out.println(board);
        System.out.println();
        assertThat(board.move("L")).isEqualTo("4 0");
        System.out.println(board);
        System.out.println();
    }
}