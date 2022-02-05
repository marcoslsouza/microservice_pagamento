package com.github.marcoslsouza.crud.service;

import com.github.marcoslsouza.crud.data.vo.ProdutoVO;
import com.github.marcoslsouza.crud.entity.Produto;
import com.github.marcoslsouza.crud.exception.ResourceNotFoundException;
import com.github.marcoslsouza.crud.repository.ProdutoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Log4j2
@Service
public class ProdutoServiceImpl implements ProdutoService  {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoVO create(ProdutoVO produtoVO) {

        ProdutoVO produtoVoRetorno =
                ProdutoVO.convertProdutoParaProdutoVO(
                        this.produtoRepository.save(
                                Produto.convertProdutoVOParaProduto(produtoVO)
                        )
                );
        return produtoVoRetorno;
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        Page<Produto> page = this.produtoRepository.findAll(pageable);
        return page.map(this::convertParaProdutoVO);
    }

    public ProdutoVO findById(Long id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        produto.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
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
                }).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
    }

    @Transactional
    public void delete(Long id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        produto.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        this.produtoRepository.delete(produto.get());
    }

    private ProdutoVO convertParaProdutoVO(Produto produto) {
       return ProdutoVO.convertProdutoParaProdutoVO(produto);
    }
}
