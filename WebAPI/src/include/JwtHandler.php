<?php

use Lcobucci\JWT\JwtFacade;
use Lcobucci\JWT\Signer\Hmac\Sha256;
use Lcobucci\JWT\Signer\Key\InMemory;
use Lcobucci\JWT\Token\Builder;
use Lcobucci\JWT\Token\RegisteredClaims;
use Lcobucci\JWT\Encoding\ChainedFormatter;
use Lcobucci\JWT\Encoding\JoseEncoder;

class JwtHandler {
	
	public function __construct() {
	
	}
	
	/**
	 * @throws Exception
	 */
	public static function getToken($claims = []): \Lcobucci\JWT\Token {
		$tokenBuilder = new Builder(new JoseEncoder(), ChainedFormatter::default());
		$algorithm = new Sha256();
		$signingKey = InMemory::plainText(random_bytes(32));
		
		$now = new DateTimeImmutable();
		$tokenBuilder = $tokenBuilder
			// Configures the issuer (iss claim)
			->issuedBy('http://api.s-b-c.com')
			// Configures the audience (aud claim)
			->permittedFor('http://api.s-b-c.com')
			// Configures the time that the token was issue (iat claim)
			->issuedAt($now)
			// Configures the expiration time of the token (exp claim)
			->expiresAt($now->modify('+30 minute'))
			// Configures a new header, called "foo"
			->withHeader('foo', 'bar');
		
		foreach ($claims as $key => $value) {
			// Configures a new claim, called "uid"
			$tokenBuilder->withClaim($key, $value);
		}
		
		// Builds a new token
		return $tokenBuilder->getToken($algorithm, $signingKey);
	}
	
}