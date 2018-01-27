package competition.platinum;

import competition.platinum.Player.Game;
import competition.platinum.Player.Zone;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    public void should_get_sole_zone_with_id() {
        // Given
        Game game = new Game();
        Zone zone = new Zone(1, 3);

        // When
        game.addZone(zone);

        // Then
        assertThat(game.getZone(1)).isNotNull();
        assertThat(game.getZone(1)).isEqualTo(zone);
    }

    @Test
    public void should_get_multiple_zone_with_id() {
        // Given
        Game game = new Game();
        Zone zone1 = new Zone(1, 3);
        Zone zone2 = new Zone(2, 5);

        // When
        game.addZone(zone1);
        game.addZone(zone2);

        // Then
        assertThat(game.getZone(1)).isNotNull();
        assertThat(game.getZone(1)).isEqualTo(zone1);
    }

    @Test
    public void should_first_empty_zone_return_sole_zone() {
        Game game = new Game();
        Zone zone = new Zone(1, 2);
        game.addZone(zone);

        assertThat(game.firstEmptyZone(1)).containsExactly(zone);
    }

    @Test
    public void should_first_empty_zone_return_second_zone_if_first_one_is_not_empty() {
        Game game = new Game();
        Zone zone1 = new Zone(1, 2);
        zone1.setOwner(2);
        Zone zone2 = new Zone(2,2);
        game.addZone(zone1);
        game.addZone(zone2);

        assertThat(game.firstEmptyZone(1)).containsExactly(zone2);
    }

    @Test
    public void should_first_empty_zone_return_several_empty_zones() {
        Game game = new Game();
        Zone zone1 = new Zone(1, 2);
        Zone zone2 = new Zone(2,2);
        game.addZone(zone1);
        game.addZone(zone2);

        assertThat(game.firstEmptyZone(2)).containsExactly(zone1, zone2);
    }

    @Test
    public void should_highest_empty_zone_return_highest_zone() {
        Game game = new Game();
        Zone zone1 = new Zone(1, 2);
        Zone zone2 = new Zone(2,4);
        Zone zone3 = new Zone(3,3);
        game.addZone(zone1);
        game.addZone(zone2);
        game.addZone(zone3);

        assertThat(game.highestValueEmptyZone().get()).isEqualTo(zone2);
    }

    @Test
    public void should_highest_empty_zone_return_highest_zone_empty() {
        Game game = new Game();
        Zone zone1 = new Zone(1, 2);
        Zone zone2 = new Zone(2,4);
        zone2.setOwner(2);
        Zone zone3 = new Zone(3,3);
        game.addZone(zone1);
        game.addZone(zone2);
        game.addZone(zone3);

        assertThat(game.highestValueEmptyZone().get()).isEqualTo(zone3);
    }
}