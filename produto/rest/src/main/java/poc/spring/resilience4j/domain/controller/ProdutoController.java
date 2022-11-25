package poc.spring.resilience4j.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.domain.service.IProdutoService;

@RestController
@RequestMapping("/v1/rest/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final IProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<Produto>> listarProdutos(@RequestParam Boolean isDisponivel, @RequestParam(required = false) String nomeCategoria, Pageable pageable){
        return new ResponseEntity<>(produtoService.listarProduto(isDisponivel, nomeCategoria, pageable), HttpStatus.OK);
    }
}
