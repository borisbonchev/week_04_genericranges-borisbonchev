package nl.fontys.sebivenlo.ranges;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;

public class InstantRange implements Range<InstantRange, Instant, Duration> {

    private Instant start;
    private Instant end;

    public InstantRange(Instant start, Instant end) {
        if (start.compareTo(end) > 0) {
            Instant aux;
            aux = start;
            start = end;
            end = aux;
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public Instant start() {
        return this.start;
    }

    @Override
    public Instant end() {
        return this.end;
    }

    @Override
    public BiFunction<Instant, Instant, Duration> meter() {
        return (a,b)->Duration.between(a, b);
    }

    @Override
    public InstantRange between(Instant startInclusive, Instant endExclusive) {
        return new InstantRange(startInclusive,endExclusive);
    }

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
    public Duration zero() {
        return Duration.ZERO;
    }

    @Override
    public String toString()
    {
        return rangeToString();
    }

    public static InstantRange of(Instant start, Instant end) {
        return new InstantRange(start, end);
    }
}
