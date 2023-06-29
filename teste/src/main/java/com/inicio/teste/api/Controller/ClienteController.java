package com.inicio.teste.api.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.validation.Valid;

import com.inicio.teste.Domain.Model.Cliente;
import com.inicio.teste.Domain.Repository.ClienteRepository;
import com.inicio.teste.Domain.Service.CatalogoClienteService;

@RestController 
public class ClienteController {

    //interface usada para fazer operações com as entidades que serão refletidas no banco de dados.
    //@PersistenceContext //vai injetar um entity manger nessa variavel de instancia.
    //private EntityManager manager;

    @Autowired //queremos injetar uma instancia que esta sendo gerenciada pelo spring.
    private ClienteRepository clienteRepository; //se eu deixar assim seria nulo, não teria nada
    //não conseguimos instancias uma interface, o spring fornece uma implementação automaticamente.
    //com isto o entity manager se torna desnecessario.
    private CatalogoClienteService catalogoClienteService;
    //Autowired dificulta um pouco os testes e fazer mock.



    @GetMapping("/clientes")
        public List<Cliente> listar (){//por conta dessa herança posso usar varios metodos automaticamente.
                return clienteRepository.findAll();
            }

    @GetMapping("/clientes/{clienteId}")//vinculo o parametro do metodo á variavel de caminho.
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId){ 
        return clienteRepository.findById(clienteId)
                .map(cliente -> ResponseEntity.ok(cliente)) //optional embutido.
                .orElse(ResponseEntity.notFound().build());

         }

    //boa pratica retornar o recurso que adicionamos na response.
    //request body vincula o parameto do metodo ao corpo da requisição.
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)//mais simples do que o responseEntity, não coloquei condições.
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return catalogoClienteService.salvar(cliente);
    }

    //se a validação der problema o metodo nem continua.
    @PutMapping("/clientes/{clienteId}") //o valid vai validar o bean antes mesmo de deixar entrar no repositorio.
    public ResponseEntity<Cliente> atualizar ( @PathVariable Long clienteId,@Valid @RequestBody Cliente cliente){
        if(!clienteRepository.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clienteId); //o cliente que vier no meu request vai ter o id que estiver na url.
        cliente = catalogoClienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/clientes/{clienteId}")
    public ResponseEntity<Void> excluir (@PathVariable Long clienteId){
        if(!clienteRepository.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }
        //clienteRepository.deleteById(clienteId);
        catalogoClienteService.excluir(clienteId);
        return ResponseEntity.noContent().build(); //retorno sem corpo.
    }

}


//com response entity, podemos manipular a response, com cabeçalho customizado, codigo de status etc.
//return cliente.orElse(null);
//retorna o que tem dentro do container(Optional) do cliente, se não tiver nada, retorna null.    

 /*return manager.createQuery("from Cliente", Cliente.class)
            .getResultList();
        jpql, linguagem usada por jakarta persistance(jpa), consultar objetos.
        
        
 
        metodo alternativo a usar Autowired:
        
        private ClienteRepository clienteRepository;

        public ClienteController(ClienteRepository clienteRepository){
            super();
            this.clienteRepository = clienteRepository;
        }
        
        atráves de construtor, tambem posso usar construtor lombok AllArgsConstructor






        cliente por id:

        @GetMapping("/clientes/{clienteId}")//vinculo o parametro do metodo á variavel de caminho.
        public ResponseEntity<Cliente> buscar(@PathVariable Long clientId){ 
            Optional<Cliente> cliente = clienteRepository.findById(clientId);
            //container que pode ou não conter um cliente.
            if(cliente.isPresent()){
                return ResponseEntity.ok(cliente.get()); //get pega o que tem dentro do optional.
            }
            return ResponseEntity.notFound().build();
    }


        */

