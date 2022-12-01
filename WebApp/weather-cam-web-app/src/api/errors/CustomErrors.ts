import type HttpStatusCode from "@/utils/HttpStatusCode.js";
import Http from "@/utils/HttpStatusCode.js";
import type { ModelApiResponse } from "../openapi/index.js";

export class HttpError extends Error {
  statusCode: HttpStatusCode;
  constructor(statusCode: HttpStatusCode, errorString: string) {
    super(errorString);
    this.statusCode = statusCode;
    this.name = "BadRequestError";
  }
}

export class BadRequestError extends HttpError {
  //400
  constructor(errorString: string) {
    super(400, errorString);
    this.name = "BadRequestError";
  }
}

export class UnauthorizedError extends HttpError {
  //401
  constructor(errorString: string) {
    super(401, errorString);
    this.name = "UnauthorizedError";
  }
}

export class ForbiddenError extends HttpError {
  //403
  constructor(errorString: string) {
    super(403, errorString);
    this.name = "ForbiddenError";
  }
}

export class NotFoundError extends HttpError {
  //404
  constructor(errorString: string) {
    super(404, errorString);
    this.name = "NotFoundError";
  }
}

export class ConflictError extends HttpError {
  //409
  constructor(errorString: string) {
    super(409, errorString);
    this.name = "ConflictError";
  }
}

export class InternalServerError extends HttpError {
  //500
  constructor(errorString: string) {
    super(500, errorString);
    this.name = "InternalServerError";
  }
}

const errorStr = (msg: string, status: Http, body: unknown) =>
  `ERROR: ${status}.${msg} ${body ? `body : ${body}` : ""}`;

export const unifyError = (error: unknown) =>
  error instanceof HttpError
    ? error
    : (new InternalServerError(
        "An unknown error occured! Maybe the server is offline."
      ) as HttpError);

export function throwErrorByResponse(status: Http, responseBody: unknown = null) {
  const errorNotDefined = (status: HttpStatusCode) => {
    if (status >= 200 && status <= 299) {
      return;
    }
    throw Error(errorStr("An error occured during your request.", status, responseBody));
  };
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
    case Http.INTERNAL_SERVER_ERROR:
      throw new ConflictError(
        errorStr("An internal server error occured, please try again later!", status, responseBody)
      );
    default:
      errorNotDefined(status);
  }
}
