package poc.spring.resilience4j.integration.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import poc.spring.resilience4j.domain.model.Categoria;
import poc.spring.resilience4j.integration.entity.CategoriaEntity;

@Mapper(componentModel = "spring")
public abstract class CategoriaMapper {

    public static final  CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);
    public abstract CategoriaEntity categoriaToCategoriaEntity(Categoria categoria);
    public abstract Categoria categoriaEntityToCategoria(CategoriaEntity categoriaEntity);
}
