package com.github.marcoslsouza.pagamento.pagamento.service;

import com.github.marcoslsouza.pagamento.pagamento.data.vo.VendaVO;
import com.github.marcoslsouza.pagamento.pagamento.entity.ProdutoVenda;
import com.github.marcoslsouza.pagamento.pagamento.entity.Venda;
import com.github.marcoslsouza.pagamento.pagamento.exception.ResourceNotFoundException;
import com.github.marcoslsouza.pagamento.pagamento.repository.ProdutoVendaRepository;
import com.github.marcoslsouza.pagamento.pagamento.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaServiceImpl implements VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public VendaServiceImpl(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoVendaRepository = produtoVendaRepository;
    }

    @Transactional
    public VendaVO create(VendaVO vendaVO) {

        // Salva a venda
        Venda venda = this.vendaRepository.save(Venda.convertVendaVOToVenda(vendaVO));

        // Adiciona venda aos produtos
        List<ProdutoVenda> produtosSalvos = new ArrayList();
        vendaVO.getProdutosVO().forEach(p -> {
            ProdutoVenda pv = ProdutoVenda.convertProdutoVendaVOToProdutoVenda(p);
            pv.setVenda(venda);
            produtosSalvos.add(this.produtoVendaRepository.save(pv));
        });

        venda.setProdutos(produtosSalvos);

        return VendaVO.convertVendaToVendaVO(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        Page<Venda> page = this.vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaVO);
    }

    @Transactional
    public VendaVO findById(Long id) {
        Optional<Venda> venda = this.vendaRepository.findById(id);
        venda.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return VendaVO.convertVendaToVendaVO(venda.get());
    }

    private VendaVO convertToVendaVO(Venda venda) {
        return VendaVO.convertVendaToVendaVO(venda);
    }
}
