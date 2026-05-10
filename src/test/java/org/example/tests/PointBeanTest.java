package org.example.tests;

import org.example.beans.PointBean;
import org.example.beans.PointResult;
import org.example.beans.PointResultRepository;
import org.example.beans.ResultsBean;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PointBeanTest {
    private PointBean pointBean;
    private ResultsBean resultsBean;

    @Before
    public void setUp() {
        pointBean = new PointBean();
        resultsBean = new ResultsBean();

        resultsBean.init();
        resultsBean.setRepository(new PointResultRepository() {
            private final List<PointResult> storage = new ArrayList<>();

            @Override
            public PointResult save(PointResult result) {
                result.setId((long) storage.size() + 1);
                storage.add(result);
                return result;
            }

            @Override
            public List<PointResult> findAll() {
                return new ArrayList<>(storage);
            }

            @Override
            public void deleteAll() {
                storage.clear();
            }
        });

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
        pointBean.setX(0.3);
        pointBean.setY(-0.3);
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
