package studio.stew.domain;

import jakarta.persistence.*;
import lombok.*;
import studio.stew.common.BaseEntity;

import java.util.List;

@Table(name = "application")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Application extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @Column(nullable = false)
    private String title;

    private String imgUrl;

    @ElementCollection
    @CollectionTable(name = "purpose", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "purpose")
    private List<String> purpose;

    @Column(nullable = false)
    private int intensity;

    private String memo;

    @Builder.Default
    private boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
