package ru.kubsu.geocoder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestUtilTest {

    @Test
    void unitTest() {
        System.out.println("TEST 2");
        assertEquals(3, TestUtil.sum(1, 2));
        assertEquals(0, TestUtil.sum(0, 0));
        assertEquals(0, TestUtil.sum(1, -1));
        assertEquals(-2, TestUtil.sum(4, -6));
        assertEquals(-400, TestUtil.sum(-100, -300));
    }

}