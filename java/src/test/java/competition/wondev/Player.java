package competition.wondev;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {

    public static enum CardinalPoint {
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
            }
            return null;
        }

        public float getScoreForAction(Case startCase, String actionType, CardinalPoint dir1, CardinalPoint dir2, int depth) {
            float result = 1;
            Case nextCase = getCaseInDir(startCase, dir1);
            result *= (nextCase.height == 0 ? 1 : nextCase.height);
            Case nextBuild = getCaseInDir(nextCase, dir2);
            result *= (nextBuild.height == 0 ? 2 : nextBuild.height < 3 ? nextBuild.height : 0);
            int countNeighbors = 0;
            for (CardinalPoint cp : CardinalPoint.values()) {
                Case caseAtCp = getCaseInDir(nextCase, cp);
                if (caseAtCp != null) {
                    countNeighbors++;
                    if (depth <2) {
                        depth++;
                        for (CardinalPoint cpb : CardinalPoint.values()) {
                            result += getScoreForAction(caseAtCp, "MOVE&BUILD", cp, cpb, depth);
                        }
                    }
                }
            }
            result *= countNeighbors;
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
                players[i] = grid.getCaseAt(unitX, unitY).markPlayerCase();
            }

            Case[] opponents = new Case[unitsPerPlayer];
            for (int i = 0; i < unitsPerPlayer; i++) {
                int otherY = in.nextInt();
                int otherX = in.nextInt();
                opponents[i] = grid.getCaseAt(otherX, otherY).markOpponentCase();
            }

            System.err.println(grid.toString());

            String actions[] = new String[unitsPerPlayer];
            int legalActions = in.nextInt();
            for (int i = 0; i < legalActions; i++) {
                String atype = in.next();
                int index = in.nextInt();
                String dir1 = in.next();
                String dir2 = in.next();
                String action = atype + " " + index + " " + dir1 + " " + dir2;
                float maxScore = 0;
                float score = grid.getScoreForAction(players[index], atype, CardinalPoint.valueOf(dir1), CardinalPoint.valueOf(dir2), 2);
                System.err.println(action + " : " + score);
                if (actions[index] == null || score > maxScore) {
                    maxScore = score;
                    actions[index] = action;
                }
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            for (int i = 0; i < unitsPerPlayer; i++) {
                System.out.println(actions[i]);
            }
        }
    }
}