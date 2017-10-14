package easy.chucknorris;

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
        String MESSAGE = in.nextLine();
        System.err.println(MESSAGE);
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        String bin = "";
        for (int i = 0 ; i < MESSAGE.length() ; i++) {
            bin += String.format("%07d",Integer.valueOf(Integer.toBinaryString(MESSAGE.charAt(i))));
        }
        System.err.println(bin);
        System.out.println(chuck(bin));
    }

    public static String chuck(String bin) {
        String res = "";

        char last = bin.charAt(0);
        int count = 1;
        for (int i = 1 ; i < bin.length(); i++) {
            char cur = bin.charAt(i);
            if (cur != last) {
                res += convertBloc(last, count);
                res += " ";
                count = 1;
            } else {
                count++;
            }
            last = cur;
        }

        res += convertBloc(last, count);

        return res;
    }

    public static String convertBloc(char last, int count) {
        String res = "";
        res += (last == '1') ? "0 " : "00 ";
        for (int j = 0; j < count; j++) {
            res += "0";
        }
        return res;
    }
}