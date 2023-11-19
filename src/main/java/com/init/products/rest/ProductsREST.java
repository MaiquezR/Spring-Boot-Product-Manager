package com.init.products.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.init.products.dao.ProductsDAO;
import com.init.products.entities.Product;

@RestController
@RequestMapping("products")
public class ProductsREST {
	
	@Autowired
	private ProductsDAO productsDAO;
	
	//Requesting a list of products
	@GetMapping
	public ResponseEntity<List<Product>> getProduct(){
		List<Product> products =productsDAO.findAll();
		return ResponseEntity.ok(products);
	}
	
	//Requesting a single product by the ID
	@RequestMapping(value="{productId}") // /products/{productId} -> /products/1
	public ResponseEntity<Product> getProductById(@PathVariable("productId")Long productId){
		Optional<Product> optionalProduct = productsDAO.findById(productId);
		if(optionalProduct.isPresent()){
			return ResponseEntity.ok(optionalProduct.get());
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	//Creating a product
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productsDAO.save(product);
		return ResponseEntity.ok(newProduct);
	}
	
	//Update an existing product
	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		Optional<Product> optionalProduct = productsDAO.findById(product.getId());
		if(optionalProduct.isPresent()) {
			Product updateProduct = optionalProduct.get();
			updateProduct.setName(product.getName());
			productsDAO.save(updateProduct);
			return ResponseEntity.ok(updateProduct);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//Remove a product
	@DeleteMapping(value="{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("productId")Long productId){
		productsDAO.deleteById(productId);
		return ResponseEntity.ok(null);
	}
}
