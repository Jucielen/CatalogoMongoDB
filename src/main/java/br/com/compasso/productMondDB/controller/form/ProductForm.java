package br.com.compasso.productMondDB.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import br.com.compasso.productMondDB.controller.model.Product;
import br.com.compasso.productMondDB.controller.repository.ProductRepository;

public class ProductForm {
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotBlank
	private String price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public Product converter() {
		Double pricedouble =Double.valueOf(price);
		return new Product(name, description, pricedouble);
	}
	public Product atualizar(String id, ProductRepository repository) {
		Optional<Product> prod = repository.findById(id);
		prod.get().setName(this.name);
		prod.get().setDescription(this.description);
		prod.get().setPrice(Double.valueOf(this.price));
		repository.save(prod.get());
		return prod.get();
	}
}
