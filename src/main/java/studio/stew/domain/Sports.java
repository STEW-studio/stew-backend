package studio.stew.domain;

import jakarta.persistence.*;
import lombok.*;
import studio.stew.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Sports extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sports_id")
    private Long sportsId;

    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "sports", cascade = CascadeType.ALL)
    private List<Tutor> tutorList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
