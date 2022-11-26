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
import poc.spring.resilience4j.domain.exception.CategoriaException;
import poc.spring.resilience4j.domain.exception.CategoriaNotFoundException;
import poc.spring.resilience4j.domain.model.Categoria;
import poc.spring.resilience4j.domain.repository.ICategoriaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CategoriaServiceTest {

    @InjectMocks
    public CategoriaService categoriaService;

    @Mock
    private ICategoriaRepository categoriaRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Categoria categoria;

    private List<Categoria> categorias;

    @Before
   public void setUp() {
        MockitoAnnotations.initMocks(this);
        categoria = Categoria.builder()
                .id(1L)
                .codigo("1")
                .nome("eletrodomesticos")
                .build();
        categorias = Arrays.asList(categoria,
                Categoria.builder()
                        .id(2L)
                        .codigo("2")
                        .nome("gamer")
                .build(),
                Categoria.builder()
                        .id(3L)
                        .codigo("3")
                        .nome("smartphone")
                        .build()
        );
    }
    @Test
    public void buscarCategoriaComSucesso() throws CategoriaNotFoundException {
        Mockito.when(categoriaRepository.findByCodigo(Mockito.anyString())).thenReturn(Optional.of(categoria));
        Categoria categoriaRetorno = categoriaService.buscarCategoria(categoria.getCodigo());
        Assert.assertNotNull(categoriaRetorno);
        Assert.assertEquals(categoria.getCodigo(), categoriaRetorno.getCodigo());
        Assert.assertEquals(categoria.getId(), categoriaRetorno.getId());
        Assert.assertEquals(categoria.getNome(), categoriaRetorno.getNome());
    }

    @Test
    public void buscarCategoriaException() throws CategoriaNotFoundException{
        expectedException.expect(CategoriaNotFoundException.class);
        expectedException.expectMessage("Categoria não localizada, verifique o codigo da categoria!");
        categoriaService.buscarCategoria("5");
    }

    @Test
    public void cadastrarCategoriaComSucesso()throws CategoriaException{
        Mockito.when(categoriaRepository.findByCodigo(categoria.getCodigo())).thenReturn(Optional.empty());
        Mockito.when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria categoriaSave = categoriaService.cadastrarCategoria(categoria);
        Assert.assertNotNull(categoriaSave);
        Mockito.verify(categoriaRepository, Mockito.times(1)).findByCodigo(categoria.getCodigo());
        Mockito.verify(categoriaRepository, Mockito.times(1)).save(categoria);
    }

    @Test
    public void cadastrarCategoriaException() throws CategoriaException{
        Mockito.when(categoriaRepository.findByCodigo(Mockito.anyString())).thenReturn(Optional.of(categoria));
        expectedException.expect(CategoriaException.class);
        expectedException.expectMessage("Categoria já existe, verifique o codigo da categoria");
        categoriaService.cadastrarCategoria(categoria);
    }

    @Test
    public void listarCategoriasComSucesso(){
        Mockito.when(categoriaRepository.findAll()).thenReturn(categorias);
        List<Categoria> categoriasRetorno = categoriaService.listarCategoria();
        Assert.assertEquals(3, categoriasRetorno.size());
        Assert.assertEquals("3", categoriasRetorno.get(2).getCodigo());
    }

    @Test
    public void listarCategoriaEmpty(){
        Mockito.when(categoriaRepository.findAll()).thenReturn(new ArrayList<>());
        List<Categoria> categoriasRetorno = categoriaService.listarCategoria();
       Assert.assertTrue(categoriasRetorno.isEmpty());



    }
}