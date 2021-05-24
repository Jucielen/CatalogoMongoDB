package br.com.compasso.productMongoDB.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.productMongoDB.controller.dto.ProductDto;
import br.com.compasso.productMongoDB.controller.form.ProductForm;
import br.com.compasso.productMongoDB.controller.model.MensagensErro;
import br.com.compasso.productMongoDB.controller.model.Product;
import br.com.compasso.productMongoDB.controller.repository.ProductRepository;
import br.com.compasso.productMongoDB.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService service;
	
	@GetMapping
	public List<ProductDto> lista(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable String id){
		Optional <Product> prod = service.findById(id);
		if(prod.isPresent()) {
			return ResponseEntity.ok(new ProductDto(prod.get()));
		}
		return ResponseEntity.status(404).body(new MensagensErro(404,"Produto não Encontrado"));
	}

	@GetMapping("/search")
	public List<ProductDto> listaFiltrada(@RequestParam(required = false) String q, @RequestParam(required = false) Double min_price, @RequestParam(required = false) Double max_price){
		
		List<ProductDto> lista = null;
		
		if(min_price == null) {
			min_price= 0.0;
		}
		if(max_price == null) {
			max_price= 10000.0;
		}
		if (q==null) {
			List<Product> produtos = service.findByMinMaxPrice(min_price, max_price);
			lista = ProductDto.converter(produtos);
		}else {
			// preenchimento da q="name=exemplo,description=exemplo"
			String parametros = q;
			String[] split = parametros.split(","); 
			String[] paramName = split[0].split("=");
			String name = paramName[1];
			String[] paramDescription = split[1].split("=");
			String description = paramDescription[1].replace("\"", "");
			System.out.println(name+" "+description);		
			List<Product> produtos = service.findBySearch(min_price, max_price, name, description);
			lista = ProductDto.converter(produtos);
		}
		return lista;
	}
	
	@PostMapping
	public ResponseEntity<?> newProduct(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder, BindingResult result){
		if(result.hasErrors()) {
			System.out.println("Erro");
			return ResponseEntity.status(400).body(new MensagensErro(400,"Verifique as informações informadas"));
		}
		
		Product prod = form.converter();
		service.save(prod);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(prod.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProductDto(prod));
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody @Valid ProductForm form, BindingResult result){
		Optional<Product> optional = service.findById(id);
		if(optional.isPresent()) {
			Product prod = form.atualizar(optional.get());
			service.save(prod);
			return ResponseEntity.ok().body(new ProductDto(prod));
		}
		return ResponseEntity.status(404).body(new MensagensErro(404,"Produto não encontrado para a realização de alterações"));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable String id){
		Optional<Product> optional = service.findById(id);
		if(optional.isPresent()) {
			service.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(404).body(new MensagensErro(404,"Produto não encontrado para a realização de remoção"));
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> onError() {
		return ResponseEntity.status(400).body(new MensagensErro(400,"Verifique as informações informadas"));
	}
}
