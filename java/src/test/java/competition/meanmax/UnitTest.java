package competition.meanmax;

import competition.meanmax.Player.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

    @Test
    public void should_reaper_be_in_wreck_with_same_position() throws Exception {
        Reaper reaper = new Reaper(new Position(0, 0), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(0, 0), 0,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.isInUnit(wreck)).isTrue();
    }

    @Test
    public void should_reaper_be_in_wreck_with_position_in_radius() throws Exception {
        Reaper reaper = new Reaper(new Position(100, 0), 400,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(0, 0), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.isInUnit(wreck)).isTrue();
    }

    @Test
    public void should_not_reaper_be_in_wreck_with_separate_position() throws Exception {
        Reaper reaper = new Reaper(new Position(-5000, 400), 400,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(4500, -600), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.isInUnit(wreck)).isFalse();
    }

    @Test
    public void should_wreck_overlap_wreck_when_center_is_in_radius() throws Exception {
        Wreck wreck1 = new Wreck(new Position(4500, -600), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck2 = new Wreck(new Position(4400, -500), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(wreck1.overlap(wreck2)).isTrue();
    }

    @Test
    public void should_wreck_overlap_wreck_when_center_is_not_in_radius() throws Exception {
        Wreck wreck1 = new Wreck(new Position(4500, -600), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck2 = new Wreck(new Position(3700, -600), 300,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(wreck1.overlap(wreck2)).isTrue();
    }

    @Test
    public void should_not_wreck_overlap_wreck_with_separate_position() throws Exception {
        Wreck wreck1 = new Wreck(new Position(4500, -600), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck2 = new Wreck(new Position(-4500, 600), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(wreck1.overlap(wreck2)).isFalse();
    }

    @Test
    public void should_not_wreck_overlap_oil_with_separate_position() throws Exception {
        Oil oil = new Oil(new Position(4300, 2000), 650, 1, 0, 0, new GamePlayer(false, 0, 0), 4);
        Wreck wreck1 = new Wreck(new Position(3600, 800), 650, 1, 0, 0, new GamePlayer(false, 0, 0), 1);
        assertThat(wreck1.overlap(oil)).isFalse();
    }

    @Test
    public void should_predict_next_position_when_no_move() throws Exception {
        Reaper reaper = new Reaper(new Position(0,0), 400,1.5f, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.getNextPositionForMove(new Position(0,0),0)).isEqualToComparingFieldByField(new Position(0,0));
    }

    @Test
    public void should_predict_next_position_when_moving_without_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4316,1222), 400,0.5f, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.getNextPositionForMove(new Position(2632,1439),300)).isEqualToComparingFieldByField(new Position(3721,1299));
    }

    @Test
    public void should_predict_next_vx_when_moving_without_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4316,1222), 400,0.5f, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.getNextVxForMove(new Position(2632,1439),300)).isEqualTo(-476);
    }

    @Test
    public void should_predict_next_vy_when_moving_without_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4316,1222), 400,0.5f, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.getNextVyForMove(new Position(2632,1439),300)).isEqualTo(61);
    }

    @Test
    public void should_predict_next_position_when_moving_with_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(3721,1299), 400,0.5f, -476, 61,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.getNextPositionForMove(new Position(3108,1378),300)).isEqualToComparingFieldByField(new Position(2650,1437));
    }

    @Test
    public void should_predict_next_vx_when_moving_with_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(3721,1299), 400,0.5f, -476, 61,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.getNextVxForMove(new Position(3108,1378),300)).isEqualTo(-857);
    }

    @Test
    public void should_predict_next_vy_when_moving_with_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(3721,1299), 400,0.5f, -476, 61,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.getNextVyForMove(new Position(3108,1378),300)).isEqualTo(110);
    }

    @Test
    public void should_compute_acceleration_to_join_position_without_initial_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4080,950), 400,1.5f, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.computeAcceleration(new Position(4091,1016))).isEqualTo(100);
        assertThat(reaper.computeAcceleration(new Position(4208,1026))).isEqualTo(223);
        assertThat(reaper.computeAcceleration(new Position(4252,1052))).isEqualTo(300);
    }

    @Test
    public void should_compute_acceleration_to_join_position_with_initial_vx_speed() throws Exception {
        Reaper reaper = new Reaper(new Position(4080,950), 400,1.5f, 68, 68,new GamePlayer(false,0, 0), -1);
        //assertThat(reaper.computeAcceleration(new Position(4275,1045))).isEqualTo(280);
//        assertThat(reaper.computeAcceleration(new Position(4216,1011))).isEqualTo(178);

        Position pos = reaper.getNextPositionForMove(new Position(4275, 1045), 287);
        System.out.println(pos);


//        assertThat(true).isFalse();
    }

    @Test
    public void should_wreck_be_accessible_to_reaper_if_same_position() throws Exception {
        Reaper reaper = new Reaper(new Position(100, 0), 400,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(100, 0), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.canMoveOn(wreck)).isTrue();
    }

    @Test
    public void should_wreck_be_accessible_to_reaper_if_not_too_far() throws Exception {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(4300, 2100), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.canMoveOn(wreck)).isTrue();
    }

    @Test
    public void should_wreck_be_accessible_to_reaper_if_not_too_far_2() throws Exception {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(3600, 800), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.canMoveOn(wreck)).isTrue();
    }

    @Test
    public void should_not_wreck_be_accessible_to_reaper_if_a_little_too_far() throws Exception {
        Reaper reaper = new Reaper(new Position(4114,1147), 400,1.5f, 24, 138,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(4300, 2200), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.canMoveOn(wreck)).isFalse();
    }

    @Test
    public void should_not_wreck_be_accessible_to_reaper_if_too_far() throws Exception {
        Reaper reaper = new Reaper(new Position(100, 0), 400,1, 0, 0,new GamePlayer(false,0, 0), -1);
        Wreck wreck = new Wreck(new Position(3000, 3000), 650,1, 0, 0,new GamePlayer(false,0, 0), -1);
        assertThat(reaper.canMoveOn(wreck)).isFalse();
    }


}