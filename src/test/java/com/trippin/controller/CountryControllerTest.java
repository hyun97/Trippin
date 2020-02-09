package com.trippin.controller;

import com.trippin.domain.Country;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.UserRepository;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() {
        countryRepository.deleteAll();
    }

    @Test
    public void createCountry() {
        // given
//        User user = User.builder()
//                .name("test")
//                .email("test@ddd.ddd")
//                .build();
//
//        userRepository.save(user);
//
//        CountryDto countryDto = CountryDto.builder()
//                .image("/test/test.img")
//                .name("Korea2")
//                .content("Great2")
//                .userId(12L)
//                .build();
//
//        String url = "http://localhost:" + port + "/api/country";
//
//        // when
//        ResponseEntity<Long> responseEntity
//                = restTemplate.postForEntity(url, countryDto, Long.class);
//
//        // then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<Country> countryList = countryRepository.findAll();
//        assertThat(countryList.get(0).getName()).isEqualTo("Korea2");
//        assertThat(countryList.get(0).getContent()).isEqualTo("Great2");
    }

    @Test
    @Transactional // DB 에 반영 안됨
    public void read() {
        Country country = countryRepository.findById(20L).orElse(null);

        System.out.println(country.getUser().getId());
    }

}