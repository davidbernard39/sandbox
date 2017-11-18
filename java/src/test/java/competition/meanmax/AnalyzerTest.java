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
        Reaper reaper = new Reaper(new Position(0,0));
        Wreck wreck = new Wreck(new Position(0,0));

        board.add(reaper);
        board.add(wreck);

        assertThat(analyzer.action(reaper)).isEqualTo("WAIT");
    }

    @Test
    public void should_reaper_move_on_wreck() throws Exception {
        //Given
        Reaper reaper = new Reaper(new Position(0,0));
        Wreck wreck = new Wreck(new Position(600,0));

        board.add(reaper);
        board.add(wreck);

        assertThat(analyzer.action(reaper)).isEqualTo("600 0 300");
    }
}