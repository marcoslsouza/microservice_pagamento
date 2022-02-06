package com.github.marcoslsouza.pagamento.pagamento.service;

import com.github.marcoslsouza.pagamento.pagamento.data.vo.VendaVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendaService {
    public VendaVO create(VendaVO vendaVO);
    public Page<VendaVO> findAll(Pageable pageable);
    public VendaVO findById(Long id);
}
