package hu.progmasters.hotel.repository;

import hu.progmasters.hotel.domain.OauthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OauthTokenRepository extends JpaRepository<OauthToken, Long> {


    OauthToken findTopByOrderByIdDesc();


}
