package poc.spring.resilience4j.domain.repository;

import poc.spring.resilience4j.domain.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaRepository {

    Optional<Categoria> findByCodigo(String codigo);
    Categoria save(Categoria categoria);
    List<Categoria> findAll();
}
