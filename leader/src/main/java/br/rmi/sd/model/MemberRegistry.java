package br.rmi.sd.model;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemberRegistry {
    private final List<MemberRemote> members = new ArrayList<>();

    public synchronized void addMember(MemberRemote member) {
        members.add(member);
    }

    public synchronized List<MemberRemote> getAllMembers() {
        return new ArrayList<>(members);
    }

    public synchronized void removeMember(MemberRemote member) {
        members.remove(member);
    }
}
