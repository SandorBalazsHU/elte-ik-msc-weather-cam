import Http from "@/utils/HttpStatusCode.js";
import type { ModelApiResponse } from "../openapi/index.js";

export class BadRequestError extends Error {
  //400
  constructor(errorString: string) {
    super(errorString);
    this.name = "BadRequestError";
  }
}

export class UnauthorizedError extends Error {
  //401
  constructor(errorString: string) {
    super(errorString);
    this.name = "UnauthorizedError";
  }
}

export class ForbiddenError extends Error {
  //403
  constructor(errorString: string) {
    super(errorString);
    this.name = "ForbiddenError";
  }
}

export class NotFoundError extends Error {
  //404
  constructor(errorString: string) {
    super(errorString);
    this.name = "NotFoundError";
  }
}

export class ConflictError extends Error {
  //409
  constructor(errorString: string) {
    super(errorString);
    this.name = "ConflictError";
  }
}

const errorStr = (msg: string, status: Http, body: unknown) =>
  `ERROR: ${status}.${msg} ${body ? `body : ${body}` : ""}`;

export function throwErrorByResponse(status: Http, responseBody: unknown = null) {
  switch (status) {
    case Http.BAD_REQUEST:
      throw new BadRequestError(
        errorStr("Malformed request body or parameters!", status, responseBody)
      );
    case Http.UNAUTHORIZED:
      throw new UnauthorizedError(errorStr("Authentication failed!", status, responseBody));
    case Http.FORBIDDEN:
      throw new ForbiddenError(errorStr("Authorization failed!", status, responseBody));
    case Http.NOT_FOUND:
      throw new NotFoundError(errorStr("Requested recource not found!", status, responseBody));
    case Http.CONFLICT:
      throw new ConflictError(
        errorStr("There was a conflict with processing your request!", status, responseBody)
      );
    default:
      throw new Error(
        errorStr("There was an error during processing your request!", status, responseBody)
      );
  }
}
