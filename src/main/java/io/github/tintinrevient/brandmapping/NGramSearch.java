package io.github.tintinrevient.brandmapping;

import java.util.*;

import io.github.tintinrevient.brandmapping.domain.Brand;
import io.github.tintinrevient.brandmapping.utils.CounterMap;
import io.github.tintinrevient.brandmapping.utils.MathUtils;
import io.github.tintinrevient.brandmapping.algorithm.QGramMap;
import io.github.tintinrevient.brandmapping.utils.StringUtils;

/**
 * Statistical testing for n-gram approximate searching
 *
 *
 * @author Zhao Shu
 *
 */
public class NGramSearch {
	
	public static void main(String[] args){
		int q = 3;
		String input = "Ivy";
		int totalCount = input.length() - q + 1;
		
		long startR = System.currentTimeMillis();
		List<Brand> brandList = new ArrayList<Brand>();
		
		long endR = System.currentTimeMillis();
		
		String[] brandArray = new String[brandList.size()];
		for(int i = 0; i < brandList.size(); i++){
			brandArray[i] = brandList.get(i).getName();
		}
		
		long endT = System.currentTimeMillis();
		
		QGramMap.invertedList(brandList, q);
		
		long startC = System.currentTimeMillis();

		CounterMap<Brand> counterMap = QGramMap.approxStringMatch(input, q);
				
		int i = 0;
		Brand[] matches = (Brand[])counterMap.keysOrderedByCountList().toArray(new Brand[0]);
		Map<Brand, Double> hashMap = new HashMap<Brand, Double>();
		for(Brand brand : matches){
			String match = brand.getName();
			int count = counterMap.getCount(brand);
			double rate1 = ((double) count)/((double) totalCount);

			double rate2 = MathUtils.getDiff(input, match);
			double rate = MathUtils.getWeightedAvg(rate1, rate2);
			
			hashMap.put(brand, rate);
						
			if(i > 11)
				break;
		}
		
		TreeMap<Brand, Double> treeMap = MathUtils.getSortedHashMap(hashMap);
        List<Brand> resultBrandList = StringUtils.getBrandList(treeMap);
		
		long endC = System.currentTimeMillis();
		
		System.out.println();
		System.out.println();
		
		System.out.println("Computing Time(ms): " + (endC-startC));
		System.out.println("Reading DB Time(ms): " + (endR - startR));
		System.out.println("Tranforming Array Time(ms): " + (endT - endR));
		System.out.println("Building Inverted List Time(ms): " + (startC - endT));
		
	}

}
