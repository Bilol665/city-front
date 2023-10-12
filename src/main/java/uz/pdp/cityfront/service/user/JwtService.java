package uz.pdp.cityfront.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.cityfront.exceptions.MyException;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Jws<Claims> extractToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }
    public void checkTokenExpiration(String token) {
        Jws<Claims> claimsJws = extractToken(token);
        Date expiration = claimsJws.getBody().getExpiration();
        if(expiration.before(new Date())) throw new MyException("Session expired!");
    }
}
