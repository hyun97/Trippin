package com.trippin.domain;

import com.trippin.domain.util.Auditing;
import com.trippin.domain.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"country", "post", "bookmark", "follower", "following", "comments"})
@Entity
public class User extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String picture;

    private String email;

    private String name;

    private String feel;

    @Transient
    private boolean validFollow;

    @Transient
    private boolean validFollowing;

    @Transient
    private boolean validFollower;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Country> country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> post;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmark;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "following", cascade = CascadeType.REMOVE)
    private List<Follow> following;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "follower", cascade = CascadeType.REMOVE)
    private List<Follow> follower;

    @Builder
    public User(String name, String email, String picture, String feel, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.feel = feel;
        this.role = role;
    }

    public void update(String picture, String name, String feel) {
        this.picture = picture;
        this.name = name;
        this.feel = feel;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
