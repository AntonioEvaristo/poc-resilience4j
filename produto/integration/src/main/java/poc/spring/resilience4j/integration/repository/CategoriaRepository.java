package poc.spring.resilience4j.integration.repository;

import lombok.RequiredArgsConstructor;
import poc.spring.resilience4j.domain.model.Categoria;
import poc.spring.resilience4j.domain.repository.ICategoriaRepository;
import poc.spring.resilience4j.integration.mapper.CategoriaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoriaRepository implements ICategoriaRepository {
    private final ICategoriaEntityRepository categoriaEntityRepository;
    @Override
    public Optional<Categoria> findByCodigo(String codigo) {
        return categoriaEntityRepository.findByCodigo(codigo).map(CategoriaMapper.INSTANCE::categoriaEntityToCategoria);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return CategoriaMapper.INSTANCE.categoriaEntityToCategoria(categoriaEntityRepository.save(CategoriaMapper.INSTANCE.categoriaToCategoriaEntity(categoria)));
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaEntityRepository.findAll()
                .stream()
                .map(CategoriaMapper.INSTANCE::categoriaEntityToCategoria)
                .collect(Collectors.toList());
    }
}
