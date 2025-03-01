package br.rmi.sd.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MemberRemote extends Remote {
    void saveMessage(String message) throws RemoteException;
    void executeCommand(String sqlCommand) throws RemoteException;
}
