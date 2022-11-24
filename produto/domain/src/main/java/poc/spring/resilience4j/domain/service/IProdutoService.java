package poc.spring.resilience4j.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.model.Produto;

public interface IProdutoService {
    Produto buscarProduto(String codigo) throws ProdutoException;
    Page<Produto> listarProduto(Boolean isDisponivel, String nomeCategoria, Pageable pageable);
    Produto cadastrarProduto(Produto produto) throws ProdutoException;
    Produto atualizaProduto(Produto produto, Long id) throws ProdutoException;
}
