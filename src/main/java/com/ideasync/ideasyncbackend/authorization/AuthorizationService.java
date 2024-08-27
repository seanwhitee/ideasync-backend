package com.ideasync.ideasyncbackend.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.Base64;




@Service
public class AuthorizationService {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationService.class);

    public String generateToken() throws Exception {
        RSAPublicKey rsaPublicKey = getPublicKey();
        RSAPrivateKey rsaPrivateKey = getPrivateKey();
        try {
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            return JWT.create()
                    .withIssuer("https://ideasync.withpaipai.com")
                    // expire in 1 hour
                    .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 3600000))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            // Invalid Signing configuration / Couldn't convert Claims.
            log.error("Error creating token", exception);
            throw exception;
        }
    }

    public static RSAPublicKey getPublicKey() throws Exception {
        try {
            String publicKeyContent = Files.readString(Paths.get(ClassLoader.getSystemResource("public.key").toURI()), Charset.defaultCharset());
            publicKeyContent = publicKeyContent
                    .replaceAll("\\n", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", ""); // Remove all whitespace
            byte[] encoded = Base64.getDecoder().decode(publicKeyContent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            log.error("Error reading public key", e);
            throw e;
        }
    }

    public static RSAPrivateKey getPrivateKey() throws Exception {
        try {
            String privateKeyContent = Files.readString(Paths.get(ClassLoader.getSystemResource("private.key").toURI()), Charset.defaultCharset());
            privateKeyContent = privateKeyContent
                    .replaceAll("\\n", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", ""); // Remove all whitespace
            byte[] encoded = Base64.getDecoder().decode(privateKeyContent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            log.error("Error reading private key", e);
            throw e;
        }
    }
}
