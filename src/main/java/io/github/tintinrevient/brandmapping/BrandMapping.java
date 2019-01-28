package io.github.tintinrevient.brandmapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.tintinrevient.brandmapping.algorithm.KeywordPriority;
import io.github.tintinrevient.brandmapping.algorithm.LCS;
import io.github.tintinrevient.brandmapping.algorithm.Levenshtein;
import io.github.tintinrevient.brandmapping.domain.Brand;
import io.github.tintinrevient.brandmapping.domain.MappingRate;
import io.github.tintinrevient.brandmapping.utils.BoundedPriorityQueue;
import io.github.tintinrevient.brandmapping.utils.BrandComparator;
import io.github.tintinrevient.brandmapping.utils.StringUtils;

public class BrandMapping {

	public static final String[] STOPWORDS = { "north america", "technologies", "technology", "techniques", "\\s+productions", "corporate", "\\s+design", "\\s+games", "\\s+designs",
		"corporation", "enterprises", "international", "manufacturing", "publishing", "company", "companies", "systems", "system", "america", "\\s+products",
		"limited$", "co\\. , ltd\\.", "co\\. ,ltd\\.", "\\s+group$", "co\\.,ltd\\.", "\\s+series\\s+", "\\s+series$", "\\s+prod\\.", "\\s+distribution", "\\s+homeopathic",
		"\\s+corp\\.", ", llc\\.", ", inc\\.", ", ltd\\.", ",llc\\.", ",inc\\.", "\\s+,ltd\\.", "(txp)", "\\s+corp\\.", "\\s+prod", "\\.co\\.",
		"\\s+,ltd", "\\s+mfg\\.", "\\s+llc\\.", "\\s+inc\\.", "\\s+,llc", "\\s+inc", "\\s+the\\s+", "^the\\s+", "\\s+llc", "\\s+and\\s+",
		"\\s+mfg$", "\\s+mfg\\s+", "co\\.", "\\.co", "\\s+ltd", "^usa\\s+", "\\s+usa\\s+", "\\s+pub$", "\\s+pub\\s+", "\\s+co\\s+", "\\s+co$",
		"u s\\s+", "\\s+us\\s+", "\\s+pr\\s+", "\\s+pr$", "/", "-", "_", ",", "，", "\\.", "\\s+industries"};
	
	
	public static String stopWordRegex;	
	
	static {
		stopWordRegex = stopWordsRegex();
	}

	
	public static void compute(String s, List<Brand> brandList, int topk) {
		List<Brand> list = new ArrayList<Brand>();
		
		if(topk == -1){
			topk = brandList.size();
		}
		
		BrandComparator comparator = new BrandComparator();
		BoundedPriorityQueue<Brand> queue = new BoundedPriorityQueue<Brand>(comparator, topk);
		
		DecimalFormat df = new DecimalFormat("0.00");			
		for (int i = 0; i < brandList.size(); i++) {
			Brand brand = brandList.get(i);
			String name = brand.getName();
			
			MappingRate mappingRate = computeMappingRate(s, name);
			
			brand.setName(StringUtils.uniform(name));
			brand.setLevenshteinRate(mappingRate.getLevenshteinRate());
			brand.setLcsRate(mappingRate.getLcsRate());
			brand.setContainRate(mappingRate.getContainRate());
			brand.setAverageRate(mappingRate.getAverageRate());
			
			queue.offer(brand);
		}
		
		System.out.println("Result : " + s);
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Rate %\tCategory            \t LE Rate %\t LCS Rate %\t CO Rate %");
		System.out.println("------------------------------------------------------------------------------");
		
		Iterator<Brand> iterator = queue.iterator();
		while(iterator.hasNext()){
			Brand brand = iterator.next();
			
			String brandName = StringUtils.uniform(brand.getName());
			String rate = df.format(brand.getAverageRate() * 100);
			String levenshteinRate = df.format(brand.getLevenshteinRate() * 100);
			String lcsRate = df.format(brand.getLcsRate() * 100);
			String containRate = df.format(brand.getContainRate() * 100);
			
			System.out.println(rate + "%" + "\t" + brandName + "\t " + levenshteinRate + "%" 
					+ "\t\t " + lcsRate + "%\t\t " + containRate + "%");
		}
	}
	
	/**
	 * Get top-k approximate results
	 * 
	 * @param s
	 * @param brandList
	 * @param topk
	 * @return
	 */
	public static List<Brand> getBrand(String s, List<Brand> brandList, int topk){
		List<Brand> list = new ArrayList<Brand>();
		
		if(topk == -1){
			topk = brandList.size();
		}
		
		BrandComparator comparator = new BrandComparator();
		BoundedPriorityQueue<Brand> queue = new BoundedPriorityQueue<Brand>(comparator, topk);
		
		for (int i = 0; i < brandList.size(); i++) {
			Brand brand = brandList.get(i);
			String name = brand.getName();
			
			MappingRate mappingRate = computeMappingRate(s, name);
			
			brand.setName(StringUtils.uniform(name));
			brand.setLevenshteinRate(mappingRate.getLevenshteinRate());
			brand.setLcsRate(mappingRate.getLcsRate());
			brand.setContainRate(mappingRate.getContainRate());
			brand.setAverageRate(mappingRate.getAverageRate());

			queue.offer(brand);
		}
		
		Iterator<Brand> iterator = queue.iterator();
		while(iterator.hasNext()){
			Brand brand = iterator.next();
			
			list.add(brand);
		}
		return list;
	}

	
	public static MappingRate computeMappingRate(String s, String t) {
		if (!StringUtils.isNotBlank(s) || !StringUtils.isNotBlank(t)) {
			return null;
		}
		s = stopWordFilter(s.toLowerCase().trim());
		t = stopWordFilter(t.toLowerCase().trim());
		
		double levenshteinRate = Levenshtein.getLevenshteinMappingRate(s, t);
		double lcsRate = LCS.getLCSMappingRate2(s, t);
		double containsRate = KeywordPriority.getKeywordPriorityRate(s, t);
		
		return new MappingRate(levenshteinRate, lcsRate, containsRate);
	}
	
	public static String stopWordsRegex() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < STOPWORDS.length; i++) {
			if (i > 0) {
				sb.append("|");
			}
			sb.append(STOPWORDS[i]);
		}
		return sb.toString();
	}
	
	public static String stopWordFilter(String str) {
		return str.replaceAll(stopWordRegex, " ").trim();
	}

	public static void main(String[] args) {
		List<Brand> brandList = new ArrayList<Brand>();

		Brand brand1 = new Brand();
		brand1.setName("NEZUMOKOZO");

		Brand brand2 = new Brand();
		brand2.setName("toshiba");

		Brand brand3 = new Brand();
		brand3.setName("konan");


		brandList.add(brand1);
		brandList.add(brand2);
		brandList.add(brand3);

		BrandMapping.compute("KONAMI", brandList, 1);
	}
	
}
