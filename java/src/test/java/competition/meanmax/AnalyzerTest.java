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
        Reaper reaper = new Reaper(new Position(0,0),0, 12, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(0,0),0,12, 0, 0,new GamePlayer(false,0));

        board.add(reaper);
        board.add(wreck);

        assertThat(analyzer.action(reaper)).isEqualTo("WAIT");
    }

    @Test
    public void should_reaper_wait_when_no_wreck() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0));

        board.add(reaper);

        assertThat(analyzer.action(reaper)).isEqualTo("WAIT");
    }

    @Test
    public void should_reaper_move_on_wreck() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0));

        board.add(reaper);
        board.add(wreck);

        assertThat(analyzer.action(reaper)).isEqualTo("600 0 300");
    }

    @Test
    public void should_destroyer_wait_when_no_tanker() throws Exception {
        //Given
        Destroyer destroyer = new Destroyer(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0));

        board.add(destroyer);

        assertThat(analyzer.action(destroyer)).isEqualTo("WAIT");
    }

    @Test
    public void should_destroyer_move_on_tanker() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0));
        Destroyer destroyer = new Destroyer(new Position(-3000, -3000), 0,1, 0, 0,new GamePlayer(false,0));
        Tanker tanker1 = new Tanker(new Position(3000, 3000), 0,1, 0, 0,new GamePlayer(false,0));
        Tanker tanker2 = new Tanker(new Position(-2000, -2000), 0,1, 0, 0,new GamePlayer(false,0));


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
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0));
        Doof doof = new Doof(new Position(4000,4000),0,1, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0));
        Destroyer destroyer = new Destroyer(new Position(-3000, -3000), 0,1, 0, 0,new GamePlayer(false,0));
        Tanker tanker1 = new Tanker(new Position(3000, 3000), 0,1, 0, 0,new GamePlayer(false,0));
        Tanker tanker2 = new Tanker(new Position(-2000, -2000), 0,1, 0, 0,new GamePlayer(false,0));
        Reaper enemyReaper1 = new Reaper(new Position(5000,5000), 0,1,100,200,new GamePlayer(true,0));
        Reaper enemyReaper2 = new Reaper(new Position(-5000,-5000), 0,1,0,0,new GamePlayer(true,0));
        Destroyer enemyDestroyer = new Destroyer(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0));


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
        Reaper reaper = new Reaper(new Position(0,0),0,1, 0, 0,new GamePlayer(false,0));
        Doof doof = new Doof(new Position(4000,4000),0,1, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(600,0),0,1, 0, 0,new GamePlayer(false,0));
        Destroyer destroyer = new Destroyer(new Position(-3000, -3000), 0,1, 0, 0,new GamePlayer(false,0));
        Tanker tanker1 = new Tanker(new Position(3000, 3000), 0,1, 0, 0,new GamePlayer(false,0));
        Tanker tanker2 = new Tanker(new Position(-2000, -2000), 0,1, 0, 0,new GamePlayer(false,0));
        Reaper enemyReaper1 = new Reaper(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0));
        Reaper enemyReaper2 = new Reaper(new Position(-5000,-5000), 0,1,0,0,new GamePlayer(true,0));
        Destroyer enemyDestroyer = new Destroyer(new Position(5000,5000), 0,1,0,0,new GamePlayer(true,0));


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
}