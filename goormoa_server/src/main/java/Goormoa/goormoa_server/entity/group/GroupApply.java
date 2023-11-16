package Goormoa.goormoa_server.entity.group;


import Goormoa.goormoa_server.entity.group.Group;
import Goormoa.goormoa_server.entity.profile.Profile;

import javax.persistence.*;

public class GroupApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_uid")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}