package poc.spring.resilience4j.integration.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.integration.entity.ProdutoEntity;

@Mapper(componentModel = "spring")
public abstract class ProdutoMapper {

    public static final ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    @Mapping(target = "categoria", source = "produtoEntity.categoriaEntity")
    public abstract Produto produtoEntityToProduto(ProdutoEntity produtoEntity);
    @Mapping(target = "categoriaEntity", source ="produto.categoria")
    public abstract ProdutoEntity produtoToProdutoEntity(Produto produto);
}
