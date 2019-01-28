package io.github.tintinrevient.brandmapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;

import io.github.tintinrevient.brandmapping.domain.Brand;
import io.github.tintinrevient.brandmapping.utils.CounterMap;
import io.github.tintinrevient.brandmapping.utils.MathUtils;
import io.github.tintinrevient.brandmapping.algorithm.QGramMap;
import io.github.tintinrevient.brandmapping.utils.StringUtils;
import io.github.tintinrevient.brandmapping.repository.BrandListRepository;

/**
 * Brand Controller
 *
 * @author Zhao Shu
 *
 */
@Controller
@RequestMapping("/")
public class BrandController {

	private BrandListRepository brandListRepository;

	@Autowired
	public BrandController(BrandListRepository brandListRepository) {
		this.brandListRepository = brandListRepository;
	}


	/**
	 * Compute the matched top-k brand names for the input brand
	 *
	 * @throws Exception
	 */
	@RequestMapping(value="/{brand}", method = RequestMethod.GET)
	public String getBrands(@PathVariable("brand") String name, Model model) {
		DecimalFormat df = new DecimalFormat("0.00");

		name = StringUtils.uniform(name);

		long startTime = System.currentTimeMillis();

		int q = 3;
		int length = name.length() - q + 1;

		List<Brand> brandListRepositoryAll = brandListRepository.findAll();

        QGramMap.invertedList(brandListRepositoryAll, q);

		CounterMap<Brand> counterMap = QGramMap.approxStringMatch(name, q);
		Brand[] matches = counterMap.keysOrderedByCountList().toArray(new Brand[0]);

		Map<Brand, Double> hashMap = new HashMap();

		for(Brand brand : matches){
			String match = brand.getName();
			int count = counterMap.getCount(brand);
			double rate1 = ((double) count)/((double) length);
			double rate2 = MathUtils.getDiff(name, match);

			double rate = MathUtils.getWeightedAvg(rate1, rate2);

			hashMap.put(brand, rate);
		}

		TreeMap<Brand, Double> treeMap = MathUtils.getSortedHashMap(hashMap);

		Map<Brand, Double> topkHashMap = new HashMap<Brand, Double>();
		List<Brand> brandList = new ArrayList<Brand>();

		int i = 0;
		int topk = 1;
		if(topk == -1){
			brandList = StringUtils.getBrandList(treeMap);
		}else{
			for(Entry<Brand, Double> entry : treeMap.entrySet()){
				if((++i) > topk)
					break;

				Brand brand = entry.getKey();
				double score = entry.getValue();
				topkHashMap.put(brand, score);
			}

			TreeMap<Brand, Double> topkTreeMap = MathUtils.getSortedHashMap(topkHashMap);
			brandList = StringUtils.getBrandList(topkTreeMap);
		}


		long endTime = System.currentTimeMillis();

		if(brandList != null) {
			model.addAttribute("brands", brandList);
		}

		return "brandList";
	}

	@RequestMapping(value="/{brand}", method = RequestMethod.POST)
	public String addBrands(@PathVariable("brand") String name) {

		Brand brand = new Brand();
		brand.setName(name);
		brandListRepository.save(brand);

		return "redirect:/{brand}";
	}

}