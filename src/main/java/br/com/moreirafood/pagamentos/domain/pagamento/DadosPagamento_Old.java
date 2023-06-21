package br.com.moreirafood.pagamentos.domain.pagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosPagamento_Old(Long id, @NotNull BigDecimal valor, @NotBlank String nome, @NotBlank String numero, @NotBlank String expiracao, @NotBlank String codigo, Status status, @NotNull Long pedidoId, @NotNull Long formaDePagamentoId) {
}


