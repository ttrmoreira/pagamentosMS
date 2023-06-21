package br.com.moreirafood.pagamentos.service;

import br.com.moreirafood.pagamentos.domain.pagamento.DadosPagamento;
import br.com.moreirafood.pagamentos.domain.pagamento.Pagamento;
import br.com.moreirafood.pagamentos.domain.pagamento.PagamentoRepository;
import br.com.moreirafood.pagamentos.domain.pagamento.Status;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;


    @Autowired
    private ModelMapper modelMapper;

    public PagamentoService() {
    }

    public Page<DadosPagamento> obterTodos (Pageable paginacao){
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, DadosPagamento.class));
    }

    public DadosPagamento obterPorId(Long id){
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(pagamento, DadosPagamento.class);
    }

    public DadosPagamento criarPagamento (DadosPagamento dadosPagamento){
        Pagamento pagamento = modelMapper.map(dadosPagamento, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, DadosPagamento.class);
    }

    public DadosPagamento atualizarPagamento (Long id, DadosPagamento dadosPagamento){
        Pagamento pagamento = modelMapper.map(dadosPagamento, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, DadosPagamento.class);
    }

    public void excluirPagamento (Long id){
        repository.deleteById(id);
    }
}
