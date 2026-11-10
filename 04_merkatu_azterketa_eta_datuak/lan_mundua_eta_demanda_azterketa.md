# Merkatuaren eta Lan Munduaren Azterketa: Software Garapena eta Biomedikuntza

> **Dokumentuaren xedea:** EIP II (Enplegagarritasunerako Ibilbide Pertsonala) moduluaren 2. ebaluazioko negoziorako ikerketa-oinarria.  
> **Egilea:** Multiplataforma Garapeneko (DAM) ikaslea & Biomedikuntza Ingeniaria.  
> **Testuingurua:** Euskadiko eta Europako osasun-ekosistema (Basque Health Cluster, Osakidetza, Eusko Jaurlaritzako RIS3 Bioosasuna).

---

## 1. Sarrera eta Testuinguru Orokorra

Gaur egungo gizarteak eta osasun-sistemak aurrekaririk gabeko erronka bati egin behar diote aurre: **aldaketa demografikoa, gaixotasun kronikoen gorakada eta osasun-azpiegitura tradizionalen gainezkatzea**. Testuinguru honetan, teknologia informatikoa eta zientzia biomedikoa ez dira jada arlo independenteak; alderantziz, osasunaren etorkizuna bi diziplina hauen erabateko konbergentzian oinarritzen da: **Digital Health (Osasun Digitala)**, **IoMT (Internet of Medical Things)** eta **SaMD (Software as a Medical Device)**.

Ikerketa honek lan-merkatuaren benetako beharra, profil profesionalen eskaria eta merkatu soziosanitarioaren eskaria (publikoarena zein osasun-erakundeena) aztertzen ditu datu erreal eta eguneratuen bidez.

---

## 2. Lan Munduan Dagoen Beharra: Enplegagarritasuna eta Gaitasunak

### 2.1. Software Garapenaren Arloan (DAM ikuspegia)
* **Aplikazio Multiplataformen Behar Larria:** Osasun arloan ezin da plataforma bakarrerako garatu. Pazienteak bere gailu pertsonalean (Android edo iOS telefonoan, smartwatchean) jaso behar du arreta; baina medikuak, erizainak edo fisioterapeutak bere bulegoan (Web nabigatzailean edo Windows/macOS mahaigainean) kontsultatu behar ditu datuak. Hori dela eta, kode-oinarri bateratuko multiplataforma teknologiak (adibidez, Flutter, React Native, Kotlin Multiplatform) eta API sendoak garatzeko gaitasuna duten profesionalen eskaria %24 hazi da azken urteotan.
* **IoT eta Gailuen Arteko Komunikazio Protokoloak:** Sentsore medikoekin haririk gabeko konexio lokalak kudeatzeko espezialistak behar dira (bereziki **Bluetooth Low Energy - BLE**, NFC, MQTT eta WebSockets). Erabiltzaile arruntak (sarritan adineko pertsonak) ez daki konfigurazio teknikorik egiten; beraz, komunikazioak automatikoa, sendoa eta etenik gabekoa izan behar du.
* **Datu-Segurtasuna eta Zibersegurtasun Klinikoa:** Osasun-datuak legedi zorrotzenen pean daude (DBEO / GDPR Europan, ENS - Segurtasun Eskema Nazionala Espainian, HIPAA AEBn). Datuak muturretik muturrera enkriptatzeko, baimenak kudeatzeko eta auditoria-erregistroak sortzeko gaitasuna duten software garatzaileak oso preziatuak dira.

### 2.2. Biomedikuntza Ingeniaritzaren Arloan
* **MDR (Medical Device Regulation - EB 2017/745) Araudiaren Erronka:** Europako araudi berriak baldintza oso zorrotzak ezarri dizkio osasun-softwareari. Software arrunt batek ezin du diagnostikorik edo tratamendu-laguntzarik eman CE marka medikorik gabe (Software as a Medical Device - SaMD). Enpresa teknologiko gehienek ez dute araudi klinikoa ezagutzen duten profesionalik, eta horrek produktu berrien merkaturatzea oztopatzen du.
* **Seinale Fisiologikoen Analisia eta Prozesamendua:** Gailu eramangarriek seinale ugari sortzen dituzte (elektrokardiogramak - EKG, pultsu-uhinak - PPG, azelerometria eta mugimendu-sentsoreak - IMU, arnas-maiztasuna). Seinale horiek zarata handia dute (mugimendu-artefaktuak, kontaktu txarra). Seinale biomedikoak garbitzeko eta parametro esanguratsuak ateratzeko ingeniaritza biomedikoko jakintza ezinbestekoa da.
* **Elkarreragingarritasun Klinikoa (HL7 / FHIR):** Ospitaleek eta osasun-zentroek beren historia kliniko elektronikoak (EHR) dituzte (adibidez, Osakidetzako *Osabide Global*). Gailu berriek sortutako datuek estandar medikoen arabera bidaiatu behar dute (**HL7 FHIR** - Fast Healthcare Interoperability Resources).

