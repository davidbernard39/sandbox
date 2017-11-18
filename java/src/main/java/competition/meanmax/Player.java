package competition.meanmax;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static class Analyzer {
        public static final String WAIT = "WAIT";
        private Board board;

        public Analyzer(Board board) {
            this.board = board;
        }

        public String action(Reaper reaper) {
            Wreck wreck = board.nearestWreck(reaper);
            if (reaper.overlap(wreck)) {
                return WAIT;
            }
            return move(wreck, 300);
        }

        private String move(Wreck wreck, int acc) {
            return wreck.position.x + " " + wreck.position.y + " " + acc;
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

        public List<Wreck> getWreckList() {
            return unitList
                    .stream()
                    .filter(Wreck.class::isInstance)
                    .map(Wreck.class::cast)
                    .collect(Collectors.toList());
        }

        public Wreck nearestWreck(Reaper reaper) {
            Wreck nearestWreck = null;
            for (Wreck wreck : getWreckList()) {
                if (reaper.overlap(wreck)) {
                    return wreck;
                }
                nearestWreck = wreck;
            }
            return nearestWreck;
        }

        public Reaper getReaper() {
            return unitList
                    .stream()
                    .filter(Reaper.class::isInstance)
                    .map(Reaper.class::cast)
                    .findFirst()
                    .get();
        }
    }

    public static class Unit {
        protected Position position;
        protected int radius;

        public Unit(Position position, int radius) {
            this.position = position;
            this.radius = radius;
        }

        public boolean overlap(Unit unit) {
            if (getDistance(unit) <= unit.radius) {
                return true;
            } else {
                return false;
            }
        }

        private double getDistance(Unit unit) {
            return this.position.distance(unit.position);
        }
    }

    public static class Reaper extends Unit {
        public Reaper(Position position, int radius) {
            super(position, radius);
        }
    }

    public static class Wreck extends Unit {
        public Wreck(Position position, int radius) {
            super(position, radius);
        }
    }

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double distance(Position position) {
            return Math.sqrt(getSquaredAbscissaDiff(position) + getSquaredOrdinateDiff(position));
        }

        private double getSquaredAbscissaDiff(Position position) {
            return Math.pow(this.x - position.x, 2);
        }

        private double getSquaredOrdinateDiff(Position position) {
            return Math.pow(this.y - position.y, 2);
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Board board = new Board();
        Analyzer analyzer = new Analyzer(board);

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
                if (isMyReaper(unitType, player)) {
                    board.add(new Reaper(new Position(x, y), radius));
                } else if (isWreck(unitType)) {
                    board.add(new Wreck(new Position(x, y), radius));
                }
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(analyzer.action(board.getReaper()));
            System.out.println("WAIT");
            System.out.println("WAIT");
        }
    }

    private static boolean isWreck(int unitType) {
        return unitType == 4;
    }

    private static boolean isMyReaper(int unitType, int player) {
        return unitType == 0 && player != -1;
    }


}