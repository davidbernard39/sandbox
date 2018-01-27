package competition.platinum;

import competition.platinum.Player.Zone;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZoneTest {
    @Test
    public void should_zone_without_neighbor_return_empty_neighbors() {
        Zone zone1 = new Zone(1,3);
        assertThat(zone1.neighbors()).isNotNull();
        assertThat(zone1.neighbors()).isEmpty();
    }

    @Test
    public void should_zone_with_one_neighbor_return_neighbor() {
        Zone zone1 = new Zone(1,3);
        Zone zone2 = new Zone(2, 4);
        zone1.addNeighbor(zone2);
        assertThat(zone1.neighbors()).containsExactly(zone2);
    }

    @Test
    public void should_neighbor_of_a_zone_return_zone() {
        Zone zone1 = new Zone(1,3);
        Zone zone2 = new Zone(2, 4);
        zone1.addNeighbor(zone2);
        assertThat(zone2.neighbors()).containsExactly(zone1);
    }

    @Test
    public void should_add_neighbors_zones() {
        Zone zone1 = new Zone(1, 2);
        Zone zone2 = new Zone(2, 3);
        Zone zone3 = new Zone(3, 4);
        Zone zone4 = new Zone(4, 5);

        zone1.addNeighbor(zone2);
        zone1.addNeighbor(zone3);
        zone2.addNeighbor(zone3);
        zone4.addNeighbor(zone3);

        assertThat(zone1.neighbors()).containsExactly(zone2, zone3);
        assertThat(zone2.neighbors()).containsExactly(zone1, zone3);
        assertThat(zone3.neighbors()).containsExactly(zone1, zone2, zone4);
        assertThat(zone4.neighbors()).containsExactly(zone3);
    }
}