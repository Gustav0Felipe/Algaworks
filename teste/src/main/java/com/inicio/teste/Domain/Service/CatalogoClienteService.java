package com.inicio.teste.Domain.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inicio.teste.Domain.Exception.NegocioException;
import com.inicio.teste.Domain.Model.Cliente;
import com.inicio.teste.Domain.Repository.ClienteRepository;

@Service //componente Spring que representa os serviços de um negócio, com a semantica correspondente.
public class CatalogoClienteService {//crud cliente.
    //service facilita a manutenção do codigo, centralizando as regras de negócio.
    private ClienteRepository clienteRepository;

    
    public CatalogoClienteService(ClienteRepository clienteRepository){
            this.clienteRepository = clienteRepository;
        }

    //vai ser executado dentro de uma transação, ou seja se algo der errado, as operações feitas
    //no banco de dados vão ser descartadas.    
    @Transactional 
    public Cliente salvar(Cliente cliente){
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
        .stream().anyMatch(clienteExistente -> !clienteExistente.equals(cliente));

        if(emailEmUso){ //se a pessoa tentar atualizar o email, não vai poder, é isso que isso faz.
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void excluir(Long clienteId){
        clienteRepository.deleteById(clienteId);
    }
    
}
/*com relação á metodos de consultas, que apenas msotram algo e não alteram nada no banco de dados, tem quem
faça esses metodos passarem pelo service, e tem quem deixe direto no controller, provavelmente é melhor deixar
no controller mesmo, já que dificilmente precisaria de regras de negocio em uma consulta, no maximo precisaria
filtrar quem pode ou não fazer uma determinada consulta mas isso seria assunto de usar o SpringSecurity, não algo
que seja lidado no service ou no controller.
alem de não ter regras de negocio em consultar uma das provaveis vantagens de deixar no controller, é que já faço
a validação basica nele, se o id não existir no banco de dados o metodo nem é iniciado, então não vai pro service
desnecessariamente.
*/