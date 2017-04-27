package p12message;

public class MessageIntegrity2 {

    public static void main(String args[]) {
        String[] chunks = {
                "11001101110",
                "10000111010",
                "10101011110",
                "10000110111",
                "11001111000"
        };

        for (String c : chunks) {
            int chunkAsInt = Integer.parseInt(c, 2);
            if (isValidChunk(chunkAsInt)) {
                System.out.println("ok");
            } else {
                System.out.println("corrupt");
            }
        }
    }

    static final int P3 = 0b1011;
    static final int P3_SIZE = 4;  // P3 is 4 bits long
    static final int CHUNK_SIZE = 11;  // each chunk is 11 bits long

    public static boolean isValidChunk(int chunk) {
        // shift P3 so it lines up with the leftmost bits of chunk
        int shiftingXor = P3 << (CHUNK_SIZE - P3_SIZE);
        // make a bitmask so we can use bitwise AND to see if the leftmost bit is a one
        int leftmostMask = 1 << (CHUNK_SIZE - 1);

        for (int i = CHUNK_SIZE - P3_SIZE; i >= 0; i--) {
            if ((chunk & leftmostMask) != 0) {
                chunk = chunk ^ shiftingXor;
            }

            shiftingXor = shiftingXor >> 1;
            leftmostMask = leftmostMask >> 1;
        }

        int remainder = chunk & 0b00000000111;  // a bitmask to take only the 3 rightmost bits

        if (remainder == 0) {
            return true;
        } else {
            return false;
        }
    }
}
