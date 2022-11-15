package poc.spring.resilience4j.domain.service;

import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.model.Produto;

import java.util.List;

public interface IProdutoService {
    Produto buscarProduto(String codigo) throws ProdutoException;
    List<Produto> listarProduto(Boolean isDisponivel, String nomeCategoria);
    Produto cadastrarProduto(Produto produto) throws ProdutoException;
    Produto atualizaProduto(Produto produto, Long id) throws ProdutoException;
}
