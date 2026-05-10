package org.example.tests;

import org.example.beans.PointResult;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class PointResultTest {

    @Test
    public void testFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        PointResult result = new PointResult(1.0, 2.0, 3.0, true, now, 10.0);

        assertEquals(Double.valueOf(1.0), result.getX());
        assertEquals(Double.valueOf(2.0), result.getY());
        assertEquals(Double.valueOf(3.0), result.getR());
        assertTrue(result.getHit());
        assertEquals(now, result.getServerTime());
        assertEquals(Double.valueOf(10.0), result.getProcessingTime());
    }

    @Test
    public void testSettersAndGetters() {
        PointResult result = new PointResult();
        LocalDateTime now = LocalDateTime.now();

        result.setX(5.0);
        result.setY(6.0);
        result.setR(7.0);
        result.setHit(false);
        result.setServerTime(now);
        result.setProcessingTime(100.0);

        assertEquals(Double.valueOf(5.0), result.getX());
        assertEquals(Double.valueOf(6.0), result.getY());
        assertEquals(Double.valueOf(7.0), result.getR());
        assertFalse(result.getHit());
        assertEquals(now, result.getServerTime());
        assertEquals(Double.valueOf(100.0), result.getProcessingTime());
    }
}
