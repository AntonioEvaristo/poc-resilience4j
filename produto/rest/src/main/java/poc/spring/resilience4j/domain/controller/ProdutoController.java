package poc.spring.resilience4j.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.exception.ProdutoNotFoundException;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.domain.service.IProdutoService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/rest/produto")
@RequiredArgsConstructor
public class ProdutoController {
    private final IProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<Produto>> findAll(@RequestParam Boolean isDisponivel, @RequestParam(required = false) String nomeCategoria, Pageable pageable){
        return new ResponseEntity<>(produtoService.listarProduto(isDisponivel, nomeCategoria, pageable), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> getProduto(@PathVariable String codigo) throws ProdutoNotFoundException {
        return new ResponseEntity<>(produtoService.buscarProduto(codigo),HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Produto> saveProduto(@RequestBody @Valid Produto produto) throws ProdutoException {
        return new ResponseEntity<>(produtoService.cadastrarProduto(produto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Produto> putProduto(@RequestBody @Valid Produto produto, @PathVariable Long id) throws ProdutoNotFoundException {
        return new ResponseEntity<>(produtoService.atualizaProduto(produto,id), HttpStatus.OK);
    }
}
