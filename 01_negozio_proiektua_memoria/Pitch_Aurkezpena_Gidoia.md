# KuraLink Health Technologies — Pitch Aurkezpenaren Gidoia (5-7 Minutu)

> **Egitasmoa:** Enplegagarritasunerako Ibilbide Pertsonala II (EIP II) — 2. Ebaluaketa  
> **Modulua / Zikloa:** Multiplataforma Garapena (DAM) & Biomedikuntza Ingeniaritza  
> **Ikastetxea:** Goierri Eskola  
> **Formatua:** Ahozko aurkezpena (Pitch Deck) irakasle eta ebaluazio-tribunalaren aurrean  
> **Iraupen estimatua:** 5 minutu (aurkezpena) + 2 minutu (galdera-erantzunak)

---

## 1. Aurkezpenaren Fitxa Estrategikoa

* **KuraLink Eslogana:** *"Zubi adimenduna gailu medikoen eta zure osasunaren artean"*
* **Helburu Nagusia:** Ebaluazio-tribunalari erakustea nola bihurtu den nire ezagutza bikoitza (Biomedikuntza Ingeniaritza + Multiplataforma Garapena) balio errealeko, errentagarriko eta bideragarriko negozio-proiektu bat.
* **Erabiliko diren Baliabideak:**
  1. `kuralink_web_app/index.html` (Zuzeneko telemetria eta ROI simulagailua pantailan erakusteko).
  2. Logotipoa eta Miramongo egoitzaren argazki profesionalak.

---

## 2. Diapositibaz Diapositibako Ahozko Gidoia

### Diapositiba 1: Sarrera eta Kakoa (Hook) — [0:00 - 0:45]
* **Ikus-entzunezkoa pantailan:** KuraLink logotipoa, Miramongo laborategiaren argazkia eta eslogana.
* **Ahozko gidoia:**
  > *"Egun on guztioi. Datu bakar batekin hasi nahi dut gaur: ba al zenekiten Euskadin biztanleriaren %23,5etik gora 65 urtetik gorakoak direla eta osasun-gastu publikoaren %77 gaixotasun kronikoak artatzera doala?  
  > Ospitaleak eta lehen arretako zentroak gainezka daude. Paziente batek ebakuntza kardiako baten edo iktus baten ostean alta hartzen duenean, benetako arazoa hasten da: etxean ez dago jarraipen kliniko digitalizaturik.  
  > Nire izena [Zure Izena] da, Biomedikuntza Ingeniaria naiz eta gaur egun Goierri Eskolan Multiplataforma Garapena ikasten ari naiz. Eta gaur **KuraLink Health Technologies** aurkezten dizuet: osasun tele-zaintza adimentsua gailu medikoen eta pazienteen artean."*

---

### Diapositiba 2: Arazoa eta Merkatuko Hutsunea — [0:45 - 1:30]
* **Ikus-entzunezkoa pantailan:** 3 zifra nagusiak (%23,5 zahartzea, %50 ariketa-uztea, %77 gastua) eta gaur egungo alternatiben mugak.
* **Ahozko gidoia:**
  > *"Zergatik dago arazo larri bat hemen? Gaur egun merkatuan bi aukera muturreko bakarrik ditugulako:  
  > Alde batetik, kontsumoko erlojuak ditugu (Fitbit, Apple Watch). Pausoak neurtzen dituzte, baina ez dute balio medikorik, ez dute Europako MDR homologaziorik eta mediku batek ezin ditu diagnostikorako erabili.  
  > Beste aldetik, ospitaleko makina klasikoak ditugu: oso zehatzak dira, baina milaka euro balio dute, kable astunak dituzte eta pazientea oheari lotuta egotera behartzen dute.  
  > Emaitza? Etxera doazen pazienteen %50ek errehabilitazio ariketak uzten dituzte, eta horrek ospitaleratze errepikatuak eragiten ditu, osasun-sistemari milioika euroko kostua sortuz."*

---

### Diapositiba 3: Irtenbidea eta Balio Proposamena — [1:30 - 2:30]
* **Ikus-entzunezkoa pantailan:** KuraLink mugikorreko aplikazioaren eta bularreko BioPatch sentsorearen argazkia (`kuralink_app_mockup.jpg`).
* **Ahozko gidoia:**
  > *"Hemen sartzen da KuraLink. KuraLink ez da fitness app bat; **SaMD (Software as a Medical Device - Klasea IIa)** plataforma bat da.  
  > Gure sistemak merkatuko sentsore eramangarri arinak (adabaki kardiakoak eta mugimendu-sentsoreak) automatikoki detektatzen ditu Bluetooth bidez.  
  > Pazienteak ez du inolako konfigurazio teknikorik egin behar: sentsorea bularrean jarri eta mugikorrak dena automatikoki egiten du (Zero-Touch BLE).  
  > Baina garrantzitsuena: KuraLink ez da irla isolatu bat. Gure softwareak datuak garbitu eta Osakidetzako historian (**Osabide Global / HL7 FHIR**) txertatzen ditu zuzenean. Medikuek ez dute grafiko zentzugabez betetako pantailarik nahi; KuraLink-ek anomalia larriak bakarrik iragazten ditu, alerten nekea saihestuz."*

