# The project's web API
REST API for communication with the web page and the Android app.  
Read the [API documentation here](https://editor.swagger.io/?url=https://raw.githubusercontent.com/SandorBalazsHU/elte-ik-msc-weather-cam/main/Documentation/WebApi/weather-cam-web-api.yaml)!

## Project structure

| Folder | Description |
| - | - |
| src | Source code |
| tests | Unit tests |
| mock | Mock API endpoints |
| vendor | Dependencies (in `.gitignore` to avoid bloating the repo) |

## Mock API
List of endpoints:
```
├── check-data.php
├── failure
│   ├── 400.php
│   ├── 401.php
│   ├── 403.php
│   ├── 404.php
│   └── 409.php
├── get-picture-decoded.php
├── get-picture.php
├── get-stations-id-api.php
├── get-stations-measurement-one.php
├── get-stations-measurements.php
├── get-stations-storage.php
├── get-user-stations.php
├── index.php
├── post-put-user-stations.php
└── success
    ├── 200.php
    ├── 201 .php
    └── 206.php
```

Access path: `mock.weather.s-b-x.com/{mock_endpoint}`

Examples:
- [http://mock.weather.s-b-x.com/get-stations-measurements.php]()
- [http://mock.weather.s-b-x.com/success/200.php]()
- [http://mock.weather.s-b-x.com/check-data.php?id=123&version=beta]()

Endpoints usually start with the HTTP access method

Endpoints:
- under `success` - success messages
- under `failure` - failure messages
- `get-picture` - image base64 encoded as a string
- `get-picture-decoded` - decoded jpeg image
- `get-stations-id-api` - API key of the station
- `get-stations-measurement-one` - one measurement
- `get-stations-measurements` - array of measurements
- `get-stations-storage` - storage query
- `get-user-stations`
- `index.php` - Belongs to hardware and sensor data mocking (could be reached just by [http://mock.weather.s-b-x.com]())
- `post-put-user-stations` - mocks both:
  - POST `/user/stations`
  - PUT `/user/stations/{station_id}`

## PHPUnit setup

### Setup Composer
```
php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');"
php -r "if (hash_file('sha384', 'composer-setup.php') === '55ce33d7678c5a611085589f1f3ddf8b3c52d662cd01d4ba75c0ee0459970c2200a51f492d557530c71c15d8dba01eae') { echo 'Installer verified'; } else { echo 'Installer corrupt'; unlink('composer-setup.php'); } echo PHP_EOL;"
php composer-setup.php
php -r "unlink('composer-setup.php');"
```
More info about installation:
- [composer download page](https://getcomposer.org/download/)
- [programmatic installation](https://getcomposer.org/doc/faqs/how-to-install-composer-programmatically.md)

### Add PHPUnit as a local dependency to the project
Required dependencies:
- `ext-dom` &rarr; `sudo apt-get install php-xml -y`
- `ext-mbstring` &rarr; `sudo apt-get install php-mbstring -y` ([help if using docker](https://stackoverflow.com/a/49087941/12382368))

Install PHPUnit
```
composer require --dev phpunit/phpunit ^9
```

### Run unit tests
```
./vendor/bin/phpunit tests
```

Demo output
```
PHPUnit 9.5.25 #StandWithUkraine

..                                                                  2 / 2 (100%)

Time: 00:00.003, Memory: 4.00 MB

OK (2 tests, 2 assertions)
```
