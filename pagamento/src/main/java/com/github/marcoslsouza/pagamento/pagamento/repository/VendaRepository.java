package com.github.marcoslsouza.pagamento.pagamento.repository;

import com.github.marcoslsouza.pagamento.pagamento.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
