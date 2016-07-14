package org.deeplearning4j.datasets.iterator;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.*;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author raver119@gmail.com
 */
public class AsyncDataSetIteratorTest {
    private ExistingDataSetIterator backIterator;
    private static final int TEST_SIZE = 100;

    @Before
    public void setUp() throws Exception {
        List<DataSet> iterable = new ArrayList<>();
        for (int i = 0; i < TEST_SIZE; i++) {
            iterable.add(new DataSet(Nd4j.create(new float[100]), Nd4j.create(new float[10])));
        }

        backIterator = new ExistingDataSetIterator(iterable);
    }

    @Test
    public void hasNext1() throws Exception {
        for (int prefetchSize = 1; prefetchSize <= 10; prefetchSize++) {
            AsyncDataSetIterator iterator = new AsyncDataSetIterator(backIterator, prefetchSize);
            int cnt = 0;
            while (iterator.hasNext()) {
                DataSet ds = iterator.next();
                assertNotEquals(null, ds);
                cnt++;
                assertEquals(100, ds.getFeatureMatrix().length());
                assertEquals(10, ds.getLabels().length());
            }
            assertEquals(TEST_SIZE, cnt);
        }
    }

}