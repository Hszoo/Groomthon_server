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
@Table(name = "etc_alarm_table")
public class EtcAlarm extends Alarm{
    @Column(name = "group_id")
    private Long groupId;
}
