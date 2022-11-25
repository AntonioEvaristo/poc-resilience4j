package poc.spring.resilience4j.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import poc.spring.resilience4j.domain.repository.ICategoriaRepository;
import poc.spring.resilience4j.domain.repository.IProdutoRepository;
import poc.spring.resilience4j.domain.service.CategoriaService;
import poc.spring.resilience4j.domain.service.ICategoriaService;
import poc.spring.resilience4j.domain.service.IProdutoService;
import poc.spring.resilience4j.domain.service.ProdutoService;

@Configuration
public class BeanConfig {

    @Bean
    public IProdutoService produtoService(IProdutoRepository produtoRepository){
        return new ProdutoService(produtoRepository);
    }

    @Bean
    public ICategoriaService categoriaService(ICategoriaRepository categoriaRepository){
        return new CategoriaService(categoriaRepository);
    }
}
