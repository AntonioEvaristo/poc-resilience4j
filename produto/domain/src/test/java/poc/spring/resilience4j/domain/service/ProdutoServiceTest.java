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
import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.model.Categoria;
import poc.spring.resilience4j.domain.model.Produto;
import poc.spring.resilience4j.domain.repository.IProdutoRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProdutoServiceTest {

    @InjectMocks
    public ProdutoService produtoService;
    @Mock
    private IProdutoRepository produtoRepository;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Produto produto;
    private List<Produto> produtos;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
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
                        .descricao("Mortal combate")
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
    public void listarProdutosDisponiveisComSucessoSemNomeCategoria() {
        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);
        List<Produto> listaProdutos = produtoService.listarProduto(Boolean.TRUE, "");
        Assert.assertFalse(listaProdutos.isEmpty());
        Assert.assertEquals(3, listaProdutos.size());
        Assert.assertEquals("124", listaProdutos.get(0).getCodigo());
        Assert.assertEquals("99", listaProdutos.get(1).getCodigo());
        Assert.assertEquals("7", listaProdutos.get(2).getCodigo());
    }

    @Test
    public void listarProdutosIndisponiveisComSucessoSemNomeCategoria() {
        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);
        List<Produto> listaProdutos = produtoService.listarProduto(Boolean.FALSE,"");
        Assert.assertFalse(listaProdutos.isEmpty());
        Assert.assertEquals(2, listaProdutos.size());
        Assert.assertEquals("123", listaProdutos.get(0).getCodigo());
        Assert.assertEquals("333", listaProdutos.get(1).getCodigo());
    }

    @Test
    public void listarProdutosDisponiveisComSucessoComNomeCategoria() {
        String categoria = "utensilios";
        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);
        List<Produto> listaProdutos = produtoService.listarProduto(Boolean.TRUE, categoria);
        Assert.assertFalse(listaProdutos.isEmpty());
        Assert.assertEquals(1, listaProdutos.size());
        Assert.assertEquals("99", listaProdutos.get(0).getCodigo());
        Assert.assertEquals(categoria, listaProdutos.get(0).getCategoria().getNome());
    }

    @Test
    public void listarProdutosIndisponiveisComSucessoComNomeCategoria() {
        String categoria = "Gamer";
        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);
        List<Produto> listaProdutos = produtoService.listarProduto(Boolean.FALSE,categoria);
        Assert.assertFalse(listaProdutos.isEmpty());
        Assert.assertEquals(1, listaProdutos.size());
        Assert.assertEquals("123", listaProdutos.get(0).getCodigo());
        Assert.assertEquals(categoria, listaProdutos.get(0).getCategoria().getNome());
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
}