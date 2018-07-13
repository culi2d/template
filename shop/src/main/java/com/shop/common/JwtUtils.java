package com.shop.common;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.shop.dao.UserDao;

/**
 * The Class JwtService.
 */
@Component
public class JwtUtils {

	/** The jwt key. */
	@Value("${jwt.key}")
	private String jwtKey;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(JwtUtils.class);

	/** The user DAO. */
	@Autowired
	UserDao userDao;

	/**
	 * Generate token login.
	 *
	 * @param userId the user id
	 * @return the string
	 * @throws JOSEException the JOSE exception
	 */
	public String generateTokenLogin(long userId) throws JOSEException {
		String token = null;
		// Create HMAC signer
		JWSSigner signer = new MACSigner(generateShareSecret());

		JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
		builder.claim(Constant.TOKEN_CLAIM_USER_ID, userId);

		JWTClaimsSet claimsSet = builder.build();
		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

		// Apply the HMAC protection
		signedJWT.sign(signer);

		// Serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		token = signedJWT.serialize();
		return token;
	}

	/**
	 * Gets the claims from token.
	 *
	 * @param token the token
	 * @return the claims from token
	 */
	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return claims;
	}

	/**
	 * Gets the user id from token.
	 *
	 * @param token the token
	 * @return the user id from token
	 * @throws ParseException the parse exception
	 */
	public Long getUserIdFromToken(String token) throws ParseException {
		JWTClaimsSet claims = getClaimsFromToken(token);
		return claims.getLongClaim(Constant.TOKEN_CLAIM_USER_ID);
	}

	/**
	 * Generate share secret.
	 *
	 * @return the byte[]
	 */
	private byte[] generateShareSecret() {
		// Generate 256-bit (32-byte) shared secret
		byte[] sharedSecret = new byte[32];
		sharedSecret = jwtKey.getBytes();
		return sharedSecret;
	}

}