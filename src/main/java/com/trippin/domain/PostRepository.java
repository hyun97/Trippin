package com.trippin.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByCountryIdOrderByCreatedAtDesc(Long countryId);

    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);

//    List<Post> findAllByOrderByCreatedAtDesc();

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByUserIdOrderByCreatedAtDesc(List<Long> userId);

    List<Post> findByRegionContainingOrCountry_NameContaining(String region, String country);

}
