package com.trippin.domain;

import com.trippin.domain.util.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"country", "user", "bookmark", "comment"})
@Entity
public class Post extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String region;

    private String content;

    @Transient
    private Integer favorite;

    @Transient
    private Integer countComment;

    @Transient
    private boolean validBookmark;

    @Transient
    private boolean validFavorite;

    @Transient
    private boolean validFollow;

    @Transient
    private boolean validUser;

    @ManyToOne
    private Country country;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmark;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    public void update(String image, String region, String content) {
        this.image = image;
        this.region = region;
        this.content = content;
    }

}
