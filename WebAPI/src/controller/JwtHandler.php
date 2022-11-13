<?php

use Lcobucci\Clock\SystemClock;
use Lcobucci\JWT\Encoding\CannotDecodeContent;
use Lcobucci\JWT\Encoding\ChainedFormatter;
use Lcobucci\JWT\Encoding\JoseEncoder;
use Lcobucci\JWT\Signer\Hmac\Sha256;
use Lcobucci\JWT\Signer\Key\InMemory;
use Lcobucci\JWT\Token;
use Lcobucci\JWT\Token\Builder;
use Lcobucci\JWT\Token\InvalidTokenStructure;
use Lcobucci\JWT\Token\Parser;
use Lcobucci\JWT\Token\UnsupportedHeaderFound;
use Lcobucci\JWT\Validation\Constraint\IssuedBy;
use Lcobucci\JWT\Validation\Constraint\PermittedFor;
use Lcobucci\JWT\Validation\RequiredConstraintsViolated;
use Lcobucci\JWT\Validation\Validator;

class JwtHandler extends BaseController {
	public static string $CLAIM_UID = 'uid';
	public static int $CLAIM_NOT_FOUND = -1;
	
	private Parser $parser;
	private SystemClock $clock;
	private Validator $validator;
	private InMemory $signingKey;
	
	public function __construct() {
		$this->parser = new Parser(new JoseEncoder());
		$this->clock = SystemClock::fromSystemTimezone();
		$this->validator = new Validator();
		$this->signingKey = InMemory::plainText(JWT_KEY);
	}
	
	public function getToken($user_id): Token {
		$tokenBuilder = new Builder(new JoseEncoder(), ChainedFormatter::default());
		$now = $this->clock->now();
		$algorithm = new Sha256();
		
		return $tokenBuilder
			->issuedBy(JWT_ISSUED_BY)
			->permittedFor(JWT_PERMITTED_FOR)
			->issuedAt($now)
			->expiresAt($now->modify(JWT_VALID_FOR))
			->withClaim(JwtHandler::$CLAIM_UID, $user_id)
			->getToken($algorithm, $this->signingKey);
	}
	
	public function validate($token_string): int {
		try {
			$token = $this->parser->parse($token_string);
		} catch (CannotDecodeContent | InvalidTokenStructure | UnsupportedHeaderFound $e) {
			$this->error(403);
		}
		
		try {
			$this->validator->assert($token, new IssuedBy(JWT_ISSUED_BY));
			$this->validator->assert($token, new PermittedFor(JWT_PERMITTED_FOR));
		} catch (RequiredConstraintsViolated $exception) {
			foreach ($exception->violations() as $violation) {
				echo $violation->getMessage(), PHP_EOL;
			}
			$this->error(403);
		}
		
		$user_id = $token->claims()->get(JwtHandler::$CLAIM_UID, JwtHandler::$CLAIM_NOT_FOUND);
		if ($user_id == JwtHandler::$CLAIM_NOT_FOUND) {
			$this->error(403);
		}
		
		$user_dao = new UserDao();
		$user = $user_dao->getUserById($user_id);
		
		if (empty($user)) {
			$this->error(403);
		}
		
		return $user['user_id'];
	}
	
}