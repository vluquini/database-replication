package br.rmi.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.rmi.sd.model.MemberRegistry;
import br.rmi.sd.model.MemberRemote;
import br.rmi.sd.model.MessageEntity;
import br.rmi.sd.repository.MessageRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.rmi.RemoteException;

@Service
public class LeaderService {

    private static final Logger logger = LoggerFactory.getLogger(LeaderService.class);
    private final RmiClientService rmiClientService;
    private final MemberRegistry memberRegistry;
    private final MessageRepository messageRepository;
    private final JdbcTemplate jdbcTemplate;

    public LeaderService(RmiClientService rmiClientService,
                         MemberRegistry memberRegistry,
                         MessageRepository messageRepository,
                         JdbcTemplate jdbcTemplate) {
        this.rmiClientService = rmiClientService;
        this.memberRegistry = memberRegistry;
        this.messageRepository = messageRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método para registrar um membro
    public void registerMember(String rmiUrl) {
        MemberRemote memberStub = rmiClientService.lookupMember(rmiUrl);
        memberRegistry.addMember(memberStub);
        logger.info("Database registrado com sucesso!");
    }

    // Método para salvar uma mensagem simples
    public void saveMessage(String message) {
        MessageEntity command = new MessageEntity();
        command.setMessage(message);
        messageRepository.save(command);
        logger.info("Mensagem simples salva no Leader!");

        // Replica a mensagem para os membros registrados via RMI
        int i = 0;
        for (MemberRemote member : memberRegistry.getAllMembers()) {
            try {
                member.saveMessage(message);
                logger.info("Mensagem simples replicada no database {}", i+1);
                i++;
            } catch (RemoteException e) {
                logger.warn("Falha ao salvar mensagem no database {}", i+1);
                memberRegistry.removeMember(member);
            }
        }
    }

    // Método para executar um comando SQL e replicá-lo
    public void executeSqlCommand(String sqlCommand) {
        jdbcTemplate.execute(sqlCommand);
        logger.info("Comando SQL executado no Leader!");

        // Replica o comando SQL para os membros para que eles também o executem
        int i = 0;
        for (MemberRemote member : memberRegistry.getAllMembers()) {
            try {
                member.executeCommand(sqlCommand);
                logger.info("Comando SQL replicado no database {}", i+1);
                i++;
            } catch (RemoteException e) {
                logger.warn("Falha ao replicar mensagem no database {}", i+1);
                memberRegistry.removeMember(member);
            }
        }
    }

}
