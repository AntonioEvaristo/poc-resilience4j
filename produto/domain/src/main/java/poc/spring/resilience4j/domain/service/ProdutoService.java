package poc.spring.resilience4j.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.exception.ProdutoNotFoundException;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.domain.repository.IProdutoRepository;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public final class ProdutoService implements IProdutoService{

    private final IProdutoRepository produtoRepository;

    @Override
    public Produto buscarProduto(String codigo) throws ProdutoNotFoundException {
        Produto produto = getProduto(codigo).orElseThrow(() -> {
            log.error("Produto não localizado {}", codigo);
            return new ProdutoNotFoundException("Produto não localizado, verifique o codigo do produto!");
        });
        log.info("Produto {}", produto);
        return produto;
    }

    @Override
    public Page<Produto> listarProduto(Boolean isDisponivel, String nomeCategoria, Pageable pageable) {
        return filtraListaProdutos(isDisponivel, nomeCategoria, pageable);
    }

    private Page<Produto> filtraListaProdutos(Boolean isDisponivel, String nomeCategoria, Pageable pageable) {
        if(StringUtils.isNotBlank(nomeCategoria)){
            Page<Produto> produtosPorNomeCategoria = produtoRepository.findByIsDisponivelAndCategoriaEntityNome(isDisponivel, nomeCategoria, pageable);
            log.info("Lista de produtos filtrada por nome da categoria {} ", produtosPorNomeCategoria);
            return produtosPorNomeCategoria;
        }
        Page<Produto> produtos = produtoRepository.findByIsDisponivel(isDisponivel, pageable);
        log.info("Lista de produtos por flag de disponivel {}", produtos);
        return produtos;
    }

    @Override
    public Produto cadastrarProduto(Produto produto) throws ProdutoException {
        if(getProduto(produto.getCodigo()).isPresent()){
            log.error("Produto já existe com esse codigo! {}", produto);
            throw new ProdutoException("Produto já existe, verifique o codigo do produto!!");
        }
        Produto produtoSave = produtoRepository.save(produto);
        log.info("Produto salvo {}", produtoSave);
        return produtoSave;
    }

    @Override
    public Produto atualizaProduto(Produto produto, Long id) throws ProdutoNotFoundException {
        Produto produtoEntity = produtoRepository.findById(id).orElseThrow(() -> {
            log.error("Produto não existe! {}", produto);
            return new ProdutoNotFoundException("Produto não existe, verifique o codigo/id do produto!!");
        });
        produto.setId(produtoEntity.getId());
        produto.getCategoria().setId(produtoEntity.getCategoria().getId());
        Produto produtoUpdate = produtoRepository.save(produto);
        log.info("Produto atualizado! {}", produtoUpdate);
        return produtoUpdate;
    }

    private Optional<Produto> getProduto(String codigo) {
        return produtoRepository.findByCodigo(codigo);
    }

}
