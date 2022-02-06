package com.github.marcoslsouza.pagamento.pagamento.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.github.marcoslsouza.pagamento.pagamento.data.vo.ProdutoVO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="produto")
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
    
    public static Produto convertProdutoVOToProduto(ProdutoVO produtoVO) {
		
		return new ModelMapper().map(produtoVO, Produto.class);
	}
}
