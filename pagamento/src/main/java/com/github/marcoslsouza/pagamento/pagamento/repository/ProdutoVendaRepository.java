package com.github.marcoslsouza.pagamento.pagamento.repository;

import com.github.marcoslsouza.pagamento.pagamento.entity.ProdutoVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Long> {
}
