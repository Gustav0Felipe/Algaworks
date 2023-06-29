package com.inicio.teste.Domain.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Cliente {
   //na entidade coloco regras de negocio mais basicas que não interagem com outras entidades.
   //ou seja, quando a regra é mais voltada para o cliente mesmo.

   //grupo de validação padrão, o default é o padrão.
   @Id //na hora de chamar o valid, valida tudo que tem o Default, posso customizar.
   //para que valide separadamente o id, já que só quero ele not null na hora de passar uma entrega.
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @NotBlank //impede nulo ou vazio.
   @Size(max = 60) //tem que ser o mesmo limite permitido no banco de dados, não queremos exceções.
   private String nome;

   @NotBlank
   @Email //vai validar que esta na sintaxe correta para um Email.
   @Size(max = 255)
   private String email;


   @NotBlank
   @Size(max = 20)
   @Column(name = "fone")
   private String telefone;


   
        @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Cliente other = (Cliente) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

   
}
