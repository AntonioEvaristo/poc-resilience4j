package poc.spring.resilience4j.domain.model;


import lombok.*;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Categoria {

    private Long id;
    private String nome;
    private String codigo;
}
