package Goormoa.goormoa_server.repository.group;

import Goormoa.goormoa_server.entity.group.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
