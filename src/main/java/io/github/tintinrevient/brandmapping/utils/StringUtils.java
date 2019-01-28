package io.github.tintinrevient.brandmapping.utils;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;


import io.github.tintinrevient.brandmapping.domain.Brand;

/**
 * <p>String Utility Class</p>
 *
 *
 * @author Zhao Shu
 *
 */
public class StringUtils {

	public static boolean isNotBlank(String str) {
		return str != null && str.trim().length() != 0;
	}

	public static String apostropheFilter(String str) {
		str = str.replaceAll("&#44", ",");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&apos;", "'");
		str = str.replaceAll("\\?", "'");
		return str.replaceAll("'", "''");
	}

	public static String uniform(String key) {
		String displayKey = key.toLowerCase().trim().replaceAll("\\s", "");

		return displayKey;
	}

	public static List<Brand> getBrandList(TreeMap<Brand, Double> treeMap) {

		List<Brand> brandList = new ArrayList<>();

		for (Entry<Brand, Double> entry : treeMap.entrySet()) {
			String name = String.valueOf(entry.getKey().getName());
			double score = Double.parseDouble(String.valueOf(entry.getValue()));

			Brand brand = new Brand();
			brand.setName(name);
			brand.setScore(score);

			brandList.add(brand);
		}

		return brandList;
	}

}