package br.com.compasso.productMondDB.controller.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compasso.productMondDB.controller.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
	
	//@Query("select p from Product p where p.price >= :min_price and p.price <= :max_price")
	@Query("{ $and : [ { 'price' : { $gte : ?0} } , {'price' : { $lte : ?1 } } ] }")
	List<Product> findByMinMaxPrice( Double min_price,  Double max_price);

	//@Query("select p from Product p where p.price >= :min_price and p.price <= :max_price and p.name = :paramName and p.description = :paramDescription")
	@Query("{ $and : [ { 'price' : { $gte : ?0} } , {'price' : { $lte : ?1 } }, {'name' : { $eq: ?2 } }, {'description' : { $eq: ?3 } } ] }")
	List<Product> findBySearch(@Param("min_price") Double min_price, @Param("max_price") Double max_price, String paramName, String paramDescription);
	
}