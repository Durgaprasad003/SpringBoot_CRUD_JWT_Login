package com.learningstage.demo.DAO;

import com.learningstage.demo.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
}

//if we dont mention repository here it will handle because spring will scan at runtime for jparepository
//during runtime it will create the bean