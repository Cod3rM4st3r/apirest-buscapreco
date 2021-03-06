package com.apirest.resources;
import com.apirest.models.Categoria;
import com.apirest.models.HistoricoMelhorPreco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apirest.repository.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.apirest.models.Produto;
import com.apirest.repository.HistoricoMelhorPrecoRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value="/api")
@Api(value="API REST Produtos")
@CrossOrigin(origins="*", allowedHeaders = "*") //Determina quais domínios podem acessar a API
public class ProdutoResource {
    
	
	@Autowired
	ProdutoRepository produtoRepository;
        
        @Autowired
        HistoricoMelhorPrecoRepository his;
        
	@ApiOperation(value = "Mostra uma lista de produtos")
	@GetMapping("/produtos")
	public List<Produto> listaProdutos(){
            
            return produtoRepository.findAll();
	}
	
	@ApiOperation(value="Mostra um unico produto")
	@GetMapping("/produto/{id}")
	public Produto listaProdutoUnico(@PathVariable(value="id")long id) {
		return produtoRepository.findById(id);
	}
	
	@ApiOperation(value="Salva um Produto")
	@PostMapping("/produto")
	public Produto salvarProduto(@RequestBody @Valid Produto produto) {
		return produtoRepository.save(produto);
	}
	 
	@ApiOperation(value="Deleta um produto")
	@DeleteMapping("/produto/{id}")
	public void deletarProduto(@PathVariable(value="id")long id) {
		produtoRepository.delete(produtoRepository.findById(id));
	}
	
        @ApiOperation(value="Mostra um unico produto")
	@GetMapping("/produtoN/{name}")
        public Produto listProdByName(@PathVariable(value = "name") String name){
            return null;//produtoRepository.findByDescproduto(name);
        }
        
	@ApiOperation(value="Atualiza um produto")
	@PutMapping("/produto")
	public Produto atualizarProduto(@RequestBody @Valid Produto produto) {
            return produtoRepository.save(produto);
	}
        
        @PostMapping("/produtocat")
        public List<Produto> getProdPerCat(@RequestBody @Valid Categoria id){
            return produtoRepository.findByCategoria(id);
        }
        
        /*Metodo responsável para receber a foto, atualmente ainda está sendo efetuado os testes */
       @RequestMapping(method=RequestMethod.POST, value="/send/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
       public String receiveData(@RequestParam MultipartFile foto,@PathVariable(value="id")int id){
           Produto p = produtoRepository.findById(id);
           try{
               p.convert(foto);
               produtoRepository.save(p);
                return "Ok";
           }catch(Exception e){
               return e.getMessage();
               
           }
           
       }

}
