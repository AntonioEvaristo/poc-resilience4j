package poc.spring.resilience4j.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import poc.spring.resilience4j.domain.model.Produto;

import java.util.Optional;

public interface IProdutoRepository {

    Optional<Produto> findByCodigo(String codigo);
    Optional<Produto> findById(Long id);
    Page<Produto> findByIsDisponivel(Boolean isDisponivel, Pageable pageable);
    Page<Produto> findByIsDisponivelAndCategoriaEntityNome(Boolean isDisponivel,String categoriaNome, Pageable pageable);
    Produto save(Produto produto);
}
