package ej.ch3;

import java.util.Arrays;

public class HashCode {

    private boolean myBoolean = false;
    private int myShort = 20;
    private long myLong = 30;
    private float myFloat = 10.3f;
    private double myDouble = 12.2;
    private String myString = new String();
    private byte[] myByteArray = new byte[10];
    private int[] myIntArray = new int[10];

    @Override
    public int hashCode() {
        int result = 17; // Start off with a constant
        result = 31 * result + (myBoolean ? 1 : 0);
        result = 31 * result + (int) myShort; // Apply this to byte, char, short
                                              // or int
        result = 31 * result + (int) (myLong ^ (myLong >>> 32));
        result = 31 * result + Float.floatToIntBits(myFloat);
        result = 31
                * result
                + (int) (Double.doubleToLongBits(myDouble) ^ (Double
                        .doubleToLongBits(myDouble) >>> 32)); // Similar to long
        result = 31 * result + myString.hashCode(); // Invoke the object's
                                                    // hashCode()
        for (byte i : myByteArray) {
            result = 31 * result + (int) i; // Iterate through each significant
                                            // element
        }
        result = 31 * result + Arrays.hashCode(myIntArray); // Or use
                                                            // Arrays.hashCode()
        return result;
    }

}
