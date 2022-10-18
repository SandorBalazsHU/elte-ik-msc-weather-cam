# The project's web API
REST API for communication with the web page and the Android app.

## Project structure

| Folder | Description |
| - | - |
| src | Source code |
| tests | Unit tests |
| vendor | Dependencies (in `.gitignore` to avoid bloating the repo) |

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
