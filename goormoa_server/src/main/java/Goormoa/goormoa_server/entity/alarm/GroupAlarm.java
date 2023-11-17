package Goormoa.goormoa_server.entity.alarm;

import Goormoa.goormoa_server.entity.follow.Follow;
import Goormoa.goormoa_server.entity.group.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_alarm_table")
public class GroupAlarm extends Alarm{
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;
}
