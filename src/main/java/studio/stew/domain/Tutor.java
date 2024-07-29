package studio.stew.domain;

import jakarta.persistence.*;
import lombok.*;
import studio.stew.common.BaseEntity;
import studio.stew.domain.enums.Gender;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
public class Tutor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_id")
    private Long tutorId;
    private String imgUrl;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String career;
    @Column(nullable = false)
    private String intro;
    @Column(nullable = false)
    private String self_intro;
    @Column(nullable = false)
    private String sports_intro;
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<Application> applicationList = new ArrayList<>();
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<Portfolio> portfolioList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_id")
    private Sports sports;
}
