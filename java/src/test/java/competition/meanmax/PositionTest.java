package competition.meanmax;


import competition.meanmax.Player.Position;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    @Test
    public void should_distance_between_same_points_be_0() throws Exception {
        Position position = new Position(0, 0);
        assertThat(position.distance(position)).isEqualTo(0);
    }

    @Test
    public void should_calculate_distance_between_positions_on_same_x() throws Exception {
        Position pos1 = new Position(0, 1000);
        Position pos2 = new Position(0, 3000);
        assertThat(pos1.distance(pos2)).isEqualTo(2000);
        assertThat(pos2.distance(pos1)).isEqualTo(2000);
    }

    @Test
    public void should_calculate_distance_between_positions_on_same_y() throws Exception {
        Position pos1 = new Position(200, 0);
        Position pos2 = new Position(300, 0);
        assertThat(pos1.distance(pos2)).isEqualTo(100);
        assertThat(pos2.distance(pos1)).isEqualTo(100);
    }

    @Test
    public void should_calculate_distance_between_different_positions() throws Exception {
        Position pos1 = new Position(100,400);
        Position pos2 = new Position(500,100);
        assertThat(pos1.distance(pos2)).isEqualTo(500);
    }
}