package com.github.marcoslsouza.crud.service;

import com.github.marcoslsouza.crud.data.vo.ProdutoVO;
import com.github.marcoslsouza.crud.entity.Produto;
import com.github.marcoslsouza.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ProdutoServiceImpl implements ProdutoService  {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoVO create(ProdutoVO produtoVO) {

        return  ProdutoVO.convertProdutoParaProdutoVO(
                    this.produtoRepository.save(
                            Produto.convertProdutoVOParaProduto(produtoVO)
                    )
                );
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        Page<Produto> page = this.produtoRepository.findAll(pageable);
        return page.map(this::convertParaProdutoVO);
    }

    public ProdutoVO findById(Long id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        produto.orElseThrow(this.getEntityNotFoundExceptionSupplier());
        return ProdutoVO.convertProdutoParaProdutoVO(produto.get());
    }

    @Transactional
    public ProdutoVO update(ProdutoVO produtoVO, Long id) {

        return this.produtoRepository.findById(id)
                .map(linha -> {

                    produtoVO.setId(linha.getId());
                    Produto produto = this.produtoRepository.save(
                            Produto.convertProdutoVOParaProduto(produtoVO)
                    );
                    return ProdutoVO.convertProdutoParaProdutoVO(produto);
                }).orElseThrow(this.getEntityNotFoundExceptionSupplier());
    }

    @Transactional
    public void delete(Long id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        produto.orElseThrow(this.getEntityNotFoundExceptionSupplier());
        this.produtoRepository.delete(produto.get());
    }

    private Supplier<EntityNotFoundException> getEntityNotFoundExceptionSupplier() {
        return EntityNotFoundException::new;
    }

    private ProdutoVO convertParaProdutoVO(Produto produto) {
       return ProdutoVO.convertProdutoParaProdutoVO(produto);
    }
    
}
