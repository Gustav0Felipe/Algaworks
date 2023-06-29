package com.inicio.teste.Common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //componente spring com a função de configurar beans, metodos que definem beans spring.
public class ModelMapperConfig {
    
    @Bean //esse metodo instancia inicia e configura um bean que sera gerenciado pelo Spring, portanto sera 
    //injetavel em outras classes.
    public ModelMapper modelMapper(){
        return new ModelMapper(); //posso configurar passando argumentos e afins.
    }
}
