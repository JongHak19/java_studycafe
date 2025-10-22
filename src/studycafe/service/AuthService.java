package studycafe.service;

import studycafe.db.StudyCafeRepository;
import studycafe.model.Member;

public class AuthService {
    private final StudyCafeRepository repository;
    
    public AuthService(StudyCafeRepository repository) {
        this.repository = repository;
    }
    
    public Member login(String id, String password) {
        if (id == null || id.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }
        
        return repository.findMemberByIdAndPassword(id.trim(), password);
    }
    
    public boolean register(Member newMember) {
        if (newMember == null || newMember.getId() == null || newMember.getPassword() == null) {
            return false;
        }
        
        // ID duplicate check (simple implementation)
        Member existingMember = repository.findMemberByIdAndPassword(newMember.getId(), "");
        if (existingMember != null) {
            return false; // ID already exists
        }
        
        return repository.createMember(newMember);
    }
    
    public boolean updateMemberTime(String id, int additionalTime) {
        Member member = repository.findMemberByIdAndPassword(id, "");
        if (member == null) {
            return false;
        }
        
        int newTime = member.getTime() + additionalTime;
        return repository.updateMemberTime(id, newTime);
    }
}
