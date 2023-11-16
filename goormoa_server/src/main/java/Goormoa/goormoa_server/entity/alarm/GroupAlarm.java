package Goormoa.goormoa_server.entity.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_alarm_table")
public class GroupAlarm extends Alarm{
    @Column(name = "group_user_id")
    private Long groupUserId;

    @Column(name = "group_id")
    private Long groupId;
}
