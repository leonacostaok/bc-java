package org.bouncycastle.crypto.signers.slhdsa;

import java.util.Random;

import junit.framework.TestCase;

public class ForsTest
    extends TestCase
{
    public void testBase2BBitOrder()
    {
        byte[] message = {(byte)0xab, (byte)0xcd, (byte)0xef};
        int[] expected = {42, 60, 55, 47};
        int[] actual = Fors.base2B(message, 6, 4);
        for (int i = 0; i < expected.length; ++i)
        {
            assertEquals(expected[i], actual[i]);
        }
        assertEquals(0, Fors.base2B(new byte[0], 14, 0).length);
    }

    public void testBase2BParameterSets()
    {
        int[][] parameters = {{6, 33}, {12, 14}, {8, 33}, {14, 17}, {9, 35}, {14, 22}};
        Random random = new Random(204);
        for (int p = 0; p < parameters.length; ++p)
        {
            int width = parameters[p][0];
            for (int count = 1; count <= parameters[p][1]; ++count)
            {
                byte[] message = new byte[(width * count + 7) / 8];
                for (int trial = 0; trial < 16; ++trial)
                {
                    random.nextBytes(message);
                    if (trial < 2)
                    {
                        java.util.Arrays.fill(message, (byte)(trial == 0 ? 0 : 0xff));
                    }
                    byte[] original = (byte[])message.clone();
                    int[] actual = Fors.base2B(message, width, count);
                    assertEquals(count, actual.length);
                    for (int i = 0; i < count; ++i)
                    {
                        // Read individual MSB-first bits independently of the byte-buffer implementation.
                        int expected = 0;
                        for (int j = 0; j < width; ++j)
                        {
                            int bit = i * width + j;
                            expected = (expected << 1) | ((message[bit / 8] >>> (7 - bit % 8)) & 1);
                        }
                        assertEquals(expected, actual[i]);
                    }
                    assertTrue(java.util.Arrays.equals(original, message));
                }
            }
        }
    }
}
