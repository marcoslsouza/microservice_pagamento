package com.github.marcoslsouza.crud.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.crud.data.vo.ProdutoVO;
import com.github.marcoslsouza.crud.service.ProdutoService;

@RestController
@RequestMapping("/produto")
@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final PagedResourcesAssembler<ProdutoVO> assembler;

    @Autowired
    public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler<ProdutoVO> assembler) {

        this.produtoService = produtoService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProdutoVO> findById(@PathVariable("id") Long id)  {
        ProdutoVO produtoVO = this.produtoService.findById(id);
        produtoVO.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
        return ResponseEntity.ok().body(produtoVO);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PagedModel<EntityModel<ProdutoVO>>> findAll(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "limit", defaultValue = "12") int limit,
        @RequestParam(value = "direction", defaultValue = "asc") String direction)  {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"nome"));
        Page<ProdutoVO> produtos = this.produtoService.findAll(pageable);
        produtos.stream().forEach(p ->
        	p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel())
        );
        PagedModel<EntityModel<ProdutoVO>> pagedModel = assembler.toModel(produtos);

        return ResponseEntity.ok().body(pagedModel);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProdutoVO> create(@RequestBody ProdutoVO produtoVO) {
        ProdutoVO prodVO = this.produtoService.create(produtoVO);
        prodVO.add(linkTo(methodOn(ProdutoController.class).findById(prodVO.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(prodVO);
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProdutoVO> update(@RequestBody ProdutoVO produtoVO, @PathVariable("id") Long id) {
        ProdutoVO prodVO = this.produtoService.update(produtoVO, id);
        prodVO.add(linkTo(methodOn(ProdutoController.class).findById(prodVO.getId())).withSelfRel());
        return ResponseEntity.ok().body(prodVO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        this.produtoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
