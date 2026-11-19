# -*- coding: utf-8 -*-
import subprocess
import os
import shutil

current_dir = os.path.abspath(os.path.dirname(__file__))

svg_abs = os.path.abspath('02_marka_eta_identitate_bisuala/kuralink_logo_print.svg').replace('\\', '/')
icon_abs = os.path.abspath('02_marka_eta_identitate_bisuala/kuralink_icon.svg').replace('\\', '/')

html_logo = f"""<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style>
  * {{ margin: 0; padding: 0; box-sizing: border-box; }}
  body {{ background: #ffffff; width: 900px; height: 260px; overflow: hidden; display: flex; align-items: center; justify-content: center; }}
  img {{ width: 900px; height: 260px; }}
</style>
</head>
<body>
<img src="file:///{svg_abs}">
</body>
</html>"""

temp_logo_path = os.path.join(current_dir, "temp_logo.html")
with open(temp_logo_path, "w", encoding="utf-8") as f:
    f.write(html_logo)

html_icon = f"""<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style>
  * {{ margin: 0; padding: 0; box-sizing: border-box; }}
  body {{ background: #ffffff; width: 400px; height: 400px; overflow: hidden; display: flex; align-items: center; justify-content: center; }}
  img {{ width: 360px; height: 360px; }}
</style>
</head>
<body>
<img src="file:///{icon_abs}">
</body>
</html>"""

temp_icon_path = os.path.join(current_dir, "temp_icon.html")
with open(temp_icon_path, "w", encoding="utf-8") as f:
    f.write(html_icon)

chrome_path = r"C:\Program Files\Google\Chrome\Application\chrome.exe"
if not os.path.exists(chrome_path):
    chrome_path = r"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"

out_logo = os.path.join(current_dir, "02_marka_eta_identitate_bisuala", "kuralink_logo.png")
out_icon = os.path.join(current_dir, "02_marka_eta_identitate_bisuala", "kuralink_icon.png")

# Use chrome headless
url_logo = "file:///" + temp_logo_path.replace('\\', '/')
url_icon = "file:///" + temp_icon_path.replace('\\', '/')

cmd_logo = [chrome_path, "--headless=new", "--disable-gpu", "--window-size=900,260", f"--screenshot={out_logo}", url_logo]
res1 = subprocess.run(cmd_logo, capture_output=True, text=True)
print("Logo res:", res1.returncode, "exists:", os.path.exists(out_logo))

cmd_icon = [chrome_path, "--headless=new", "--disable-gpu", "--window-size=400,400", f"--screenshot={out_icon}", url_icon]
res2 = subprocess.run(cmd_icon, capture_output=True, text=True)
print("Icon res:", res2.returncode, "exists:", os.path.exists(out_icon))

if os.path.exists(out_logo):
    print("Logo size:", os.path.getsize(out_logo))
    shutil.copy2(out_logo, "03_irudiak_eta_mockupak/kuralink_logo.png")
    shutil.copy2(out_logo, "kuralink_web_app/assets/images/kuralink_logo.png")

if os.path.exists(out_icon):
    print("Icon size:", os.path.getsize(out_icon))
    shutil.copy2(out_icon, "03_irudiak_eta_mockupak/kuralink_icon.png")
    shutil.copy2(out_icon, "kuralink_web_app/assets/images/kuralink_icon.png")

if os.path.exists(temp_logo_path):
    os.remove(temp_logo_path)
if os.path.exists(temp_icon_path):
    os.remove(temp_icon_path)

print("Done converting logos!")
