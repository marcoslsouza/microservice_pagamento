package com.github.marcoslsouza.pagamento.pagamento.controller;

import com.github.marcoslsouza.pagamento.pagamento.data.vo.VendaVO;
import com.github.marcoslsouza.pagamento.pagamento.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaVO> assembler;

    @Autowired
    public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaVO> assembler) {

        this.vendaService = vendaService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<VendaVO> findById(@PathVariable("id") Long id)  {
        VendaVO vendaVO = this.vendaService.findById(id);
        vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return ResponseEntity.ok().body(vendaVO);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PagedModel<EntityModel<VendaVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction)  {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"data"));
        Page<VendaVO> vendas = this.vendaService.findAll(pageable);
        vendas.stream().forEach(p ->
                p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel())
        );
        PagedModel<EntityModel<VendaVO>> pagedModel = assembler.toModel(vendas);

        return ResponseEntity.ok().body(pagedModel);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<VendaVO> create(@RequestBody VendaVO vendaVO) {
        VendaVO vdVO = this.vendaService.create(vendaVO);
        vdVO.add(linkTo(methodOn(VendaController.class).findById(vdVO.getId())).withSelfRel());
        return ResponseEntity.ok().body(vdVO);
    }
}
