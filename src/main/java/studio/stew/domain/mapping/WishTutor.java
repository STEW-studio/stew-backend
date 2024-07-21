package studio.stew.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import studio.stew.common.BaseEntity;
import studio.stew.domain.Tutor;
import studio.stew.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WishTutor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_tutor_id")
    private Long wishTutorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
}
