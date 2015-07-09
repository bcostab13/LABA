package example.laba.laba.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import example.laba.laba.main;

/**
 * Created by Brenda on 08/07/2015.
 */
public class mainTest extends ActivityInstrumentationTestCase2<main> {

    public mainTest() {
        super(main.class);
    }


    @MediumTest
    public void test_esto_siempre_pasa(){
        assertTrue(5>1);
    }

    @MediumTest
    public void test_esto_nunca_pasa(){
        assertTrue(5<1);
        fail("nil");
    }
}
