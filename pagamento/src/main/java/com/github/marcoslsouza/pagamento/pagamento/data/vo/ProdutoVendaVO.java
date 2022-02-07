package com.github.marcoslsouza.pagamento.pagamento.data.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.marcoslsouza.pagamento.pagamento.entity.ProdutoVenda;
import com.github.marcoslsouza.pagamento.pagamento.entity.Venda;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "idProduto", "quantidade"})
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoVendaVO extends RepresentationModel<ProdutoVendaVO> {

    @JsonProperty("id")
    private long id;

    @JsonProperty("idProduto")
    private Long idProduto;

    @JsonProperty("quantidade")
    private Integer quantidade;
    
    @JsonIgnore
    //@JsonProperty("venda")
    private Venda venda;

    public static ProdutoVendaVO convertProdutoVendaToProdutoVendaVO(ProdutoVenda produtoVenda) {
        return new ModelMapper().map(produtoVenda, ProdutoVendaVO.class);
    }
}
