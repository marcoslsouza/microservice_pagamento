package com.github.marcoslsouza.pagamento.pagamento.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.marcoslsouza.pagamento.pagamento.entity.Produto;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "estoque"})
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoVO extends RepresentationModel<ProdutoVO> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("estoque")
    private Integer estoque;

    public static ProdutoVO convertProdutoToProdutoVO(Produto produto) {
        return new ModelMapper().map(produto, ProdutoVO.class);
    }
}
