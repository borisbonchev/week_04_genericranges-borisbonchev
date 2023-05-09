
package nl.fontys.sebivenlo.ranges;

public class IntegerRangeTest extends RangeTestBase<IntegerRange,Integer,Integer>{

    Integer[] pointsA = {42, 51, 55, 1023, 1610, 2840};
    RangeTestDataFactory<IntegerRange, Integer, Integer> daf;

    @Override
    RangeTestDataFactory helper() {
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
}
