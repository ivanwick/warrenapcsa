
public class StringCompare {

    static int myStringCompareTo(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());

        for (int i = 0; i < minLength; i++) {
            int charsub = s1.charAt(i) - s2.charAt(i);

            if (charsub != 0) {
                return charsub;
            }
        }

        return s1.length() - s2.length();
    }

    public static void main(String[] args) {
        String[] testStrings = {"Hello", "what's up", "hi", "y0", "what's good b", "hey", "HOWDY"};

        for (int i = 0; i < testStrings.length; i++) {
            for (int j = 0; j < testStrings.length; j++) {
                int myComparison = myStringCompareTo(testStrings[i], testStrings[j]);
                int standardComparison = testStrings[i].compareTo(testStrings[j]);

                System.out.println("\"" + testStrings[i] + "\" vs \"" + testStrings[j] + "\":\t" +
                        myComparison + " " + standardComparison);
            }
        }
    }
}
