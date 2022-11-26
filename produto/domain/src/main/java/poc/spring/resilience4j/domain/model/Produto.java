package poc.spring.resilience4j.domain.model;


import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Produto {

    private Long id;
    @NotBlank(message = "Codigo não pode ser nulo ou vazio.")
    private String codigo;
    @NotBlank(message = "Nome não pode ser nulo ou vazio.")
    private String nome;
    @NotNull(message = "Valor não pode ser nulo.")
    @Positive(message = "Valor deve ser positivo.")
    private BigDecimal valor;
    @NotBlank(message = " Descrição não pode ser nula ou vazia.")
    private String descricao;
    @NotNull(message = "Quantidade não pode ser nula.")
    @Positive(message = "Quantidade deve ser positiva.")
    private Integer quantidade;
    @NotNull(message = "Produto deve ter uma categoria.")
    @Valid
    private Categoria categoria;
    @NotNull(message = "isDisponivel não pode ser nulo.")
    private Boolean isDisponivel;
}
