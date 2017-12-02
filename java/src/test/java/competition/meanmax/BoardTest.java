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
        Reaper reaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);

        //When
        board.add(reaper);

        //Then
        assertThat(board.getUnits()).containsExactly(reaper);
    }

    @Test
    public void should_board_give_nearest_wreck_from_reaper_when_wreck_is_on_reaper() {
        Reaper reaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Wreck wreck = new Wreck(new Position(100, 100), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        board.add(reaper);
        board.add(wreck);
        assertThat(board.nearestWreck(reaper)).isEqualTo(wreck);
    }

    @Test
    public void should_board_give_nearest_wreck_from_reaper_when_wreck_is_far_from_reaper() {
        Reaper reaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Wreck wreck = new Wreck(new Position(3000, 1000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        board.add(reaper);
        board.add(wreck);
        assertThat(board.nearestWreck(reaper)).isEqualTo(wreck);
    }

//    @Test
//    public void should_not_board_give_nearest_wreck_from_reaper_when_wreck_is_oiled() {
//        Reaper reaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
//        Wreck wreck1 = new Wreck(new Position(3000, 1000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
//        Oil oil = new Oil(new Position(3000, 1000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
//        Wreck wreck2 = new Wreck(new Position(4000, 2000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
//        board.add(reaper);
//        board.add(wreck1);
//        board.add(wreck2);
//        board.add(oil);
//        assertThat(board.nearestWreckWithoutOil(reaper)).isEqualTo(wreck2);
//    }
//
//    @Test
//    public void should_board_give_nearest_wreck_from_reaper_when_wreck_is_oiled_and_is_unique() {
//        Reaper reaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
//        Wreck wreck1 = new Wreck(new Position(3000, 1000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
//        Oil oil = new Oil(new Position(3000, 1000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
//        board.add(reaper);
//        board.add(wreck1);
//        board.add(oil);
//        assertThat(board.nearestWreckWithoutOil(reaper)).isEqualTo(wreck1);
//    }

    @Test
    public void should_board_give_nearest_tanker_from_destroyer_when_tanker_is_far_from_destroyer() {
        Destroyer destroyer = new Destroyer(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Tanker tanker = new Tanker(new Position(3000, 1000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        board.add(destroyer);
        board.add(tanker);
        assertThat(board.nearestTanker(destroyer)).isEqualTo(tanker);
    }

    @Test
    public void should_board_choose_nearest_tanker_from_destroyer_among_several_choices() {
        Destroyer destroyer = new Destroyer(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Tanker tanker1 = new Tanker(new Position(3000, 1000), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Tanker tanker2 = new Tanker(new Position(400, 200), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Tanker tanker3 = new Tanker(new Position(3400, 0), 600, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        board.add(destroyer);
        board.add(tanker1);
        board.add(tanker2);
        board.add(tanker3);
        assertThat(board.nearestTanker(destroyer)).isEqualTo(tanker2);
    }

    @Test
    public void should_board_give_no_nearest_tanker_from_destroyer_when_no_tanker_present() throws Exception {
        Destroyer destroyer = new Destroyer(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        board.add(destroyer);
        assertThat(board.nearestTanker(destroyer)).isNull();
    }

    @Test
    public void should_board_give_my_reaper() throws Exception {
        Reaper myReaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Reaper enemyReaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(true, 0, 0), -1);
        board.add(enemyReaper);
        board.add(myReaper);
        assertThat(board.getReaper()).isEqualTo(myReaper);
    }

    @Test
    public void should_board_give_my_doof() throws Exception {
        Doof myDoof = new Doof(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Doof enemyDoof = new Doof(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(true, 0, 0), -1);
        board.add(myDoof);
        board.add(enemyDoof);
        assertThat(board.getDoof()).isEqualTo(myDoof);
    }

    @Test
    public void should_board_give_nearest_enemy_reaper() throws Exception {
        Reaper myReaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Reaper reaper1 = new Reaper(new Position(300, 4000), 450, 1, 0, 0, new GamePlayer(true, 0, 0), -1);
        Reaper reaper2 = new Reaper(new Position(300, 400), 450, 1, 0, 0, new GamePlayer(true, 0, 0), -1);
        Reaper reaper3 = new Reaper(new Position(4999, 400), 450, 1, 0, 0, new GamePlayer(true, 0, 0), -1);
        board.add(myReaper);
        board.add(reaper1);
        board.add(reaper2);
        board.add(reaper3);
        assertThat(board.nearestEnemyReaper(myReaper)).isEqualTo(reaper2);
    }


    @Test
    public void should_board_give_enemy_with_higher_score() throws Exception {
        Reaper myReaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Reaper reaper1 = new Reaper(new Position(300, 4000), 450, 1, 0, 0, new GamePlayer(true, 10, 0), -1);
        Reaper reaper2 = new Reaper(new Position(300, 400), 450, 1, 0, 0, new GamePlayer(true, 0, 0), -1);
        Reaper reaper3 = new Reaper(new Position(4999, 400), 450, 1, 0, 0, new GamePlayer(true, 0, 0), -1);
        board.add(myReaper);
        board.add(reaper1);
        board.add(reaper2);
        board.add(reaper3);
        assertThat(board.getEnemyReaperWithHigherScore()).isEqualTo(reaper1);
    }



    @Test
    public void should_board_give_my_destroyer() throws Exception {
        Reaper reaper = new Reaper(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(0, 0), 450, 1, 0, 0, new GamePlayer(false, 0, 0), -1);
        board.add(reaper);
        board.add(destroyer);

        assertThat(board.getDestroyer()).isEqualTo(destroyer);
    }

    @Test
    public void should_board_give_accessible_wreck_from_reaper_if_only_one_wreck_is_present() {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(4300, 2100), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        board.add(reaper);
        board.add(wreck);
        assertThat(board.getAccessibleWrecks(reaper)).containsExactly(wreck);
    }

    @Test
    public void should_board_give_accessible_wrecks_from_reaper() {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck1 = new Wreck(new Position(4300, 2000), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck2 = new Wreck(new Position(4300, 2100), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        board.add(reaper);
        board.add(wreck1);
        board.add(wreck2);
        assertThat(board.getAccessibleWrecks(reaper)).containsExactly(wreck1,wreck2);
    }

    @Test
    public void should_board_give_accessible_wrecks_from_reaper_with_too_far_wreck() {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck1 = new Wreck(new Position(4300, 2000), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck2 = new Wreck(new Position(4300, 2100), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck3 = new Wreck(new Position(4300, 2200), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        board.add(reaper);
        board.add(wreck1);
        board.add(wreck2);
        board.add(wreck3);
        assertThat(board.getAccessibleWrecks(reaper)).containsExactly(wreck1,wreck2);
    }


    @Test
    public void should_board_give_best_accessible_wreck() {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck1 = new Wreck(new Position(4300, 2000), 650,1, 0, 0,new GamePlayer(false,0, 0), 4);
        Wreck wreck2 = new Wreck(new Position(4300, 2100), 650,1, 0, 0,new GamePlayer(false,0, 0), 1);
        Wreck wreck3 = new Wreck(new Position(4300, 2200), 650,1, 0, 0,new GamePlayer(false,0, 0), 8);
        board.add(reaper);
        board.add(wreck1);
        board.add(wreck2);
        board.add(wreck3);
        assertThat(board.bestWreck(reaper)).isEqualTo(wreck1);
    }

    @Test
    public void should_board_not_give_best_accessible_oiled_wreck() {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck1 = new Wreck(new Position(4300, 2000), 650,1, 0, 0,new GamePlayer(false,0, 0), 4);
        Oil oil = new Oil(new Position(4300, 2000), 650,1, 0, 0,new GamePlayer(false,0, 0), 4);
        Wreck wreck2 = new Wreck(new Position(3600, 800), 650,1, 0, 0,new GamePlayer(false,0, 0), 1);
        Wreck wreck3 = new Wreck(new Position(4300, 2200), 650,1, 0, 0,new GamePlayer(false,0, 0), 8);
        board.add(reaper);
        board.add(wreck1);
        board.add(oil);
        board.add(wreck2);
        board.add(wreck3);
        assertThat(board.bestWreck(reaper)).isEqualTo(wreck2);
    }




}