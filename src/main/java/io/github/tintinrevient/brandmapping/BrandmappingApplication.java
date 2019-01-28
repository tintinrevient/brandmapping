package io.github.tintinrevient.brandmapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import java.io.File;
import java.nio.file.Files;

import io.github.tintinrevient.brandmapping.repository.BrandListRepository;
import io.github.tintinrevient.brandmapping.domain.Brand;

@SpringBootApplication
public class BrandmappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrandmappingApplication.class, args);
	}

	@Bean
	public CommandLineRunner ini(BrandListRepository repository) {
		return (args) -> {
			ClassLoader classLoader = new BrandmappingApplication().getClass().getClassLoader();
			File file = new File(classLoader.getResource("fortune500.json").getFile());

			String content = new String(Files.readAllBytes(file.toPath()));
			String[] lines = content.split("\n");
			for(int index = 4; index < lines.length - 2; index++){
				String line = lines[index];
				String name = line.trim().replaceAll("\"", "").replaceAll(",", "");

				Brand brand = new Brand();
				brand.setName(name);
				repository.save(brand);
			}
		};
	}

}

