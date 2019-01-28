package io.github.tintinrevient.brandmapping.utils;

/**
 * <p>Counter class</p>
 *
 *
 * @author Zhao Shu
 *
 */
public class Counter extends Number {
    
    private int mCount;

    public Counter() {
        this(0);
    }


    public Counter(int count) {
        mCount = count;
    }

  
    public int value() {
        return mCount;
    }

   
    public void increment() {
        ++mCount;
    }

    
    public void increment(int n) {
        mCount += n;
    }


   
    public void set(int count) {
        mCount = count;
    }

   
    @Override
    public double doubleValue() {
        return (double) mCount;
    }

   
    @Override
    public float floatValue() {
        return (float) mCount;
    }

   
    @Override
    public int intValue() {
        return mCount;
    }

   
    @Override
    public long longValue() {
        return (long) mCount;
    }

   
    @Override
    public String toString() {
        return String.valueOf(value());
    }

}

