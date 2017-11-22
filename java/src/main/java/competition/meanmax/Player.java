package competition.meanmax;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return move(reaper,wreck, reaper.computeAcceleration(wreck.position));
        }

        public String action(Destroyer destroyer) {
            Reaper enemyReaper = board.nearestEnemyReaper(destroyer);
            if (enemyReaper != null && destroyer.canLaunchGrenadeOnEnemyReaper(enemyReaper, board.getReaper())) {
                return skill(enemyReaper);
            }
            Tanker tanker = board.nearestTanker(destroyer);
            if (tanker == null) {
                return WAIT;
            }
            return move(destroyer,tanker, destroyer.computeAcceleration(tanker.position));
        }

        public String action(Doof doof) {
            Reaper enemyReaper = board.getEnemyReaperWithHigherScore();
            return move(doof, enemyReaper, 300);
        }

        private String skill(Unit unit) {
            return "SKILL" + ACTION_SEPARATOR + (unit.position.x + unit.vx) + ACTION_SEPARATOR + (unit.position.y + unit.vy);
        }

        private String move(Unit fromUnit, Unit target, int acc) {
            return (target.position.x + target.vx - fromUnit.vx) + ACTION_SEPARATOR + (target.position.y + target.vy - fromUnit.vy) + ACTION_SEPARATOR + acc;
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

        private Stream<Unit> streamUnitsByType(Class unitClass) {
            return unitList.stream().filter(unitClass::isInstance);
        }

        private Stream<Unit> streamMyUnitsByType(Class unitClass) {
            return streamUnitsByType(unitClass)
                    .filter(unit -> !Unit.isEnemy(unit));
        }

        private Optional<? extends Unit> nearest(Unit unit, Class targetUnitClass) {
            return getUnitWithMinDistance(unit, streamUnitsByType(targetUnitClass));
        }

        private Optional<? extends Unit> getUnitWithMinDistance(Unit unit, Stream<? extends Unit> unitStream) {
            return unitStream.min(Comparator.comparingDouble(unit::getDistance));
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
            return streamMyUnitsByType(Reaper.class)
                    .map(Reaper.class::cast)
                    .findFirst()
                    .get();
        }

        public Destroyer getDestroyer() {
            return streamMyUnitsByType(Destroyer.class)
                    .map(Destroyer.class::cast)
                    .findFirst()
                    .get();
        }

        public Doof getDoof() {
            return streamMyUnitsByType(Doof.class)
                    .map(Doof.class::cast)
                    .findFirst()
                    .get();
        }

        private List<Reaper> getEnemysReaper() {
            return streamUnitsByType(Reaper.class)
                    .filter(Reaper::isEnemy)
                    .map(Reaper.class::cast)
                    .collect(Collectors.toList());
        }

        public Reaper nearestEnemyReaper(Unit fromUnit) {
            return getUnitWithMinDistance(fromUnit, getEnemysReaper()
                    .stream())
                    .map(Reaper.class::cast)
                    .orElse(null);
        }

        public Reaper getEnemyReaperWithHigherScore() {
            return getEnemysReaper()
                    .stream()
                    .max(Comparator.comparingInt(reaper -> reaper.player.score))
                    .orElse(null);
        }
    }

    public static abstract class Unit {
        protected Position position;
        protected int radius;
        protected float mass;
        protected int vx;
        protected int vy;
        protected GamePlayer player;

        public Unit(Position position, int radius, float mass, int vx, int vy, GamePlayer player) {
            this.position = position;
            this.radius = radius;
            this.mass = mass;
            this.vx = vx;
            this.vy = vy;
            this.player = player;
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
            return (acc / mass) / getDistance(targetPosition);
        }


        // TODO Buggy
        public int computeAcceleration(Position targetPosition) {
            int accx = (int) Math.round(mass * getDistance(targetPosition) * (targetPosition.x - vx - this.position.x) / (targetPosition.x - this.position.x));
            int accy = (int) Math.round(mass * getDistance(targetPosition) * (targetPosition.y - vy - this.position.y) / (targetPosition.y - this.position.y));
            int result = (int) (0.5 * (accx + accy));
            return Math.abs(result) > 300 ? 300 : Math.abs(result);
        }

        public static boolean isEnemy(Unit unit) {
            return unit.player.enemy;
        }
    }

    public static class Reaper extends Unit {
        public Reaper(Position position, int radius, float mass, int vx, int vy, GamePlayer player) {
            super(position, radius, mass, vx, vy, player);
        }
    }

    public static class Wreck extends Unit {
        public Wreck(Position position, int radius, float mass, int vx, int vy, GamePlayer player) {
            super(position, radius, mass, vx, vy, player);
        }
    }

    public static class Destroyer extends Unit {
        public Destroyer(Position position, int radius, float mass, int vx, int vy, GamePlayer player) {
            super(position, radius, mass, vx, vy, player);
        }

        public boolean canLaunchGrenadeOnEnemyReaper(Reaper enemyReaper, Reaper myReaper) {
            return player.rage > 60 && position.distance(enemyReaper.position) > 1000
                    && position.distance(enemyReaper.position) < 2000
                    && myReaper.position.distance(enemyReaper.position) > 1000;
        }
    }

    public static class Tanker extends Unit {
        public Tanker(Position position, int radius, float mass, int vx, int vy, GamePlayer player) {
            super(position, radius, mass, vx, vy, player);
        }
    }

    public static class Doof extends Unit {
        public Doof(Position position, int radius, float mass, int vx, int vy, GamePlayer player) {
            super(position, radius, mass, vx, vy, player);
        }
    }

    public static class GamePlayer {
        protected boolean enemy;
        protected int score;
        protected int rage;

        public GamePlayer(boolean enemy, int score, int rage) {
            this.enemy = enemy;
            this.score = score;
            this.rage = rage;
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
        Map<Integer, Integer> scoreMap = new HashMap<>();
        Map<Integer, Integer> rageMap = new HashMap<>();

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
            scoreMap.put(0, myScore);
            scoreMap.put(1, enemyScore1);
            scoreMap.put(2, enemyScore2);
            rageMap.put(0, myRage);
            rageMap.put(1, enemyRage1);
            rageMap.put(2, enemyRage2);
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
                if (isReaper(unitType)) {
                    board.add(new Reaper(new Position(x, y), radius, mass, vx, vy, new GamePlayer(isEnemy(player), scoreMap.get(player), rageMap.get(player))));
                } else if (isWreck(unitType)) {
                    board.add(new Wreck(new Position(x, y), radius, mass, vx, vy, new GamePlayer(isEnemy(player), 0, 0)));
                } else if (isTanker(unitType)) {
                    board.add(new Tanker(new Position(x, y), radius, mass, vx, vy, new GamePlayer(isEnemy(player), 0, 0)));
                } else if (isDestroyer(unitType)) {
                    board.add(new Destroyer(new Position(x, y), radius, mass, vx, vy, new GamePlayer(isEnemy(player), scoreMap.get(player), rageMap.get(player))));
                } else if (isDoof(unitType)) {
                    board.add(new Doof(new Position(x, y), radius, mass, vx, vy, new GamePlayer(isEnemy(player), scoreMap.get(player), rageMap.get(player))));
                }
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(analyzer.action(board.getReaper()));
            System.out.println(analyzer.action(board.getDestroyer()));
            System.out.println(analyzer.action(board.getDoof()));
        }
    }

    private static boolean isDoof(int unitType) {
        return unitType == 2;
    }

    private static boolean isEnemy(int player) {
        return player != 0;
    }

    private static boolean isDestroyer(int unitType) {
        return unitType == 1;
    }

    private static boolean isTanker(int unitType) {
        return unitType == 3;
    }

    private static boolean isWreck(int unitType) {
        return unitType == 4;
    }

    private static boolean isReaper(int unitType) {
        return unitType == 0;
    }


}