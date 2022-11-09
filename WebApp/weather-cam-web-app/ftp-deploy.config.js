// @ts-ignore

// eslint-disable-next-line no-undef
const FtpDeploy = require("ftp-deploy");
const ftpDeploy = new FtpDeploy();
// eslint-disable-next-line no-undef
const SECRETS = require("./.vscode/secrets.json");
const config = {
  user: SECRETS.FTP_USERNAME,
  // Password optional, prompted if none given
  password: SECRETS.FTP_PASSWORD,
  host: SECRETS.FTP_HOST,
  port: 21,
  // eslint-disable-next-line no-undef
  localRoot: __dirname + "/dist",
  remoteRoot: "/",
  // include: ["*", "**/*"],      // this would upload everything except dot files
  include: ["*"],
  // e.g. exclude sourcemaps, and ALL files in node_modules (including dot files)
  exclude: [],
  // delete ALL existing files at destination before uploading, if true
  deleteRemote: false,
  // Passive mode is forced (EPSV command is not sent)
  forcePasv: true,
  // use sftp or ftp
  sftp: false,
};

ftpDeploy
  .deploy(config)
  .then((res) => console.log("finished:", res))
  .catch((err) => console.log(err));
