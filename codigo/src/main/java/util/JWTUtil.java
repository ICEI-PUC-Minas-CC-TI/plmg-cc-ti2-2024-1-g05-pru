package util;

import java.util.Date;
import io.jsonwebtoken.*;
import model.Usuario;

public class JWTUtil {
	public static String generateToken(Usuario usuario) {
		return Jwts.builder()
			.setSubject(usuario.getEmail())
			.claim("tipo", usuario.getClass().getSimpleName())
			.signWith(SignatureAlgorithm.HS512, "ACME")
			.setExpiration(new Date(System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000))
			.compact();
	}

	public static boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey("ACME").parseClaimsJws(token);

			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}

			return true;
		} catch (SignatureException e) {
			return false;
		}
	}
}
