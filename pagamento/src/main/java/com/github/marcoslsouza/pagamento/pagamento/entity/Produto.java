package com.github.marcoslsouza.pagamento.pagamento.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="produto_venda")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Produto {

    @Id
    private Long id;

    @Column(name = "estoque", nullable = false)
    private Integer estoque;
}
