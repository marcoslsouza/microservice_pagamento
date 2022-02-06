package com.github.marcoslsouza.pagamento.pagamento.repository;

import com.github.marcoslsouza.pagamento.pagamento.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
