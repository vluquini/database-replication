package br.rmi.sd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Configuration
public class RmiConfig {
    @Bean
    public Registry rmiRegistry() throws RemoteException {
        return LocateRegistry.createRegistry(1099); // Inicia o registro RMI
    }
}