---

### Diapositiba 4: Zuzeneko Erakustaldia (Demo Interaktiboa) — [2:30 - 3:30]
* **Ikus-entzunezkoa pantailan:** `kuralink_web_app` ireki eta "Simulagailu Klinikoa" tab-a erakutsi.
* **Ahozko gidoia:**
  > *"Hau hobeto ulertzeko, ikus dezagun garatu dugun web-aplikazio interaktiboa.  
  > [Erakutsi Canvas-eko EKG uhina]: Hemen ikus dezakezue paziente baten denbora errealeko EKG telemetria. Laginketa maiztasuna eta zarata-iragazketa MDR araudiaren arabera konfiguratuta daude.  
  > [Mugitu BPM graduatzailea 72tik 110era]: Pazienteak ariketa egiten duenean, bihotz-maiztasuna aldatzen da eta algoritmoak erritmoa jarraitzen du.  
  > [Sakatu 'Arritmia Simulatua']: Ikusi nola aldatzen den uhina: berehala alerta gorria pizten da eta sistemak triatze automatikoa bidaltzen dio espezialistari.  
  > Aldi berean, Kotlin eta Jetpack Compose bidez Android Studio-ko aplikazio natiboa garatu dugu, pazienteak bere mugikorrean ariketak, bizi-seinaleak eta erronka pertsonalak jarraitu ditzan."*

---

### Diapositiba 5: Negozio Eredua eta Errentagarritasuna (ROI) — [3:30 - 4:15]
* **Ikus-entzunezkoa pantailan:** Web app-eko "ROI Kalkulagailua" tab-a eta Lean Canvas-aren laburpena.
* **Ahozko gidoia:**
  > *"Nola bihurtzen da hau negozio bideragarri eta errentagarri? Gure bezero eroslea ez da pazientea, ospitaleak eta aseguru etxeak baizik (B2B2C eredua).  
  > Eredua **B2B SaaS harpidetza** da: ospitaleek hileroko lizentzia ordaintzen dute aktibo duten paziente bakoitzeko.  
  > Zergatik ordainduko luke Osakidetzak edo IMQ-k? Zenbakiak oso argiak dira:  
  > Ospitaleko ohe batek 750 € balio du egunean. KuraLink-ekin 120 pazienteren alta 4 egun aurreratzen badugu, ospitaleak **360.000 € aurrezten ditu urtean**, eta aldi berean bir-ospitaleratzeak %35 murrizten dira. Inbertsioaren itzulera (ROI) berehalakoa da."*

---

### Diapositiba 6: Taldea eta Nire Desberdintasun Lehiakorra — [4:15 - 5:00]
* **Ikus-entzunezkoa pantailan:** Miramongo egoitza, DAM zikloa eta Biomedikuntza formazioa.
* **Ahozko gidoia:**
  > *"Zergatik egin dezaket nik proiektu hau aurrera? Merkatuan dagoen arrakala handi bat ixten dudalako:  
  > Informatikari hutsak ez daki seinale biologiko batek nola funtzionatzen duen ezta MDR araudia nola bete; eta biomediku hutsak ez daki aplikazio multiplataforma eskalagarriak garatzen. Bi munduak menderatzeak balio paregabea ematen dio nire profilari.  
  > KuraLink-ek gure gizartearen erronka handienari erantzun teknologiko bideragarria ematen dio.  
  > Proiektu osoa, kodea eta simulagailua nire GitHub biltegi publikoan dituzue ikusgai.  
  > Eskerrik asko zure arretagatik, eta orain pozik erantzungo ditut zuen galderak."*

---

## 3. Epaimahaiaren Galdera Tipikoak eta Nola Erantzun

1. **Galdera:** *"Nola bermatzen duzue datu medikoen segurtasuna pazientearen etxetik ospitalera?"*
   * **Erantzuna:** *"Muturreko enkriptazioa erabiltzen dugu (AES-256 transmisioan eta TLS 1.3 garraioan). Datuak GDPR eta ENS (Segurtasun Eskema Nazionala) Altuko araudia betetzen duten zerbitzarietan gordetzen dira, eta HL7/FHIR protokolo estandarraren bidez konektatzen gara Osakidetzako Osabiderekin."*
2. **Galdera:** *"Zenbat denbora beharko litzateke CE MDR ziurtagiri medikoa lortzeko?"*
   * **Erantzuna:** *"SaMD IIa klaseko gailu gisa, prozesuak 12 eta 18 hilabete artean irauten du. Horregatik, lehen fasean BioGipuzkoa eta ospitaleekin ikerketa-pilotua abiatzen dugu, ebidentzia klinikoa biltzeko ziurtapena lortu bitartean."*
3. **Galdera:** *"Hardwarea zuek fabrikatuko duzue?"*
   * **Erantzuna:** *"Ez, hori akats estrategiko bat litzateke hasieran. KuraLink software enpresa bat da. Merkatuan jada CE marka duten sentsore eramangarriekin (adabakiak, IMU sentsoreak) elkartzen gara, eta gure balio nagusia software multiplataforman, algoritmoetan eta integrazio klinikoan dago."*
