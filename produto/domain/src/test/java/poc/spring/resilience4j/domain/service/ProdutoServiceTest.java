package poc.spring.resilience4j.domain.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.model.Categoria;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.domain.repository.IProdutoRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdutoServiceTest {

    @InjectMocks
    public ProdutoService produtoService;
    @Mock
    private IProdutoRepository produtoRepository;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Produto produto;
    private List<Produto> produtos;

    private PageRequest page;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        page = PageRequest.of(0, 20);
        produto = Produto.builder()
                .id(1L)
                .codigo("100")
                .nome("Garrafa")
                .descricao("Garrafa para armazenado de agua")
                .valor(BigDecimal.TEN)
                .quantidade(100)
                .isDisponivel(Boolean.TRUE)
                .categoria(Categoria.builder()
                        .codigo("1")
                        .nome("utensilios")
                        .build())
                .build();
        produtos = Arrays.asList(Produto.builder()
                        .codigo("123")
                        .nome("FIFA2023")
                        .descricao("Jogo de futebol")
                        .valor(BigDecimal.TEN)
                        .quantidade(0)
                        .isDisponivel(Boolean.FALSE)
                        .categoria(Categoria.builder()
                                .codigo("2")
                                .nome("Gamer")
                                .build())
                        .build(),
                Produto.builder()
                        .codigo("124")
                        .nome("COD")
                        .descricao("Jogo de FPS")
                        .valor(BigDecimal.TEN)
                        .quantidade(50)
                        .isDisponivel(Boolean.TRUE)
                        .categoria(Categoria.builder()
                                .codigo("2")
                                .nome("Gamer")
                                .build())
                        .build(),
                Produto.builder()
                        .codigo("333")
                        .nome("Colher")
                        .descricao("Colher de prata")
                        .valor(BigDecimal.TEN)
                        .quantidade(0)
                        .isDisponivel(Boolean.FALSE)
                        .categoria(Categoria.builder()
                                .codigo("1")
                                .nome("utensilios")
                                .build())
                        .build(),
                Produto.builder()
                        .codigo("99")
                        .nome("Taça")
                        .descricao("Taça de vinho")
                        .valor(BigDecimal.TEN)
                        .quantidade(25)
                        .isDisponivel(Boolean.TRUE)
                        .categoria(Categoria.builder()
                                .codigo("1")
                                .nome("utensilios")
                                .build())
                        .build(),
                Produto.builder()
                        .codigo("7")
                        .nome("MK10")
                        .descricao("Mortal Kombate")
                        .valor(BigDecimal.TEN)
                        .quantidade(25)
                        .isDisponivel(Boolean.TRUE)
                        .categoria(Categoria.builder()
                                .codigo("1")
                                .nome("Gamer")
                                .build())
                        .build());
    }

    @Test
    public void buscarProdutoDisponivelComSucesso() throws ProdutoException {
        Mockito.when(produtoRepository.findByCodigo(Mockito.anyString())).thenReturn(Optional.of(produto));
        Produto produtoRetorno = produtoService.buscarProduto(produto.getCodigo());
        Assert.assertNotNull(produtoRetorno);
        Assert.assertEquals(produto.getCodigo(), produtoRetorno.getCodigo());
        Assert.assertEquals(Boolean.TRUE, produtoRetorno.getIsDisponivel());
        Assert.assertEquals(produto.getCategoria().getNome(), produtoRetorno.getCategoria().getNome());
        Assert.assertEquals(produto, produtoRetorno);
    }

    @Test
    public void buscarProdutoException() throws ProdutoException {
        expectedException.expect(ProdutoException.class);
        expectedException.expectMessage("Produto não localizado, verifique o codigo do produto!");
        produtoService.buscarProduto("");
    }

    @Test
    public void cadastrarProdutoComSucesso() throws ProdutoException {
        Mockito.when(produtoRepository.findByCodigo(produto.getCodigo())).thenReturn(Optional.empty());
        Mockito.when(produtoRepository.save(produto)).thenReturn(produto);
        Produto produtoSalvo = produtoService.cadastrarProduto(produto);
        Assert.assertNotNull(produtoSalvo);
        Mockito.verify(produtoRepository, Mockito.times(1)).findByCodigo(produto.getCodigo());
        Mockito.verify(produtoRepository, Mockito.times(1)).save(produto);
    }

    @Test
    public void cadastrarProdutoException() throws ProdutoException {
        Mockito.when(produtoRepository.findByCodigo(Mockito.any())).thenReturn(Optional.of(produto));
        expectedException.expect(ProdutoException.class);
        expectedException.expectMessage("Produto já existe, verifique o codigo do produto!!");
        produtoService.cadastrarProduto(produto);
    }

    @Test
    public void atualizarProdutoComSucesso() throws ProdutoException {
        Long id = 1L;
        Produto prd = Produto.builder()
                .id(id)
                .codigo("100")
                .nome("Garrafa")
                .valor(BigDecimal.ONE)
                .quantidade(50)
                .isDisponivel(Boolean.TRUE)
                .categoria(Categoria.builder()
                        .codigo("1")
                        .nome("utensilios")
                        .build())
                .build();
        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.save(prd)).thenReturn(prd);
        Produto produtoAtualizado = produtoService.atualizaProduto(prd, id);
        Mockito.verify(produtoRepository, Mockito.times(1)).save(prd);
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(id);
        Assert.assertNotNull(produtoAtualizado);
        Assert.assertEquals(prd.getQuantidade(), produtoAtualizado.getQuantidade());
        Assert.assertEquals(prd.getValor(), produtoAtualizado.getValor());
        Assert.assertEquals(id, produtoAtualizado.getId());
    }

    @Test
    public void atualizarProdutoException() throws ProdutoException {
        Long id = 1L;
        Mockito.when(produtoRepository.findById(produto.getId())).thenReturn(Optional.empty());
        expectedException.expect(ProdutoException.class);
        expectedException.expectMessage("Produto não existe, verifique o codigo/id do produto!!");
        produtoService.atualizaProduto(produto, id);

    }

    @Test
    public void listaProdutosDisponiveis() {
        Page<Produto> produtosDisponiveis = new PageImpl<>(produtos.stream()
                .filter(produto -> Boolean.TRUE.equals(produto.getIsDisponivel())).collect(Collectors.toList()));
        Mockito.when(produtoRepository.findByIsDisponivel(Boolean.TRUE, page)).thenReturn(produtosDisponiveis);
        Page<Produto> produtosPage = produtoService.listarProduto(Boolean.TRUE, "  ", page);
        Assert.assertFalse(produtosPage.isEmpty());
        Assert.assertEquals(Boolean.TRUE, produtosPage.iterator().next().getIsDisponivel());
    }

    @Test
    public void listaProdutosNaoDisponiveis() {
        Page<Produto> produtosDisponiveis = new PageImpl<>(produtos.stream()
                .filter(produto -> Boolean.FALSE.equals(produto.getIsDisponivel())).collect(Collectors.toList()));
        Mockito.when(produtoRepository.findByIsDisponivel(Boolean.FALSE, page)).thenReturn(produtosDisponiveis);
        Page<Produto> produtosPage = produtoService.listarProduto(Boolean.FALSE, "  ", page);
        Assert.assertFalse(produtosPage.isEmpty());
        Assert.assertEquals(Boolean.FALSE, produtosPage.iterator().next().getIsDisponivel());
    }

    @Test
    public void listaProdutosDisponivelPorCategoria(){
        String nomeCategoria = "Gamer";
        Page<Produto> produtosDisponiveisPorCategoria = new PageImpl<>(produtos.stream()
                .filter(produto -> Boolean.TRUE.equals(produto.getIsDisponivel()) && nomeCategoria.equals(produto.getCategoria().getNome()))
                .collect(Collectors.toList()));
        Mockito.when(produtoRepository.findByIsDisponivelAndCategoriaEntityNome(Boolean.TRUE, nomeCategoria, page)).thenReturn(produtosDisponiveisPorCategoria);
        Page<Produto> produtosPage = produtoService.listarProduto(Boolean.TRUE, nomeCategoria, page);
        Assert.assertFalse(produtosPage.isEmpty());
        Assert.assertEquals(nomeCategoria, produtosPage.iterator().next().getCategoria().getNome());
    }
    @Test
    public void listaProdutosNaoDisponivelPorCategoria(){
        String nomeCategoria = "Gamer";
        Page<Produto> produtosDisponiveisPorCategoria = new PageImpl<>(produtos.stream()
                .filter(produto -> Boolean.FALSE.equals(produto.getIsDisponivel()) && nomeCategoria.equals(produto.getCategoria().getNome()))
                .collect(Collectors.toList()));
        Mockito.when(produtoRepository.findByIsDisponivelAndCategoriaEntityNome(Boolean.FALSE, nomeCategoria, page)).thenReturn(produtosDisponiveisPorCategoria);
        Page<Produto> produtosPage = produtoService.listarProduto(Boolean.FALSE, nomeCategoria, page);
        Assert.assertFalse(produtosPage.isEmpty());
        Assert.assertEquals(nomeCategoria, produtosPage.iterator().next().getCategoria().getNome());
    }
}