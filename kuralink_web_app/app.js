/**
 * KuraLink Health Technologies — Frontend Interaktiboaren Kontrolatzailea
 * EIP II Erronka | Goierri Eskola | DAM & Biomedikuntza
 * Murrizketa zorrotza: EZ ERABILI EMOTIKONOAK (ikono bektorialak soilik).
 */

document.addEventListener("DOMContentLoaded", () => {
  initNavigation();
  initBurgerMenu();
  initThemeAndLanguage();
  initPatientGate();
  initEcgSimulator();
  initRoiCalculator();
  initBlePairingSimulator();
});

/* ==========================================================================
   Single-Page Landing & Patient Portal View Switcher
   ========================================================================== */
function showLandingView(scrollTarget) {
  const landingView = document.getElementById("landingView");
  const portalView = document.getElementById("patientPortalView");

  if (portalView) portalView.style.display = "none";
  if (landingView) landingView.style.display = "block";

  // Deactivate patient nav buttons
  document.querySelectorAll("#navBtnPatientPortal, #drawerBtnPatientPortal, #mobileBtnPatientPortal").forEach((btn) => {
    btn.classList.remove("active");
  });

  if (scrollTarget) {
    setTimeout(() => {
      const targetEl = document.querySelector(scrollTarget);
      if (targetEl) {
        targetEl.scrollIntoView({ behavior: "smooth", block: "start" });
      }
    }, 60);
  }
}

