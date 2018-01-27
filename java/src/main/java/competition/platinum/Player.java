package competition.platinum;

import java.util.*;
import java.util.stream.Collectors;


class Player {

    static class Game {

        private int playersNumber;
        private int myId;
        private int myPlatinum;
        private World world;

        public Game() {
            this.world = new World();
        }

        public void setPlayersNumber(int playersNumber) {
            this.playersNumber = playersNumber;
        }

        public void setMyId(int myId) {
            this.myId = myId;
        }

        public void setMyPlatinum(int myPlatinum) {
            this.myPlatinum = myPlatinum;
        }

        public void addZone(Zone zone) {
            this.world.addZone(zone);
        }

        public Zone getZone(int zoneId) {
            return this.world.getZone(zoneId);
        }

        public List<Zone> firstEmptyZone(int numberOfZones) {
            return this.world.firstEmptyZone(numberOfZones);
        }

        public Optional<Zone> highestValueEmptyZone() {
            return this.world.highestValueEmptyZone();
        }
    }

    static class World {
        private Map<Integer, Zone> zones = new HashMap<>();

        public void addZone(Zone zone) {
            this.zones.put(zone.zoneId, zone);
        }

        public Zone getZone(int zoneId) {
            return zones.get(zoneId);
        }

        public List<Zone> firstEmptyZone(int numberOfZones) {
            return zones.entrySet().stream()
                    .filter(entry -> entry.getValue().isEmpty())
                    .map(Map.Entry::getValue)
                    .limit(numberOfZones)
                    .collect(Collectors.toList());
        }

        public Optional<Zone> highestValueEmptyZone() {
            return zones.entrySet().stream()
                    .filter(entry -> entry.getValue().isEmpty())
                    .map(Map.Entry::getValue)
                    .sorted(Comparator.comparingInt(Zone::getPlatinum).reversed())
                    .findFirst();
        }
    }

    static class Zone {

        private final int zoneId;
        private final int platinumSource;
        private List<Zone> neighbors = new ArrayList<>();
        private int owner = -1;
        private int[] playerPodsNumber = new int[4];

        public Zone(int zoneId, int platinumSource) {
            this.zoneId = zoneId;
            this.platinumSource = platinumSource;
        }

        public void addNeighbor(Zone zone) {
            this.neighbors.add(zone);
            if (!zone.neighbors.contains(this))
                zone.addNeighbor(this);
        }

        public List<Zone> neighbors() {
            return neighbors;
        }

        public void setOwner(int owner) {
            this.owner = owner;
        }

        public void setPlayerPodsNumber(int playerId, int podsNumber) {
            this.playerPodsNumber[playerId] = podsNumber;
        }

        public boolean isEmpty() {
            return owner == -1;
        }

        public int getPlatinum() {
            return platinumSource;
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Game game = new Game();
        game.setPlayersNumber(in.nextInt()); // the amount of players (2 to 4)
        game.setMyId(in.nextInt()); // my player ID (0, 1, 2 or 3)

        int zoneCount = in.nextInt(); // the amount of zones on the map
        int linkCount = in.nextInt(); // the amount of links between all zones
        for (int i = 0; i < zoneCount; i++) {
            int zoneId = in.nextInt(); // this zone's ID (between 0 and zoneCount-1)
            int platinumSource = in.nextInt(); // the amount of Platinum this zone can provide per game turn
            Zone zone = new Zone(zoneId, platinumSource);
            game.addZone(zone);
        }
        for (int i = 0; i < linkCount; i++) {
            Zone zone1 = game.getZone(in.nextInt());
            Zone zone2 = game.getZone(in.nextInt());
            zone1.addNeighbor(zone2);
        }

        // game loop
        while (true) {
            game.setMyPlatinum(in.nextInt()); // my available Platinum
            for (int i = 0; i < zoneCount; i++) {
                Zone zone = game.getZone(in.nextInt()); // this zone's ID

                zone.setOwner(in.nextInt()); // the player who owns this zone (-1 otherwise)
                zone.setPlayerPodsNumber(0, in.nextInt()); // player 0's PODs on this zone
                zone.setPlayerPodsNumber(1, in.nextInt()); // player 1's PODs on this zone
                zone.setPlayerPodsNumber(2, in.nextInt()); // player 2's PODs on this zone (always 0 for a two player game)
                zone.setPlayerPodsNumber(3, in.nextInt()); // player 3's PODs on this zone (always 0 for a two or three player game)
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            MoveStrategy moveStrategy = new MoveWaitStrategy(game);
            BuyingStrategy buyingStrategy = new BuyFirstEmptyZoneStrategy(game);

            // first line for movement commands, second line for POD purchase (see the protocol in the statement for details)
            System.out.println(moveStrategy.move());
            System.out.println(buyingStrategy.buy());
        }
    }

    static abstract class GameStrategy {
        protected final Game game;

        GameStrategy(Game game) {
            this.game = game;
        }
    }

    interface BuyingStrategy {
        String buy();
    }

    interface MoveStrategy {
        String move();
    }

    static class BuyFirstEmptyZoneStrategy extends GameStrategy implements BuyingStrategy {
        public BuyFirstEmptyZoneStrategy(Game game) {
            super(game);
        }

        public String buy() {
            List<Zone> firstEmptyZone = game.firstEmptyZone(game.myPlatinum / 20);
            if (!firstEmptyZone.isEmpty()) {
                StringJoiner sb = new StringJoiner(" ");
                for (Zone zone : firstEmptyZone) {
                    sb.add("1").add(String.valueOf(zone.zoneId));
                }
                return sb.toString();
            } else
                return "WAIT";
        }
    }

    static class BuyHighValueEmptyZoneStrategy extends GameStrategy implements BuyingStrategy {
        BuyHighValueEmptyZoneStrategy(Game game) {
            super(game);
        }

        public String buy() {
            Optional<Zone> highestValueEmptyZone = game.highestValueEmptyZone();
            if (highestValueEmptyZone.isPresent()) {
                return "1 " + game.highestValueEmptyZone().get().zoneId;
            }
            return "WAIT";
        }
    }

    static class MoveWaitStrategy extends GameStrategy implements MoveStrategy {
        public MoveWaitStrategy(Game game) {
            super(game);
        }

        public String move() {
            return "WAIT";
        }
    }
}
