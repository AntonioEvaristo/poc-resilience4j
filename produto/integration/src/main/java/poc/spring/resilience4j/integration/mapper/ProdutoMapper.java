package poc.spring.resilience4j.integration.mapper;


import org.mapstruct.factory.Mappers;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.integration.entity.ProdutoEntity;

public abstract class ProdutoMapper {

    public static final ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    public abstract Produto produtoEntityToProduto(ProdutoEntity produtoEntity);
    public abstract ProdutoEntity produtoToProdutoEntity(Produto produto);
}
