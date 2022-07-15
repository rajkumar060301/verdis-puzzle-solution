/*
 * Author Name: Raj Kumar
 * IDE: IntelliJ IDEA Ultimate Edition
 * JDK: 18 version
 * Date: 15-Jul-22
 */


/*
Problem-A
 The input of the program is the equation "SEND+MORE=MONEY" and similar equations. Similar to the question, the program should output
 which number is assigned to which letter. Same rules are also applied. The program should also work with similar equations.
 If there is no solution to the equation, the code should output 'No solution'
 */

import java.io.IOException;
import java.util.Scanner;

// This codes solves the puzzle WORD1 + WORD2 = WORD3
// The simpler version (SEND MORE MONEY) will require less code.

public class SendMoreMoney {

    static boolean[] usedLetter;
    static boolean[] usedDigit;
    static int[] assignedDigit;

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        String w1 = sc.next();            // SEND
        String w2 = sc.next();        // MORE
        String w3 = sc.next();            // MONEY

        solve(w1, w2, w3);
    }

    static void solve(String w1, String w2, String w3)    // w1 + w2 = w3
    {
        usedLetter = new boolean[26];    // usedLetter[i] = true iff letter i appears in w1, w2 or w3
        usedDigit = new boolean[26];    // usedDigit[i] = true iff digit i is used by a letter (used in backtracking)
        assignedDigit = new int[26];    // assignedDigiti[i] = digit assigned to letter i (used in backtracking)
        markLetters(w1);
        markLetters(w2);
        markLetters(w3);
        backtrack(0, w1, w2, w3);
        System.out.println("No more solutions :(");
    }

    // mark the letters appeared in w1, w2 and w3 to use them in the search.
    static void markLetters(String w) {
        for (int i = 0; i < w.length(); ++i)
            usedLetter[w.charAt(i) - 'A'] = true;
    }

    static boolean check(String w1, String w2, String w3) {
        if (leadingZero(w1) || leadingZero(w2) || leadingZero(w3))
            return false;
        return value(w1) + value(w2) == value(w3);
    }

    static boolean leadingZero(String w) {
        return assignedDigit[w.charAt(0) - 'A'] == 0;
    }

    // if w = ABCD, then the function returns A * 1000 + B * 100 + C * 10 + D.
    static int value(String w) {
        int val = 0;
        for (int i = 0; i < w.length(); ++i)
            val = val * 10 + assignedDigit[w.charAt(i) - 'A'];
        return val;
    }

    // do the backtracking (brute force)
    static void backtrack(int char_idx, String w1, String w2, String w3) {
        if (char_idx == 26) {
            // finished assigning values for the 26 letters
            if (check(w1, w2, w3)) {
                System.out.println("Found a solution!");
                for (int i = 0; i < 26; ++i)
                    if (usedLetter[i])
                        System.out.printf("[%c = %d]", (char) (i + 'A'), assignedDigit[i]);
                System.out.println("\n------");
            }
            return;
        }

        if (!usedLetter[char_idx]) {
            // skip this letter, it was not used in the input.
            backtrack(char_idx + 1, w1, w2, w3);
            return;
        }
        // try assigning different digits for this letter
        for (int digit = 0; digit < 10; ++digit)
            if (!usedDigit[digit])    // this condition guarantees that no digit is used for more than one letter
            {
                usedDigit[digit] = true;
                assignedDigit[char_idx] = digit;
                backtrack(char_idx + 1, w1, w2, w3);
                usedDigit[digit] = false;
            }
    }
}
