package competition.meanmax;

import competition.meanmax.Player.*;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnalyzerTest {

    private Analyzer analyzer;
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        analyzer = new Analyzer(board);
    }

    @Test
    public void should_reaper_wait_when_on_wreck() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0, 12, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(0,0),0,12, 0, 0,new GamePlayer(false,0, 0), -1);

        board.add(reaper);
        board.add(wreck);

        assertThat(analyzer.action(reaper)).isEqualTo("WAIT");
    }

    @Test
    public void should_reaper_move_to_destroyer_when_no_wreck() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(1000,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);

        board.add(reaper);
        board.add(destroyer);

        assertThat(analyzer.action(reaper)).isEqualTo("1000 0 300");
    }

    @Test
    public void should_reaper_move_on_wreck() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);

        board.add(reaper);
        board.add(wreck);

        assertThat(analyzer.action(reaper)).isEqualTo("600 0 300");
    }

    @Test
    public void should_reaper_move_on_best_wreck() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck1 = new Wreck(new Position(4300, 2000), 650,1, 0, 0,new GamePlayer(false,0, 0), 2);
        Wreck wreck2 = new Wreck(new Position(4300, 2100), 650,1, 0, 0,new GamePlayer(false,0, 0), 5);
        Wreck wreck3 = new Wreck(new Position(4300, 2200), 650,1, 0, 0,new GamePlayer(false,0, 0), 8);
        board.add(reaper);
        board.add(wreck1);
        board.add(wreck2);
        board.add(wreck3);

        assertThat(analyzer.action(reaper)).isEqualTo("4276 1962 300");
    }


    @Test
    public void should_destroyer_wait_when_no_tanker() throws Exception {
        //Given
        Destroyer destroyer = new Destroyer(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);

        board.add(destroyer);

        assertThat(analyzer.action(destroyer)).isEqualTo("WAIT");
    }

    @Test
    public void should_destroyer_move_on_tanker() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(-3000, -3000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Tanker tanker1 = new Tanker(new Position(3000, 3000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Tanker tanker2 = new Tanker(new Position(-2000, -2000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);


        board.add(reaper);
        board.add(wreck);
        board.add(destroyer);
        board.add(tanker1);
        board.add(tanker2);

        assertThat(analyzer.action(destroyer)).isEqualTo("-2000 -2000 300");
    }

    @Test
    public void should_doof_get_a_move_on_nearest_enemy_reaper_with_initial_speed() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Doof doof = new Doof(new Position(4000,4000),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(-3000, -3000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Tanker tanker1 = new Tanker(new Position(3000, 3000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Tanker tanker2 = new Tanker(new Position(-2000, -2000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Reaper enemyReaper1 = new Reaper(new Position(5000,5000), 0,1,100,200,new GamePlayer(true,0, 0), -1);
        Reaper enemyReaper2 = new Reaper(new Position(-5000,-5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Destroyer enemyDestroyer = new Destroyer(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);


        board.add(reaper);
        board.add(wreck);
        board.add(destroyer);
        board.add(tanker1);
        board.add(tanker2);
        board.add(enemyReaper1);
        board.add(enemyReaper2);
        board.add(enemyDestroyer);

        assertThat(analyzer.action(doof)).isEqualTo("5100 5200 300");
    }

    @Test
    public void should_doof_get_a_move_on_nearest_enemy_reaper_with_no_initial_speed() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Doof doof = new Doof(new Position(4000,4000),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(-3000, -3000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Tanker tanker1 = new Tanker(new Position(3000, 3000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Tanker tanker2 = new Tanker(new Position(-2000, -2000), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Reaper enemyReaper1 = new Reaper(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Reaper enemyReaper2 = new Reaper(new Position(-5000,-5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Destroyer enemyDestroyer = new Destroyer(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);


        board.add(reaper);
        board.add(wreck);
        board.add(destroyer);
        board.add(tanker1);
        board.add(tanker2);
        board.add(enemyReaper1);
        board.add(enemyReaper2);
        board.add(enemyDestroyer);

        assertThat(analyzer.action(doof)).isEqualTo("5000 5000 300");
    }

    @Test
    public void should_destroyer_launch_a_grenade_on_enemy_reaper_at_distance_between_1000_and_2000_with_no_initial_speed() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(-3000,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(0, 0), 0,1, 0, 0,new GamePlayer(false,0, 61), -1);
        Reaper enemyReaper1 = new Reaper(new Position(1300,0), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Reaper enemyReaper2 = new Reaper(new Position(-5000,-5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Destroyer enemyDestroyer = new Destroyer(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);


        board.add(reaper);
        board.add(destroyer);
        board.add(enemyReaper1);
        board.add(enemyReaper2);
        board.add(enemyDestroyer);

        assertThat(analyzer.action(destroyer)).isEqualTo("SKILL 1300 0");
    }

    @Test
    public void should_destroyer_launch_a_grenade_on_enemy_reaper_at_distance_between_1000_and_2000_with_initial_speed() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(-3000,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(0, 0), 0,1, 0, 0,new GamePlayer(false,0, 61), -1);
        Reaper enemyReaper1 = new Reaper(new Position(1300,0), 0,1,130,240,new GamePlayer(true,0, 0), -1);
        Reaper enemyReaper2 = new Reaper(new Position(-5000,-5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Destroyer enemyDestroyer = new Destroyer(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);


        board.add(reaper);
        board.add(destroyer);
        board.add(enemyReaper1);
        board.add(enemyReaper2);
        board.add(enemyDestroyer);

        assertThat(analyzer.action(destroyer)).isEqualTo("SKILL 1430 240");
    }


    @Test
    public void should_not_destroyer_launch_a_grenade_on_enemy_reaper_at_distance_between_1000_and_2000_with_no_initial_speed_if_my_reaper_is_near() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(1000,0),0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Destroyer destroyer = new Destroyer(new Position(0, 0), 0,1, 0, 0,new GamePlayer(false,0, 61), -1);
        Reaper enemyReaper1 = new Reaper(new Position(1300,0), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Reaper enemyReaper2 = new Reaper(new Position(-5000,-5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);
        Destroyer enemyDestroyer = new Destroyer(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0, 0), -1);


        board.add(reaper);
        board.add(destroyer);
        board.add(enemyReaper1);
        board.add(enemyReaper2);
        board.add(enemyDestroyer);

        assertThat(analyzer.action(destroyer)).isNotEqualTo("SKILL 1300 0");
    }
}