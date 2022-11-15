package poc.spring.resilience4j.domain.model;


import lombok.*;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Produto {

    private Long id;
    private String codigo;
    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Integer quantidade;
    private Categoria categoria;
    private Boolean isDisponivel;
}
