package com.trippin.domain;

import com.trippin.domain.util.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"user", "post"})
@Entity
public class Country extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String name;

    private String content;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country", cascade = CascadeType.REMOVE)
    private List<Post> post;

    public void update(String name, String content, String image) {
        this.name = name;
        this.content = content;
        this.image = image;
    }

}