function showPatientPortal() {
  const landingView = document.getElementById("landingView");
  const portalView = document.getElementById("patientPortalView");

  if (landingView) landingView.style.display = "none";
  if (portalView) {
    portalView.style.display = "block";
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  // Set patient nav buttons active, deactivate landing nav buttons
  document.querySelectorAll(".nav-tab-btn, .mobile-nav-btn, .drawer-link-item, .dropdown-menu-item").forEach((btn) => {
    btn.classList.remove("active");
  });
  document.querySelectorAll("#navBtnPatientPortal, #drawerBtnPatientPortal, #mobileBtnPatientPortal").forEach((btn) => {
    btn.classList.add("active");
  });

  const dropdownWrapper = document.getElementById("projectDropdownWrapper");
  if (dropdownWrapper) dropdownWrapper.classList.remove("open");
  const btnDropdown = document.getElementById("btnDropdownProject");
  if (btnDropdown) {
    btnDropdown.setAttribute("aria-expanded", "false");
    btnDropdown.classList.remove("active");
  }

  // Trigger resize so EKG canvas renders properly
  setTimeout(() => {
    window.dispatchEvent(new Event("resize"));
  }, 100);
}

const projectTargets = [
  "#sec-txostena",
  "#sec-arazoa",
  "#sec-merkatua",
  "#sec-misioa",
  "#sec-target",
  "#sec-balioa",
  "#sec-kap-marka",
  "#sec-marka"
];

function updateActiveNav(targetSelector) {
  const isProject = projectTargets.includes(targetSelector);
  const btnDropdown = document.getElementById("btnDropdownProject");
  if (btnDropdown) {
    if (isProject) {
      btnDropdown.classList.add("active");
    } else {
      btnDropdown.classList.remove("active");
    }
  }

  // Update dropdown items active status
  document.querySelectorAll(".dropdown-menu-item[data-scroll]").forEach((item) => {
    if (item.getAttribute("data-scroll") === targetSelector) {
      item.classList.add("active");
    } else {
      item.classList.remove("active");
    }
  });

  // Update tabs, drawer items and mobile navigation buttons
  document.querySelectorAll(".nav-tab-btn[data-scroll], .mobile-nav-btn[data-scroll], .drawer-link-item[data-scroll]").forEach((btn) => {
    const scrollTarget = btn.getAttribute("data-scroll");
    if (scrollTarget === targetSelector || (isProject && scrollTarget === "#sec-txostena")) {
      btn.classList.add("active");
    } else {
      btn.classList.remove("active");
    }
  });
}

function initNavigation() {
  // Project Dropdown Navigation
  const dropdownWrapper = document.getElementById("projectDropdownWrapper");
  const btnDropdown = document.getElementById("btnDropdownProject");
  const dropdownMenu = document.getElementById("projectDropdownMenu");

  function openProjectDropdown() {
    if (dropdownWrapper) dropdownWrapper.classList.add("open");
    if (btnDropdown) btnDropdown.setAttribute("aria-expanded", "true");
  }

  function closeProjectDropdown() {
    if (dropdownWrapper) dropdownWrapper.classList.remove("open");
    if (btnDropdown) btnDropdown.setAttribute("aria-expanded", "false");
  }

  function toggleProjectDropdown(e) {
    if (e) {
      e.preventDefault();
      e.stopPropagation();
    }
    const isOpen = dropdownWrapper && dropdownWrapper.classList.contains("open");
    if (isOpen) {
      closeProjectDropdown();
    } else {
      openProjectDropdown();
    }
  }

  if (btnDropdown) {
    btnDropdown.addEventListener("click", toggleProjectDropdown);
  }

  // Click outside closes dropdown
  document.addEventListener("click", (e) => {
    if (dropdownWrapper && !dropdownWrapper.contains(e.target)) {
      closeProjectDropdown();
    }
  });

  // Escape key closes dropdown
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
      closeProjectDropdown();
    }
  });

  // Dropdown items click handling
  if (dropdownMenu) {
    dropdownMenu.querySelectorAll("[data-scroll]").forEach((item) => {
      item.addEventListener("click", (e) => {
        e.preventDefault();
        e.stopPropagation();
        const target = item.getAttribute("data-scroll");
        closeProjectDropdown();
        if (target) {
          showLandingView(target);
          updateActiveNav(target);
        }
      });
    });
  }

  // Return to landing button from patient portal
  const btnReturn = document.getElementById("btnReturnToLanding");
  if (btnReturn) {
    btnReturn.addEventListener("click", () => {
      showLandingView("#sec-txostena");
      updateActiveNav("#sec-txostena");
    });
  }

  // All buttons with data-scroll (header, mobile bar, drawer)
  const scrollButtons = document.querySelectorAll(".nav-tab-btn[data-scroll], .mobile-nav-btn[data-scroll]");
  scrollButtons.forEach((btn) => {
    btn.addEventListener("click", () => {
      const target = btn.getAttribute("data-scroll");
      if (target) {
        showLandingView(target);
        updateActiveNav(target);
      }
    });
  });

  // Patient Portal CTA buttons
  const patientButtons = document.querySelectorAll("#navBtnPatientPortal, #mobileBtnPatientPortal");
  patientButtons.forEach((btn) => {
    btn.addEventListener("click", () => {
      showPatientPortal();
    });
  });

  // Top header brand icon clicks scroll to hero
  const brandIcon = document.querySelector(".brand-icon-link");
  if (brandIcon) {
    brandIcon.addEventListener("click", (e) => {
      e.preventDefault();
      showLandingView("#sec-hero");
      updateActiveNav("#sec-txostena");
    });
  }

  initScrollSpy();
}

function initScrollSpy() {
  const sections = document.querySelectorAll(
    ".hero-section, .landing-section, #sec-arazoa, #sec-misioa, #sec-target, #sec-balioa, #sec-kap-marka"
  );
  if (!("IntersectionObserver" in window)) return;

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        const id = entry.target.id;
        const selector = `#${id}`;
        updateActiveNav(selector);
      }
    });
  }, {
    rootMargin: "-20% 0px -65% 0px",
    threshold: 0
  });

  sections.forEach((sec) => observer.observe(sec));
}

/* ==========================================================================
   Burger Drawer Menu Kontrola (Figma Overlay Pattern)
   ========================================================================== */
