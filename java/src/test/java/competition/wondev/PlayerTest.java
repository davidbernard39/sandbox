package competition.wondev;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    public static Player.Grid initEmptyGrid(int size) {
        Player.Grid grid = new Player.Grid(size);
        for (int i = 0; i < grid.size; i++) {
            for (int j = 0; j < grid.size; j++) {
                grid.setCaseAt(i, j,0);
            }
        }
        return grid;
    }

    @Test
    public void moveOnCasesAtLevel0ShouldReturn1() {
        Player.Grid grid = initEmptyGrid(3);
        Player.Case centerCase = grid.getCaseAt(1,1);

        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.N)).isEqualTo(1.0f);
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.NE)).isEqualTo(1.0f);
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.E)).isEqualTo(1.0f);
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.SE)).isEqualTo(1.0f);
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.S)).isEqualTo(1.0f);
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.SW)).isEqualTo(1.0f);
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.W)).isEqualTo(1.0f);
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.NW)).isEqualTo(1.0f);
    }

    @Test
    public void moveOnCasesAtLevel1ShouldReturn2() {
        Player.Grid grid = initEmptyGrid(3);
        grid.getCaseAt(0,1).height=1;
        Player.Case centerCase = grid.getCaseAt(1,1);

        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.N)).isEqualTo(2.0f);
    }

    @Test
    public void moveOnCasesAtLevel2ShouldReturn0IfInaccessible() {
        Player.Grid grid = initEmptyGrid(3);
        grid.getCaseAt(0,1).height=2;
        Player.Case centerCase = grid.getCaseAt(1,1);

        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.N)).isEqualTo(0.0f);
    }

    @Test
    public void moveOnCasesAtLevel2ShouldReturn3IfAccessible() {
        Player.Grid grid = initEmptyGrid(3);
        grid.getCaseAt(0,1).height=2;
        Player.Case centerCase = grid.getCaseAt(1,1);
        centerCase.height = 1;

        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.N)).isEqualTo(3.0f);
    }

    @Test
    public void moveOnCasesAtLevel3ShouldReturn0IfInaccessible() {
        Player.Grid grid = initEmptyGrid(3);
        grid.getCaseAt(0,1).height=3;
        Player.Case centerCase = grid.getCaseAt(1,1);

        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.N)).isEqualTo(0.0f);

        centerCase.height = 1;
        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.N)).isEqualTo(0.0f);
    }

    @Test
    public void moveOnCasesAtLevel3ShouldReturn4IfAccessible() {
        Player.Grid grid = initEmptyGrid(3);
        grid.getCaseAt(0,1).height=3;
        Player.Case centerCase = grid.getCaseAt(1,1);
        centerCase.height = 2;

        assertThat(grid.getMoveScore(centerCase, Player.CardinalPoint.N)).isEqualTo(4.0f);
    }

    @Test
    public void buildOnCasesFromLevel0AtLevel0ShouldReturn1() {
        Player.Grid grid = initEmptyGrid(3);
        Player.Case centerCase = grid.getCaseAt(1,1);

        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.N)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.NE)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.E)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.SE)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.S)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.SW)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.W)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.NW)).isEqualTo(1.0f);
    }

    @Test
    public void buildOnCasesFromLevel1AtLevel0ShouldReturn1() {
        Player.Grid grid = initEmptyGrid(3);
        Player.Case centerCase = grid.getCaseAt(1,1);
        centerCase.height = 1;

        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.N)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.NE)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.E)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.SE)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.S)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.SW)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.W)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.NW)).isEqualTo(1.0f);
    }

    @Test
    public void buildOnCasesWithNeighborAtLevel2ShouldReturn2() {
        Player.Grid grid = initEmptyGrid(3);
        Player.Case centerCase = grid.getCaseAt(1,1);
        grid.getCaseAt(0,2).height = 2;

        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.N)).isEqualTo(2.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.E)).isEqualTo(2.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.SE)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.S)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.SW)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.W)).isEqualTo(1.0f);
        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.NW)).isEqualTo(1.0f);
    }

    @Test
    public void buildOnCasesWithToHighLevelShouldReturn0() {
        Player.Grid grid = initEmptyGrid(3);
        Player.Case centerCase = grid.getCaseAt(1,1);
        grid.getCaseAt(0,2).height = 2;

        assertThat(grid.getBuildScore(centerCase, Player.CardinalPoint.NE)).isEqualTo(0.0f);
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
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.NE);
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.E);
        assertThat(c).isEqualTo(rightUpCorner);
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.SE);
        assertThat(c).isEqualTo(rightDownCorner);
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.S);
        assertThat(c).isEqualTo(leftDownCorner);
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.SW);
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.W);
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftUpCorner, Player.CardinalPoint.NW);
        assertThat(c).isNull();

        //Check from right up corner
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.N);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.NE);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.E);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.SE);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.S);
        assertThat(c).isEqualTo(rightDownCorner);
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.SW);
        assertThat(c).isEqualTo(leftDownCorner);
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.W);
        assertThat(c).isEqualTo(leftUpCorner);
        c = grid.getCaseInDir(rightUpCorner, Player.CardinalPoint.NW);
        assertThat(c).isNull();

        //Check from right down corner
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.N);
        assertThat(c).isEqualTo(rightUpCorner);
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.NE);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.E);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.SE);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.S);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.SW);
        assertThat(c).isNull();
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.W);
        assertThat(c).isEqualTo(leftDownCorner);
        c = grid.getCaseInDir(rightDownCorner, Player.CardinalPoint.NW);
        assertThat(c).isEqualTo(leftUpCorner);

        //Check from left down corner
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.N);
        assertThat(c).isEqualTo(leftUpCorner);
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.NE);
        assertThat(c).isEqualTo(rightUpCorner);
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.E);
        assertThat(c).isEqualTo(rightDownCorner);
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.SE);
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.S);
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.SW);
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.W);
        assertThat(c).isNull();
        c = grid.getCaseInDir(leftDownCorner, Player.CardinalPoint.NW);
        assertThat(c).isNull();
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
