package poc.spring.resilience4j.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Produto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Integer quantidade;
    @Column(name = "isDisponivel", columnDefinition = "boolean default true")
    private Boolean isDisponivel;
    @ManyToOne
    private CategoriaEntity categoriaEntity;
}
