package io.github.tintinrevient.brandmapping.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import io.github.tintinrevient.brandmapping.utils.Counter;

/**
 * <p>CounterMap for word-frequency statistical use</p>
 *
 *
 * @author Zhao Shu
 *
 * @param <E>
 *
 */
public class CounterMap<E> extends HashMap<E, Counter> {

   
    public CounterMap() {
        /* do nothing */
    }

   
    public CounterMap(int initialSize) {
        super(initialSize);
    }

    /**
     * Increment counter by 1 for an input key
     * 
     * @param key
     */
    public void increment(E key) {
        increment(key, 1);
    }

   
    /**
     * Increment counter by n for an input key
     * 
     * @param key
     * @param n
     */
    public void increment(E key, int n) {
        if (!containsKey(key)) {
            put(key, new Counter(n));
            return;
        }

        Counter counter = get(key);
        counter.increment(n);

        if (counter.value() == 0) remove(key);
    }

   
    public void set(E key, int n) {
        if (n == 0) {
            remove(key);
            return;
        }

        if (!containsKey(key)) {
            put(key, new Counter(n));
            return;
        }

        Counter counter = get(key);
        counter.set(n);
    }

   
    public int getCount(E key) {
        if (!containsKey(key)) return 0;

        return get(key).value();
    }

   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<E> keyList = keysOrderedByCountList();
        for (E key : keyList) {
            sb.append(key);
            sb.append('=');
            sb.append(getCount(key));
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * return keyset ordered by counter
     * 
     * @return List<E>
     */
    public List<E> keysOrderedByCountList() {
        Set<E> keySet = keySet();

        List<E> result = new ArrayList<E>(keySet().size());
        result.addAll(keySet);

        Collections.sort(result,countComparator());

        return result;
    }

   
    public Object[] keysOrderedByCount() {
        return keysOrderedByCountList().toArray();
    }

   
    public void prune(int minCount) {
        Iterator<Entry<E,Counter>> it = entrySet().iterator();

        while (it.hasNext())
            if (it.next().getValue().value() < minCount)
                it.remove(); // remove this entry
    }

   
    public Comparator<E> countComparator() {
        return new Comparator<E>() {
            public int compare(E o1, E o2) {
                int count1 = getCount(o1);
                int count2 = getCount(o2);

                if (count1 < count2) return 1;
                if (count1 > count2) return -1;

                if (!(o1 instanceof Comparable)
                    || !(o2 instanceof Comparable))
                    return 0;

                // must be instances given above, but may still fail in compareTo
                @SuppressWarnings("unchecked")
                Comparable<? super E> c1 = (Comparable<? super E>) o1;
                return c1.compareTo(o2);
            }
        };
    }

}