package studio.stew.domain;

import jakarta.persistence.*;
import lombok.*;
import studio.stew.common.BaseEntity;
import studio.stew.domain.mapping.WishTutor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Tutor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_id")
    private Long tutorId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String area;
    private String imgUrl;
    @Column(nullable = false)
    private String intro;
    @Column(nullable = false)
    private String style;
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<WishTutor> wishTutorList = new ArrayList<>();
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
