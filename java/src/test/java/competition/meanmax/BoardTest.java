package competition.meanmax;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BoardTest {

    private Player.Board board;

    @Before
    public void setUp() throws Exception {
        board = new Player.Board();
    }

    @Test
    public void should_board_let_add_a_unit() throws Exception {
        //Given
        Player.Reaper reaper = new Player.Reaper(new Player.Position(0,0));

        //When
        board.add(reaper);

        //Then
        assertThat(board.getUnits()).containsExactly(reaper);
    }
}