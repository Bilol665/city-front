package uz.pdp.cityfront.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.tokens.ScalarToken;
import uz.pdp.cityfront.domain.entity.token.JwtTokenEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, UUID> {
    JwtTokenEntity findJwtTokenEntitiesByUsername(String username);
    @Query("select count(t) from tokens t where t.username=:username")
    Long countJwtTokenEntitiesByUsername(@Param("username") String username);
}
