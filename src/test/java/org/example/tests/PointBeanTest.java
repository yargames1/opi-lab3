package org.example.tests;

import org.example.beans.PointBean;
import org.example.beans.ResultsBean;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PointBeanTest {
    private PointBean pointBean;
    private ResultsBean resultsBean;

    @Before
    public void setUp() {
        pointBean = new PointBean();
        resultsBean = new ResultsBean();

        resultsBean.getResults().clear();
        pointBean.setResultsBean(resultsBean);
    }

    @Test
    public void testHitInRectangle() {
        pointBean.setX(0.5);
        pointBean.setY(0.25);
        pointBean.setR(1.0);
        pointBean.submit();
        assertTrue("Точка должна быть в прямоугольнике", resultsBean.getResults().get(0).getHit());
    }

    @Test
    public void testHitInCircle() {
        pointBean.setX(0.4);
        pointBean.setY(-0.4);
        pointBean.setR(1.0);
        pointBean.submit();
        assertTrue("Точка должна быть в четверти круга", resultsBean.getResults().get(0).getHit());
    }

    @Test
    public void testMiss() {
        pointBean.setX(2.0);
        pointBean.setY(2.0);
        pointBean.setR(1.0);
        pointBean.submit();
        assertFalse("Точка должна быть вне области", resultsBean.getResults().get(0).getHit());
    }

}
