/**
 * KuraLink Health Technologies — Frontend Interaktiboaren Kontrolatzailea
 * EIP II Erronka | Goierri Eskola | DAM & Biomedikuntza
 * Murrizketa zorrotza: EZ ERABILI EMOTIKONOAK (ikono bektorialak soilik).
 */

document.addEventListener("DOMContentLoaded", () => {
  initTabNavigation();
  initEcgSimulator();
  initRoiCalculator();
  initBlePairingSimulator();
});

/* ==========================================================================
   1. Tab Nabigazio Interaktiboa (Figma UI Pattern)
   ========================================================================== */
function initTabNavigation() {
  const tabButtons = document.querySelectorAll(".nav-tab-btn");
  const tabPanels = document.querySelectorAll(".tab-panel");

  tabButtons.forEach((btn) => {
    btn.addEventListener("click", () => {
      const targetId = btn.getAttribute("data-tab");

      // Aktibo klaseak eguneratu botoietan
      tabButtons.forEach((b) => b.classList.remove("active"));
      btn.classList.add("active");

      // Panelak erakutsi / ezkutatu
      tabPanels.forEach((panel) => {
        if (panel.id === targetId) {
          panel.classList.add("active");
        } else {
          panel.classList.remove("active");
        }
      });

      // Leihoa panelera leunki mugitu mugikorretan
      if (window.innerWidth < 768) {
        const targetPanel = document.getElementById(targetId);
        if (targetPanel) {
          targetPanel.scrollIntoView({ behavior: "smooth" });
        }
      }
    });
  });
}

/* ==========================================================================
   2. EKG Telemetria Fisiologikoaren Simulagailua (HTML5 Canvas)
   ========================================================================== */
let ecgAnimationId = null;
let currentBpm = 72;
let isArrhythmia = false;

