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
        public static final String EMPTY_STRING = "";

        private int width;
        private int height;
        private int knightLine;
        private int knightColumn;


        public Board(int width, int height) {
            super();
            this.width = width;
            this.height = height;
        }

        public void knightAt(int knightLine, int knightColumn) {
            this.knightLine = knightLine;
            this.knightColumn = knightColumn;
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

        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // the location of the next window Batman should jump to.
            System.out.println("0 0");
        }
    }
}
