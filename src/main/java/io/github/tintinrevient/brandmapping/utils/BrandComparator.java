package io.github.tintinrevient.brandmapping.utils;

import java.util.Comparator;

import io.github.tintinrevient.brandmapping.domain.Brand;

/**
 * Brand comparator
 *
 *
 * @author Zhao Shu
 *
 */
public class BrandComparator implements Comparator<Brand>{

	/**
	 * Order brands by their average mapping rate
	 * 
	 */
	public int compare(Brand b1, Brand b2){
		if(b1.getAverageRate() < b2.getAverageRate())
			return -1;

		if(b1.getAverageRate() > b2.getAverageRate())
			return 1;

		return 0;
	}
}
