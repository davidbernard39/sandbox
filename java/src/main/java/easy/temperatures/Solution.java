package easy.temperatures;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of temperatures to analyse
        List<Integer> values = new ArrayList<Integer>();
        int result = 5527;
        for (int i = 0; i < n; i++) {
            int cur = in.nextInt();
            if (Math.abs(cur) == Math.abs(result) && (cur != result)) {
                result = Math.abs(cur);
            } else if (Math.abs(cur) < Math.abs(result)) {
                result = cur;
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(result < 5527 ? result : 0);
    }
}