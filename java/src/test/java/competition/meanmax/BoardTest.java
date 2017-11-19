package competition.meanmax;

import competition.meanmax.Player.*;
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
    public void should_board_give_nearest_tanker_from_destroyer_when_tanker_is_far_from_destroyer() {
        Destroyer destroyer = new Destroyer(new Position(0,0), 450);
        Tanker tanker = new Tanker(new Position(3000, 1000), 600);
        board.add(destroyer);
        board.add(tanker);
        assertThat(board.nearestTanker(destroyer)).isEqualTo(tanker);
    }

    @Test
    public void should_board_choose_nearest_tanker_from_destroyer_among_several_choices() {
        Destroyer destroyer = new Destroyer(new Position(0,0), 450);
        Tanker tanker1 = new Tanker(new Position(3000, 1000), 600);
        Tanker tanker2 = new Tanker(new Position(400, 200), 600);
        Tanker tanker3 = new Tanker(new Position(3400, 0), 600);
        board.add(destroyer);
        board.add(tanker1);
        board.add(tanker2);
        board.add(tanker3);
        assertThat(board.nearestTanker(destroyer)).isEqualTo(tanker2);
    }

    @Test
    public void should_board_give_no_nearest_tanker_from_destroyer_when_no_tanker_present() throws Exception {
        Destroyer destroyer = new Destroyer(new Position(0,0),450);
        board.add(destroyer);
        assertThat(board.nearestTanker(destroyer)).isNull();
    }

    @Test
    public void should_board_give_my_reaper() throws Exception {
        Reaper reaper = new Reaper(new Position(0,0), 450);
        board.add(reaper);
        assertThat(board.getReaper()).isEqualTo(reaper);
    }


    @Test
    public void should_board_give_my_destroyer() throws Exception {
        Reaper reaper = new Reaper(new Position(0,0), 450);
        Destroyer destroyer = new Destroyer(new Position(0,0), 450);
        board.add(reaper);
        board.add(destroyer);

        assertThat(board.getDestroyer()).isEqualTo(destroyer);
    }
}