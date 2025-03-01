package br.rmi.sd.config;

import br.rmi.sd.model.MemberRemote;
import br.rmi.sd.service.Database2Service;
import org.springframework.context.annotation.Configuration;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

@Configuration
public class RmiConfig {

    public RmiConfig(Database2Service database2Service) throws Exception {
        MemberRemote stub = (MemberRemote) UnicastRemoteObject.exportObject(database2Service, 0);
        Naming.rebind("rmi://localhost/Database2Service", stub);
    }
}
