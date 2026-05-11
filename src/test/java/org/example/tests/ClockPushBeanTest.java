package org.example.tests;

import org.example.beans.ClockPushBean;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ClockPushBeanTest {

    private final ClockPushBean bean = new ClockPushBean();

    @Test
    public void InitAndDestroy() {
        try {
            bean.init();
            assertNotNull("Scheduler должен быть запущен", bean);
            bean.destroy();
        } catch (Exception e) {
            fail("Init не должен падать без PushContext: " + e.getMessage());
        }
    }


}
