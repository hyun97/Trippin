package com.trippin.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByCountryIdOrderByCreatedAtDesc(Long countryId, Pageable pageable);

    Page<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByRegionContainingOrCountry_NameContaining(String region, String country, Pageable pageable);

}
