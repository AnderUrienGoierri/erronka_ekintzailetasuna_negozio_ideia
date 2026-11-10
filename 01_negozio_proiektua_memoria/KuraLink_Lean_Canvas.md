# KuraLink Health Technologies — Lean Canvas Eredua

> **Metodologia:** Ash Maurya-ren Lean Canvas ekintzailetza eredua  
> **Egitasmoa:** EIP II — 2. Ebaluaketa Erronka (Goierri Eskola)  
> **Negozioa:** KuraLink Health Technologies S.L.

---

```
+------------------------------------+------------------------------------+------------------------------------+------------------------------------+------------------------------------+
| 1. ARAZOA                          | 4. IRTENBIDEA                      | 3. BALIO PROPOSAMEN BAKARRA        | 9. ABANTAILA BIDEGABEA             | 2. BEZERO SEGMENTUAK               |
|                                    |                                    |                                    | (Unfair Advantage)                 |                                    |
| • Gaixo kroniko eta errehabili-    | • Multiplataforma software medikoa | "Gailu biomediko anitzen datuak    | • Profil hibrido paregabea:        | • B2B Erosleak:                    |
|   taziokoen jarraipen kliniko      |   (Flutter Android/iOS + Web       |  modu automatikoan sinkronizatzen  |   Biomedikuntza Ingeniaritza +     |   - Osakidetza (telemedikuntza     |
|   etena etxera itzultzean.         |   Klinikoa + Cloud Backend).       |  dituen eta historia klinikoan     |   Multiplataforma Garapena (DAM).  |     pilotuak).                     |
|                                    |                                    |  zuzenean txertatzen den lehen     |                                    |   - Klinika eta ospitale pribatuak |
| • Tratamenduarekiko atxikimendu    | • Zero-Touch BLE konexioa gailu    |  software multiplataforma medikoa: | • MDR (EB 2017/745) erregulazioari |     (Kardiologia, Traumatologia).  |
|   eskasa (%50ek ariketak uzten     |   biomedikoekin (adabakiak,        |  pazientearentzat intuitiboa,      |   buruzko jatorrizko ezagutza.     |   - Osasun-aseguruak (IMQ,         |
|   dituzte).                        |   tentsiometroak, IMU sentsoreak). |  medikuarentzat fidagarria eta     |                                    |     Sanitas, Mapfre).              |
|                                    |                                    |  osasun-sistemarentzat errentaga-  | • Elkarreragingarritasun natiboa:  |                                    |
| • Datu biomedikoen deskonexioa:    | • Seinale-iragazketa adimentsua    |  rria."                            |   HL7 / FHIR estandarra Osabide    | • B2C Amaierako Erabiltzaileak:    |
|   wearable arruntek ez dute balio  |   eta anomalia-detekzioa, alerta   |                                    |   Global-ekin konektatzeko.        |   - 50-85 urteko pazienteak.       |
|   medikorik; ospitalekoak astunak. |   faltsuak saihesteko.             |                                    |                                    |   - Senideak eta zaintzaileak.     |
|------------------------------------+------------------------------------+------------------------------------+------------------------------------+------------------------------------+
| Existitzen diren alternatibak:     | 8. FUNTSEZKO METRIKAK              | Konfiantzazko Eslogana:            | 5. KANALAK                         | Lehen hartzaileak (Early Adopters):|
|                                    | (Key Metrics)                      |                                    |                                    |                                    |
| • Kontsumo-wearableak (Fitbit,     | • Ospitaleratze errepikatuen       | "Zubi adimenduna gailu medikoen    | • Basque Health Cluster sareko     | • Poliklinika Gipuzkoa eta         |
|   Apple Watch): fitness mailakoak. |   murrizketa-tasa (%35 helburu).   |  eta zure osasunaren artean"       |   sinergiak eta kontaktuak.        |   BioGipuzkoako errehabilitazio-   |
| • Paperezko auto-erregistroak.     | • Tratamendu atxikimendu tasa.     |                                    | • Kongresu medikoak (Kardiologia   |   zerbitzuak.                      |
| • Ospitaleko egonaldi luze         | • Plataformako paziente aktiboak.  | Kontzeptu Argi eta Gogoragarria:   |   eta Errehabilitazio Elkarteak).  | • Bihotz-kirurgia ondorengo        |
|   garestiak.                       | • Harpidetzaren berritze tasa      | Kura (zaintza) + Link (konexioa)   | • Osakidetzako berrikuntza         |   pazienteak etxera doazenean.     |
|                                    |   (B2B Churn < %5).                |                                    |   deialdi publikoak eta lizitazioak|                                    |
+------------------------------------+------------------------------------+------------------------------------+------------------------------------+------------------------------------+
| 7. KOSTUEN EGITURA (Cost Structure)                                     | 6. DIRU-SARRERA ITURRIAK (Revenue Streams)                              |
|                                                                         |                                                                         |
| • Pertsonal teknikoa: DAM garatzaileak, biomedikuntza ingeniariak.       | • B2B SaaS Harpidetza: Hileroko kuota ospitale eta klinika bakoitzeko     |
| • MDR (EB 2017/745) ziurtapen klinikoa eta erregulazio auditoriak.       |   aktibo dagoen paziente-bolumenaren arabera (adib. 25-40 €/paziente/hil).|
| • Hodeiko azpiegitura segurua (AWS/Azure Health, GDPR, babeskopiak).    | • Ezarpen eta Integrazio Zerbitzua: Ospitaleko EHR/Osabide sistemarekin |
| • Sentsore biomedikoen hornidura eta kalibrazio protokoloak.            |   konektatzeko hasierako konfigurazio lizentzia (10.000 - 25.000 €).    |
| • Salmenta, marketin medikoa eta kongresuetako presentzia.              | • Sentsore Kit Medikoen Salmenta / Alokairua (Hardware bazkideekin).     |
+-------------------------------------------------------------------------+-------------------------------------------------------------------------+
```

---

## Ohar Garrantzitsua Negozio Ereduari Buruz:

KuraLink ez da hardware fabrikatzaile bat (horrek kostu erraldoiak eta arrisku handia ekarriko lituzke hasieran). **KuraLink software enpresa espezializatua da (SaMD)**: merkatuan dauden gailu mediko homologatuen seinaleak (Bluetooth bidez) hartzen ditu, eta bere software multiplataformaren, algoritmoen eta hodeiaren bidez balio kliniko izugarria sortzen du. Horrek kostuak txiki mantentzea eta eskalagarritasun globala lortzea ahalbidetzen du.
