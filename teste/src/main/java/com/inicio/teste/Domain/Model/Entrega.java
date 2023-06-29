package com.inicio.teste.Domain.Model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.inicio.teste.Domain.Exception.NegocioException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Entrega {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //muitos para um, muitas entregas possuem um mesmo cliente, mapeia na coluna cliente under score id.
    //se eu usar @JoinColumn(name = "cliente_id") consigo customizar e não usar o padrão.
    //mapeia a chave estrangeira, mapeamento de uma tabela para outra.
    //quando passar um cliente sem id, vai fazer validação com as bean do id que coloquei no cliente.
    //valida o que tá dentro do cliente/cascateia pra dentro dele.
    //converte o VdGroup do default pro ClienteId, assim valida só o que eu marquei como desse grupo, que é o id.
    @ManyToOne //todos os clientes vão estar na tabela clientes, e ao mesmo tempo em entrega.
    private Cliente cliente;

    
    @Embedded //abstrair dados para outra classe porem mapeando em uma mesma tabela, ao inves do destinatario
    //ter uma tabela propria no db, todos os destinatarios vão estar na tabela Entrega, todas suas colunas.
    private Destinatario destinatario;

    private BigDecimal taxa;

    //quando no metodo adicionarOcorrencia eu crio uma nova ocorrencia, ela é sincronizada e cascateira pra dentro
    //da tabela Ocorrencia no banco de dados tambem, ao usar o Transactional no service.
    @OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
    private List<Ocorrencia> ocorrencias = new ArrayList<>();

    @Enumerated(EnumType.STRING) //armazenar na coluna do status a string que representa a operação, e não um num. 
    private StatusEntrega status;
    
    private OffsetDateTime dataPedido;

   //informação apenas ler, que o cliente não pode mandar no cabeçalho.
    private OffsetDateTime dataFinalizacao;


    //a classe de entidade pode ter metodos de negocio tambem.
    public Ocorrencia adicionarOcorrencia(String descricao){ 
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setDescricao(descricao);
        ocorrencia.setDataRegistro(OffsetDateTime.now());
        ocorrencia.setEntrega(this);

        this.getOcorrencias().add(ocorrencia);
        return ocorrencia;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entrega other = (Entrega) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    public void finalizar() {
        if(naoPodeFinalizar()){
            throw new NegocioException("Entrega não pode ser finalizada.");
        }

        
        setStatus(StatusEntrega.FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
    }
    
    public boolean podeFinalizar(){
        return StatusEntrega.PENDENTE.equals(getStatus());
    }

    public boolean naoPodeFinalizar(){
        return !podeFinalizar();
    }
}
