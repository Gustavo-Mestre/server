package br.edu.utfpr.pb.tads.server.controller;

import br.edu.utfpr.pb.tads.server.dto.ProductRecordDto;
import br.edu.utfpr.pb.tads.server.model.ProductModel;
import br.edu.utfpr.pb.tads.server.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Estereótipo utilizado para indicar que é uma API Rest.
@RestController
public class ProductController {

    //utilizado para injetar dependência do repositório que o controller necessita para realizar suas funções.
    @Autowired
    ProductRepository productRepository;

    //Usado para inserir no banco de dados.
    @PostMapping("/product") //substantivo para referenciar o recurso do método POST da API.
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid /* faz as validações que foram declaradas no RecordDto*/ ProductRecordDto productRecordDto) {//método recebe como corpo da solicitação um RecordDto
        var productModel = new ProductModel(); //Cria-se um objeto para armazenar na base de dados, com os dados advindos do Dto (em Json).
        BeanUtils.copyProperties(productRecordDto, productModel); //Conversão do DTO para model.
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));// o status serve para infromar se o recurso foi criado ou não. O body forma o pcorpo da resposta, informando O QUE foi salvo.
    }

    //Usado para listar todos os produtos.
    @GetMapping("/product")
    public ResponseEntity<List<ProductModel>> getAllProduct() {
        return ResponseEntity.status(HttpStatus.OK /*Status 200*/).body(productRepository.findAll());
    }

    //Usado para listar um produto através do ID.
    @GetMapping("/product/{id}")//passa o ID na URI.
    public ResponseEntity<Object> getOneProduct(@PathVariable (value = "id") Long id /*Indica que haverá variacao na URI ao utilizar o atributo ID.*/) {
        Optional<ProductModel> product0 = productRepository.findById(id); //Optional fornece alguns métodos, como o "isEmpty" utilizado.
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found."); // Status e corpo da repsosta.
        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());//Se tiver encontrado, retorna o produto.
    }
}
