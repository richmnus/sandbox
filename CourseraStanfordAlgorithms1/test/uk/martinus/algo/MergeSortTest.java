package uk.martinus.algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.martinus.algo.BruteForceInversions;
import uk.martinus.algo.MergeSort;

public class MergeSortTest {

    List<Integer> inputData;
    List<Integer> outputData;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        inputData = new ArrayList<Integer>();
        loadData(inputData, "IntegerArray.txt");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void inputDataSize() {
        final int EXPECTED_INPUT_SIZE = 100000;
        Assert.assertEquals("Input data size", EXPECTED_INPUT_SIZE, inputData.size());
    }

    @Test
    public final void numbersSortedAscendingOrder() {
        MergeSort ms = new MergeSort();
        long startTime = new Date().getTime();
        outputData = ms.mergeSort(inputData);
        long stopTime = new Date().getTime();
        System.out.printf("Merge-sort duration is %d ms\n", stopTime
                - startTime);
        Assert.assertEquals("Size of output data", outputData.size(),
                inputData.size());
        startTime = new Date().getTime();
        long inversions = BruteForceInversions.countInversions(inputData);
        stopTime = new Date().getTime();
        System.out.printf("BruteForceInversions duration is %d ms\n", stopTime
                - startTime);
        Assert.assertEquals("Inversions counted by the MergeSort algorithm",
                inversions, ms.getInversions());
        Assert.assertTrue("Merge-sorted data is sorted",
                checkSorted(inputData, outputData));
    }

    private boolean checkSorted(List<Integer> inputData,
            List<Integer> outputData) {
        long startTime = new Date().getTime();
        Collections.sort(inputData);
        long stopTime = new Date().getTime();
        System.out.printf("Collections.sort() duration is %d ms\n", stopTime
                - startTime);
        for (int i = 0; i < inputData.size(); i++) {
            if (inputData.get(i) != outputData.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void loadData(List<Integer> data, String filename) {
        try {
            FileInputStream fin = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    fin));
            String value;
            while ((value = reader.readLine()) != null) {
                data.add(Integer.parseInt(value));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("Input data set has %d elements\n", data.size());
    }

}
