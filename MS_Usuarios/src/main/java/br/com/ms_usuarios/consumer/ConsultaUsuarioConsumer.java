package br.com.ms_usuarios.consumer;

import br.com.ms_usuarios.model.records.ConsultaUsuarioRequest;
import br.com.ms_usuarios.model.records.ConsultaUsuarioResponse;
import br.com.ms_usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ConsultaUsuarioConsumer {

    @Autowired
    private UsuarioService service;

    @Bean(name = "consultaUsuario")
    Function<ConsultaUsuarioRequest, ConsultaUsuarioResponse> consumer(){
        return ler -> {
            return service.consultaUsuarioPorId(ler);
        };
    }

}
