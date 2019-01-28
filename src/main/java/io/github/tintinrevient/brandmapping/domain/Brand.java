package io.github.tintinrevient.brandmapping.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Brand domain
 *
 *
 * @author Zhao Shu
 *
 */

@Entity
public class Brand {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long code;

	private String name;
	private double score;

	private double levenshteinRate;
	private double lcsRate;
	private double containRate;
	private double averageRate;

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getLevenshteinRate() {
		return levenshteinRate;
	}

	public void setLevenshteinRate(double levenshteinRate) {
		this.levenshteinRate = levenshteinRate;
	}

	public double getLcsRate() {
		return lcsRate;
	}

	public void setLcsRate(double lcsRate) {
		this.lcsRate = lcsRate;
	}

	public double getContainRate() {
		return containRate;
	}

	public void setContainRate(double containRate) {
		this.containRate = containRate;
	}

	public double getAverageRate() {
		return this.levenshteinRate * 0.15 + this.lcsRate * 0.15 + this.containRate * 0.7;
	}

	public void setAverageRate(double averageRate) {
		this.averageRate = averageRate;
	}

	public Brand clone(){
		Brand brand = new Brand();
		brand.setCode(this.code);
		brand.setName(this.name);
		brand.setScore(this.score);

		brand.setLevenshteinRate(this.levenshteinRate);
		brand.setLcsRate(this.lcsRate);
		brand.setContainRate(this.containRate);
		brand.setAverageRate(this.averageRate);
		
		return brand;
	}

}