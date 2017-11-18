package competition.meanmax;

import competition.meanmax.Player.Board;
import competition.meanmax.Player.Position;
import competition.meanmax.Player.Reaper;
import competition.meanmax.Player.Wreck;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @Test
    public void should_board_let_add_a_unit() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0), 450);

        //When
        board.add(reaper);

        //Then
        assertThat(board.getUnits()).containsExactly(reaper);
    }

    @Test
    public void should_board_give_nearest_wreck_from_reaper_when_wreck_is_on_reaper() {
        Reaper reaper = new Reaper(new Position(0,0), 450);
        Wreck wreck = new Wreck(new Position(100, 100), 600);
        board.add(reaper);
        board.add(wreck);
        assertThat(board.nearestWreck(reaper)).isEqualTo(wreck);
    }

    @Test
    public void should_board_give_nearest_wreck_from_reaper_when_wreck_is_far_from_reaper() {
        Reaper reaper = new Reaper(new Position(0,0), 450);
        Wreck wreck = new Wreck(new Position(3000, 1000), 600);
        board.add(reaper);
        board.add(wreck);
        assertThat(board.nearestWreck(reaper)).isEqualTo(wreck);
    }

    @Test
    public void should_board_give_my_reaper() throws Exception {
        Reaper reaper = new Reaper(new Position(0,0), 450);
        board.add(reaper);
        assertThat(board.getReaper()).isEqualTo(reaper);
    }
}