package poc.spring.resilience4j.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.spring.resilience4j.domain.exception.CategoriaException;
import poc.spring.resilience4j.domain.model.Categoria;
import poc.spring.resilience4j.domain.service.ICategoriaService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/rest/categoria")
@RequiredArgsConstructor
public class CategoriaController {
    private final ICategoriaService categoriaService;
    @GetMapping
    public ResponseEntity<List<Categoria>> findAll(){
        return new ResponseEntity<>(categoriaService.listarCategoria(), HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable String codigo) throws CategoriaException {
        return new ResponseEntity<>(categoriaService.buscarCategoria(codigo), HttpStatus.OK);
    }
    @PostMapping
    @Transactional
    public ResponseEntity<Categoria> saveCategoria(@RequestBody @Valid Categoria categoria) throws CategoriaException {
        return new ResponseEntity<>(categoriaService.cadastrarCategoria(categoria), HttpStatus.CREATED);
    }
}