function initEcgSimulator() {
  const canvas = document.getElementById("ecgCanvas");
  if (!canvas) return;

  const ctx = canvas.getContext("2d");
  const bpmSlider = document.getElementById("bpmSlider");
  const bpmDisplay = document.getElementById("bpmDisplay");
  const btnNormal = document.getElementById("btnEcgNormal");
  const btnArrhythmia = document.getElementById("btnEcgArrhythmia");
  const statusDisplay = document.getElementById("ecgStatusDisplay");

  // Canvas tamaina fisikoa egokitu erresoluzio handirako (Retina / High DPI)
  function resizeCanvas() {
    const rect = canvas.getBoundingClientRect();
    canvas.width = rect.width * window.devicePixelRatio;
    canvas.height = rect.height * window.devicePixelRatio;
    ctx.scale(window.devicePixelRatio, window.devicePixelRatio);
  }

  resizeCanvas();
  window.addEventListener("resize", resizeCanvas);

  // Slider gertaerak
  if (bpmSlider && bpmDisplay) {
    bpmSlider.addEventListener("input", (e) => {
      currentBpm = parseInt(e.target.value, 10);
      bpmDisplay.textContent = `${currentBpm} BPM`;
      updateClinicalStatus();
    });
  }

  // Modu arrunta / Arritmia
  if (btnNormal && btnArrhythmia) {
    btnNormal.addEventListener("click", () => {
      isArrhythmia = false;
      btnNormal.classList.add("active");
      btnArrhythmia.classList.remove("active");
      updateClinicalStatus();
    });

    btnArrhythmia.addEventListener("click", () => {
      isArrhythmia = true;
      btnArrhythmia.classList.add("active");
      btnNormal.classList.remove("active");
      updateClinicalStatus();
    });
  }

  function updateClinicalStatus() {
    if (!statusDisplay) return;
    if (isArrhythmia) {
      statusDisplay.textContent = "Alerta: Fibrilazio Aurikularra / Desbiderapena";
      statusDisplay.style.color = "var(--alert-red)";
      statusDisplay.style.borderColor = "var(--alert-red)";
    } else if (currentBpm < 60) {
      statusDisplay.textContent = "Bradikardia Leuna (Atseden egoera)";
      statusDisplay.style.color = "var(--tech-blue-light)";
      statusDisplay.style.borderColor = "var(--tech-blue)";
    } else if (currentBpm > 100) {
      statusDisplay.textContent = "Takikardia Arina (Ariketa egoera)";
      statusDisplay.style.color = "var(--accent-cyan)";
      statusDisplay.style.borderColor = "var(--accent-cyan)";
    } else {
      statusDisplay.textContent = "Erritmo Sinusala Egonkorra (Normala)";
      statusDisplay.style.color = "var(--accent-green)";
      statusDisplay.style.borderColor = "var(--accent-green)";
    }
  }

  // Marrazketa animatua
  let offset = 0;

  function renderEcg() {
    const rect = canvas.getBoundingClientRect();
    const w = rect.width;
    const h = rect.height;
    const midY = h / 2;

    ctx.clearRect(0, 0, w, h);

    // Sare klinikoa marraztu (Grid)
    ctx.lineWidth = 1;
    ctx.strokeStyle = "rgba(2, 132, 199, 0.12)";
    const gridSize = 25;

    for (let x = 0; x < w; x += gridSize) {
      ctx.beginPath();
      ctx.moveTo(x, 0);
      ctx.lineTo(x, h);
      ctx.stroke();
    }
    for (let y = 0; y < h; y += gridSize) {
      ctx.beginPath();
      ctx.moveTo(0, y);
      ctx.lineTo(w, y);
      ctx.stroke();
    }

    // EKG Uhin sintetikoa
    ctx.lineWidth = 2.5;
    ctx.strokeStyle = isArrhythmia ? "#EF4444" : "#00D4B2";
    ctx.shadowBlur = 10;
    ctx.shadowColor = isArrhythmia ? "rgba(239, 68, 68, 0.5)" : "rgba(0, 212, 178, 0.5)";

    ctx.beginPath();

    const speed = (currentBpm / 60) * 2.8;
    offset += speed;

    const cycleLength = 220;

    for (let x = 0; x < w; x++) {
      const pos = (x + offset) % cycleLength;
      let y = midY;

      // P wave
      if (pos > 25 && pos < 55) {
        y -= Math.sin(((pos - 25) / 30) * Math.PI) * 12;
      }
      // PR segment
      else if (pos >= 55 && pos < 75) {
        y = midY;
      }
      // QRS Complex
      else if (pos >= 75 && pos < 80) {
        y += ((pos - 75) / 5) * 8; // Q dip
      } else if (pos >= 80 && pos < 88) {
        // R spike (spike irregular if arrhythmia)
        const rHeight = isArrhythmia ? 38 + (Math.sin(x * 0.1) * 16) : 52;
        y -= ((pos - 80) / 8) * rHeight;
      } else if (pos >= 88 && pos < 96) {
        y += ((pos - 88) / 8) * 65; // S drop
      } else if (pos >= 96 && pos < 105) {
        y += ((105 - pos) / 9) * 14; // return to baseline
      }
      // ST segment & T wave
      else if (pos > 125 && pos < 175) {
        y -= Math.sin(((pos - 125) / 50) * Math.PI) * 18;
      }

      // Zarata biomediko errealista txertatu (MDR zarata-iragazketa simulatua)
      if (isArrhythmia) {
        y += (Math.random() - 0.5) * 4;
      } else {
        y += (Math.random() - 0.5) * 1.2;
      }

      if (x === 0) {
        ctx.moveTo(x, y);
      } else {
        ctx.lineTo(x, y);
      }
    }

    ctx.stroke();
    ctx.shadowBlur = 0; // reset shadow

    ecgAnimationId = requestAnimationFrame(renderEcg);
  }

  renderEcg();
}

/* ==========================================================================
   3. Ospitaleen ROI eta Errentagarritasun Kalkulagailua
   ========================================================================== */
