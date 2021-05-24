package br.com.compasso.productMongoDB.controller.form;

import javax.validation.constraints.NotBlank;

import br.com.compasso.productMongoDB.controller.model.Product;

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
	public Product atualizar(Product prod) {
		prod.setName(this.name);
		prod.setDescription(this.description);
		prod.setPrice(Double.valueOf(this.price));
		return prod;
	}
}
