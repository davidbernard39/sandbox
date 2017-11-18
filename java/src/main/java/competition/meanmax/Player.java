package competition.meanmax;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static class Analyzer {
        private Board board;

        public Analyzer(Board board) {
            this.board = board;
        }

        public String action(Reaper reaper) {
            return "WAIT";
        }
    }

    public static class Board {

        private List<Unit> unitList = new ArrayList<>();

        public void add(Unit unit) {
            unitList.add(unit);
        }

        public List<Unit> getUnits() {
            return unitList;
        }
    }

    public static class Unit {
        protected Position position;

        public Unit(Position position) {
            this.position = position;
        }
    }

    public static class Reaper extends Unit {
        public Reaper(Position position) {
            super(position);
        }
    }

    public static class Wreck extends Unit {
        public Wreck(Position position) {
            super(position);
        }
    }

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int myScore = in.nextInt();
            int enemyScore1 = in.nextInt();
            int enemyScore2 = in.nextInt();
            int myRage = in.nextInt();
            int enemyRage1 = in.nextInt();
            int enemyRage2 = in.nextInt();
            int unitCount = in.nextInt();
            System.err.println("myscore: " + myScore + " enemyScore1: " + enemyScore1 + " enemyScore2: " + enemyScore2
                    + " myRage: " + myRage + " enemyRage1: " + enemyRage1 + " enemyRage2: " + enemyRage2
                    + " unitCount: "
                    + unitCount);
            for (int i = 0; i < unitCount; i++) {
                int unitId = in.nextInt();
                int unitType = in.nextInt();
                int player = in.nextInt();
                float mass = in.nextFloat();
                int radius = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                int vx = in.nextInt();
                int vy = in.nextInt();
                int extra = in.nextInt();
                int extra2 = in.nextInt();
                System.err.println("unitId: " + unitId + " unitType: " + unitType + " player: " + player + " mass: "
                        + mass + " radius: " + radius + " x: " + x + " y: " + y + " vx: " + vx + " vy: " + vy
                        + " extra: " + extra + " extra2: " + extra2);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println("WAIT");
            System.out.println("WAIT");
            System.out.println("WAIT");
        }
    }


}