package com.trippin.config.auth;

import com.trippin.domain.Country;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.User;
import com.trippin.domain.UserRepository;
import com.trippin.domain.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()).orElse(null);

        if (user == null) {
            User newUser = User.builder()
                    .name(attributes.getName())
                    .email(attributes.getEmail())
                    .picture(attributes.getPicture())
                    .feel("상태메시지 없음")
                    .role(Role.USER)
                    .build();

            userRepository.save(newUser);

            Country country = Country.builder()
                    .image("Korea.jpg")
                    .name("KOREA")
                    .content("")
                    .user(userRepository.findByEmail(attributes.getEmail()).orElse(null))
                    .build();

            countryRepository.save(country);

            return newUser;
        } else {
            return user;
        }
    }

}