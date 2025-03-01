package br.rmi.sd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.rmi.sd.model.MemberRemote;
import br.rmi.sd.model.MessageEntity;
import br.rmi.sd.repository.MessageRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.rmi.RemoteException;

@Service("database1Service")
public class Database1Service implements MemberRemote {

    private static final Logger logger = LoggerFactory.getLogger(Database1Service.class);
    private final MessageRepository messageRepository;
    private final JdbcTemplate jdbcTemplate;

    public Database1Service(MessageRepository messageRepository, JdbcTemplate jdbcTemplate) {
        this.messageRepository = messageRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveMessage(String message) throws RemoteException {
        // Salva a mensagem no database
        MessageEntity entity = new MessageEntity();
        entity.setMessage(message);
        messageRepository.save(entity);
        logger.info("Database1 salvando mensagem: {}", message);
    }

    @Override
    public void executeCommand(String sqlCommand) throws RemoteException {
        // Executa o comando no database
        jdbcTemplate.execute(sqlCommand);
        logger.info("Database1 executando comando: {}", sqlCommand);
    }

}
