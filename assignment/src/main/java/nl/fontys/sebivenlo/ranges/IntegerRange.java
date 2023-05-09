package nl.fontys.sebivenlo.ranges;

import java.util.function.BiFunction;

/**
 * Simple integer based range. This is the first leaf class and it is used to
 * test the default methods in Range.
 *
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
public class IntegerRange implements Range<IntegerRange, Integer, Integer> {
    //TODO implement integerRange fields and sole private constructor

    private final Integer start;
    private final Integer end;

    public IntegerRange(Integer start, Integer end) {
        Integer[] t = Range.minmax(start, end);
        this.start = t[0];
        this.end = t[1];
    }

    @Override
    public Integer start() {
        //TODO implement getter
        return start;
    }

    @Override
    public Integer end() {
        //TODO implement getter
        return end;
    }

    @Override
    public BiFunction<Integer, Integer, Integer> meter() {
        //TODO implement meter function, returning lambda or method ref
        return (a,b)-> b-a;
    }

    @Override
    public IntegerRange between( Integer start, Integer end ) {
        //TODO implement between factory
        return new IntegerRange(start,end);
    }

    // since the methods hashCode, equals and toString are defined in Object,
    // you cannot overwrite them in an interface. The best you can do is invoke the methods
    // of the interface or use the new java record type, finalized in Java 16.
    @Override
    public int hashCode() {
        return rangeHashCode();
    }

    @Override
    @SuppressWarnings( "EqualsWhichDoesntCheckParameterClass" )
    public boolean equals( Object obj ) {
        return rangeEquals( obj );
    }

    @Override
    public String toString() {
        return rangeToString();
    }

    @Override
    public Integer zero() {
        return 0;
    }

    /**
     * ConvenienceFactory.
     *
     * @param start of range
     * @param end of range
     * @return the range
     */
    public static IntegerRange of( Integer start, Integer end ) {
        //TODO implement of(Start, End)
        return new IntegerRange(start,end);
    }
}
