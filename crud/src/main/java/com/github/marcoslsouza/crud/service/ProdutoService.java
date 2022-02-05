package com.github.marcoslsouza.crud.service;

import com.github.marcoslsouza.crud.data.vo.ProdutoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {

    public ProdutoVO create(ProdutoVO produtoVO);
    public Page<ProdutoVO> findAll(Pageable pageable);
    public ProdutoVO findById(Long id);
    public ProdutoVO update(ProdutoVO produtoVO, Long id);
    public void delete(Long id);
}
