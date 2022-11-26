package poc.spring.resilience4j.domain.model;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Categoria {

    private Long id;
    @NotBlank(message = "Não pode ser nulo ou vazio")
    private String nome;
    @NotBlank(message = "Não pode ser nulo ou vazio")
    private String codigo;
}
