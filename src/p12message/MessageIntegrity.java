package p12message;

public class MessageIntegrity {

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

    public static boolean isValidChunk(int chunk) {
        while (shiftDistance(chunk, P3) >= 0 ) {
            int toXor = P3 << shiftDistance(chunk, P3);
            chunk = chunk ^ toXor;
        }

        int remainder = chunk & 0b00000000111;

        if (remainder == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the difference in bit positions between the leftmost 1-bit in
     * chunk and the leftmost 1-bit in polynomial.
     * You can use this when you need to know how many bit positions to shift
     * the polynomial so that it lines up with the leftmost 1-bit of chunk.
     *
     * Java ints are stored in 32-bits.
     * For example:
     *
     * 0000 0000 0000 0000 0000 0001 0011 0001  chunk
     * |------ 23 zero bits ------|
     *
     * 0000 0000 0000 0000 0000 0000 0000 1011  polynomial
     * |--------- 28 zero bits ---------|
     *
     * will return 28 - 23 = 5
     * such that
     *   polynomial << 5
     * will line it up with the first zero bit in the chunk.
     *
     * 0000 0000 0000 0000 0000 0001 0011 0001  chunk
     * 0000 0000 0000 0000 0000 0001 0110 0000  polynomial << 5 (shifted left 5 bits)
     *                             ↑
     *                 leftmost 1-bits line up
     *
     * @param chunk
     * @param polynomial
     * @return
     */
    public static int shiftDistance(int chunk, int polynomial) {
        return Integer.numberOfLeadingZeros(polynomial) - Integer.numberOfLeadingZeros(chunk);
    }
}
