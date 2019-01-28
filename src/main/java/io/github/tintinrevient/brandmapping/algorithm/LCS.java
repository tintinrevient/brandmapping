package io.github.tintinrevient.brandmapping.algorithm;

public class LCS {

	/**
	 * Get the LCS mapping rate.
	 * 
	 * @param s	the first String, must not be null
	 * @param t	the second String, must not be null
	 * @return
	 */
	public static double getLCSMappingRate(CharSequence s, CharSequence t) {
		int count = getLCSCount(s, t);
		if (count == 0) {
			return 0;
		}
		return (double)count * 2 / (s.length() + t.length());
	}
	
	public static double getLCSMappingRate2(String s, String t) {
		String[] arrs = s.split("\\s+");
		String[] arrt = t.split("\\s+");
		
		int n = arrs.length;
		int m = arrt.length;
		
		int length = 0;
		int count = 0;
		
		for (int i = 0; i < n; i++) {
			String word = arrs[i];
			length += word.length();
			int lcsCount = getLCSCount(word, t);
			count += lcsCount;
		}
		
		for (int i = 0; i < m; i++) {
			String word = arrt[i];
			length += word.length();
			int lcsCount = getLCSCount(word, s);
			count += lcsCount;
		}
		
		return (double)count / length;
		
	}
	
	/**
	 * Get the longest common substring count.
	 * 
	 * @param s	the first String, must not be null
	 * @param t	the second String, must not be null
	 * @return
	 */
	public static int getLCSCount(CharSequence s, CharSequence t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}
		
		int n = s.length(); // length of s
		int m = t.length(); // length of t
		
		if (n == 0 || m == 0) {
			return 0;
	    }
		
		int[][] opt = new int[n + 1][m + 1];
		for (int i = n - 1; i >= 0; i--) {
			for (int j = m - 1; j >= 0; j--) {
				if (s.charAt(i) == t.charAt(j)) {
					opt[i][j] = opt[i + 1][j + 1] + 1;
				} else {
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
				}
			}
		}
		int i = 0, j = 0, k = 0;
		while (i < n && j < m) {
			if (s.charAt(i) == t.charAt(j)) {
				i++;
				j++;
				k++;
			} else if (opt[i + 1][j] >= opt[i][j + 1]) {
				i++;
			} else {
				j++;
			}
		}
		return k;
	}
	
}
