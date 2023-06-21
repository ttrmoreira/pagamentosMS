package br.com.moreirafood.pagamentos.domain.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository <Pagamento, Long> {

}
