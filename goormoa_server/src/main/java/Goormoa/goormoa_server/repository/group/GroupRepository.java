package Goormoa.goormoa_server.repository.group;

import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.profile.Profile;
import Goormoa.goormoa_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long>  {
    Group findByGroupId(Long groupId);
    List<Group> findByCloseFalse(); // myGroups 에 사용됨

    List<Group> findByGroupHost(User user);

    List<Group> findByParticipantsContaining(Profile participant);
    default List<Group> debugFindByGroupHost(User user) {
        System.out.println("debugFindByGroupHost - User: " + user);
        List<Group> result = findByGroupHost(user);
        System.out.println("debugFindByGroupHost - Result: " + result);
        return result;
    }
    default List<Group> debugFindByParticipantsContaining(Profile participant) {
        System.out.println("debugFindByParticipantsContaining - Participant: " + participant);
        List<Group> result = findByParticipantsContaining(participant);
        System.out.println("debugFindByParticipantsContaining - Result: " + result);
        return result;
    }
}