package Goormoa.goormoa_server.entity.group;

import Goormoa.goormoa_server.entity.user.User;
import Goormoa.goormoa_server.entity.profile.Profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="group_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId; // 그룹 고유 식별자

    @Column(nullable = false, length = 50)
    private String groupTitle; // 그룹 이름

    private String groupInfo; // 그룹 설명

    private boolean groupActivate = true; // 그룹 활동 여부

    @OneToOne
    @JoinColumn(name = "group_host_user_id")
    private User groupHost;

    private Integer maxCount;

    private Integer currentCount;

    private Boolean close; // 모임 모집 마감 여부

    private String category; // 카테고리

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createDate; // 그룹 생성 날짜

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate; // 그룹 종료 날짜

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.EAGER)
    private List<GroupMember> groupMembers;

    @ManyToMany(mappedBy = "participatingGroups", fetch = FetchType.LAZY)
    private List<Profile> participants;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_applicants",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> applicants;
//
//    @OneToOne(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
//    private ChatRoom chatRoom; // 그룹과 연결된 채팅방

    @PrePersist
    protected void prePersist() {
        if (createDate == null) {
            createDate = new Date(); // 엔티티가 저장되기 전에 현재 날짜와 시간으로 초기화
        }
    }

    public void addApplicant(Profile applicant) {
        applicants.add(applicant);
    }
    public void removeApplicant(Profile applicant) {
        applicants.remove(applicant);
    }
    public void acceptApplicant(Profile applicant) {
        applicants.remove(applicant);
        participants.add(applicant);
        currentCount += 1;
    }
}
