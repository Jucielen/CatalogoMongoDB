package br.com.compasso.productMongoDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compasso.productMongoDB.controller.dto.ProductDto;
import br.com.compasso.productMongoDB.controller.model.Product;
import br.com.compasso.productMongoDB.controller.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository repository;
	
	public List<ProductDto> findAll(){
		List<Product> prod = repository.findAll();
		return ProductDto.converter(prod);
		
	}
	
	public Optional<Product> findById(String id){
		Optional<Product> prod = repository.findById(id);
		return prod;
	}
	public List<Product> findByMinMaxPrice(double Min_price, double Max_price){
		List<Product> prod = repository.findByMinMaxPrice(Min_price, Max_price);
		return prod;
		
	}
	
	public List<Product> findBySearch(Double min_price, Double max_price, String name, String description) {
		List<Product> prod = repository.findBySearch(min_price, max_price, name, description);
		return prod;
	}
	public void save(Product prod) {
		repository.save(prod);
	}

	public void deleteById(String id) {
		repository.deleteById(id);
		
	}
	
	
}
