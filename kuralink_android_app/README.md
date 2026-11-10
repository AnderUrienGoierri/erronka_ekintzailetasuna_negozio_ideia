# KuraLink Health — Android Studio Aplikazio Mugikorra (DAM)

> **Zikloa:** Multiplataforma Garapena (DAM) — 2. Maila  
> **Modulua:** Enplegagarritasunerako Ibilbide Pertsonala II (EIP II) & Mugikorren Garapena  
> **Ikastetxea:** Goierri Eskola  
> **Arkitektura:** Kotlin + Jetpack Compose + Material Design 3 + Clean Architecture

---

## 1. Proiektuaren Deskribapena

KuraLink Android aplikazio natiboa gaixo kronikoen eta errehabilitazioan dauden pertsonen monitorizazio biomedikorako diseinatuta dago. Aplikazioak gailu mediko eramangarriak (BLE bidezko adabaki kardiakoak eta IMU sentsoreak) automatikoki detektatzen ditu, seinale fisiologikoak denbora errealean irudikatzen ditu eta datuak hodeira zein Osakidetzako historia klinikora (HL7/FHIR) bidaltzen ditu.

Aplikazioak barnean hartzen ditu:
1. **HomeScreen (`HomeScreen.kt`):** EKG telemetria jarraituaren animazioa, bizi-parametroen txartelak (BPM, SpO2, Odol-presioa) eta Bluetooth Low Energy (BLE) konexioaren egoera.
2. **RehabScreen (`RehabScreen.kt`):** Ariketa terapeutikoen jarraipena, aste honetako betetze-tasa (%88) eta medikuak agindutako zereginen egoera.
3. **BusinessPlanScreen (`BusinessPlanScreen.kt`):** EIP II moduluko negozio-ideiaren 5 atal nagusien bistaratzaile dinamiko eta interaktiboa (txartel tolesgarriekin).

---

## 2. Nola Ireki eta Exekutatu Android Studio-n

1. Ireki **Android Studio** (Hedgehog, Iguana, Jellyfish edo berriagoa).
2. Hautatu **Open** eta aukeratu `kuralink_android_app` karpeta.
3. Itxaron Gradle sinkronizazioa amaitu arte (Gradle automatikoki deskargatuko ditu Jetpack Compose eta Material 3 liburutegiak).
4. Konektatu Android gailu fisiko bat edo abiarazi emuladorea (API 26+).
5. Sakatu **Run 'app'** (Shift + F10).

---

## 3. Baimen Teknikoak (`AndroidManifest.xml`)

* `android.permission.BLUETOOTH_SCAN` eta `BLUETOOTH_CONNECT`: Sentsore medikoekin haririk gabeko konexio lokal segururako.
* `android.permission.INTERNET`: Ospitaleko FHIR APIarekin eta hodeiko zerbitzariekin datuak sinkronizatzeko.
