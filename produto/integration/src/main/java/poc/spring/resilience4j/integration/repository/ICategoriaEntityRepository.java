package poc.spring.resilience4j.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poc.spring.resilience4j.integration.entity.CategoriaEntity;

import java.util.Optional;

@Repository
public interface ICategoriaEntityRepository extends JpaRepository<CategoriaEntity, Long> {
    Optional<CategoriaEntity> findByCodigo(String codigo);
}
