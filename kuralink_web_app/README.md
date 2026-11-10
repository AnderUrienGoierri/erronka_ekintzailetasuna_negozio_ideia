# KuraLink Health Technologies — Web App Interaktiboa (Figma UI & Frontend)

> **Zikloa:** Multiplataforma Garapena (DAM) — 2. Maila  
> **Modulua:** Enplegagarritasunerako Ibilbide Pertsonala II (EIP II)  
> **Ikastetxea:** Goierri Eskola  
> **Diseinu Araua:** Zero Emotikono (SVG ikur bektorial garbiak soilik) & Figma Diseinu Sistemaren Tokenak

---

## 1. Nola Ireki eta Erabili Nabigatzailean

1. Ireki fitxategi-esploratzailea eta egin klik bikoitza `index.html` fitxategian (`kuralink_web_app/index.html`).
2. Zure nabigatzaile lehenetsian irekiko da berehala (Chrome, Firefox, Edge, Safari).
3. Ez du inolako mendekotasunik edo instalaziorik behar; guztiz eramangarria eta arina da.

Nahi izanez gero, tokiko zerbitzari baten bidez ere abiarazi daiteke:
```bash
# Python bidez:
python -m http.server 8080
# Nabigatzailean: http://localhost:8080
```

---

## 2. Web App-aren Funtzionalitate Interaktiboak

1. **Figma UI Tab Nabigazioa:**
   * Orria berriro kargatu gabe atal batetik bestera aldatzen da leunki.
2. **EKG Telemetria Fisiologikoaren Simulagailua (HTML5 Canvas):**
   * Bihotz-maiztasuna aldatzeko graduatzailea (slider: 45 BPM - 150 BPM).
   * Modu arrunta eta arritmia simulatzeko kontrolak, alerta kliniko automatikoekin.
3. **BLE Parekatze Simulagailua:**
   * "Bilatu Sentsoreak (BLE)" sakatzean inguruko sentsore biomedikoa aurkitu eta zero-touch bidez sinkronizatzen du.
4. **Ospitaleen ROI eta Errentagarritasun Kalkulagailua:**
   * Paziente kopurua, aurreztutako ohe-egunak eta ohearen prezioa sartzean, aurreztutako euroak eta saihestutako birsartzeak kalkulatzen ditu denbora errealean.
5. **Marka eta Argazki Errealen Galeria:**
   * Miramongo I+G laborategiaren eta KuraLink smartphone/sentsorearen argazki profesional errealak.

---

## 3. Fitxategien Egitura

* `index.html`: Web aplikazioaren egitura semantikoa eta atal guztiak.
* `style.css`: Figma diseinu-sistemaren tokenak, aldagaiak, koloreak, sareak eta estilo sentikorrak.
* `app.js`: Tab-ak, EKG Canvas-a, BLE parekatzea eta ROI kalkulagailua kudeatzen dituen JavaScript logika.
* `assets/images/`: Logotipo bektorialak (`kuralink_logo.svg`, `kuralink_icon.svg`) eta kalitate handiko argazki errealak (`kuralink_enpresa_irudia.jpg`, `kuralink_app_mockup.jpg`).
