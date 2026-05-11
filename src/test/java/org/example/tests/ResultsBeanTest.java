package org.example.tests;

import org.example.beans.PointResult;
import org.example.beans.PointResultRepository;
import org.example.beans.ResultsBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ResultsBeanTest {

    private ResultsBean bean;

    private PointResultRepository repository;

    @Before
    public void setUp() throws Exception {
        bean = new ResultsBean();
        repository = Mockito.mock(PointResultRepository.class);

        Field field = ResultsBean.class.getDeclaredField("repository");
        field.setAccessible(true);
        field.set(bean, repository);
    }

    @Test
    public void testInit() {
        List<PointResult> list = new ArrayList<>();

        list.add(new PointResult());

        Mockito.when(repository.findAll()).thenReturn(list);

        bean.init();

        assertEquals(1, bean.getResults().size());
    }

    @Test
    public void testInitException() {
        Mockito.when(repository.findAll())
                .thenThrow(new RuntimeException());

        bean.init();

        assertNotNull(bean.getResults());
        assertTrue(bean.getResults().isEmpty());
    }

    @Test
    public void testAddResult() {
        PointResult point = new PointResult();

        Mockito.when(repository.save(point))
                .thenReturn(point);

        bean.init();
        bean.addResult(point);

        assertEquals(1, bean.getResults().size());
    }

    @Test
    public void testClearResults() {
        bean.init();
        bean.clearResults();

        Mockito.verify(repository).deleteAll();
    }

}