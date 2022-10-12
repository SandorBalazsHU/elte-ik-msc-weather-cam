## ELTE IK MSC Haladó Szoftvertechnológia projekt.

#### [Projekt 5] Időjárási kamera keretrendszer régi mobilokhoz. [Mobil, IoT, Web, Újrahasznosítás]

[![Időjárási kamera](https://people.inf.elte.hu/sabuaai/kepek/videokep.jpg)](https://www.youtube.com/watch?v=zTQyIcUM9w0)
(A projekt bemutató videója)
(Video URL: https://www.youtube.com/watch?v=zTQyIcUM9w0)

### Minimum Viable Product:

Egy könnyen telepíthető mobil alkalmazás, mely a kamera képét beállított időközönként szenzor adatokkal feliratozva feltölti egy szerverre ahol regisztráció után ezek tárolódnak és megtekinthetőek.

![My Remote Image](https://people.inf.elte.hu/sabuaai/kepek/kep1.jpg)

### Az ötlet részletes kifejtése:
A projekt célja eszközt biztosítani a fiókban porosodó, régi, de még működőképes mobiltelefonok felhasználására, hogy időjárási, vagy távfelügyeleti kameraként folytathassák az életüket.

A mobiltelefonok átlag élettartama a csere előtt 3-5 év, de sokuk még ezután is jól használható kevésbé számításigényes feladatokhoz. Mindben van GPS, iránytű, kamera, WIFI, Bluetooth, SD és SIM foglalat stb. Kár lenne veszni hagyni őket csak azért, mert az mondjuk 1Ghz-s processzor és az 512mb ram ma már kevés az átlagos felhasználásra, de maga a készülék még stabilan működik és rengeteg hardvert készen kínál, csak fel kell használnunk.
A régi telefon képességeit megsokszorozhatjuk egy kis IoT bevetésével. Egy ESP8266 mikrokontroller 1500HUF körüli áron beszerezhető és WIFI, Bluetooth (ESP32), vagy USB-n keresztül a régi telefonhoz kapcsolva megsokszorozható a régi eszközben rejlő lehetőség. A hardver kis IoT ismeret mellett és a firmware birtokában könnyen reprodukálható bárki számára.
Tehát van egy használt, de még sok potenciált rejtő mobil platformunk és egyegyszerű kis hardverünk. E kettőt összekötve a régi okostelefon új hasznos eszközként születhet újjá a költséges gyári időjárásfigyelő/távfelügyeleti kamerák alternatívájaként.
A felhasználandó eszközök: Git, Maven, Android (JAVA/Kotlin), Junit, Mockito, PHP, PHPUnit, HTML, JS, Arduino C++, ESP8266, ESP32, GoogleTest
Ha felkeltette a projekt az érdeklődésedet és otthon vagy a JAVA mobil és PHP webfejlesztésben, netán érdekel az IoT jelentkezz bátran a fórumban. Én vagyok a projekt ötletgazdája, szívesen válaszolok a felmerülő kérdésekre.
Ha sikerült felkeltenem az érdeklődésed, az alábbiakban olvashatsz a projekt működéséről és a tervezett felépítéséről.

![My Remote Image](https://people.inf.elte.hu/sabuaai/kepek/kep2.jpg)

### A projekt részei:
A rendszer három részből áll.
1.) A mobilalkalmazás
2.) A mobil képességeit kiterjesztő célhardver.
3.) A webes nyilvántartó és statisztikai rendszer.
 
#### Az eszköz összeállítása:
Az eszközt egy öntapadós mobil kar tartja az ablakon, egy mobil töltő látja el energiával és USB-n táplálja a távolabb lévő célhardvert.  (A hardver fejlettebb kiadása lehet napelemes távoli is.)
 
### Az eszköz működése és a megvalósítandó funkciók:
 
#### 1.) A mobilalkalmazás:
A mobilalkalmazás a telepítés és indítás után felkínálja a regisztrációt a weboldalhoz, majd bekéri a beállításokat. (Hálózati mód: mobil vagy wifi, ezek hozzáférési adatai, Időjárás vagy távfelügyelet üzemmód, frissítési gyakoriság, felbontás, feliratok, mértékegységek). Ezután kapcsolódik a célhardverhez és megadja az eszköznek a kezdeti beállításokat (Kapcsolat módja és hozzáférési adatok, frissítési gyakoriság)
Ezt követően a startra kattintva a mobilalkalmazás a megadott időközönként felvételt készít és lekéri az adatokat a célhardvertől, a képet méretezi, feliratozza a és feltölti a webszerverre az adatokkal együtt.

#### 2.) A célhardver:
Az eszköz táp alá helyezése és konfigurálása után wifi-n vagy BT-n keresztül időintervallumonként olvassa és küldi a szenzorok mért értékeit a telefonnak.

#### 3.) A weboldal:
A weboldal a képeket galériába rendezi a mért adatokkal együtt. Ebből videót és grafikont alkot.
Van lehetőség regisztrációra és az adatok vizualizációjára és közzétételére is. Az adatvizualizáció és a statisztika a weboldal fontos eleme, cél, hogy a mért adatok minél szemléletesebben legyenek ábrázolva a videó mellett.

![My Remote Image](https://people.inf.elte.hu/sabuaai/kepek/kep3.jpg)

### A technikai megoldások:
Git, Maven, Android (JAVA/Kotlin), Junit, Mockito, PHP, PHPUnit, HTML, JS, Arduino C++, ESP, GoogleTest

#### 1.) A mobilalkalmazás:
A mobilalkalmazás esetében cél, hogy minél régebbi készüléken használható lehessen így Android 3.0-tól felfelé történő kompatibilitást és JAVA nyelvű megvalósításra gondoltam. Kezelni kell a telefon érzékelőit, a beérkező adatokat és a telefon saját szenzorjait és az elkészült report-ot fel is kell tölteni a webre. /A mobil internetelérést kaphat a helyi WIFI-től vagy mobilinternetről. Ekkor hotspot-ként látja el WIFI-vel a hardware-t vagy BT-n át kommunikál vele. (A WIFI nagyobb távolságot enged).
A mobilalkalmazás a tárhelyet is kezeli, és egy megadott intervallumnyi adatot tart meg.

#### 2.) A célhardver:
Az alap kiadás:
Ez egy vízhatlan dobozba helyezett ESP8266 mikrokontroller a hozzá kapcsolt BMP280 légnyomás és hőmérővel és USB-s táplálási lehetőséggel.
A hardver több verzióban is elkészülhet. Ez a minimális kiadás, ebből gyártanék mindenkinek a teszteléshez.
A fejlettebb kiadásban a szenzorok száma növelhető (Pára, légminőség, szálló por mennyisége stb.). Gombok és kijelző is használható igény szerint. ESP32 vezérlővel BT kapcsolat is lehetséges.
Az eszköz 3.3v-5v tápfeszültséget igényel melyet a telefon OTG-USB-n keresztül, vagy PowerBank-ról is kaphat. A kábel hosszát, (max 1,5m) növelve a célhardver kihelyezhető az ablakon túlra. Az a fejlettebb kiadásban hajtható 3.3v-os akkumulátorról vagy napelemről is, de az alap modell vezetékes táplálású lesz.
A fejlesztés Arduino C++ és ESP mikrokontrollerek segítségével történik. Az eszközök elkészítését és a firmware megírását szívesen elvállalom, a csapat minden tagjának tudom biztosítani az alap hardvert a teszteléshez. De persze a hardver mockolása is lehetséges a fejlesztés elején. Készítettem már hasonló eszközt ami napelemmel üzemel és WIFI-n keresztül kommunikál.

![My Remote Image](https://people.inf.elte.hu/sabuaai/kepek/kep4.jpg)![My Remote Image](https://people.inf.elte.hu/sabuaai/kepek/kep5.jpg)![My Remote Image](https://people.inf.elte.hu/sabuaai/kepek/kep6.jpg)

#### 3.) A weboldal:
A weboldalhoz PHP backendet használnék, hogy a projektet minél könnyebben reprodukálhassák. Emellett rendelkezem 8GB PHP MYSQL Apache szerverrel, amit szabadon használhatunk Backend gyanánt. A frontendnek bootstrap-et képzeltem el az egyszerűség kedvéért, de bármi más is lehet.
 
### Az architektúra:
A hardverrel és a weboldallal pontosan definiált REST API-n át kommunikálna a mobilalkalmazás így mindhárom fejlesztője könnyen mockolhatja a kommunikációt a teszteléshez és a fejlesztéshez.
