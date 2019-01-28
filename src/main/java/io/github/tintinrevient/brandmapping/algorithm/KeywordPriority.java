package io.github.tintinrevient.brandmapping.algorithm;

import io.github.tintinrevient.brandmapping.BrandMapping;

public class KeywordPriority {

	public static double getKeywordPriorityRate(String s, String t) {
		if (s.replaceAll("\\s+", "").equals(t.replaceAll("\\s+", ""))) {
			return 1.0d;
		}
		
		String[] arr1 = s.split("\\s+");
		String[] arr2 = t.split("\\s+");
		
		double count = 0.0d;
		
		for (int i = 0; i < arr1.length; i++) {
			String word = arr1[i];
			if (containWord(word, arr2)) {
				count += (arr1.length - i) / (double)arr1.length;
			}
		}
		double sRate = count / totalRate(arr1.length);
		
		count = 0.0d;
		
		for (int i = 0; i < arr2.length; i++) {
			String word = arr2[i];
			if (containWord(word, arr1)) {
				count += (arr2.length - i) / (double)arr2.length;
			}
		}
		double tRate = count / totalRate(arr2.length);
		
		return (sRate + tRate) / 2;
	}
	
	public static double getKeywordPriorityRate2(String s, String t) {
		if (s.replaceAll("\\s+", "").equals(t.replaceAll("\\s+", ""))) {
			return 1.0d;
		}
		
		String[] arr1 = s.split("\\s+");
		String[] arr2 = t.split("\\s+");
		
		int count = 0;
		
		for (int i = 0; i < arr1.length; i++) {
			String word1 = arr1[i];
			for (int j = 0; j < arr2.length; j++) {
				String word2 = arr2[j];
				if (word1.equals(word2)) {
					count++;
				}
			}
		}
		
		for (int i = 0; i < arr2.length; i++) {
			String word1 = arr2[i];
			for (int j = 0; j < arr1.length; j++) {
				String word2 = arr1[j];
				if (word1.equals(word2)) {
					count++;
				}
			}
		}
		return (double)count / (arr1.length + arr2.length);
	}
	
	private static double totalRate(int length) {
		int i = length;
		double rate = 0.0d;
		while (i > 0) {
			rate += (double)i / length;
			i--;
		}
		return rate;
	}
	
	private static boolean containWord(String str, String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			String word = arr[i];
			if (word.equals(str) || word.replaceAll("\\s+", "").equals(str.replaceAll("\\s+", ""))) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		String s = "Bio-Nutritional".toLowerCase();
		String t = "BIONUTRITIONAL RESEARCH GROUP".toLowerCase();
		
		s = s.replaceAll(BrandMapping.stopWordRegex, " ").trim();
		t = t.replaceAll(BrandMapping.stopWordRegex, " ").trim();
		
		
		System.out.println(s + " " + t);
		System.out.println(KeywordPriority.totalRate(2));
		System.out.println(KeywordPriority.getKeywordPriorityRate(s, t));
	}
}
