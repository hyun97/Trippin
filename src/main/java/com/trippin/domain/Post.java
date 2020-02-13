package com.trippin.domain;

import com.trippin.controller.dto.PostDto;
import com.trippin.domain.util.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString(exclude = {"country", "user"})
@Entity
public class Post extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String region;

    private String content;

    private Integer favorite;

    @ManyToOne
    private Country country;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Bookmark> bookmark;

    public void update(PostDto postDto) {
        this.image = postDto.getImage();
        this.region = postDto.getRegion();
        this.content = postDto.getContent();
        this.favorite = postDto.getFavorite();
    }

}
