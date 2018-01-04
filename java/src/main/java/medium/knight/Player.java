package medium.knight;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static class Board {

        static final String LINE_SEPARATOR = "\n";
        static final String EMPTY_POSITION = ".";
        static final String KNIGHT_POSITION = "K";
        static final String EMPTY_STRING = "";

        static final String UP = "U";
        static final String DOWN = "D";
        static final String LEFT = "L";
        static final String RIGHT = "R";

        private int width;
        private int height;
        private int knightLine;
        private int knightColumn;

        private int maxLine;
        private int minLine;
        private int maxColumn;
        private int minColumn;

        public Board(int width, int height) {
            super();
            this.width = width;
            this.height = height;
            this.minColumn = 0;
            this.maxColumn = width;
            this.minLine = 0;
            this.maxLine = height;
        }

        public void knightAt(int knightLine, int knightColumn) {
            this.knightLine = knightLine;
            this.knightColumn = knightColumn;
        }


        public String move(String direction) {
            int line = moveLine(direction);
            int column = moveColumn(direction);
            knightAt(line, column);
            return column + " " + line;
        }

        private int moveColumn(String direction) {
            int column = knightColumn;
            if (direction.contains(LEFT)) {
                maxColumn = knightColumn;
                column = minColumn + ((knightColumn - minColumn) / 2);
            } else if (direction.contains(RIGHT)) {
                minColumn = knightColumn;
                column = knightColumn + ((maxColumn - knightColumn) / 2);
            }
            return column;
        }

        private int moveLine(String direction) {
            int line = knightLine;
            if (direction.contains(UP)) {
                maxLine = knightLine;
                line = minLine + ((knightLine - minLine) / 2);
            } else if (direction.contains(DOWN)) {
                minLine = knightLine;
                line = knightLine + ((maxLine - knightLine) / 2);
            }
            return line;
        }

        @Override
        public String toString() {
            String result = EMPTY_STRING;
            for (int line = 0; line < height; line++) {
                for (int column = 0; column < width; column++) {
                    if (isKnightAt(line, column)) {
                        result += KNIGHT_POSITION;
                    } else {
                        result += EMPTY_POSITION;
                    }
                }
                result += line < height - 1 ? LINE_SEPARATOR : EMPTY_STRING;
            }
            return result;
        }

        private boolean isKnightAt(int line, int column) {
            return line == knightLine && column == knightColumn;
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X0 = in.nextInt();
        int Y0 = in.nextInt();

        Board board = new Board(W, H);
        board.knightAt(Y0, X0);

        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // the location of the next window Batman should jump to.
            System.out.println(board.move(bombDir));
        }
    }
}
