package poc.spring.resilience4j.domain.service;

import poc.spring.resilience4j.domain.exception.CategoriaException;
import poc.spring.resilience4j.domain.model.Categoria;

import java.util.List;

public interface ICategoriaService {

    Categoria buscarCategoria(String codigo) throws CategoriaException;
    List<Categoria> listarCategoria();
    Categoria cadastrarCategoria(Categoria categoria) throws CategoriaException;

}
