package Goormoa.goormoa_server.repository.alarm;

import Goormoa.goormoa_server.entity.alarm.Alarm;
import Goormoa.goormoa_server.entity.alarm.FollowAlarm;
import Goormoa.goormoa_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByUser(User user);
    Optional<FollowAlarm> findByFollowFollowId(Long followId);
}
