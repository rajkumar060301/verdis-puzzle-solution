/*
 * Author Name: Raj Kumar
 * IDE: IntelliJ IDEA Ultimate Edition
 * JDK: 18 version
 * Date: 15-Jul-22
 */
/*
    he input is the number of bottles, number of prisoners. The program should output the bottles assigned to each prisoner as
    a .csv file. Given the sequence of dead and alive prisoners, it should also output the bottle number which is poisoned.
 */
import java.util.Arrays;

import java.util.*;
import java.util.stream.*;
import java.util.Scanner;

public class PrisonersWinePosition {

    private int NUM_PRISONERS = 10;
    private int NUM_WINES = 1000;
    private String leadingZeros = new String(new char[NUM_PRISONERS]).replace("\0", "0");
    private String alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    private char[] prisonerNames = alphabet.substring(0, NUM_PRISONERS).toCharArray();
    public HashMap<Character, ArrayList<Integer>> prisoners;
    public ArrayList<String> wines;

    public static void main(String []args) {
        // Set up riddle
        PrisonersWineRiddle riddle = new PrisonersWineRiddle();
        riddle.assignWines();

        // Choose poisoned bottle and kill off respective prisoners
        int secretPoison = (int)(Math.random() * (999));
        String deadPrisoners = riddle.killPrisoners(secretPoison);
        int verifyPoison = Integer.parseInt(deadPrisoners, 2);
        if (secretPoison != verifyPoison) {
            System.out.println("Problem was not solved correctly");
        }
        ArrayList<Character> deadPrisonersNames = riddle.getDeadPrisoners(deadPrisoners);
        printDeadPrisoners(deadPrisonersNames);

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Which wine was poisoned? (integer)");
        int guess = keyboard.nextInt();

        if (guess == secretPoison) {
            System.out.println("That's correct!");
        } else {
            System.out.println("Incorrect! Bottle number " + secretPoison + " was the poisoned one!");
        }
    }

    public PrisonersWineRiddle() {
        setUpPrisoners();
        setUpWines();
    }

    public void setUpPrisoners() {
        prisoners = new HashMap<Character, ArrayList<Integer>>();
        for (char name : prisonerNames) {
            prisoners.put(name, new ArrayList());
        }
    }

    public void setUpWines() {
        wines = new ArrayList<String>();
        int[] wineLabels = IntStream.iterate(1, n -> n + 1).limit(NUM_WINES).toArray();
        for (int label : wineLabels) {
            wines.add(getBinaryString(label));
        }
    }

    public String getBinaryString(int integer) {
        String binaryString = Integer.toBinaryString(integer);
        String leadingZeroString = leadingZeros + binaryString;
        return leadingZeroString.substring(leadingZeroString.length() - NUM_PRISONERS);
    }

    public void assignWines() {
        for (int i = 0; i < NUM_WINES; i++) {
            String wine = wines.get(i);
            for (int j = 0; j < NUM_PRISONERS; j++) {
                char binaryValue = wine.charAt(j);
                if (binaryValue == '1') {
                    addWineToPrisoner(i + 1, j);
                }
            }
        }
    }

    public void addWineToPrisoner(int wineNumber, int prisonerNumber) {
        char prisonerName = prisonerNames[prisonerNumber];
        ArrayList<Integer> prisonerWines = prisoners.get(prisonerName);
        prisonerWines.add(wineNumber);
        prisoners.put(prisonerName, prisonerWines);
    }

    public String killPrisoners(int poisonedBottle) {
        ArrayList<Character> deadPrisoners = new ArrayList<Character>();
        for (int i = 0; i < NUM_PRISONERS; i++) {
            ArrayList<Integer> winesList = prisoners.get(prisonerNames[i]);
            if (winesList.contains(poisonedBottle)) {
                deadPrisoners.add('1');
            } else {
                deadPrisoners.add('0');
            }
        }
        return deadPrisoners.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    public ArrayList<Character> getDeadPrisoners(String binaryOutcome) {
        ArrayList<Character> deadPrisoners = new ArrayList<Character>();
        for (int i = 0; i < NUM_PRISONERS; i++) {
            if (binaryOutcome.charAt(i) == '1') {
                deadPrisoners.add(prisonerNames[i]);
            }
        }
        return deadPrisoners;
    }

    public static void printDeadPrisoners(ArrayList<Character> deadPrisoners) {
        String string = "Prisoners ";
        for (int i = 0; i < deadPrisoners.size(); i++) {
            if (i != deadPrisoners.size() - 1) {
                string += deadPrisoners.get(i) + ", ";
            } else {
                string += "and " + deadPrisoners.get(i);
            }
        }
        string += " died";
        System.out.println(string);
    }
}
