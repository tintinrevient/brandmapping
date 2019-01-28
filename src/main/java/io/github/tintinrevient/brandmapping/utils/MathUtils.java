package io.github.tintinrevient.brandmapping.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import io.github.tintinrevient.brandmapping.domain.Brand;

/**
 * Mathematical utility class
 * 
 * @author Zhao Shu
 *
 */
public class MathUtils {
	
	public static double getDiff(String s1, String s2){
		double length1 = (double) s1.length();
		double length2 = (double) s2.length();
		
		double diff = Math.min(length1, length2) / Math.max(length1, length2);

		return diff;
	}
	
	public static double getWeightedAvg(double d1, double d2){
		double wAvg = d1 * 0.8 + d2 * 0.2;
		
		return wAvg;
	}
	
	public static TreeMap<Brand, Double> getSortedHashMap(Map<Brand, Double> hashMap){
		ValueComparator comparator = new ValueComparator(hashMap);
		TreeMap<Brand, Double> treeMap = new TreeMap<Brand, Double>(comparator);
		
		treeMap.putAll(hashMap);
		
		return treeMap;
	}
	
	private static class ValueComparator implements Comparator<Brand> {

	    Map<Brand, Double> base;
	    
	    public ValueComparator(Map<Brand, Double> base) {
	        this.base = base;
	    }

	    public int compare(Brand a, Brand b) {
	        if(base.get(a) > base.get(b))
	            return -1;
	        else if(base.get(a) < base.get(b))
	        	return 1;
	        else{
	        	String nameA = a.getName();
	        	String nameB = b.getName();
	        	
	        	int score = nameA.compareToIgnoreCase(nameB);
	        	
	        	if(score >= 0)
	        		return 1;
	        	else if(score < 0)
	        		return -1;
	        }
	        
	        return 0;
	    }
	}

}
