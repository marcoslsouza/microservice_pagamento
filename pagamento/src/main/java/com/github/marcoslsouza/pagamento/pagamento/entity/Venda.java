package com.github.marcoslsouza.pagamento.pagamento.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.github.marcoslsouza.pagamento.pagamento.data.vo.VendaVO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="venda")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "data", nullable = false)
    private Date data;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venda", cascade = {CascadeType.REFRESH})
    private List<ProdutoVenda> produtos;

    @Column(name = "valor_total", nullable = false, length = 10)
    private Double valorTotal;

	public static Venda convertVendaVOToVenda(VendaVO vendaVO) {
		
		return Venda.convertVendaVOToVenda(vendaVO);
	}
}
