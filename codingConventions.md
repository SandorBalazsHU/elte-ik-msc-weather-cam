## A projekt során alkalmazott kódolási és névkonvenciók

### Elnevezések:
- Nem webes alkalmazás esetén a fájlnevek angol nyelvűk CamelCase tagolással, ha a fájl egy osztályt reperezentál akkor nagy kezdőbetűvel. (Pl.: DatabaseConnector.cpp)
- Webes alkalmazás esetén a fájlnevek kis betűs ASCII karakterek kötőjeles tagolással. (Pl.: database-connector.php)
- Az osztályok nevei nagy kezdőbetűsek, a metódusok kis kezdőbetűsek, mindkettő CamelCase tagolású angol nyelvű kifejezés.
- A dokumentációk magyar nyelvűek.

### Tesztelés:
Minden létrehozott osztályhoz Unit teszt tartozik.