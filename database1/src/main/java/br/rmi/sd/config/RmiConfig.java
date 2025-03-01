package br.rmi.sd.config;

import br.rmi.sd.model.MemberRemote;
import br.rmi.sd.service.Database1Service;
import org.springframework.context.annotation.Configuration;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

@Configuration
public class RmiConfig {

    public RmiConfig(Database1Service database1Service) throws Exception {
        MemberRemote stub = (MemberRemote) UnicastRemoteObject.exportObject(database1Service, 0);
        Naming.rebind("rmi://localhost/Database1Service", stub);
    }
}
