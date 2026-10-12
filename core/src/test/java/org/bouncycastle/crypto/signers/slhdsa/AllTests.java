package org.bouncycastle.crypto.signers.slhdsa;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests
    extends TestCase
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite("SLH-DSA internal tests");
        suite.addTestSuite(ForsTest.class);
        return suite;
    }
}
