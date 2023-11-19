package com.init.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.products.entities.Product;

public interface ProductsDAO extends JpaRepository<Product, Long>{

}