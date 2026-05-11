package org.example.tests;

import org.example.beans.ClockBean;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClockBeanTest {

    @Test
    public void testCurrentTime() {
        ClockBean bean = new ClockBean();
        String time = bean.getCurrentTime();

        assertNotNull(time);
        assertFalse(time.isEmpty());
    }
}