### 2.3. Zergatik da Urrezkoa Profil Hibrido Hau? (DAM + Biomedikuntza)
Gaur egungo lan-merkatuan arrakala (gap) nabarmen bat dago:
1. **Software garatzaile hutsak:** Teknologia bikain menderatzen du, baina ez daki bihotz-taupadaren seinale batek nola funtzionatzen duen, zer den arritmia bat, nola interpretatu azelerometro batek emandako belauneko mugimendu-graduak, ezta nola idatzi MDR araudiak eskatzen duen balidazio klinikoaren txostena.
2. **Ingeniari biomediko hutsak:** Fisiologia, instrumentazioa eta araudi medikoa ezagutzen ditu, baina sarri ez dauka kode arkitektura modernoak diseinatzeko, mugikorreko UI/UX intuitiboak eraikitzeko, REST/GraphQL API eskalagarriak sortzeko edo Git/CI-CD lan-fluxu profesionalak kudeatzeko formazio sendorik.
3. **Emaitza:** Bi munduak bateratzen dituen profil profesionalak berebiziko balioa sortzen du, gailu fisikoaren eta software erabilgarriaren arteko itzulpen-lana eta garapen integrala bakarrik egiteko gai baita.

---

## 3. Publikoaren eta Osasun Sistemaren Demanda Erreala

### 3.1. Datu Demografikoak eta Soziosanitarioak (Euskadi eta Europa)
* **Biztanleriaren Zahartze Azkarra:** Eustaten datuen arabera, Euskadin biztanleriaren **%23,5 baino gehiago 65 urtetik gorakoa da**, eta 80 urtetik gorakoen segmentua da gehien hazten ari dena. Euskadiko zahartze-indizea 160tik gorakoa da (100 gazteko 160 heldu).
* **Gaixotasun Kronikoen Karga:** EAEko osasun-gastu publikoaren **%77 inguru** gaixotasun kronikoen arretara bideratzen da (kardiopatiak, hipertentsioa, COPD/BGBK arnasketa-arazoak, diabetesa eta istripu zerebrobaskularrak / iktusak).
* **Ospitaleratzeak eta Baliabide Eza:** Iktus bat edo kirurgia kardiako bat jasan duten pazienteek hilabeteetako errehabilitazioa eta monitorizazioa behar dute. Ospitaleetako errehabilitazio-zerbitzuek hilabeteetako itxaron-zerrendak dituzte, eta paziente askok etxean ariketak egiteari uzten diote (%50 baino gehiagoko desatxikimendu terapeutikoa) jarraipenik ez dutelako.

### 3.2. Osasun Publikoaren eta Pribatuaren Eskaria
* **Ospitaleetatik Etxerako Trantsizioa (*Hospital at Home*):** Eusko Jaurlaritzako Osasun Sailak eta Osakidetzak beren plan estrategikoetan lehenetsi dute pazienteak beren etxean artatzea, ospitaleko egonaldiak ahalik eta laburrenak izan daitezen. Horrek tele-monitorizazio plataforma fidagarriak eskatzen ditu.
* **Medikuen eta Erizainen Saturazioa:** Medikuek "alerta-nekea" (alarm fatigue) pairatzen dute: ez dute nahi mila grafiko zentzugabe ikusi; pazientearen egoeraren laburpen sintetizatua eta anomalia larriak bakarrik nabarmentzen dituen software adimenduna behar dute.

### 3.3. Merkatuaren Tamaina eta Aurreikuspen Ekonomikoak
* **SaMD (Software as a Medical Device) Merkatua:** Mundu mailan SaMD merkatuak **%17,8ko urteroko hazkunde metatua (CAGR)** du 2024tik 2030era, eta Europan inbertsio handiena tele-monitorizazio eta kardiologia/errehabilitazio soluzioetan kontzentratzen da.
* **Euskadiko Bioosasun Ekosistema:** Euskadin **Basque Health Cluster**-ek 100 erakunde baino gehiago biltzen ditu (enpresak, Tecnalia, Vicomtech, BioGipuzkoa, BioBizkaia, unibertsitateak). Sektoreak Euskadiko BPGaren %2 inguru sortzen du eta 10.000 langile kualifikatu baino gehiagori ematen dio lana. Eusko Jaurlaritzak lehentasunezko arlo estrategikotzat du RIS3 Euskadi planean.

---

## 4. Ondorioak eta Negozio-Aukera: KuraLink-en Justifikazioa

Ikerketak argi erakusten du hutsune estrategiko bat dagoela:
1. Alde batetik, **merkataritzako kontsumo-wearableak** daude (Apple Watch, Fitbit, Xiaomi bandak): pausoak eta pultsu arrunta neurtzen dituzte, baina ez dute balio medikorik, ez dute MDR homologaziorik, eta ezin dira Osakidetzako historian integratu.
2. Beste aldetik, **ospitaleko ekipamendu klasikoak** daude: oso zehatzak baina astunak, garestiak, kablez beteak eta erabiltzeko oso zailak, ospitaleko oheari lotuta egotera behartzen dutenak.

**Aukera erreal eta bideragarria:**  
Software plataforma multiplataforma bat sortzea (**KuraLink**), pazientearen sentsore eramangarriak (BLE bidezko adabaki kardiakoak eta mugimendu-sentsoreak) automatikoki irakurtzen dituena, mugikorrean interfaze ulergarri batean erakusten dituena, eta hodeiko algoritmoen bidez medikuari informazio balioztatua eta Osabide/FHIR bateragarria helarazten diona.
