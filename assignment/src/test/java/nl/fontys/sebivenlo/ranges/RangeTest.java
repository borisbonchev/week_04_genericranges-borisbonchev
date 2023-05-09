package nl.fontys.sebivenlo.ranges;

import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assumptions.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests the Range interface via leaf class IntegerRange.
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class RangeTest {

    // use as              a, b,  c,     d,    e,    f
    Integer[] pointsA = {42, 51, 55, 1023, 1610, 2840};
    RangeTestDataFactory<IntegerRange, Integer, Integer> daf;

    RangeTestDataFactory<IntegerRange, Integer, Integer> helper() {
        if (null == daf) {
            daf = new RangeTestDataFactory<>(pointsA) {

                @Override
                IntegerRange createRange(Integer start, Integer end) {
                    return IntegerRange.of(start, end);
                }

                @Override
                Integer distance(Integer a, Integer b) {
                    return b - a;
                }

            };

        }
        return daf;
    }

    /**
     * Create range using helper.
     *
     * @param rp1 range spec
     * @return a range
     */
    IntegerRange createRange(String rp1) {
        return helper().createRange(rp1);
    }

    /**
     * Create range using helper.
     *
     * @param p1 point
     * @param p2 point
     * @return range
     */
    IntegerRange createRange(Integer p1, Integer p2) {
        return helper().createRange(p1, p2);
    }

    /**
     * Helper to shorten writing the tests.
     *
     * @param key
     * @return
     */
    Integer lookupPoint(String key) {
        return helper().lookupPoint(key);
    }

    /**
     * Helper to compute distance.
     *
     * @param a point
     * @param b point
     * @return integer distance
     */
    Integer distance(Integer a, Integer b) {
        return helper().distance(a, b);
    }

    /**
     * Test the default max function in Range.
     *
     * @param as specifies a
     * @param bs specifies a
     * @param exs specifies expected point
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,b,b",
            "c,b,c",
            "a,a,a"
    })
    public void t01Max(String as, String bs, String exs) {
        Integer a = lookupPoint(as);
        Integer b = lookupPoint(bs);
        Integer expected = lookupPoint(exs); // the map will return the same instance
        //TODO write assert
        assertThat(Range.max(a, b)).isEqualTo(expected);
        //fail( "method t01Max reached end. You know what to do." );
    }

    /**
     * Test the default max function in Range.
     *
     * @param as specifies a
     * @param bs specifies a
     * @param exs specifies expected point
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,b,a",
            "c,b,b",
            "a,a,a"
    })
    public void t02Min(String as, String bs, String exs) {
        //TODO implement test
        Integer a = lookupPoint(as);
        Integer b = lookupPoint(bs);
        Integer expected = lookupPoint(exs);
        assertThat(Range.min(a, b)).isEqualTo(expected);
        //fail( "method t02Min reached end. You know what to do." );
    }

    /**
     * Test the default minmax function in Range.
     *
     * @param as specifies a
     * @param bs specifies b
     * @param expected0 specifies expected0 point
     * @param expected1 specifies expected1 point
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,a,a,a",
            "a,b,a,b",
            "d,c,c,d",})
    public void t03MinmaxTest(String as, String bs, String expected0,
                              String expected1) {
        Integer a = lookupPoint(as);
        Integer b = lookupPoint(bs);
        Integer exp0 = lookupPoint(expected0);
        Integer exp1 = lookupPoint(expected1);
        Integer[] t = Range.minmax(a, b);
        SoftAssertions.assertSoftly(softly -> {
            //TODO write assertions
            softly.assertThat(t[0]).isEqualTo(exp0);
            softly.assertThat(t[1]).isEqualTo(exp1);
        });

        //fail( "method t03minmaxTest reached end. You know what to do." );
    }

    /**
     * Test Range#meets.
     *
     * @param as specifies a
     * @param bs specifies b
     * @param cs specifies c
     * @param ds specifies d
     * @param expected outcome
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "a,b,c,d,false",
            "c,d,a,b,false",
            "a,b,b,d,true",
            "c,d,a,c,true",})
    public void t04Meets(String as, String bs, String cs, String ds,
                         boolean expected) {
        Integer a = lookupPoint(as);
        Integer b = lookupPoint(bs);
        Integer c = lookupPoint(cs);
        Integer d = lookupPoint(ds);
        // Make sure to implement IntergerRange.of
        //TODO create ranges and test meets method
        IntegerRange r1 = helper().createRange(a, b);
        IntegerRange r2 = helper().createRange(c, d);
        assertThat(r1.meets(r2)).isEqualTo(expected);
        //fail( "method t04Meets reached end. You know what to do." );
    }

    /**
     * Test the helper method Range#between. Given.
     */
    //@Disabled("Think TDD")
    @Test
    public void t05CreateBetween() {
        Integer a = lookupPoint("a");
        Integer b = lookupPoint("b");
        Integer c = lookupPoint("c");
        // helper is needed to get access to the between method.
        IntegerRange helper = createRange(c, c);
        IntegerRange rt = helper.between(a, b);
        assertThat(rt)
                .extracting("start", "end")
                .containsExactly(a, b);

//        fail( "createBetween completed succesfully; you know what to do" );
    }

    /**
     * Test Range#rangeHashCode and Range#rangeEquals implicitly through
     * concrete IntegerRange. Given.
     */
    //@Disabled("Think TDD")
    @Test
    public void t06HashCodeEquals() {
        Integer a = lookupPoint("a");
        Integer b = lookupPoint("b");
        Integer c = lookupPoint("c");

        IntegerRange ref = createRange(a, b);
        IntegerRange equ = createRange(a, b);
        IntegerRange diffB = createRange(a, c);
        IntegerRange diffC = createRange(c, b);

        TestUtils.verifyEqualsAndHashCode(ref, equ, diffB, diffC);

//        fail( "hashCodeEquals completed succesfully; you know what to do" );
    }

    /**
     * Test length function. Bit dubious, does it really test anything in range?
     */
    //@Disabled("Think TDD")
    @Test
    public void t07Length() {
        //TODO test length with distance function
        Integer a = helper().lookupPoint("a");
        Integer b = helper().lookupPoint("b");
        IntegerRange r1=helper().createRange(a, b);
        assertThat(r1.length()).isEqualTo(helper().distance(a, b));
        //fail( "method t07Length reached end. You know what to do." );
    }

    /**
     * Test the overlaps function. The method is given. Add more test values.
     *
     * @param rp1 point pair 1
     * @param rp2 point pair 2
     * @param overlaps expected outcome
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            "ab,cd,false", // disjunct
            "ac,cd,false", // meet
            "ac,bd,true", //  B < C < D
            "bd,ac,true",
            "ad,bc,true",
            //TODO A1A test overlaps() add more test values to improve coverage
    }
    )
    void t08Overlaps(String rp1, String rp2, boolean overlaps) {
        IntegerRange r1 = createRange(rp1);
        IntegerRange r2 = createRange(rp2);
        //TODO write assert
        assertThat(r1.overlaps(r2)).isEqualTo(overlaps);
        //fail("method t08Overlaps reached end. You know what to do.");
    }

    /**
     * Compute the overlap between two ranges.
     *
     * @param rp1 point pair one defining first range
     * @param rp2 point pair two defining second range
     * @param rp3 point pair with expected length
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // first, second, distance  points
            "ab,cd,aa", // disjunct
            "ab,bc,bb", // disjunct
            "ac,bd,bc", //  B < C < Integer
            "bd,ac,bc",
            "ad,bc,bc",
            //TODO A2A test overlap(). add test valeus for coverage
    }
    )
    void t09OverLap(String rp1, String rp2, String rp3) {
        //TODO test overlap method
        IntegerRange r1=helper().createRange(rp1);
        IntegerRange r2=helper().createRange(rp2);
        IntegerRange r3=helper().createRange(rp3);
        assertThat(r1.overlap(r2)).isEqualTo(r3.length());
        //fail("test t09overLap completed, you know what to do.");
    }

    /**
     * Assert that the range constructor puts start and end in the proper order.
     * E.g. IntergerRange(5,4) -> start: 4 and end: 5 Assert that end of range
     * is less or equal to start, using compareTo.
     */
    //@Disabled("Think TDD")
    @Test
    void t10Normalizes() {
        //TODO test that start and end are in natural order
        IntegerRange r=new IntegerRange(5,4);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(r.start().compareTo(r.end())<=0).isTrue();
            softly.assertThat(r.start()).isEqualTo(4);
            softly.assertThat(r.end()).isEqualTo(5);
        });
        //fail("test t10normalizes completed, you know what to do.");
    }

    /**
     * Check the contain(p) method works correctly. Method is given. Add test
     * values.
     *
     * @param pp first range lookup
     * @param point to check
     * @param contains expected value
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "bc,a,false",
            "bc,d,false", //TODO A3A test containsPoint(). add more test values to improve coverage
            "ac,b,true",
            "ad,c,true"
    })
    void t11ContainsPoint(String pp, String point, boolean contains) {
        // coverage
        //TODO test contains point method
        IntegerRange r=helper().createRange(pp);
        assertThat(r.contains(helper().lookupPoint(point))).isEqualTo(contains);
        //fail("t11ContainsPoint completed succesfully; you know what to do");
    }

    //@Disabled("Think TDD")
    @Test
    void t12ToStringTest() {
        //TODO test tostring
        IntegerRange r=helper().createRange("ab");
        assertThat(r.toString()).isEqualTo("[42,51)");
        //fail(" t12ToString reached end. You know what to do.");
    }

    /**
     * Test that method checkMeetsOrOverlaps throws exception at the proper
     * situation.
     *
     * Test that it DOESN'T throw the exception
     *
     * @param pp1
     * @param pp2
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,bc",
            "ac,bd"
    })
    void t13aCheckMeetsOrOverlaps(String pp1, String pp2) {
        IntegerRange r1 = createRange(pp1);
        IntegerRange r2 = createRange(pp2);
        // code that should throw the exception.
        //TODO write code and assert
        assertThatCode( ()->{r1.checkMeetsOrOverlaps(r2);} ).doesNotThrowAnyException();
        //fail("method t13aCheckMeetsOrOverlaps reached end. You know what to do.");
    }

    /**
     * Test that method checkMeetsOrOverlaps throws exception at the proper
     * situation.
     *
     * Test that it DOES throw the exception
     *
     * @param pp1
     * @param pp2
     * @param meetsOrOverLaps
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,cd"
    })
    void t13bCheckMeetsOrOverlaps(String pp1, String pp2) {
        IntegerRange r1 = createRange(pp1);
        IntegerRange r2 = createRange(pp2);
        // code that should throw or not throw exception.
        //TODO write code and assert
        assertThatThrownBy( ()->{r1.checkMeetsOrOverlaps(r2);} ).isExactlyInstanceOf(IllegalArgumentException.class);
        //fail("method t13bCheckMeetsOrOverlaps reached end. You know what to do.");
    }

    /**
     * Check joinWith. The test values should all produce a join, the exception
     * throwing is tested elsewhere.
     *
     * @param pp1 first range spec
     * @param pp2 second range spec.
     * @param expectedRange in the test
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,bc,ac",
            "ac,bd,ad"
    })
    void t14JoinWith(String pp1, String pp2, String expectedRange) {
        //TODO test joinWith method
        IntegerRange r1=helper().createRange(pp1);
        IntegerRange r2=helper().createRange(pp2);
        assertThat(r1.joinWith(r2)).isEqualTo(helper().createRange(expectedRange));
        //fail("method t14JoinWith reached end. You know what to do.");
    }

    /**
     * Check the intersect method part 1.
     *
     * In this test all values should produce a non-empty intersection.
     *
     * @param rp1 range specification
     * @param rp2 cutter range spec
     * @param intersection expected result of cut.
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // this, cutter, intersection
            "ac,bd,bc", //TODO A5A test intersectWith add more test values to improve coverage
            "ad,bc,bc",
    }
    )
    void t15aCommonRangeSuccess(String rp1, String rp2, String intersection) {
        IntegerRange range = createRange(rp1);
        IntegerRange cutter = createRange(rp2);
        Optional<IntegerRange> result = range.intersectWith(cutter);
        //TODO write action and assert
        IntegerRange expectedResult= createRange(intersection);
        assertThat(result.get().equals(expectedResult)).isTrue();
        //fail("t15aCommonRangeSuccess completed succesfully; you know what to do");
    }

    /**
     * Check the intersect method part 2.
     *
     * In this test all values should produce an empty intersection.
     *
     * @param rp1 range specification
     * @param rp2 cutter range spec
     * @param interSection expected value 1
     * @param interSection expected result of cut.
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // this, cutter, cuts, expected result
            "ab,cd,false,", //TODO A5A test intersectWith add more test values to improve coverage
            "ab,bc,false,",
            "ac,bd,true,",
            "ad,bc,true,",
    })
    void t15bCommonRangeEmpty(String rp1, String rp2, boolean interSects, String interSection) {
        IntegerRange range = createRange(rp1);
        IntegerRange cutter = createRange(rp2);
        //TODO write action and assert that result is empty
        Optional<IntegerRange> result = range.intersectWith(cutter);
        assertThat(result.isPresent()).isEqualTo(interSects);
        //fail("t15bCommonRangeEmpty completed succesfully; you know what to do");
    }

    /**
     * Test if range is fully contained in other. (contains method)
     *
     * Method is given. Add test values
     *
     * @param rp1 this range
     * @param rp2 other range
     * @param expected outcome
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // this, cutter, cuts, expected result
            "ab,cd,false", // disjunct
            //TODO A4A test containsRange. add more test values to improve coverage
            "ad,bc,true",
            "ac,bb,true",
            "ab,bc,false",
    }
    )
    void t16ContainsRange(String rp1, String rp2, boolean expected) {
        IntegerRange range = createRange(rp1);
        IntegerRange other = createRange(rp2);
        //TODO write assert
        assertThat(range.contains(other)).isEqualTo(expected);
        //fail("t16ContainsRange completed succesfully; you know what to do");

    }

    /**
     * Test the punchThrough method. Test is given. Add test values.
     *
     * In expected value ab|bc means a stream with exactly the elements [ab) and
     * [bc). Use the method restRanges of the RangeTestDataFactory to help
     * convert expected value to Stream of ranges
     *
     * @param rangeP range value
     * @param punchP punch value
     * @param restPairs, | separated list of expected ranges in stream
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource(value = {
            // range, punch, results, | separated
            "ab,ab,ab", // replace
            "ac,ab,ab|bc", // left punch
            //TODO A6A test punchThrough(...). add more test values to improve coverage
            "ac,bd,ac",
            "ad,bc,ab|bc|cd",
    }
    )
    void t17PunchThrough(String rangeP, String punchP, String restPairs) {
        IntegerRange range = createRange(rangeP);
        IntegerRange punch = createRange(punchP);
        var expectedParts = helper().restRanges("\\|", restPairs);
        Stream<IntegerRange> result = range.punchThrough(punch);
        //TODO write assert using stream
        assertThat(result).isEqualTo(expectedParts);
        //fail("t17PunchThrough completed succesfully; you know what to do");

    }

    /**
     * Test compareTo. The outcome is negative, zero or positive, which is
     * expressed in the table as -1, 0. or 1.
     *
     * Have a look at Integer.signum to help with the assertion
     *
     * @param pp1 range 1
     * @param pp2 range 2
     * @param expected value
     */
    //@Disabled("Think TDD")
    @ParameterizedTest
    @CsvSource({
            "ab,ac,0", // same start
            "ac,bd,-1", // start left of
            "bc,ad,1", // start right of
    })
    void t18CompareTo(String pp1, String pp2, int expected) {
        IntegerRange r1 = createRange(pp1);
        IntegerRange r2 = createRange(pp2);
        //TODO write assert. Remember signum use for comparable or comparator.
        assertThat(Integer.signum(r1.compareTo(r2))).isEqualTo(expected);
        //fail("t18CompareTo completed succesfully; you know what to do");
    }
}
