package Goormoa.goormoa_server.entity.alarm;


import Goormoa.goormoa_server.entity.follow.Follow;
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
@Table(name = "follow_alarm_table")
public class FollowAlarm extends Alarm {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "follow_id")
    private Follow follow;
}
