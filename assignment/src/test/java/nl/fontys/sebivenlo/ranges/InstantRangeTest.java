package nl.fontys.sebivenlo.ranges;

import java.time.Duration;
import java.time.Instant;

public class InstantRangeTest extends RangeTestBase<InstantRange,Instant,Duration> {

    Instant[] pointsA ={Instant.MIN,Instant.EPOCH.minusSeconds(2000),Instant.EPOCH,Instant.EPOCH.plusSeconds(2000),Instant.MAX};
    RangeTestDataFactory<InstantRange, Instant, Duration> daf;

    @Override
    RangeTestDataFactory<InstantRange, Instant, Duration> helper() {
        if (null == daf) {
            daf = new RangeTestDataFactory<>(pointsA) {

                @Override
                InstantRange createRange(Instant start, Instant end) {
                    return InstantRange.of(start,end);
                }

                @Override
                Duration distance(Instant a, Instant b) {
                    return Duration.between(a, b);
                }

            };

        }
        return daf;
    }
}
