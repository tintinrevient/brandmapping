package io.github.tintinrevient.brandmapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import io.github.tintinrevient.brandmapping.domain.Brand;

public interface BrandListRepository extends JpaRepository<Brand, Long>{
    List<Brand> findAll();

}
