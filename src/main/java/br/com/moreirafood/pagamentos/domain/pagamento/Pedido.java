package br.com.moreirafood.pagamentos.domain.pagamento;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pedido {
    private List<ItemDoPedido> itens;
}
