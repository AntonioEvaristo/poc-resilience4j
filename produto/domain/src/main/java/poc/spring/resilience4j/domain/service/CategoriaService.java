package poc.spring.resilience4j.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import poc.spring.resilience4j.domain.exception.CategoriaException;
import poc.spring.resilience4j.domain.model.Categoria;
import poc.spring.resilience4j.domain.repository.ICategoriaRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public final class CategoriaService implements ICategoriaService{

    private final ICategoriaRepository categoriaRepository;

    @Override
    public Categoria buscarCategoria(String codigo) throws CategoriaException {
        Categoria categoria = getCategoria(codigo).orElseThrow(() -> {
            log.error("Categoria não localizada {}", codigo);
            return new CategoriaException("Categoria não localizada, verifique o codigo da categoria!");
        });
        log.info("Categoria {}", categoria);
        return categoria;
    }

    private Optional<Categoria> getCategoria(String codigo) {
        return categoriaRepository.findByCodigo(codigo);
    }

    @Override
    public List<Categoria> listarCategoria() {
        return categoriaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Categoria::getNome))
                .collect(Collectors.toList());
    }

    @Override
    public Categoria cadastrarCategoria(Categoria categoria) throws CategoriaException {
        if(getCategoria(categoria.getCodigo()).isPresent()){
            log.error("Categoria já existe com esse codigo! {}", categoria.getCodigo());
            throw new CategoriaException("Categoria já existe, verifique o codigo da categoria");
        }
        Categoria categoriaSave = categoriaRepository.save(categoria);
        log.info("Categoria {}", categoriaSave);
        return categoriaSave;
    }
}
