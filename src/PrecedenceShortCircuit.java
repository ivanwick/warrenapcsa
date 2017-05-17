public class PrecedenceShortCircuit {

    static int A = 1;
    static int B = 2;
    static int C = 3;

    public static int evalA(String msg) {
        System.out.println("evalA(" + msg + ")");
        return A;
    }

    public static int evalB(String msg) {
        System.out.println("evalB(" + msg + ")");
        return B;
    }

    public static int evalC(String msg) {
        System.out.println("evalC(" + msg + ")");
        return C;
    }

    public static void main(String[] args) {

        // a < c || a < b && !(a == c)
        // 1   2    3   4      5    6
        if (evalA("1") < evalC("2") || evalA("3") < evalB("4") && !(evalA("5") == evalC("6"))) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        // a < b && !(a == c) || a < c
        // 1   2      3    4     5   6
        if (evalA("1") < evalB("2") && !(evalA("3") == evalC("4")) || evalA("5") < evalC("6") ) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        // false && !(a == c)
        //            3    4
        if (false && !(evalA("3") == evalC("4")) ) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
