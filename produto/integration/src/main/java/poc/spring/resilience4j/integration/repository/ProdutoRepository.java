package poc.spring.resilience4j.integration.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.domain.repository.IProdutoRepository;
import poc.spring.resilience4j.integration.mapper.ProdutoMapper;

import java.util.Optional;

@RequiredArgsConstructor
public class ProdutoRepository implements IProdutoRepository {
    private final IProdutoEntityRepository produtoEntityRepository;

    @Override
    public Optional<Produto> findByCodigo(String codigo) {
        return produtoEntityRepository.findByCodigo(codigo)
                .map(ProdutoMapper.INSTANCE::produtoEntityToProduto);
    }
    @Override
    public Produto save(Produto produto) {
        return ProdutoMapper.INSTANCE.produtoEntityToProduto(produtoEntityRepository.save(ProdutoMapper.INSTANCE.produtoToProdutoEntity(produto)));
    }

    @Override
    public Page<Produto> findByIsDisponivel(Boolean isDisponivel, Pageable pageable) {
        return produtoEntityRepository.findByIsDisponivel(isDisponivel, pageable)
                .map(ProdutoMapper.INSTANCE::produtoEntityToProduto);
    }

    @Override
    public Page<Produto> findByIsDisponivelAndCategoriaEntityNome(Boolean isDisponivel, String categoriaNome, Pageable pageable) {
        return produtoEntityRepository.findByIsDisponivelAndCategoriaEntityNome(isDisponivel, categoriaNome, pageable)
                .map(ProdutoMapper.INSTANCE::produtoEntityToProduto);
    }
    @Override
    public Optional<Produto> findById(Long id) {
        return produtoEntityRepository.findById(id).map(ProdutoMapper.INSTANCE::produtoEntityToProduto);
    }

}
