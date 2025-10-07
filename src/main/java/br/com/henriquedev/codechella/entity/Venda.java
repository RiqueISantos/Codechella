package br.com.henriquedev.codechella.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("vendas")
public class Venda {
    @Id
    private Long id;
    private Long ingressoId;
    private int total;
}