function initRoiCalculator() {
  const patientInput = document.getElementById("roiPatients");
  const daysSavedInput = document.getElementById("roiDaysSaved");
  const bedCostInput = document.getElementById("roiBedCost");

  const outDaysSaved = document.getElementById("outTotalDaysSaved");
  const outEurosSaved = document.getElementById("outTotalSavings");
  const outReadmissionCut = document.getElementById("outReadmissionsPrevented");

  function calculateRoi() {
    if (!patientInput || !daysSavedInput || !bedCostInput) return;

    const patients = parseInt(patientInput.value, 10) || 0;
    const daysSaved = parseFloat(daysSavedInput.value) || 0;
    const bedCost = parseFloat(bedCostInput.value) || 0;

    // Kalkulu formulak
    const totalDays = Math.round(patients * daysSaved);
    const totalSavings = Math.round(totalDays * bedCost);
    const readmissionsPrevented = Math.round(patients * 0.35); // %35 murrizketa

    if (outDaysSaved) outDaysSaved.textContent = totalDays.toLocaleString("eu-ES");
    if (outEurosSaved) outEurosSaved.textContent = `${totalSavings.toLocaleString("eu-ES")} €`;
    if (outReadmissionCut) outReadmissionCut.textContent = `${readmissionsPrevented} Paziente`;
  }

  [patientInput, daysSavedInput, bedCostInput].forEach((input) => {
    if (input) {
      input.addEventListener("input", calculateRoi);
    }
  });

  calculateRoi(); // Hasierako kalkulua exekutatu
}

/* ==========================================================================
   4. BLE Sentsoreen Parekatze Simulagailua (Zero-Touch Pairing)
   ========================================================================== */
function initBlePairingSimulator() {
  const btnScan = document.getElementById("btnBleScan");
  const bleStatusPill = document.getElementById("bleStatusPill");
  const bleDeviceList = document.getElementById("bleDeviceList");

  if (!btnScan || !bleStatusPill || !bleDeviceList) return;

  btnScan.addEventListener("click", () => {
    btnScan.disabled = true;
    btnScan.innerHTML = `
      <svg class="svg-icon sm" viewBox="0 0 24 24"><path d="M21 12a9 9 0 1 1-6.219-8.56"></path></svg>
      Sentsoreak Bilatzen...
    `;
    btnScan.style.opacity = "0.7";

    bleDeviceList.innerHTML = `
      <div style="padding: 1rem; color: var(--text-muted); font-size: 0.88rem; font-style: italic;">
        Inguruko Bluetooth Low Energy (BLE) seinale medikoak eskaneatzen...
      </div>
    `;

    setTimeout(() => {
      bleDeviceList.innerHTML = `
        <div style="background: var(--secondary-bg); border: 1px solid var(--accent-cyan); border-radius: 10px; padding: 1rem; display: flex; justify-content: space-between; align-items: center;">
          <div>
            <strong style="color: var(--text-primary); font-size: 0.95rem;">KuraLink BioPatch #702</strong>
            <p style="color: var(--accent-cyan); font-size: 0.8rem; margin-top: 2px;">MAC: C4:B8:22:91:AA • Seinalea: -48 dBm (Bikaina)</p>
          </div>
          <button id="btnConnectPatch" style="background: var(--tech-blue); border: none; color: white; padding: 0.4rem 0.9rem; border-radius: 6px; font-size: 0.82rem; font-weight: 700; cursor: pointer;">
            Konektatu
          </button>
        </div>
      `;

      const btnConnectPatch = document.getElementById("btnConnectPatch");
      if (btnConnectPatch) {
        btnConnectPatch.addEventListener("click", () => {
          btnConnectPatch.textContent = "Konektatuta!";
          btnConnectPatch.style.background = "var(--accent-green)";

          bleStatusPill.innerHTML = `
            <span class="live-indicator"></span>
            BioPatch #702 Konektatuta (BLE)
          `;
          bleStatusPill.style.color = "var(--accent-green)";
          bleStatusPill.style.borderColor = "var(--accent-green)";

          btnScan.disabled = false;
          btnScan.innerHTML = `
            <svg class="svg-icon sm" viewBox="0 0 24 24"><path d="M23 4v6h-6"></path><path d="M20.49 15a9 9 0 1 1-2.12-9.36L23 10"></path></svg>
            Berriro Eskaneatu
          `;
          btnScan.style.opacity = "1";
        });
      }
    }, 1200);
  });
}