function initBurgerMenu() {
  const burgerBtn = document.getElementById("burgerBtn");
  const drawer = document.getElementById("burgerDrawer");
  const overlay = document.getElementById("drawerOverlay");
  const closeBtn = document.getElementById("drawerCloseBtn");
  const drawerLinks = document.querySelectorAll(".drawer-link-item");

  function openDrawer() {
    if (drawer && overlay) {
      drawer.classList.add("open");
      overlay.classList.add("open");
      document.body.style.overflow = "hidden";
    }
  }

  function closeDrawer() {
    if (drawer && overlay) {
      drawer.classList.remove("open");
      overlay.classList.remove("open");
      document.body.style.overflow = "";
    }
  }

  if (burgerBtn) burgerBtn.addEventListener("click", openDrawer);
  if (closeBtn) closeBtn.addEventListener("click", closeDrawer);
  if (overlay) overlay.addEventListener("click", closeDrawer);

  drawerLinks.forEach((link) => {
    link.addEventListener("click", () => {
      const isPatient = link.getAttribute("data-view") === "patient" || link.id === "drawerBtnPatientPortal";
      const targetScroll = link.getAttribute("data-scroll");

      closeDrawer();

      if (isPatient) {
        showPatientPortal();
      } else if (targetScroll) {
        showLandingView(targetScroll);
        updateActiveNav(targetScroll);
      }
    });
  });
}

/* ==========================================================================
   Tema eta Hizkuntza Kontrolatzailea (Modu Argia / Iluna & Euskara/ES/EN)
   ========================================================================== */
/* ==========================================================================
   Tema eta Hizkuntza Kontrolatzailea (Modu Argia / Iluna & Euskara/ES/EN)
   ========================================================================== */
const I18N_WEB = {
  eu: {
    navProject: "Negozio Proiektua",
    navRoi: "ROI Kalkulagailua",
    navPatient: "Paziente Ataria (Login)",
    navPdf: "Txostena (PDF)",
    heroTag: "SAAS MEDIKOA & IOMT TELE-MONITORIZAZIO KLINIKOA",
    heroTitle: "Osasun tele–zaintza adimentsua gailu medikoen eta pazienteen artean.",
    heroDesc: "KuraLink Health Technologies gailu mediko eramangarriak eta osasun-sistemak bateratzen dituen plataforma digital aurreratua da. Sentsore biomedikoak zero-touch bidez sinkronizatzen ditu, seinale fisiologikoak adimenez iragazten ditu eta ospitaleko historian (Osabide Global / HL7 FHIR) zuzenean txertatzen da, ospitaleratzeak %35 murriztuz.",
    stat1Label: "Ospitaleratze Murrizketa",
    stat2Label: "Ariketen Atxikimendua",
    stat3Label: "CE Homologazio Klinikoa",
    pdfFilename: "KuraLink_Negozio_Txostena.pdf",
    pdfUrl: "assets/docs/KuraLink_Negozio_Txostena.pdf"
  },
  es: {
    navProject: "Plan de Negocio",
    navRoi: "Calculadora ROI",
    navPatient: "Portal Paciente (Login)",
    navPdf: "Informe (PDF)",
    heroTag: "SAAS MÉDICO & TELE-MONITORIZACIÓN CLÍNICA IOMT",
    heroTitle: "Tele-cuidado inteligente de salud entre dispositivos médicos y pacientes.",
    heroDesc: "KuraLink Health Technologies es una avanzada plataforma digital que unifica dispositivos médicos vestibles y sistemas hospitalarios. Sincroniza sensores biomédicos sin fricción (zero-touch), filtra señales fisiológicas con IA y se integra directamente en el historial clínico (HL7 FHIR), reduciendo rehospitalizaciones un 35%.",
    stat1Label: "Reducción Rehospitalizaciones",
    stat2Label: "Adherencia a Ejercicios",
    stat3Label: "Homologación Clínica CE",
    pdfFilename: "KuraLink_Plan_Negocio_ES.pdf",
    pdfUrl: "assets/docs/KuraLink_Plan_Negocio_ES.pdf"
  },
  en: {
    navProject: "Business Plan",
    navRoi: "ROI Calculator",
    navPatient: "Patient Portal (Login)",
    navPdf: "Report (PDF)",
    heroTag: "MEDICAL SAAS & IOMT CLINICAL TELE-MONITORING",
    heroTitle: "Intelligent healthcare tele-monitoring bridging medical devices and patients.",
    heroDesc: "KuraLink Health Technologies is an advanced digital platform unifying wearable medical sensors with hospital EHR systems. It synchronizes biomedical sensors with zero-touch UX, filters physiological noise with edge algorithms and links into HL7 FHIR clinical records, reducing readmissions by 35%.",
    stat1Label: "Readmission Reduction",
    stat2Label: "Exercise Adherence",
    stat3Label: "CE MDR IIa Certification",
    pdfFilename: "KuraLink_Business_Report_EN.pdf",
    pdfUrl: "assets/docs/KuraLink_Business_Report_EN.pdf"
  }
};

