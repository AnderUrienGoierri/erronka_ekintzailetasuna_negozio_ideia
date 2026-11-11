@echo off
chcp 65001 > nul
title KuraLink EIP2 - Docker Desktop Erregistroa
echo ========================================================================
echo   KuraLink Health (EIP2) Edukiontzia Docker Desktop-era Gehitzen
echo ========================================================================
echo.
echo [1/3] Azken fitxategiak (kuralink_web_app) Docker stack-era sinkronizatzen...
robocopy "%~dp0kuralink_web_app" "C:\Dev\01_Stack\Docker_Stacks\goierri_eskola_lab\services\eip2-webapp" /E /PURGE > nul

cd /d "C:\Dev\01_Stack\Docker_Stacks\goierri_eskola_lab"

echo [2/3] Docker Desktop motorrarekin konektatzen...
echo [3/3] Edukiontzia sortzen/eguneratzen goierri_eskola_lab taldean...
echo.
docker compose up -d eip2

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================================================
    echo   [ARRAKASTA] 'eip2_ebaluaketa_webapp' Docker Desktop-en agertzen da!
    echo ========================================================================
    echo   - Taldea: goierri_eskola_lab
    echo   - Edukiontzia: eip2_ebaluaketa_webapp
    echo   - Portua: 3004:80
    echo   - Egoera: ITZALITA (zirkulu grisarekin)
    echo.
    echo   Nahi duzunean Docker Desktop-eko Play botoia sakatu eta
    echo   nabigatzailean http://localhost:3004 helbidean ireki ahal izango duzu.
) else (
    echo.
    echo ========================================================================
    echo   [OHARRA] Docker Desktop-eko motorra (daemon) ez dago piztuta.
    echo ========================================================================
    echo   1. Ireki Docker Desktop aplikazioa eta itxaron beheko ezkerreko
    echo      ikonoa berdea egon arte (Engine running).
    echo   2. Sakatu edozein tekla script hau berriro exekutatzeko.
)
echo.
pause
