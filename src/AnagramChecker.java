import java.io.*;
import java.util.Arrays;

public class AnagramChecker {
    public static void Notmain(String[] args) throws FileNotFoundException,IOException{
        //String to file
        String filePath = "Prob04.in.txt";
        //Initialize BufferedReader
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        //Grab test cases
        String testCasesStr = br.readLine();
        //Parse into int
        int testCases = Integer.parseInt(testCasesStr);
        //Loop through according to test cases
        for(int i = 0; i < testCases; i++){
            //Read line
            String line = br.readLine();
            //Make an array of strings, which is filled with the splits of the line we just read using the delimeter "|"
            String[] words = line.split("\\|");
            //Grab first and second strings to make it easier
            String firstWord = words[0];
            String secondWord = words[1];
            //Initialize anagram string
            String isAnAnagram = "";
            if(isAnagram(firstWord, secondWord)){
                isAnAnagram = "ANAGRAM";
            }else{
                isAnAnagram = "NOT AN ANAGRAM";
            }
            System.out.println(line + " = " + isAnAnagram);
        }
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("DOGS", "CATS"));
        System.out.println(isAnagram("LISTEN", "SILENT"));
        System.out.println(isAnagram("LOOPED", "POODLE"));
        System.out.println(isAnagram2("DOGS", "CATS"));
        System.out.println(isAnagram2("LISTEN", "SILENT"));
        System.out.println(isAnagram2("LOOPED", "POODLE"));
        System.out.println(isAnagram3("DOGS", "CATS"));
        System.out.println(isAnagram3("LISTEN", "SILENT"));
        System.out.println(isAnagram3("LOOPED", "POODLE"));
    }

    public static boolean isAnagram2(String first, String second) {

        if (first.length() != second.length()) {
            return false;
        }

        int wordLength = first.length(); // could have been either length, we know they are equal by now

        boolean[] checks = new boolean[wordLength]; // defaults to all false.

        // Try to find each letter of the first string in the second string.
        // But only use the s2 letter once.
        for (int i = 0; i < first.length(); i++) {
            char currentChar = first.charAt(i);

            for (int j = 0; j < second.length(); j++) {
                if (checks[j] == false) {
                    if (currentChar == second.charAt(j)) {
                        checks[j] = true;
                    }
                }
            }
        }

        // Were all letters used?
        for (boolean used : checks) {
            if (!used) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAnagram3(String s1, String s2) {

        if (s1.length() != s2.length()) {
            return false;
        }

        int wordLength = s1.length(); // could have been either length, we know they are equal by now

        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();

        Arrays.sort(chars1);
        Arrays.sort(chars2);

        String sorteds1 = new String(chars1);
        String sorteds2 = new String(chars2);

        return (sorteds1.equals(sorteds2));
    }

    public static boolean isAnagram(String first, String second){
        //Default return value to true, since we are only using this method in hopes of getting false
        boolean returnValue = true;
        if(first.length() != second.length()){
            return false;
        }
        //Construct new blacklist index array
        int[] blacklistedIndexes = new int[first.length()]; //Loop through this array, using i in this instance as an index for the strings
        //Fill index array with -1
        for(int  i = 0; i < blacklistedIndexes.length; i++){
            blacklistedIndexes[i] = -1;
        }
        boolean[] checks = new boolean[first.length()]; //Fill this array up starting with the first letter of the the first string
        //Since the letters can be anywhere, we will use a blacklisted indexes array to speicify if that index has been checked and matched to the character in the first string
        for(int i = 0; i < first.length(); i++){
            //Easy reference to current character
            char currentChar = first.charAt(i);
            //Loop through second array
            for(int j = 0; j < second.length(); j++){
                if(contains(blacklistedIndexes, j) == false){
                    //System.out.println("(OUTSIDE IF)j value is " + j);
                    if(currentChar == second.charAt(j)){
                        //System.out.println(String.format("(INSIDE IF)j value is %d", j));
                        checks[j] = true;
                        blacklistedIndexes[j] = j;
                    }
                }
            }
        }
        //Loop through checks array
        for(int i = 0; i < checks.length; i++){
            if(checks[i] != true){
                returnValue = false;
                break;
            }
        }
        return returnValue;
    }

    public static boolean contains(final int[] array, final int v){
        boolean outputValue = false;

        for(int i = 0; i < array.length; i++){
            if(array[i] == v){
                outputValue = true;
                break;
            }
        }
        return outputValue;
    }

    public static void printArray(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.println("Length: " + array.length);
            System.out.print(array[i] + ", ");
        }
    }
}
