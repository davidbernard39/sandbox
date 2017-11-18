package competition.meanmax;

import competition.meanmax.Player.*;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

    @Test
    public void should_overlap_reaper_wreck_with_same_position() throws Exception {
        Reaper reaper = new Reaper(new Position(0, 0), 0);
        Wreck wreck = new Wreck(new Position(0, 0), 0);
        assertThat(reaper.overlap(wreck)).isTrue();
    }

    @Test
    public void should_overlap_reaper_wreck_with_position_in_radius() throws Exception {
        Reaper reaper = new Reaper(new Position(100, 0), 400);
        Wreck wreck = new Wreck(new Position(0, 0), 650);
        assertThat(reaper.overlap(wreck)).isTrue();
    }

    @Test
    public void should_not_overlap_reaper_wreck_with_separate_position() throws Exception {
        Reaper reaper = new Reaper(new Position(-5000, 400), 400);
        Wreck wreck = new Wreck(new Position(4500, -600), 650);
        assertThat(reaper.overlap(wreck)).isFalse();
    }
}