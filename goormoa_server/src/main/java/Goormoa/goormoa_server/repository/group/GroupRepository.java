package Goormoa.goormoa_server.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;

import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.entity.profile.Profile;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    //    List<Group> findByGroupUsersUserEmail(String email);

    Group findByGroupId(Long groupId);

    /* 사용자가 포함된 모든 모임 */

    List<Group> findByCloseFalse();

    List<Group> findByGroupHost(User user);
    List<Group> findByParticipantsContaining(Profile participant);
    // 추가된 로깅
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