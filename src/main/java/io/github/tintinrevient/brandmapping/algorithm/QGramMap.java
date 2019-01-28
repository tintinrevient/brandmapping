package io.github.tintinrevient.brandmapping.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.tintinrevient.brandmapping.domain.Brand;
import io.github.tintinrevient.brandmapping.utils.CounterMap;
import io.github.tintinrevient.brandmapping.utils.StringUtils;

/**
 * <p>Compute an approximate matched string for an input key based on the document inverted list</p>
 *
 *
 * @author Zhao Shu
 *
 */
public class QGramMap {

	private static Map<String, Set<Brand>> map;
	
	static {
		map = new HashMap<String, Set<Brand>>();
	}

	
	/**
	 * Initialize a document inverted list based on the input attribute array
	 * 
	 * @param brandList
	 * @param q
	 */
	public static void invertedList(List<Brand> brandList, int q){
		for(int i = 0; i < brandList.size(); i++){
			Brand brand = brandList.get(i).clone();
			String attribute = StringUtils.uniform(brand.getName());
			String[] parts = qgram(attribute, q);
			
			for(int j = 0; j < parts.length; j++){
				Set<Brand> set = map.get(parts[j]);
				
				if(set == null)
					set = new HashSet<Brand>();
				set.add(brand);
				
				map.put(parts[j], set);
			}
		}
	}
	
	/**
	 * Get the approximate string match for a given attribute
	 * 
	 * @param attribute
	 * @param q
	 * @return CounterMap<Brand>
	 */
	public static CounterMap<Brand> approxStringMatch(String attribute, int q){
		CounterMap<Brand> counterMap = new CounterMap<Brand>();
		
		String[] parts = qgram(attribute, q);
		for(String part : parts){
			if(hasKey(part)){
				Brand[] attributes = attributes(part);
				
				for(Brand brand : attributes){
					counterMap.increment(brand);
				}
			}
		}
		
		return counterMap;
	}
	
	private static boolean hasKey(String key){
		return map.containsKey(key);
	}
	
	/**
	 * Check for the document inverted list for an approximate match for input key
	 * 
	 * @param key
	 * @return String[]
	 */
	private static Brand[] attributes(String key){
		Brand[] attributes = new Brand[map.get(key).size()];
		
		int i = 0;
		for(Brand brand : map.get(key)){
			attributes[i++] = brand;
		}
		return attributes;
	}
	
	/**
	 * Divide the string based on the given q and attribute
	 * 
	 * @param attribute
	 * @param q
	 * @return String[]
	 */
	private static String[] qgram(String attribute, int q){
		char[] array = (char[])attribute.toCharArray();
		
		int size = array.length - q + 1;
		if(size < 1)
			return new String[]{attribute};
			
		String[] parts = new String[size];

		String part = "";
		for(int i = 0; i < size; i++){
			part = "";
			for(int j = 0; j < q; j++){
				part += array[i+j];
				parts[i] = part;
			}
		}
		
		return parts;
	}
	
	public static void clear(){
		map.clear();		
	}
	
}
