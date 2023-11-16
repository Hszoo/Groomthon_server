package Goormoa.goormoa_server.entity.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgreeAlarm extends Alarm{
    @Column(name = "group_id")
    private Long groupId;
}
