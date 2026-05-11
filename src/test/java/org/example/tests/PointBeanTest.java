package org.example.tests;

import org.example.beans.PointBean;
import org.example.beans.PointResult;
import org.example.beans.PointResultRepository;
import org.example.beans.ResultsBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class PointBeanTest {
    private PointBean pointBean;
    private ResultsBean resultsBean;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        pointBean = new PointBean();
        resultsBean = new ResultsBean();

        PointResultRepository repository = Mockito.mock(PointResultRepository.class);

        // Настройка save
        doAnswer(invocation -> {
            PointResult point = invocation.getArgument(0);
            point.setId(1L);
            return point;
        }).when(repository).save(any(PointResult.class));

        Field field = ResultsBean.class.getDeclaredField("repository");
        field.setAccessible(true);
        field.set(resultsBean, repository);

        resultsBean.init();
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
