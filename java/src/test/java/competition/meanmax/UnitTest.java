package competition.meanmax;

import competition.meanmax.Player.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

    @Test
    public void should_overlap_reaper_wreck_with_same_position() throws Exception {
        Reaper reaper = new Reaper(new Position(0, 0), 0,1, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(0, 0), 0,1, 0, 0,new GamePlayer(false,0));
        assertThat(reaper.overlap(wreck)).isTrue();
    }

    @Test
    public void should_overlap_reaper_wreck_with_position_in_radius() throws Exception {
        Reaper reaper = new Reaper(new Position(100, 0), 400,1, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(0, 0), 650,1, 0, 0,new GamePlayer(false,0));
        assertThat(reaper.overlap(wreck)).isTrue();
    }

    @Test
    public void should_not_overlap_reaper_wreck_with_separate_position() throws Exception {
        Reaper reaper = new Reaper(new Position(-5000, 400), 400,1, 0, 0,new GamePlayer(false,0));
        Wreck wreck = new Wreck(new Position(4500, -600), 650,1, 0, 0,new GamePlayer(false,0));
        assertThat(reaper.overlap(wreck)).isFalse();
    }

    @Test
    public void should_predict_next_position_when_no_move() throws Exception {
        Reaper reaper = new Reaper(new Position(0,0), 400,1.5f, 0, 0,new GamePlayer(false,0));
        assertThat(reaper.getNextPositionForMove(new Position(0,0),0)).isEqualToComparingFieldByField(new Position(0,0));
    }

    @Test
    public void should_predict_next_position_when_moving_without_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4080,950), 400,1.5f, 0, 0,new GamePlayer(false,0));
        assertThat(reaper.getNextPositionForMove(new Position(5106,6920),300)).isEqualToComparingFieldByField(new Position(4114,1147));
    }

    @Test
    public void should_predict_next_position_when_moving_with_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0));
        assertThat(reaper.getNextPositionForMove(new Position(4770,6464),300)).isEqualToComparingFieldByField(new Position(4162,1483));
    }

    @Test
    public void should_compute_acceleration_to_join_position_without_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4080,950), 400,1.5f, 0, 0,new GamePlayer(false,0));
        assertThat(reaper.computeAcceleration(new Position(4091,1016))).isEqualTo(100);
    }
}