function initThemeAndLanguage() {
  const headerThemeBtn = document.getElementById("headerThemeToggleBtn");
  const drawerThemeBtn = document.getElementById("drawerThemeToggleBtn");
  const descEl = document.getElementById("themeStatusDesc");
  const btnTextEl = document.getElementById("themeBtnText");

  const headerSettingsBtn = document.getElementById("headerSettingsBtn");
  const modalOverlay = document.getElementById("settingsModalOverlay");
  const closeSettingsBtn = document.getElementById("closeSettingsModalBtn");
  const modalThemeDarkBtn = document.getElementById("modalThemeDarkBtn");
  const modalThemeLightBtn = document.getElementById("modalThemeLightBtn");

  const langBtns = document.querySelectorAll(".lang-btn");
  const langPills = document.querySelectorAll(".lang-pill");

  // Load saved theme or default to dark
  const savedTheme = localStorage.getItem("kuralink-theme") || "dark";
  applyTheme(savedTheme);

  // Load saved language or default to eu
  const savedLang = localStorage.getItem("kuralink-lang") || "eu";
  applyLanguage(savedLang);

  function applyTheme(theme) {
    if (theme === "light") {
      document.documentElement.setAttribute("data-theme", "light");
      if (descEl) descEl.textContent = "Modu Argia (Egun/Kliniko)";
      if (btnTextEl) btnTextEl.textContent = "Iluna";
      localStorage.setItem("kuralink-theme", "light");
    } else {
      document.documentElement.removeAttribute("data-theme");
      if (descEl) descEl.textContent = "Modu Iluna (Gau/Kliniko)";
      if (btnTextEl) btnTextEl.textContent = "Argia";
      localStorage.setItem("kuralink-theme", "dark");
    }
  }

  function toggleTheme() {
    const isLight = document.documentElement.getAttribute("data-theme") === "light";
    applyTheme(isLight ? "dark" : "light");
  }

  function applyLanguage(lang) {
    const dict = I18N_WEB[lang] || I18N_WEB.eu;
    localStorage.setItem("kuralink-lang", lang);

    // Update active badges in modal and drawer
    langBtns.forEach((btn) => {
      btn.classList.toggle("active", btn.getAttribute("data-lang") === lang);
    });
    langPills.forEach((pill) => {
      pill.classList.toggle("active", pill.getAttribute("data-lang") === lang);
    });

    // Update nav links
    const btnDropdown = document.getElementById("btnDropdownProject");
    if (btnDropdown) {
      const textNode = Array.from(btnDropdown.childNodes).find((n) => n.nodeType === Node.TEXT_NODE && n.nodeValue.trim());
      if (textNode) textNode.nodeValue = ` ${dict.navProject} `;
    }

    const patientBtns = document.querySelectorAll("#navBtnPatientPortal, #drawerBtnPatientPortal");
    patientBtns.forEach((btn) => {
      const strong = btn.querySelector("strong");
      if (strong) strong.textContent = dict.navPatient;
      else {
        const textNode = Array.from(btn.childNodes).find((n) => n.nodeType === Node.TEXT_NODE && n.nodeValue.trim());
        if (textNode) textNode.nodeValue = ` ${dict.navPatient} `;
      }
    });

    const pdfLinks = document.querySelectorAll(".btn-download-pdf, .drawer-cta-btn");
    pdfLinks.forEach((link) => {
      link.setAttribute("href", dict.pdfUrl);
      link.setAttribute("download", dict.pdfFilename);
    });

    // Update Hero text
    const heroTitle = document.querySelector(".hero-content h1");
    if (heroTitle) heroTitle.textContent = dict.heroTitle;
    const heroDesc = document.querySelector(".hero-content p");
    if (heroDesc) heroDesc.textContent = dict.heroDesc;
    const heroTag = document.querySelector(".hero-content .section-tag");
    if (heroTag) {
      const tagText = Array.from(heroTag.childNodes).find((n) => n.nodeType === Node.TEXT_NODE && n.nodeValue.trim());
      if (tagText) tagText.nodeValue = ` ${dict.heroTag}`;
    }
  }

  if (headerThemeBtn) headerThemeBtn.addEventListener("click", toggleTheme);
  if (drawerThemeBtn) drawerThemeBtn.addEventListener("click", toggleTheme);

  // Settings Modal Controls
  if (headerSettingsBtn && modalOverlay) {
    headerSettingsBtn.addEventListener("click", () => {
      modalOverlay.style.display = "flex";
    });
  }

  if (closeSettingsBtn && modalOverlay) {
    closeSettingsBtn.addEventListener("click", () => {
      modalOverlay.style.display = "none";
    });
  }

  if (modalOverlay) {
    modalOverlay.addEventListener("click", (e) => {
      if (e.target === modalOverlay) modalOverlay.style.display = "none";
    });
  }

  if (modalThemeDarkBtn) {
    modalThemeDarkBtn.addEventListener("click", () => applyTheme("dark"));
  }
  if (modalThemeLightBtn) {
    modalThemeLightBtn.addEventListener("click", () => applyTheme("light"));
  }

  // Language selectors
  langBtns.forEach((btn) => {
    btn.addEventListener("click", () => {
      const lang = btn.getAttribute("data-lang");
      applyLanguage(lang);
    });
  });

  langPills.forEach((pill) => {
    pill.addEventListener("click", () => {
      const lang = pill.getAttribute("data-lang");
      applyLanguage(lang);
    });
  });
}

/* ==========================================================================
   Paziente Ataria: Login Segurua (Gated Medical Access)
   ========================================================================== */
let isPatientLoggedIn = false;

function initPatientGate() {
  const gate = document.getElementById("patientLockedGate");
  const unlocked = document.getElementById("patientUnlockedContent");
  const btnSubmit = document.getElementById("btnLoginSubmit");
  const btnDemo = document.getElementById("btnDemoLogin");
  const btnLogout = document.getElementById("btnLogoutBtn");

  function doLogin() {
    isPatientLoggedIn = true;
    if (gate) gate.style.display = "none";
    if (unlocked) unlocked.style.display = "block";
    // Trigger canvas resize so EKG renders properly
    window.dispatchEvent(new Event("resize"));
  }

  function doLogout() {
    isPatientLoggedIn = false;
    if (gate) gate.style.display = "flex";
    if (unlocked) unlocked.style.display = "none";
  }

  if (btnSubmit) btnSubmit.addEventListener("click", doLogin);
  if (btnDemo) btnDemo.addEventListener("click", doLogin);
  if (btnLogout) btnLogout.addEventListener("click", doLogout);
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
