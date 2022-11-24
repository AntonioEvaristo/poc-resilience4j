package poc.spring.resilience4j.integration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import poc.spring.resilience4j.integration.entity.ProdutoEntity;

import java.util.Optional;

@Repository
public interface IProdutoEntityRepository extends PagingAndSortingRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findByCodigo(String codigo);
    Page<ProdutoEntity> findByIsDisponivel(Boolean isDisponivel, Pageable pageable);
    Page<ProdutoEntity> findByIsDisponivelAndCategoriaEntityNome(Boolean isDisponivel,String categoriaNome, Pageable pageable);
}
