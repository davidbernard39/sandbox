package competition.meanmax;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static class Analyzer {
        public static final String WAIT = "WAIT";
        public static final String ACTION_SEPARATOR = " ";
        private Board board;

        public Analyzer(Board board) {
            this.board = board;
        }

        public String action(Reaper reaper) {
            Wreck wreck = board.nearestWreck(reaper);
            if (wreck == null || reaper.overlap(wreck)) {
                return WAIT;
            }
            return move(wreck, reaper.computeAcceleration(wreck.position));
        }

        public String action(Destroyer destroyer) {
            Tanker tanker = board.nearestTanker(destroyer);
            if (tanker == null) {
                return WAIT;
            }
            return move(tanker, destroyer.computeAcceleration(tanker.position));
        }

        private String move(Unit target, int acc) {
            return target.position.x + ACTION_SEPARATOR + target.position.y + ACTION_SEPARATOR + acc;
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

        private Optional<Unit> nearest(Unit unit, Class targetUnitClass) {
            return unitList
                    .stream()
                    .filter(targetUnitClass::isInstance)
                    .min(Comparator.comparingDouble(unit::getDistance));
        }

        public Wreck nearestWreck(Reaper reaper) {
            return nearest(reaper, Wreck.class)
                    .map(Wreck.class::cast)
                    .orElse(null);
        }

        public Tanker nearestTanker(Destroyer destroyer) {
            return nearest(destroyer, Tanker.class)
                    .map(Tanker.class::cast)
                    .orElse(null);
        }

        public Reaper getReaper() {
            return unitList
                    .stream()
                    .filter(Reaper.class::isInstance)
                    .map(Reaper.class::cast)
                    .findFirst()
                    .get();
        }

        public Destroyer getDestroyer() {
            return unitList
                    .stream()
                    .filter(Destroyer.class::isInstance)
                    .map(Destroyer.class::cast)
                    .findFirst()
                    .get();
        }
    }

    public static abstract class Unit {
        protected Position position;
        protected int radius;
        protected float mass;
        private int vx;
        private int vy;

        public Unit(Position position, int radius, float mass, int vx, int vy) {
            this.position = position;
            this.radius = radius;
            this.mass = mass;
            this.vx = vx;
            this.vy = vy;
        }

        public boolean overlap(Unit unit) {
            if (getDistance(unit.position) <= unit.radius) {
                return true;
            } else {
                return false;
            }
        }

        private double getDistance(Position position) {
            return this.position.distance(position);
        }

        private double getDistance(Unit unit) {
            return getDistance(unit.position);
        }

        public Position getNextPositionForMove(Position targetPosition, int acc) {
            double nextX = vx + position.x + ((targetPosition.x - position.x) * movingCoefficient(targetPosition, acc));
            double nextY = vy + position.y + ((targetPosition.y - position.y) * movingCoefficient(targetPosition, acc));
            return new Position((int) Math.round(nextX), (int) Math.round(nextY));
        }

        private double movingCoefficient(Position targetPosition, int acc) {
            return (acc/mass)/getDistance(targetPosition);
        }


        public int computeAcceleration(Position targetPosition) {
            int result = (int) Math.round(mass*getDistance(targetPosition)*(targetPosition.x - vx - this.position.x)/(targetPosition.x - this.position.x));
            return Math.abs(result) > 300 ? 300 : Math.abs(result);
        }
    }

    public static class Reaper extends Unit {
        public Reaper(Position position, int radius, float mass, int vx, int vy) {
            super(position, radius, mass, vx, vy);
        }
    }

    public static class Wreck extends Unit {
        public Wreck(Position position, int radius, float mass, int vx, int vy) {
            super(position, radius, mass, vx, vy);
        }
    }

    public static class Destroyer extends Unit {
        public Destroyer(Position position, int radius, float mass, int vx, int vy) {
            super(position, radius, mass, vx, vy);
        }
    }

    public static class Tanker extends Unit {
        public Tanker(Position position, int radius, float mass, int vx, int vy) {
            super(position, radius, mass, vx, vy);
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

        // game loop
        while (true) {
            Board board = new Board();
            Analyzer analyzer = new Analyzer(board);
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
                    board.add(new Reaper(new Position(x, y), radius, mass, 0, 0));
                } else if (isWreck(unitType)) {
                    board.add(new Wreck(new Position(x, y), radius, mass, vx, vy));
                } else if (isTanker(unitType)) {
                    board.add(new Tanker(new Position(x, y), radius, mass, vx, vy));
                } else if (isMyDestroyer(unitType, player)) {
                    board.add(new Destroyer(new Position(x, y), radius, mass, vx, vy));
                }
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(analyzer.action(board.getReaper()));
            System.out.println(analyzer.action(board.getDestroyer()));
            System.out.println("WAIT");
        }
    }

    private static boolean isMyDestroyer(int unitType, int player) {
        return unitType == 1 && player == 0;
    }

    private static boolean isTanker(int unitType) {
        return unitType == 3;
    }

    private static boolean isWreck(int unitType) {
        return unitType == 4;
    }

    private static boolean isMyReaper(int unitType, int player) {
        return unitType == 0 && player == 0;
    }


}