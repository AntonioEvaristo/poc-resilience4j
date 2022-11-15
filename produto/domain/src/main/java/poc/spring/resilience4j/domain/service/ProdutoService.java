package poc.spring.resilience4j.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.domain.repository.IProdutoRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public final class ProdutoService implements IProdutoService{

    private final IProdutoRepository produtoRepository;

    @Override
    public Produto buscarProduto(String codigo) throws ProdutoException {
        Produto produto = getProduto(codigo).orElseThrow(() -> {
            log.error("Produto não localizado {}", codigo);
            return new ProdutoException("Produto não localizado, verifique o codigo do produto!");
        });
        log.info("Produto {}", produto);
        return produto;
    }

    @Override
    public List<Produto> listarProduto(Boolean isDisponivel, String nomeCategoria) {
        List<Produto> produtos = produtoRepository
                .findAll()
                .stream()
                .filter(produto -> produto.getIsDisponivel().equals(isDisponivel))
                .collect(Collectors.toList());

        if (StringUtils.isNotBlank(nomeCategoria)) {
            produtos = produtos
                    .stream()
                    .filter(getProdutoNomeCategoria(nomeCategoria))
                    .collect(Collectors.toList());
            log.info("Filtrando lista por nomeCategoria {}", nomeCategoria);
        }
        produtos = produtos
                .stream()
                .sorted(Comparator.comparing(Produto::getValor))
                .collect(Collectors.toList());
        log.info("Lista de todos produtos {}", produtos);
        return produtos;
    }

    @Override
    public Produto cadastrarProduto(Produto produto) throws ProdutoException {
        if(getProduto(produto.getCodigo()).isPresent()){
            log.error("Produto já existe com esse codigo! {}", produto);
            throw  new ProdutoException("Produto já existe, verifique o codigo do produto!!");
        }
        Produto produtoSave = produtoRepository.save(produto);
        log.info("Produto salvo {}", produtoSave);
        return produtoSave;
    }

    @Override
    public Produto atualizaProduto(Produto produto, Long id) throws ProdutoException {
        Produto produtoEntity = produtoRepository.findById(id).orElseThrow(() -> {
            log.error("Produto não existe! {}", produto);
            return new ProdutoException("Produto não existe, verifique o codigo/id do produto!!");
        });
        produto.setId(produtoEntity.getId());
        produto.getCategoria().setId(produtoEntity.getCategoria().getId());
        Produto produtoSave = produtoRepository.update(produto);
        log.info("Produto atualizado! {}", produtoSave);
        return produtoSave;
    }

    private Optional<Produto> getProduto(String codigo) {
        return produtoRepository.findByCodigo(codigo);
    }

    private static Predicate<Produto> getProdutoNomeCategoria(String nomeCategoria) {
        return prd -> prd.getCategoria().getNome().equals(nomeCategoria);
    }


}
