package br.rmi.sd.service;

import br.rmi.sd.model.MemberRemote;
import org.springframework.stereotype.Service;

import java.rmi.Naming;

@Service
public class RmiClientService {
    public MemberRemote lookupMember(String rmiUrl) {
        try {
            return (MemberRemote) Naming.lookup(rmiUrl);
        } catch (Exception e) {
            throw new RuntimeException("Erro no lookup do membro: " + e.getMessage(), e);
        }
    }
}
