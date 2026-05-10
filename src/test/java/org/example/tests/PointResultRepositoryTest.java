package org.example.tests;

import org.example.beans.PointResult;
import org.example.beans.PointResultRepository;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PointResultRepositoryTest {
    private PointResultRepository repo;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        repo = new PointResultRepository();
        EntityManager mockEm = mock(EntityManager.class);
        ArrayList<PointResult> storage = new ArrayList<>();

        // Настройка persist
        doAnswer(invocation -> {
            PointResult result = invocation.getArgument(0);
            result.setId((long) storage.size() + 1);
            storage.add(result);
            return null;
        }).when(mockEm).persist(any(PointResult.class));

        // Настройка createQuery
        Query mockQuery = mock(Query.class);
        when(mockEm.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(storage);

        // Настройка executeUpdate для deleteAll
        doAnswer(invocation -> {
            storage.clear();
            return null;
        }).when(mockQuery).executeUpdate();

        // Внедряем EntityManager через рефлексию
        Field emField = PointResultRepository.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(repo, mockEm);
    }


    @Test
    public void testFindAllEmpty() {
        List<PointResult> results = repo.findAll();
        assertEquals(0, results.size());
    }

    @Test
    public void testSaveAndFindAll() {
        PointResult result = new PointResult(1.0, 1.0, 1.0, true,
                java.time.LocalDateTime.now(), 5.0);
        repo.save(result);
        List<PointResult> results = repo.findAll();
        assertEquals(1, results.size());
        assertEquals(result.getX(), results.get(0).getX());
    }

    @Test
    public void testDeleteAll() {
        repo.save(new PointResult(1.0, 1.0, 1.0, true,
                java.time.LocalDateTime.now(), 5.0));
        repo.deleteAll();
        List<PointResult> results = repo.findAll();
        assertEquals(0, results.size());
    }
}