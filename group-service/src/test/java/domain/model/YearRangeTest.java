package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


// just a test for unit testing
class YearRangeTest {
    private final YearRange year_range = new YearRange(0, 1999);
    private final YearRange year_range_Integer = new YearRange(Integer.valueOf(0), Integer.valueOf(1999));

    @Test
    void testGetYearFrom() {
        assertEquals(0, year_range.getYear_from());
    }

    @Test
    void testGetYearTo() {
        assertEquals(1999, year_range.getYear_to());
    }

    @Test
    void testSetYearFromTo() {
        YearRange year_range_tmp = new YearRange(0, 1999);
        year_range_tmp.setYear_from(42);
        year_range_tmp.setYear_to(24);
        assertEquals(42, year_range_tmp.getYear_from());
        assertEquals(24, year_range_tmp.getYear_to());
    }

    @Test
    void testToString() {
        assertEquals("YearRange(year_from=0, year_to=1999)", year_range.toString());
    }

    @Test
    void testGetYearFromInteger() {
        assertEquals(0, year_range_Integer.getYear_from());
    }

    @Test
    void testGetYearToInteger() {
        assertEquals(1999, year_range_Integer.getYear_to());
    }

    @Test
    void testSetYearFromToInteger() {
        YearRange year_range_tmp = new YearRange(Integer.valueOf(0), Integer.valueOf(1999));
        year_range_tmp.setYear_from(42);
        year_range_tmp.setYear_to(24);
        assertEquals(42, year_range_tmp.getYear_from());
        assertEquals(24, year_range_tmp.getYear_to());
    }

    @Test
    void testToStringInteger() {
        assertEquals("YearRange(year_from=0, year_to=1999)", year_range_Integer.toString());
    }

}