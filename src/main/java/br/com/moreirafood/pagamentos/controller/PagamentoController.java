package br.com.moreirafood.pagamentos.controller;

import br.com.moreirafood.pagamentos.domain.pagamento.DadosPagamento;
import br.com.moreirafood.pagamentos.service.PagamentoService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public Page<DadosPagamento> listar(@PageableDefault(size = 10)Pageable paginacao){
        return service.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosPagamento> detalhar (@PathVariable @NotNull Long id){
        DadosPagamento dadosPagamento = service.obterPorId(id);
        return ResponseEntity.ok(dadosPagamento);
    }

    @PostMapping
    public ResponseEntity<DadosPagamento> cadastrar(@RequestBody @Valid DadosPagamento dadosPagamento, UriComponentsBuilder uriBuilder){
        DadosPagamento pagamentosCadastrar = service.criarPagamento(dadosPagamento);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentosCadastrar.getId()).toUri();
        return ResponseEntity.created(endereco).body(pagamentosCadastrar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosPagamento> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid DadosPagamento dadosPagamento){
        DadosPagamento pagamentoAtualizar = service.atualizarPagamento(id, dadosPagamento);
        return ResponseEntity.ok(pagamentoAtualizar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DadosPagamento> remover (@PathVariable @NotNull Long id){
        service.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(@PathVariable @NotNull Long id){
        service.confirmarPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e){
        service.alteraStatus(id);
    }

}
