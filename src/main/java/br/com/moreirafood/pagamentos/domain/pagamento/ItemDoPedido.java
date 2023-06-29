package br.com.moreirafood.pagamentos.domain.pagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDoPedido {
    private Long id;
    private Integer quantidade;
    private String descricao;
}
