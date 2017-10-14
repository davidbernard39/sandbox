package competition.wondev;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.NoSuchElementException;

public class PlayerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    public static Player.Grid initEmptyGrid(int size) {
        Player.Grid grid = new Player.Grid(2);
        for (int i = 0; i < grid.size; i++) {
            for (int j = 0; j < grid.size; j++) {
                grid.setCaseAt(i, j,0);
            }
        }
        return grid;
    }

    @Test
    public void shouldGetCaseInDirGiveTheGoodNeighborCase() {
        Player.Grid grid = initEmptyGrid(2);

        // Case in the left up corner
        Player.Case leftUpCorner = grid.getCaseAt(0,0);
        // Case in the right up corner
        Player.Case rightUpCorner = grid.getCaseAt(0, 1);
        // Case in the right down corner
        Player.Case rightDownCorner = grid.getCaseAt(1,1);
        // Case in the left down corner
        Player.Case leftDownCorner = grid.getCaseAt(1,0);

        // Check from left up corner
        Player.Case c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.N);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.NE);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.E);
        Assertions.assertThat(c).isEqualTo(rightUpCorner);
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.SE);
        Assertions.assertThat(c).isEqualTo(rightDownCorner);
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.S);
        Assertions.assertThat(c).isEqualTo(leftDownCorner);
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.SW);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.W);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.NW);
        Assertions.assertThat(c).isNull();

        //Check from right up corner
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.N);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.NE);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.E);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.SE);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.S);
        Assertions.assertThat(c).isEqualTo(rightDownCorner);
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.SW);
        Assertions.assertThat(c).isEqualTo(leftDownCorner);
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.W);
        Assertions.assertThat(c).isEqualTo(leftUpCorner);
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.NW);
        Assertions.assertThat(c).isNull();

        //Check from right down corner
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.N);
        Assertions.assertThat(c).isEqualTo(rightUpCorner);
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.NE);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.E);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.SE);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.S);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.SW);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.W);
        Assertions.assertThat(c).isEqualTo(leftDownCorner);
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.NW);
        Assertions.assertThat(c).isEqualTo(leftUpCorner);

        //Check from left down corner
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.N);
        Assertions.assertThat(c).isEqualTo(leftUpCorner);
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.NE);
        Assertions.assertThat(c).isEqualTo(rightUpCorner);
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.E);
        Assertions.assertThat(c).isEqualTo(rightDownCorner);
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.SE);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.S);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.SW);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.W);
        Assertions.assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.NW);
        Assertions.assertThat(c).isNull();
    }

//    @Test
//    public void test3ToursInLeagueWood1BugWithLeagueWood2Algorithm() throws Exception {
//        try (InputStream isPrb = getClass().getResourceAsStream("/wondev/in01.txt")) {
//            System.setIn(isPrb);
//            try {
//                Player.main(null);
//            } catch (NoSuchElementException e) {
//                System.err.println("End of game");
//            }
//        }
//    }


}
