package competition.wondev;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {

    public enum CardinalPoint {
        N, NE, E, SE, S, SW, W, NW
    }

    public static class Case {
        int x;
        int y;
        int height;
        boolean opponent;
        boolean player;

        public Case(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }

        public Case markOpponentCase() {
            this.opponent = true;
            return this;
        }

        public Case markPlayerCase() {
            this.player = true;
            return this;
        }
    }

    public static class Grid {
        Case[][] grid;
        int size;

        public Grid(int size) {
            this.grid = new Case[size][size];
            this.size = size;
        }

        public Case setCaseAt(int x, int y, int length) {
            grid[x][y] = new Case(x, y, length);
            return grid[x][y];
        }

        public Case getCaseAt(int x, int y) {
            return this.grid[x][y];
        }

        public int getNeighborHigherLevel(Case startCase) {
            int max = 0;
            for (CardinalPoint dir : CardinalPoint.values()) {
                Case neighbor = getCaseInDir(startCase, dir);
                if (neighbor != null && neighbor.height > max) {
                    max = neighbor.height;
                }
            }
            return max;
        }

        public Case getCaseInDir(Case startCase, CardinalPoint dir) {
            switch (dir) {
                case N:
                    return startCase.x > 0 ? grid[startCase.x - 1][startCase.y] : null;
                case NE:
                    return startCase.x > 0 && startCase.y < size - 1 ? grid[startCase.x - 1][startCase.y + 1] : null;
                case E:
                    return startCase.y < size - 1 ? grid[startCase.x][startCase.y + 1] : null;
                case SE:
                    return startCase.x < size - 1 && startCase.y < size - 1 ? grid[startCase.x + 1][startCase.y + 1] : null;
                case S:
                    return startCase.x < size - 1 ? grid[startCase.x + 1][startCase.y] : null;
                case SW:
                    return startCase.x < size - 1 && startCase.y > 0 ? grid[startCase.x + 1][startCase.y - 1] : null;
                case W:
                    return startCase.y > 0 ? grid[startCase.x][startCase.y - 1] : null;
                case NW:
                    return startCase.x > 0 && startCase.y > 0 ? grid[startCase.x - 1][startCase.y - 1] : null;
                default:
                    return null;
            }
        }

        public float getMoveScore(Case startCase, CardinalPoint dir) {
            Case destCase = getCaseInDir(startCase, dir);
            if (destCase.opponent || destCase.player || destCase.height > startCase.height+1) {
                return 0f;
            }
            return 1f + destCase.height;
        }

        public float getBuildScore(Case startCase, CardinalPoint dir) {
            Case destCase = getCaseInDir(startCase, dir);
            if (destCase.opponent || destCase.player || destCase.height > startCase.height+1) {
                return 0f;
            } else {
                int neighborHigherLevel = getNeighborHigherLevel(destCase);
                if (neighborHigherLevel > 1) {
                    return neighborHigherLevel * 1.0f;
                }
            }
            return 1f;
        }

        public float getScoreForAction(Case startCase, String actionType, CardinalPoint dir1, CardinalPoint dir2) {
            float result = 50f;
            result *= getMoveScore(startCase, dir1);
            result *= getBuildScore(getCaseInDir(startCase, dir1), dir2);
            return result;
        }

        public String toString() {
            StringJoiner join = new StringJoiner("");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (grid[i][j].opponent) {
                        join.add("O");
                    } else if (grid[i][j].player) {
                        join.add("P");
                    } else if (grid[i][j].height == -1) {
                        join.add(".");
                    } else {
                        join.add(String.valueOf(grid[i][j].height));
                    }
                    join.add(" ");
                }
                join.add("\n");
            }
            return join.toString();
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int unitsPerPlayer = in.nextInt();

        Grid grid = new Grid(size);
        // game loop
        while (true) {
            for (int i = 0; i < size; i++) {
                String row = in.next();
                for (int j = 0; j < size; j++) {
                    grid.setCaseAt(i, j, Character.getNumericValue(row.charAt(j)));
                }
            }

            Case[] players = new Case[unitsPerPlayer];
            for (int i = 0; i < unitsPerPlayer; i++) {
                int unitY = in.nextInt();
                int unitX = in.nextInt(); // inversion par rapport au jeu x,y en haut à gauche
                // FUNC
                players[i] = grid.getCaseAt(unitX, unitY).markPlayerCase();
            }

            Case[] opponents = new Case[unitsPerPlayer];
            for (int i = 0; i < unitsPerPlayer; i++) {
                int otherY = in.nextInt();
                int otherX = in.nextInt();
                // FUNC
                opponents[i] = grid.getCaseAt(otherX, otherY).markOpponentCase();
            }

            System.err.println(grid.toString());

            String myaction = null;
            int legalActions = in.nextInt();
            float maxScore = 0; // FUNC
            for (int i = 0; i < legalActions; i++) {
                String atype = in.next();
                int index = in.nextInt();
                String dir1 = in.next();
                String dir2 = in.next();
                String action = atype + " " + index + " " + dir1 + " " + dir2;

                if (atype.startsWith("MOVE")) {
                    float score = grid.getScoreForAction(players[index], atype, CardinalPoint.valueOf(dir1), CardinalPoint.valueOf(dir2));
                    System.err.println(action + " : " + score);
                    if (score > maxScore) {
                        maxScore = score;
                        myaction = action;
                    }
                }

            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            System.out.println(myaction);

        }
    }
}