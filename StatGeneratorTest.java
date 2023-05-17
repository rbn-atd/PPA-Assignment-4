

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.runners.Parameterized.*;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

/**
 * The test class StatGeneratorTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StatGeneratorTest
{
    private StatGenerator statGene1;

    /**
     * Default constructor for test class StatGeneratorTest
     */
    public StatGeneratorTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        statGene1 = new StatGenerator();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    /** 
     * Test to determine statsitic arrays are correct initialised
     * and does not produce a null result.
     */
    @Test
    public void testStatsIni()
    {
        assertNotNull(statGene1.generateStats());
        assertEquals(8, statGene1.generateStats().size());
    }
    
    
    /** 
     * Test to determine method delivers correct result for 
     * generating reviews per property.
     */
    @Test
    public void testReviewsPerProperty()
    {
        assertEquals("12", statGene1.reviewsPerProperty().getStatValue());
    }
    
    /** 
     * Test to determine method delivers correct result for 
     * generating total available properties.
     */
    @Test
    public void testTotalAvailable()
    {
        assertEquals("41,941", statGene1.totalAvailable().getStatValue());
    }

    /** 
     * Test to determine method delivers correct result for 
     * generating the numberof properties that are not houses
     * or shared rooms.
     */
    @Test
    public void testNotPrivateRoom()
    {
        assertEquals("27,885", statGene1.numNotPrivateRooms().getStatValue());
    }
    
    /** 
     * Test to determine method delivers correct result for 
     * generating the most expensive borought to rent in.
     */
    @Test
    public void testHighestPriceBorough()
    {
        assertEquals("Richmond upon Thames", statGene1.highestPriceBorough().getStatValue());
    }
    
    /** 
     * Test to determine method delivers correct result for 
     * generating the cheapest room to rent.
     */
    @Test
    public void testCheapestRoom()
    {
       assertEquals("£8", statGene1.cheapestPrice().getStatValue()); 
    }
    
    /** 
     * Test to determine method delivers correct result for 
     * generating the number of properties in West London.
     */
    @Test
    public void testLocationWestLondon()
    {
        assertEquals("23,529", statGene1.westLondonProperties().getStatValue());
    }
    
    /** 
     * Test to determine method delivers correct result for 
     * generating the number of properties with a landmark
     * within 0.25 miles.
     */
    @Test
    public void testLandmarkDistance()
    {
        assertEquals("38,980", statGene1.withinLandmarkDistance().getStatValue());
    }
    
    /** 
     * Test to determine method delivers correct result for 
     * generating distance between two properties.
     */
    @Test
    public void testDistance()
    {
        assertEquals(37.73, statGene1.calcDistance(51.4098659, -0.2918746, 51.4612735, -0.1381561), 0.1);
    }
    
    /** 
     * Test to determine method generate the borough hashMap correctly,
     * is not null and the size of the array is equal to the number
     * of boroughs in London.
     */
    @Test
    public void testBoroughGenerator() 
    {
        statGene1.generateBoroughs();
        assertNotNull(statGene1.generateBoroughs());
        assertEquals(33, statGene1.generateBoroughs().size());
    }
    
    

}