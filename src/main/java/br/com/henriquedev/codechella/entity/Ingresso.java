package br.com.henriquedev.codechella.entity;

import br.com.henriquedev.codechella.enums.TipoIngresso;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ingressos")
public class Ingresso {
    @Id
    private Long id;
    private Long eventoId;
    private TipoIngresso tipo;
    private Double valor;
    private int total;
}
