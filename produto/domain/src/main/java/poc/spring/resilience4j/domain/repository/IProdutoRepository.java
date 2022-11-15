package poc.spring.resilience4j.domain.repository;

import poc.spring.resilience4j.domain.model.Produto;

import java.util.List;
import java.util.Optional;

public interface IProdutoRepository {

    Optional<Produto> findByCodigo(String codigo);
    Produto save(Produto produto);
    List<Produto> findAll();
    Produto update(Produto produto);
    Optional<Produto> findById(Long id);
}
