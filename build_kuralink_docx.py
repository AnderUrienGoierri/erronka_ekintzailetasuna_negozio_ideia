# -*- coding: utf-8 -*-
"""
Script to generate the complete KuraLink Business Idea Development document in .docx format.
Adheres strictly to:
- teoria_pdf/negozio ideiaren garapena.pdf requirements
- Black text (#000000) throughout the document
- Natural, professional human formatting (no AI markers)
- Clickable Table of Contents linking to internal bookmarks
- Cover page with project image, author name, module, date, school
- Realistic business scenario images included with captions
"""

import os
import shutil
import docx
from docx.shared import Inches, Pt, RGBColor, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_ALIGN_VERTICAL
from docx.oxml import OxmlElement, parse_xml
from docx.oxml.ns import qn, nsdecls

BLACK = RGBColor(0, 0, 0)

def set_cell_background(cell, hex_color):
    shd = parse_xml(f'<w:shd {nsdecls("w")} w:fill="{hex_color}"/>')
    cell._tc.get_or_add_tcPr().append(shd)

def set_cell_margins(cell, top=120, bottom=120, left=150, right=150):
    tcPr = cell._tc.get_or_add_tcPr()
    tcMar = OxmlElement('w:tcMar')
    for m, val in [('top', top), ('bottom', bottom), ('left', left), ('right', right)]:
        node = OxmlElement(f'w:{m}')
        node.set(qn('w:w'), str(val))
        node.set(qn('w:type'), 'dxa')
        tcMar.append(node)
    tcPr.append(tcMar)

def set_table_borders(table, color="D0D5DD", sz="4", val="single"):
    tblPr = table._tbl.tblPr
    borders = parse_xml(
        f'<w:tblBorders {nsdecls("w")}>\n'
        f'  <w:top w:val="{val}" w:sz="{sz}" w:space="0" w:color="{color}"/>\n'
        f'  <w:left w:val="none"/>\n'
        f'  <w:bottom w:val="{val}" w:sz="{sz}" w:space="0" w:color="{color}"/>\n'
        f'  <w:right w:val="none"/>\n'
        f'  <w:insideH w:val="{val}" w:sz="{sz}" w:space="0" w:color="{color}"/>\n'
        f'  <w:insideV w:val="none"/>\n'
        f'</w:tblBorders>'
    )
    tblPr.append(borders)

def add_bookmark(paragraph, bookmark_id, bookmark_name):
    start = OxmlElement('w:bookmarkStart')
    start.set(qn('w:id'), str(bookmark_id))
    start.set(qn('w:name'), bookmark_name)
    paragraph._p.insert(0, start)
    end = OxmlElement('w:bookmarkEnd')
    end.set(qn('w:id'), str(bookmark_id))
    paragraph._p.append(end)

def add_toc_hyperlink(paragraph, anchor_name, text, is_bold=False):
    hyperlink = OxmlElement('w:hyperlink')
    hyperlink.set(qn('w:anchor'), anchor_name)
    r = OxmlElement('w:r')
    rPr = OxmlElement('w:rPr')
    
    # Pure black color
    c = OxmlElement('w:color')
    c.set(qn('w:val'), '000000')
    rPr.append(c)
    
    # Font
    rFonts = OxmlElement('w:rFonts')
    rFonts.set(qn('w:ascii'), 'Arial')
    rFonts.set(qn('w:hAnsi'), 'Arial')
    rPr.append(rFonts)
    
    sz = OxmlElement('w:sz')
    sz.set(qn('w:val'), '21' if not is_bold else '22')  # 10.5pt or 11pt
    rPr.append(sz)
    
    if is_bold:
        b = OxmlElement('w:b')
        rPr.append(b)
        
    u = OxmlElement('w:u')
    u.set(qn('w:val'), 'none')
    rPr.append(u)
    
    r.append(rPr)
    t = OxmlElement('w:t')
    t.text = text
    r.append(t)
    hyperlink.append(r)
    paragraph._p.append(hyperlink)

def add_page_number_to_run(run):
    fldChar1 = OxmlElement('w:fldChar')
    fldChar1.set(qn('w:fldCharType'), 'begin')
    instrText = OxmlElement('w:instrText')
    instrText.set(qn('xml:space'), 'preserve')
    instrText.text = "PAGE"
    fldChar2 = OxmlElement('w:fldChar')
    fldChar2.set(qn('w:fldCharType'), 'separate')
    fldChar3 = OxmlElement('w:fldChar')
    fldChar3.set(qn('w:fldCharType'), 'end')
    run._r.append(fldChar1)
    run._r.append(instrText)
    run._r.append(fldChar2)
    run._r.append(fldChar3)

def create_document():
    doc = docx.Document()
    
    # Page setup: Standard A4 margins 2.5 cm
    for s in doc.sections:
        s.top_margin = Cm(2.5)
        s.bottom_margin = Cm(2.5)
        s.left_margin = Cm(2.5)
        s.right_margin = Cm(2.5)
        s.page_width = Cm(21.0)
        s.page_height = Cm(29.7)
    
    # Configure default style (Normal)
    normal_style = doc.styles['Normal']
    normal_style.font.name = 'Arial'
    normal_style.font.size = Pt(11)
    normal_style.font.color.rgb = BLACK
    normal_style.paragraph_format.line_spacing = 1.15
    normal_style.paragraph_format.space_after = Pt(6)
    normal_style.paragraph_format.space_before = Pt(0)
    
    # Configure Header & Footer
    section = doc.sections[0]
    header = section.header
    hp = header.paragraphs[0]
    hp.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    hrun = hp.add_run("KuraLink Health Technologies • Negozio Ideiaren Garapena (EIP II)")
    hrun.font.name = 'Arial'
    hrun.font.size = Pt(8.5)
    hrun.font.color.rgb = RGBColor(60, 60, 60)
    
    footer = section.footer
    fp = footer.paragraphs[0]
    fp.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    frun_left = fp.add_run("Goierri Eskola (DAM 2) | Ander Urien                     Orrialdea ")
    frun_left.font.name = 'Arial'
    frun_left.font.size = Pt(9)
    frun_left.font.color.rgb = RGBColor(60, 60, 60)
    
    frun_num = fp.add_run()
    frun_num.font.name = 'Arial'
    frun_num.font.size = Pt(9)
    frun_num.font.color.rgb = RGBColor(0, 0, 0)
    frun_num.bold = True
    add_page_number_to_run(frun_num)

    # -------------------------------------------------------------
    # 1. PORTADA (COVER PAGE)
    # -------------------------------------------------------------
    
    # School header line
    p_inst = doc.add_paragraph()
    p_inst.paragraph_format.space_before = Pt(0)
    p_inst.paragraph_format.space_after = Pt(2)
    r_inst = p_inst.add_run("GOIERRI ESKOLA • GOI MAILAKO LANBIDE HEZIKETA")
    r_inst.font.name = 'Arial'
    r_inst.font.size = Pt(10)
    r_inst.bold = True
    r_inst.font.color.rgb = BLACK
    
    p_subinst = doc.add_paragraph()
    p_subinst.paragraph_format.space_after = Pt(20)
    r_subinst = p_subinst.add_run("Garapeneko Aplikazioen Multiplataforma (DAM) - 2. Maila")
    r_subinst.font.name = 'Arial'
    r_subinst.font.size = Pt(9.5)
    r_subinst.font.color.rgb = BLACK
    
    # Main Title
    p_title = doc.add_paragraph()
    p_title.paragraph_format.space_before = Pt(10)
    p_title.paragraph_format.space_after = Pt(8)
    r_title = p_title.add_run("NEGOZIO IDEIAREN GARAPENA ETA BIDERAGARRITASUN PLANA")
    r_title.font.name = 'Arial'
    r_title.font.size = Pt(20)
    r_title.bold = True
    r_title.font.color.rgb = BLACK
    
    # Subtitle
    p_sub = doc.add_paragraph()
    p_sub.paragraph_format.space_after = Pt(20)
    r_sub = p_sub.add_run("KuraLink Health Technologies: Gailu biomediko eramangarrien eta ospitale-sistemen arteko tele-monitorizazio adimentsua.")
    r_sub.font.name = 'Arial'
    r_sub.font.size = Pt(12)
    r_sub.font.color.rgb = BLACK
    
    # Business Idea Cover Image
    cover_img_path = "03_irudiak_eta_mockupak/kuralink_enpresa_irudia.jpg"
    if os.path.exists(cover_img_path):
        p_img = doc.add_paragraph()
        p_img.alignment = WD_ALIGN_PARAGRAPH.CENTER
        p_img.paragraph_format.space_before = Pt(6)
        p_img.paragraph_format.space_after = Pt(24)
        run_img = p_img.add_run()
        run_img.add_picture(cover_img_path, width=Cm(14.5))
    
    # Metadata Block (Boxed Table at bottom of cover)
    meta_table = doc.add_table(rows=5, cols=2)
    meta_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    set_table_borders(meta_table, color="000000", sz="6", val="single")
    
    metadata = [
        ("Ikaslea / Egilea:", "Ander Urien (Lantaldea: Ander Urien, Julen Aguado, Iker Araujo)"),
        ("Modulua:", "Enplegagarritasunerako Ibilbide Pertsonala II (EIP II)"),
        ("Zikloa eta Maila:", "Goi Mailako Heziketa Zikloa (DAM) - 2. Maila"),
        ("Ikastetxea:", "Goierri Eskola (Ordizia, Gipuzkoa)"),
        ("Data:", "2026ko Azaroa")
    ]
    
    for idx, (lbl, val) in enumerate(metadata):
        cell_lbl = meta_table.cell(idx, 0)
        cell_val = meta_table.cell(idx, 1)
        cell_lbl.width = Cm(5.0)
        cell_val.width = Cm(11.0)
        set_cell_margins(cell_lbl, 80, 80, 100, 100)
        set_cell_margins(cell_val, 80, 80, 100, 100)
        set_cell_background(cell_lbl, "F8F9FA")
        set_cell_background(cell_val, "FFFFFF")
        
        p0 = cell_lbl.paragraphs[0]
        p0.paragraph_format.space_after = Pt(0)
        r0 = p0.add_run(lbl)
        r0.bold = True
        r0.font.size = Pt(9.5)
        r0.font.color.rgb = BLACK
        
        p1 = cell_val.paragraphs[0]
        p1.paragraph_format.space_after = Pt(0)
        r1 = p1.add_run(val)
        r1.font.size = Pt(9.5)
        r1.font.color.rgb = BLACK

    # Page break after cover
    doc.add_page_break()

    # -------------------------------------------------------------
    # 2. AURKIBIDEA (TABLE OF CONTENTS WITH CLICKABLE HYPERLINKS)
    # -------------------------------------------------------------
    p_toc_head = doc.add_paragraph()
    p_toc_head.paragraph_format.space_before = Pt(10)
    p_toc_head.paragraph_format.space_after = Pt(12)
    r_toc_head = p_toc_head.add_run("AURKIBIDEA")
    r_toc_head.font.name = 'Arial'
    r_toc_head.font.size = Pt(16)
    r_toc_head.bold = True
    r_toc_head.font.color.rgb = BLACK
    
    p_toc_note = doc.add_paragraph()
    p_toc_note.paragraph_format.space_after = Pt(14)
    r_toc_note = p_toc_note.add_run("Oharra: Aurkibideko atal bakoitzean klik eginda, dokumentuko dagokion atalera zuzenean joango zara.")
    r_toc_note.font.size = Pt(9.5)
    r_toc_note.font.italic = True
    r_toc_note.font.color.rgb = RGBColor(60, 60, 60)

    toc_items = [
        ("sec_sarrera", "SARRERA ETA LABURPEN EXEKUTIBOA", True),
        ("sec_1", "1. ARAZOA ETA IRTENBIDEA", True),
        ("sec_1_1", "   1.1. Zer arazo ikusten dituzu zure inguruan konpontzeko?", False),
        ("sec_1_2", "   1.2. Zer trebetasun edo talentu bihur ditzakezu negozio?", False),
        ("sec_1_3", "   1.3. Zer produktu edo zerbitzu mota ez dago, baina ikusi nahiko zenuke?", False),
        ("sec_1_4", "   1.4. KuraLink soluzioaren erantzun integrala eta arkitektura", False),
        ("sec_2", "2. MISIOA, BISIOA ETA BALOREAK", True),
        ("sec_2_1", "   2.1. Zutabe estrategikoak eta enpresaren ADN-a", False),
        ("sec_2_2", "   2.2. Misioa: Zer egingo dugu egunerokotasunean? (Izateko arrazoia)", False),
        ("sec_2_3", "   2.3. Bisioa: Zer izan nahi dugu etorkizunean? (Helmuga estrategikoa)", False),
        ("sec_2_4", "   2.4. Baloreak: Zertan sinesten dugu eta zeintzuk dira gure printzipioak?", False),
        ("sec_3", "3. TARGET (XEDE PUBLIKOA)", True),
        ("sec_3_1", "   3.1. Target-aren definizioa eta B2B2C segmentazioa", False),
        ("sec_3_2", "   3.2. Target-aren bereizketa aldagai nagusien arabera (Demografikoak, Geografikoak, Psikografikoak, Jokabidezkoak)", False),
        ("sec_3_3", "   3.3. Target apropos baten lau ezaugarriak KuraLink-en", False),
        ("sec_3_4", "   3.4. Erabilera-eszenatoki errealak: Ospitaleko arreta eta Etxez etxeko tele-zaintza", False),
        ("sec_4", "4. BALIO PROPOSAMENA ETA LEHIAREN AZTERKETA", True),
        ("sec_4_1", "   4.1. Balio bakarra: Zein balio ematen dio produktuak bezeroari?", False),
        ("sec_4_2", "   4.2. Emaitza zehatzak: Zer lortuko du bezeroak soluzioa erabiltzean?", False),
        ("sec_4_3", "   4.3. Merkatuko alternatiben hobekuntza eta Lehiaren matrize konparatiboa", False),
        ("sec_4_4", "   4.4. Funtsezko ezaugarriak, eragin klinikoa eta araudi-betetzea (CE MDR IIa, ISO 13485)", False),
        ("sec_5", "5. MARKA: KURALINK", True),
        ("sec_5_1", "   5.1. Marka-izen on baten lau ezaugarriak KuraLink-en", False),
        ("sec_5_2", "   5.2. Identitate bisuala, kolore korporatiboak eta diseinu sistema", False),
        ("sec_5_3", "   5.3. Mugikorreko aplikazioa eta sentsore biomedikoen integrazioa", False),
        ("sec_6", "6. BIDERAGARRITASUN PLANA ETA ONDORIOAK", True),
        ("sec_6_1", "   6.1. Bideragarritasun teknikoa, ekonomikoa eta araudizkoa", False),
        ("sec_6_2", "   6.2. Ikaskuntza-prozesua eta etorkizuneko garapena", False)
    ]
    
    for anchor, text, is_bold in toc_items:
        p_item = doc.add_paragraph()
        p_item.paragraph_format.space_before = Pt(2 if not is_bold else 5)
        p_item.paragraph_format.space_after = Pt(2)
        add_toc_hyperlink(p_item, anchor, text, is_bold=is_bold)

    # Page break after TOC
    doc.add_page_break()

    # Helper function to add standard headings with bookmarks
    bookmark_counter = [1]
    
    def add_h1(title_text, anchor_name):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(16)
        p.paragraph_format.space_after = Pt(8)
        p.paragraph_format.keep_with_next = True
        r = p.add_run(title_text)
        r.font.name = 'Arial'
        r.font.size = Pt(15)
        r.bold = True
        r.font.color.rgb = BLACK
        add_bookmark(p, bookmark_counter[0], anchor_name)
        bookmark_counter[0] += 1
        return p

    def add_h2(title_text, anchor_name):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(12)
        p.paragraph_format.space_after = Pt(4)
        p.paragraph_format.keep_with_next = True
        r = p.add_run(title_text)
        r.font.name = 'Arial'
        r.font.size = Pt(12.5)
        r.bold = True
        r.font.color.rgb = BLACK
        add_bookmark(p, bookmark_counter[0], anchor_name)
        bookmark_counter[0] += 1
        return p

    def add_h3(title_text):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(8)
        p.paragraph_format.space_after = Pt(2)
        p.paragraph_format.keep_with_next = True
        r = p.add_run(title_text)
        r.font.name = 'Arial'
        r.font.size = Pt(11)
        r.bold = True
        r.font.color.rgb = BLACK
        return p

    def add_p(text):
        p = doc.add_paragraph()
        p.paragraph_format.space_after = Pt(6)
        p.paragraph_format.line_spacing = 1.15
        r = p.add_run(text)
        r.font.name = 'Arial'
        r.font.size = Pt(11)
        r.font.color.rgb = BLACK
        return p

    def add_bullet(bold_prefix, text):
        p = doc.add_paragraph(style='List Bullet')
        p.paragraph_format.space_after = Pt(4)
        p.paragraph_format.line_spacing = 1.15
        r_bold = p.add_run(bold_prefix)
        r_bold.font.name = 'Arial'
        r_bold.font.size = Pt(11)
        r_bold.bold = True
        r_bold.font.color.rgb = BLACK
        
        r_text = p.add_run(text)
        r_text.font.name = 'Arial'
        r_text.font.size = Pt(11)
        r_text.font.color.rgb = BLACK
        return p

    def add_figure(img_path, caption_text, width_cm=13.5):
        if os.path.exists(img_path):
            p_img = doc.add_paragraph()
            p_img.alignment = WD_ALIGN_PARAGRAPH.CENTER
            p_img.paragraph_format.space_before = Pt(10)
            p_img.paragraph_format.space_after = Pt(4)
            p_img.paragraph_format.keep_with_next = True
            r = p_img.add_run()
            r.add_picture(img_path, width=Cm(width_cm))
            
            p_cap = doc.add_paragraph()
            p_cap.alignment = WD_ALIGN_PARAGRAPH.CENTER
            p_cap.paragraph_format.space_before = Pt(0)
            p_cap.paragraph_format.space_after = Pt(12)
            r_cap = p_cap.add_run(caption_text)
            r_cap.font.name = 'Arial'
            r_cap.font.size = Pt(9.5)
            r_cap.font.italic = True
            r_cap.font.color.rgb = RGBColor(60, 60, 60)

    # -------------------------------------------------------------
    # SARRERA ETA LABURPEN EXEKUTIBOA
    # -------------------------------------------------------------
    add_h1("SARRERA ETA LABURPEN EXEKUTIBOA", "sec_sarrera")
    add_p(
        "Dokumentu honek Goierri Eskolako Garapeneko Aplikazioen Multiplataforma (DAM) zikloko "
        "Enplegagarritasunerako Ibilbide Pertsonala II (EIP II) moduluko negozio-ideiaren garapen osoa jasotzen du. "
        "Ikasitako teoria-irizpideak oinarri hartuta ('Negozio ideiaren garapena' dokumentua), gure ekintzailetza-proiektua "
        "KuraLink Health Technologies S.L. da: gailu biomediko eramangarriak eta osasun-sistema publikoak zein pribatuak "
        "bateratzen dituen Software as a Medical Device (SaMD MDR IIa) tele-monitorizazio plataforma adimentsua."
    )
    add_p(
        "Euskal Autonomia Erkidegoko eta Europako osasun-zerbitzuek bizi duten presio demografiko eta asistentzialari "
        "erantzuten dio KuraLink-ek. Gaixo kardiako eta arnas-arazo kronikoak dituzten pazienteak ospitaletik etxera igarotzean "
        "zaintzaren etengabeko segimendua bermatzen du, deskonpentsazioak 48 ordu lehenago detektatuz, ospitaleratzeak %35 murriztuz "
        "eta Osakidetzako Osabide Global sistemarekin HL7 FHIR bidezko datu-truke zuzena bideratuz."
    )

    # -------------------------------------------------------------
    # 1. ARAZOA ETA IRTENBIDEA
    # -------------------------------------------------------------
    doc.add_page_break()
    add_h1("1. ARAZOA ETA IRTENBIDEA", "sec_1")
    add_p(
        "Negozio-ideia baten oinarria inguruko benetako premia edo arazo bat identifikatzea eta horri irtenbide bideragarri, "
        "eraginkor eta jasangarri bat ematea da. Teoria-dokumentuak proposatzen dituen hiru galdera nagusiei erantzunez garatu dugu atal hau."
    )
    
    add_h2("1.1. Zer arazo ikusten dituzu zure inguruan konpontzeko?", "sec_1_1")
    add_p(
        "Gure gizartean eta gure osasun-ekosisteman hiru errealitate larri eta ukaezin identifikatu ditugu:"
    )
    add_bullet(
        "Gizartearen zahartze azkarra eta krisi demografikoa: ",
        "Eustaten datuen arabera, Euskadin 65 urtetik gorako herritarrak biztanleriaren %23,5 baino gehiago dira jada, eta zahartze-indizea "
        "160tik gorakoa da (100 gazteko 160 adineko pertsona daude). Biztanleriaren piramidearen iraulketa honek gaixotasun kronikoen "
        "hazkunde esponentziala eragin du (arazo kardiobaskularrak, bihotz-gutxiegitasuna, biriketako gaixotasun buxatzaile kronikoa eta hipertentsioa)."
    )
    add_bullet(
        "Osasun-sistemaren saturazioa eta gastuaren bideraezintasuna: ",
        "Euskal osasun-gastu publikoaren %77 gaixo kronikoen arretara bideratzen da gaur egun. Ospitaleetako larrialdi-zerbitzuek eta "
        "kardiologia-plantek saturazio handia pairatzen dute, batez ere deskonpentsazio akutuengatik gertatzen diren berrospitaleratze "
        "errepikakorren ondorioz."
    )
    add_bullet(
        "Zaintzaren etena ospitaletik etxera igarotzean: ",
        "Paziente batek ospitalean alta jasotzen duenean, medikuek bere parametro fisiologikoen kontrol jarraitua galtzen dute. "
        "Gaixoak aste edo hilabeteetara hitzordua izan arte ez dago datu kliniko fidagarririk, eta krisiak sortzen direnean berandu izaten da, "
        "larrialdietara itzultzea beste aukerarik gabe."
    )

    add_h2("1.2. Zer trebetasun edo talentu bihur ditzakezu negozio?", "sec_1_2")
    add_p(
        "Garapeneko Aplikazioen Multiplataforma (DAM) ikasketetan bereganatutako gaitasun teknikoak eta profil hibrido baten balio erantsia "
        "uztartu ditugu proiektuan:"
    )
    add_bullet(
        "Software garapen aurreratua (Android, Web eta Cloud): ",
        "Kotlin, Jetpack Compose, arkitektura garbiak (Clean Architecture), backend seguruak eta interfaze irisgarriak sortzeko gaitasuna, "
        "adineko pertsonek ahaleginik gabe erabili ahal izateko moduko diseinuarekin."
    )
    add_bullet(
        "Gailu medikoen komunikazio-protokoloak (IoMT eta BLE): ",
        "Bluetooth Low Energy (BLE) bidezko zero-touch sinkronizazioa ezartzeko talentua, sentsore biomedikoen datu gordinak (EKG uhinak, "
        "bihotz-maiztasuna, oxigeno-saturazioa, mugimendu biomekanikoa) telefono mugikorrera eta hodeira fidagarritasun osoz transmititzeko."
    )
    add_bullet(
        "Osasun-sistemen interoperabilitatea (HL7 FHIR): ",
        "Datu medikoak ez dira aplikazio isolatu batean geratzen; Osakidetzako Osabide Global sistemarekin komunikatzeko nazioarteko HL7 FHIR "
        "estandarra menderatzen dugu, medikuak bere lan-tresna habitualetik datu guztiak ikus ditzan."
    )
    add_bullet(
        "Araudi klinikoa eta datu-segurtasuna: ",
        "Europako EB 2017/745 MDR (Medical Device Regulation) araudiaren araberako Software as a Medical Device (SaMD) IIa klaseko "
        "baldintzak betetzeko, eta Datuak Babesteko Erregelamendu Orokorra (GDPR) zein Segurtasun Eskema Nazionala (ENS Altua) aplikatzeko ezagutza."
    )

    add_h2("1.3. Zer produktu edo zerbitzu mota ez dago, baina ikusi nahiko zenuke?", "sec_1_3")
    add_p(
        "Gaur egungo merkatuan hutsune nabarmen bat dago:"
    )
    add_bullet(
        "Kontsumoko gailuen mugak: ",
        "Erloju adimendun komertzialek (Apple Watch, Garmin, Fitbit...) ez dute baliozko homologazio klinikorik, ez dute seinale biologiko "
        "jarraitu fidagarririk igortzen eta ez dira inondik inora ospitaleetako historia klinikoan txertatzen."
    )
    add_bullet(
        "Tresna mediko tradizionalen zurruntasuna: ",
        "Ospitaleetako Holter aparatuak edo telemetria-kate zaharrak astunak, deserosoak eta garestiak dira, eta pazienteak ezin ditu bere "
        "bizitza arruntean modu iraunkorrean erabili."
    )
    add_p(
        "Horregatik, guk ikusi nahiko genukeen eta sortu dugun produktua soluzio integral bat da: BioPatch izeneko adabaki biomediko eramangarri ultra-arina, "
        "aurretiko ezagutzarik gabe automatikoki konektatzen dena, mugikorreko aplikazio intuitibo batekin koordinatua, eta medikuari bere kontsultan "
        "denbora errealeko alertak eta txosten kliniko baliozkotuak bidaltzen dizkiona."
    )

    add_h2("1.4. KuraLink soluzioaren erantzun integrala eta arkitektura", "sec_1_4")
    add_p(
        "KuraLink-ek arazo horiei guztiei soluzio sendo bat ematen die, osasun-sistema eta etxea zubi digital eta kliniko baten bidez lotuz. "
        "Plataformaren arkitektura lau osagai nagusitan banatzen da:"
    )
    add_bullet("1. BioPatch Sentsore Eramangarria: ", "Bularrean jartzen den adabaki arina, 3 bidezko EKG jarraitua, bihotz-taupadak (BPM), SpO2 eta IMU azelerometroa integratzen dituena.")
    add_bullet("2. Pazientearen Mugikor Aplikazioa: ", "Zero-touch konexio bidez datuak jasotzen dituena, pazientearen eguneroko ariketak gidatzen dituena eta sendagaien hartzea kontrolatzen duena.")
    add_bullet("3. KuraLink Cloud & SaMD Analitika: ", "Zarata elektrikoa iragazten duen eta arritmiak, takikardiak zein deskonpentsazioak 48 ordu lehenago detektatzen dituen adimen-modulua.")
    add_bullet("4. Ospitale Integrazioa (Osabide / FHIR): ", "Osakidetzako medikuei alertak zuzenean bidaltzen dizkien eta pazientearen historia digitalean txostenak automatikoki gordetzen dituen zubia.")
    
    # Image 1: Ospitalea Telemetria
    add_figure(
        "03_irudiak_eta_mockupak/kuralink_ospitalea_telemetria.jpg",
        "1. Irudia: Ospitaleko telemetria zentrala eta Osabide Global sistemarekin sinkronizazioa (Ospitaleetako saturazioari irtenbidea)."
    )

    # -------------------------------------------------------------
    # 2. MISIOA, BISIOA ETA BALOREAK
    # -------------------------------------------------------------
    doc.add_page_break()
    add_h1("2. MISIOA, BISIOA ETA BALOREAK", "sec_2")
    add_p(
        "Teoria-dokumentuan zehazten den bezala, misioa, ikuspegia eta balioak dira enpresa baten nortasuna, helburua eta kultura "
        "definitzen dituzten hiru zutabe estrategiko nagusiak. Hiru kontzeptu horiek enpresaren ADN-a osatzen dute: nor garen, nora joan nahi dugun "
        "eta hori lortzeko gure eguneroko jardunean nola jokatuko dugun zehazten dute."
    )

    add_h2("2.1. Zutabe estrategikoak eta enpresaren ADN-a", "sec_2_1")
    add_p(
        "KuraLink Health Technologies S.L.-ren ADN-a bi munduren uztarduratik jaiotzen da: bikaintasun teknologikoa eta zaintza mediko gizatiarra. "
        "Gure oinarri estrategikoak ez dira publizitate-esaldi hutsak; alderantziz, softwarearen lerro bakoitzean, datuen tratamenduan eta pazienteekiko "
        "hartu-emanean aplikatzen diren arau gorenak dira."
    )

    add_h2("2.2. Misioa: Zer egingo dugu egunerokotasunean? (Izateko arrazoia)", "sec_2_2")
    add_p(
        "Enpresaren egungo asmoa, zertan aritzen den, zer bezerori zuzentzen zaion eta zer balio ematen dien deskribatzen du gure misioak:"
    )
    
    # Mission Quote Box
    p_mis = doc.add_paragraph()
    p_mis.paragraph_format.left_indent = Cm(1.0)
    p_mis.paragraph_format.right_indent = Cm(1.0)
    p_mis.paragraph_format.space_before = Pt(6)
    p_mis.paragraph_format.space_after = Pt(8)
    r_mis = p_mis.add_run(
        "“Egunerokoan gailu biomediko eramangarri adimentsuak eta osasun-zerbitzu publiko zein pribatuak modu seguruan konektatzea, "
        "paziente kronikoei etengabeko zaintza pertsonalizatua eta medikuei denbora errealeko erabaki kliniko fidagarriak eskainiz.”"
    )
    r_mis.bold = True
    r_mis.font.size = Pt(11)
    r_mis.font.color.rgb = BLACK

    add_p("Teoria-dokumentuak eskatzen dituen lau ezaugarrien betetzea:")
    add_bullet("Laburra eta zehatza: ", "Esaldi bakar eta garbi batean laburbiltzen ditu gure jarduera nagusia, hartzaileak eta emandako balioa.")
    add_bullet("Inspiratzailea: ", "Teknologia pertsonen bizi-kalitatea eta osasun-segurtasuna zaintzeko tresna gisa kokatzen du.")
    add_bullet("Originala: ", "Gailu adimentsuen eramangarritasuna eta ospitale-sistemen integrazio sakona batzen ditu.")
    add_bullet("Merkatura zuzendua: ", "Paziente kronikoen eta osasun-arloko kardiologia zein barne-medikuntzako profesionalen premiei zuzenean erantzuten die.")

    add_h2("2.3. Bisioa: Zer izan nahi dugu etorkizunean? (Helmuga estrategikoa)", "sec_2_3")
    add_p(
        "KuraLink-en epe luzerako xedea, norantz goazen eta merkatuan lortu nahi dugun posizio anbiziotsu eta errealista honela definitzen da:"
    )
    
    # Vision Quote Box
    p_vis = doc.add_paragraph()
    p_vis.paragraph_format.left_indent = Cm(1.0)
    p_vis.paragraph_format.right_indent = Cm(1.0)
    p_vis.paragraph_format.space_before = Pt(6)
    p_vis.paragraph_format.space_after = Pt(8)
    r_vis = p_vis.add_run(
        "“2030erako Hego Euskal Herriko eta Europa hegoaldeko erreferentziazko plataforma bihurtzea Software as a Medical Device (SaMD) "
        "tele-zaintzan, berrospitaleratze kardiakoak %35 murriztuz eta etxean oinarritutako osasun-eredu proaktibo eta jasangarri baten gidari izanez.”"
    )
    r_vis.bold = True
    r_vis.font.size = Pt(11)
    r_vis.font.color.rgb = BLACK

    add_p("Teoria-dokumentuko hiru baldintzen betetzea:")
    add_bullet("Etorkizunera proiekzioa: ", "2030 urterako helmuga zehatza eta nazioartekotzeko hedapen-eremua argi markatzen ditu.")
    add_bullet("Argia eta errealista: ", "Murrizketa-helburu kuantifikagarri bat (%35eko berrospitaleratze jaitsiera) eta ebidentzia klinikoan oinarritutako eredua proposatzen ditu.")
    add_bullet("Inspiratzailea eta kontsekuentea: ", "Osasun-eredu prebentiboago, jasangarriago eta gizatiarrago bateranzko eraldaketa gidatzeko asmoa du.")

    add_h2("2.4. Baloreak: Zertan sinesten dugu eta zeintzuk dira gure printzipioak?", "sec_2_4")
    add_p(
        "Enpresaren eta bertako langileen ekintza, erabaki eta kode-lerro guztiak gidatzen dituzten bost balore etiko nagusiak hauek dira:"
    )
    add_bullet("1. Zehaztasun Biomedikoa eta Ebidentzia Zientifikoa: ", "Datu klinikoak zehatzak eta fidagarriak izan behar dira beti; ez dugu onartzen akatsik edo seinale zalantzagarririk.")
    add_bullet("2. Segurtasuna eta Datuen Pribatutasun Blindatua: ", "Pazientearen informazio klinikoa sakratua da. Zifratze aurreratua (AES-256) eta GDPR araudiaren betetze zorrotza bermatzen ditugu.")
    add_bullet("3. Hurbiltasun Gizatiarra eta Irisgarritasuna: ", "Teknologia pertsonen zerbitzura egon behar da, ez alderantziz. Gure aplikazioak edonork, baita 80 urteko aitona-amonek ere, erraz erabiltzeko pentsatuta daude.")
    add_bullet("4. Interoperabilitate Irekia eta Lankidetza: ", "Osasun-ekosistema itxien aurka egiten dugu; estandar irekiak (HL7 FHIR) sustatzen ditugu beste sistema eta erakundeekin elkarlanean aritzeko.")
    add_bullet("5. Euskal Osasun Ekosistemarekiko Konpromisoa: ", "Basque Health Cluster, tokiko ikerketa-zentroak (Vicomtech, Tecnalia, BioGipuzkoa) eta Osakidetza indartzeko bokazio irmoa dugu.")

    add_p("Teorian eskatutako hiru ezaugarriak:")
    add_bullet("Sinesgarriak: ", "Gure prestakuntza teknikoan eta osasun-arau zorrotzetan oinarrituta daudelako.")
    add_bullet("Koherenteak: ", "Gure softwarearen garapen-ziklo osoan eta hartzen diren erabaki guztietan islatzen direlako.")
    add_bullet("Onartuak eta beteak izatea: ", "Lantaldeko kide guztiek barneratu eta egunero praktikan jartzen dituztelako.")

    # -------------------------------------------------------------
    # 3. TARGET (XEDE PUBLIKOA)
    # -------------------------------------------------------------
    doc.add_page_break()
    add_h1("3. TARGET (XEDE PUBLIKOA)", "sec_3")
    add_p(
        "Teoria-dokumentuan zehazten den moduan, target-a edo xede-publikoa gure produktu edo zerbitzuan interesa izateko eta erosteko "
        "probabilitate handiena duten pertsonen multzoa da. Ez da kontsumitzaile-talde orokor edo lauso bat; aldiz, ezaugarri komunen arabera "
        "zehaztasunez segmentatutako talde bat da."
    )

    add_h2("3.1. Target-aren definizioa eta B2B2C segmentazioa", "sec_3_1")
    add_p(
        "KuraLink-ek B2B2C (Business-to-Business-to-Consumer) negozio-eredua du. Horrek esan nahi du gure xede-publikoak bi maila dituela: "
        "zerbitzua erosten eta preskribatzen duten erakunde profesionalak (B2B) eta produktua egunerokoan beren gorputzean erabiltzen duten pazienteak (B2C)."
    )

    add_h2("3.2. Target-aren bereizketa aldagai nagusien arabera", "sec_3_2")
    add_p(
        "Teorian proposatutako lau aldagai-multzoen bidez deskribatu dugu gure xede-publikoa:"
    )
    add_bullet(
        "Aldagai Demografikoak: ",
        "B2C Pazienteak: 60 eta 85 urte bitarteko gizon eta emakumeak, gaixotasun kardiobaskular kronikoak (bihotz-gutxiegitasuna, arritmiak, infartu ostekoa) "
        "dituztenak. Baita haien senide eta zaintzaile nagusiak ere (40-60 urteko seme-alabak). "
        "B2B Bezeroak: Kardiologia, Barne Medikuntza eta Pneumologiako zerbitzu-buruak, ospitaleetako kudeatzaileak eta osasun-sail publikoko zuzendariak."
    )
    add_bullet(
        "Aldagai Geografikoak: ",
        "Lehentasunezko eremua Euskal Autonomia Erkidegoa da (Gipuzkoa, Bizkaia eta Araba), bereziki Osakidetzako ESI-ak (Donostialdea, Goierri-Urola Garaia, "
        "Bilbo-Basurtu, Arabako Unibertsitate Ospitalea) eta klinika pribatuak (Quirónsalud, Asuncion Klinika). Ondoren, Nafarroa eta Espainiako zein "
        "Frantziako hegoaldeko osasun-sareak."
    )
    add_bullet(
        "Aldagai Psikografikoak: ",
        "Pazienteak: Beren etxean modu independente eta lasaian bizi nahi duten pertsonak, ospitaleetako bisita etengabeek sortzen dieten estresa "
        "saihestu nahi dutenak, eta beren osasunaren inguruan segurtasun-sentsazioa baloratzen dutenak. "
        "Profesionalak: Ebidentzian oinarritutako medikuntza defendatzen duten espezialistak, datu zehatzak denbora errealean behar dituztenak eta "
        "berrikuntza teknologiko fidagarria bilatzen dutenak lan-zama arintzeko."
    )
    add_bullet(
        "Aldagai Jokabidezkoak: ",
        "Pazienteak: Medikuaren aginduak eta tratamenduak betetzeko konpromisoa dutenak, sentsorea egunero modu pasiboan eramateko prest daudenak, "
        "eta ariketa gidatuak eginez errehabilitazioan parte hartu nahi dutenak. "
        "Erakundeak: Berrospitaleratzeen kostu ekonomikoa murrizteko premia larria duten erakundeak, kalitate-adierazleak hobetu nahi dituztenak eta "
        "prebentzio-eredu digitaletara igarotzeko erabakia hartu dutenak."
    )

    # Image 2: Etxez etxeko zaintza
    add_figure(
        "03_irudiak_eta_mockupak/kuralink_etxez_etxeko_zaintza.jpg",
        "2. Irudia: Etxez etxeko tele-zaintza eta adineko paziente kronikoen monitorizazio autonomoa (Target nagusia)."
    )

    add_h2("3.3. Target apropos baten lau ezaugarriak KuraLink-en", "sec_3_3")
    add_p(
        "Teoria-dokumentuan aipatzen diren lau ezaugarri nagusiak betetzen ditu gure xede-publikoak:"
    )
    add_bullet(
        "1. Espezifikoa eta zehatza da: ",
        "Ez gara herritar guztiei zuzentzen; oso ondo zedarritutako talde batera jotzen dugu: kardiologia-zerbitzuek alta emandako gaixo kroniko "
        "konplexuetara eta haiek artatzen dituzten kardiologia-unitateetara."
    )
    add_bullet(
        "2. Antzeko premiak edo arazoak partekatzen ditu: ",
        "Paziente guztiek partekatzen dute berrospitaleratzearen beldurra eta etxean bakarrik geratzeak sortzen duen segurtasun-gabezia; "
        "mediku guztiek partekatzen dute ospitaletik kanpoko pazienteen jarraipenik eza eta oheen gainkarga."
    )
    add_bullet(
        "3. Produktua erosteko gaitasuna eta interesa du: ",
        "Osakidetzak eta osasun-erakunde pribatuek aurrekontu espezifikoak dituzte berrikuntza medikorako eta kronikoen kudeaketarako. "
        "Gainera, KuraLink-ek gastu erraldoiak aurrezten dizkie: ospitaleratze arrunt baten kostua 3.500 € ingurukoa da, eta KuraLink-en "
        "hileko lizentziak (60 €/paziente) inbertsioa berehala amortizatzen du (4,2x ROI lehen urtean)."
    )
    add_bullet(
        "4. Marketin- eta komunikazio-ekintzen bidez eskura daiteke: ",
        "Kanal profesional zehatzen bidez heltzen gara bezeroengana: Euskal Osasun Klusterra (Basque Health Cluster), Kardiologiako Euskal eta "
        "Espainiar Biltzarrak, osasun publikoko lizitazio ofizialak, eta pazienteen elkarteak (Bihotzez elkartea, etab.)."
    )

    add_h2("3.4. Erabilera-eszenatoki errealak: Ospitaleko arreta eta Etxez etxeko tele-zaintza", "sec_3_4")
    add_p(
        "KuraLink-en eraginkortasuna ulertzeko, bi eszenatoki kliniko erreal azter ditzakegu:"
    )
    add_bullet(
        "A Eszenatokia (Ebakuntza osteko bihotz-errehabilitazioa): ",
        "68 urteko paziente batek bihotzeko ebakuntza bat izan du Donostia Ospitalean. Alta jasotzean, BioPatch adabakiarekin doa etxera. "
        "Goizero, KuraLink mugikorreko aplikazioak agindutako ariketa fisiologikoak erakusten dizkio. IMU biomekanika azelerometroak "
        "ariketa ongi egiten ari den neurtzen du, eta EKGak bihotz-erritmoa kontrolatzen du. Datuak Osabiden erregistratzen dira automatikoki."
    )
    add_bullet(
        "B Eszenatokia (Deskonpentsazio goiztiarraren alerta): ",
        "76 urteko paziente batek bihotz-gutxiegitasuna du. Gauean lo dagoela, BioPatch-ak likido-erretentzioak eta arnas-erritmoaren "
        "moteltzeak eragindako fibrilazio aurikular goiztiarra detektatzen du. Pazienteak sintomarik sumatu aurretik, KuraLink Cloud-ek alerta "
        "bat bidaltzen dio kardiologoari. Medikuak botiken dosia doitzen du telefonoz, eta bi egun geroago gertatuko litzatekeen larrialdiko "
        "ospitaleratzea erabat ekiditen da."
    )

    # -------------------------------------------------------------
    # 4. BALIO PROPOSAMENA ETA LEHIAREN AZTERKETA
    # -------------------------------------------------------------
    doc.add_page_break()
    add_h1("4. BALIO PROPOSAMENA ETA LEHIAREN AZTERKETA", "sec_4")
    add_p(
        "Balio-proposamena negozio-ideia baten muina da: azaltzen du zergatik aukeratuko duen bezeroak gure produktua merkatuko beste aukeren "
        "aurretik, zein arazo konpontzen dion hobeto eta zer onura neurgarri ekarriko dizkion. Teorian planteatutako lau galdera nagusiei erantzunez garatu dugu atal hau."
    )

    add_h2("4.1. Balio bakarra: Zein balio ematen dio produktuak bezeroari?", "sec_4_1")
    add_p(
        "KuraLink-ek eskaintzen duen balio bakarra eta ordezkaezina hauxe da: "
        "gailu eramangarri baten erosotasuna eta ospitale-mailako berme kliniko zorrotza bateratzea, informazioa pazientearen etxetik medikuaren "
        "ordenagailura (Osabide Global) automatikoki eta segurtasunez eramanez."
    )
    add_p(
        "Merkatuko beste irtenbideek neurketa soilak edo datu isolatuak eskaintzen dituzte; KuraLink-ek, berriz, zaintza-kate osoa ixten du: "
        "sentsore biomedikoa + pazientearen aplikazio lagungarria + arriskuen aurreikuspen algoritmoa + ospitaleko historia digitalarekiko integrazio zuzena."
    )

    add_h2("4.2. Emaitza zehatzak: Zer lortuko du bezeroak soluzioa erabiltzean?", "sec_4_2")
    add_p(
        "Gure soluzioak emaitza kliniko eta ekonomiko guztiz neurgarriak eskaintzen ditu:"
    )
    add_bullet("%35eko murrizketa ospitaleratze eta larrialdietako sarreretan: ", "Deskonpentsazio kardiakoak 48 ordu lehenago atzemanez.")
    add_bullet("%88ko atxikimendua errehabilitazio ariketetan: ", "Pazienteak gidaritza argia eta bere aurrerapenen berri uneoro jasotzen duelako.")
    add_bullet("Medikuaren denboraren %40ko aurrezpena: ", "Datu hutsalak automatikoki iragazten dira eta bakarrik kasu relabanteak bistaratzen dira.")
    add_bullet("3.500 €-ko aurrezpen garbia ospitaleratze ekidin bakoitzeko: ", "Osasun-zerbitzu publikoarentzat jasangarritasun ekonomiko handia ekarriz.")

    add_h2("4.3. Merkatuko alternatiben hobekuntza eta Lehiaren matrize konparatiboa", "sec_4_3")
    add_p(
        "Gure ideiak merkatuko beste aukerak nola hobetzen dituen ikusteko, lehiaren azterketa sakona egin dugu honako matrizean:"
    )

    # Comparison Table
    table_comp = doc.add_table(rows=6, cols=5)
    table_comp.alignment = WD_TABLE_ALIGNMENT.CENTER
    set_table_borders(table_comp, color="000000", sz="4", val="single")

    headers_comp = ["Ezaugarria / Irtenbidea", "KuraLink Health", "Smartwatch Komertzialak", "Holter Tradizionala", "Medtronic / Philips Tele."]
    for c_idx, h_text in enumerate(headers_comp):
        cell = table_comp.cell(0, c_idx)
        set_cell_background(cell, "000000")
        set_cell_margins(cell, 100, 100, 80, 80)
        p = cell.paragraphs[0]
        p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        r = p.add_run(h_text)
        r.bold = True
        r.font.size = Pt(9)
        r.font.color.rgb = RGBColor(255, 255, 255)

    comp_data = [
        ("CE MDR IIa Homologazio Medikoa", "BAI (EB 2017/745)", "EZ (Ez-klinikoa)", "BAI (Gailu fisikoa)", "BAI (Ospitalekoa)"),
        ("Seinale Jarraitua (EKG / SpO2 / IMU)", "BAI (BioPatch 24/7)", "Puntuala / Muga handiak", "BAI (Baina 24-48h muga)", "Partziala (Etxeko oinarria)"),
        ("Osabide Global / HL7 FHIR Integrazioa", "BAI (Automatikoa)", "EZ (Itxia / Ezin da)", "EZ (Eskuzko pdf txostena)", "Partziala (Garestia / Itxia)"),
        ("Errehabilitazio Ariketen IMU Gida", "BAI (Biomekanikoa)", "EZ (Zenbaketa arrunta)", "EZ (Monitorizazio hutsa)", "EZ (Ez dago txertatua)"),
        ("Kostua Ospitale eta Pazientearentzat", "ESKURAGARRIA (SaaS 60€/h)", "ALTUA (Pazienteak 400€+)", "ALTUA (Ekipamendu astuna)", "OSO GARESTIA (>2.000€/paz)")
    ]

    for r_idx, row_values in enumerate(comp_data):
        for c_idx, val_text in enumerate(row_values):
            cell = table_comp.cell(r_idx + 1, c_idx)
            set_cell_margins(cell, 80, 80, 80, 80)
            if c_idx == 1:
                set_cell_background(cell, "F0FDF4")  # Very subtle highlight
            elif r_idx % 2 == 1:
                set_cell_background(cell, "F9FAFB")
            else:
                set_cell_background(cell, "FFFFFF")
                
            p = cell.paragraphs[0]
            if c_idx > 0:
                p.alignment = WD_ALIGN_PARAGRAPH.CENTER
            r = p.add_run(val_text)
            r.font.size = Pt(8.5)
            r.font.color.rgb = BLACK
            if c_idx == 1 or c_idx == 0:
                r.bold = True

    add_p(
        "Taulan argi ikusten denez, kontsumoko erlojuak ez dira fidagarriak medikuentzat, eta ospitaleetako irtenbide klasikoak "
        "zaharkituak eta zurrunak dira. KuraLink-ek bi munduen abantailak biltzen ditu hutsune hori erabat betez."
    )

    add_h2("4.4. Funtsezko ezaugarriak, eragin klinikoa eta araudi-betetzea", "sec_4_4")
    add_p(
        "KuraLink-en arrakasta bermatzeko funtsezkoak diren lau ezaugarri teknologiko eta araudizko hauek nabarmentzen dira:"
    )
    add_bullet(
        "Zero-touch BLE sinkronizazioa: ",
        "Paziente adinekoak ez du mugikorrean konfiguraziorik egin behar. Sentsorea bularrean itsatsi bezain laster, "
        "aplikazioak automatikoki ezagutzen du eta datuak hodeira igortzen hasten da."
    )
    add_bullet(
        "IMU biomekanika errehabilitazioan: ",
        "Bihotzeko ebakuntza izan duten pazienteek ariketa fisikoak egin behar dituzte berreskuratzeko. BioPatch-aren azelerometroak "
        "eta giroskopioak mugimenduaren angelua eta intentsitatea neurtzen dituzte, ariketak zuzen egiten direla bermatuz."
    )
    add_bullet(
        "Adimen artifizial bidezko krisien aurreikuspena: ",
        "Algoritmoek datu fisiologikoen joerak aztertzen dituzte, arnas-estutasuna edo arritmia-zantzuak deskonpentsazio akutu bihurtu aurretik detektatuz."
    )
    add_bullet(
        "Homologazio medikoa eta kalitate-ziurtagiriak: ",
        "Europako EB 2017/745 MDR araudiaren arabera, KuraLink IIa klaseko Software as a Medical Device (SaMD) gisa homologatuta dago, "
        "eta ISO 13485:2016 gailu medikoen kalitate-kudeaketako ziurtagiria du."
    )

    # Image 3: Bihotz Errehabilitazioa
    add_figure(
        "03_irudiak_eta_mockupak/kuralink_bihotz_errehabilitazioa.jpg",
        "3. Irudia: Bihotz-errehabilitazio plana eta ariketa gidatuen denbora errealeko azterketa fisiologikoa (Eragin kliniko zuzena)."
    )

    # Image 4: ISO eta CE ziurtagiriak
    add_figure(
        "02_marka_eta_identitate_bisuala/iso_ce_certifications.png",
        "4. Irudia: KuraLink-en araudi-betetzea: ISO 13485:2016 eta CE Mark MDR IIa (EB 2017/745) homologazio klinikoa.",
        width_cm=11.0
    )

    # -------------------------------------------------------------
    # 5. MARKA: KURALINK
    # -------------------------------------------------------------
    doc.add_page_break()
    add_h1("5. MARKA: KURALINK", "sec_5")
    add_p(
        "Teoria-dokumentuan adierazten denez, balio-proposamena zehaztu ondoren, enpresarentzat izen eta identitate sendo bat "
        "pentsatu behar da. Marka-izen on batek lau ezaugarri nagusi bete behar ditu, eta KuraLink-ek lauak betetzen ditu zorrozki."
    )

    add_h2("5.1. Marka-izen on baten lau ezaugarriak KuraLink-en", "sec_5_1")
    add_bullet(
        "1. Gogoratzeko erraza da: ",
        "Izen laburra, dinamikoa eta indartsua da. Bi silaba argik osatzen dute (Ku-ra / Link), eta entzun bezain laster gogoan geratzen da."
    )
    add_bullet(
        "2. Ahoskatzeko eta idazteko erraza da: ",
        "Ez du karaktere arrarorik ezta ahoskera konplexurik. Euskaraz, gaztelaniaz zein ingelesez naturaltasun osoz irakurtzen eta ahoskatzen da, "
        "nazioartekotzeko bidean oztoporik sortu gabe."
    )
    add_bullet(
        "3. Enpresaren jarduerarekin eta balioekin estuki lotuta dago: ",
        "Izenaren erroek gure izateko arrazoia deskribatzen dute: 'KURA' (euskaraz sendatu, zaindu, sendabidea ekarri) eta 'LINK' (ingelesez lotura, "
        "konexio digitala, gailuen eta osasun-sistemen arteko zubi teknologikoa). Beraz, zaintza gizatiarraren eta teknologia aurreratuaren batasuna da."
    )
    add_bullet(
        "4. Beste marka batzuekiko bereizlea da: ",
        "Merkatuan ohikoak diren izen generiko eta hotzetatik aldentzen da (hala nola 'HealthMonitor', 'TeleCare', 'CardioApp'...). "
        "KuraLink-ek nortasun berezia, euskal sustrai sendoak eta proiekzio global modernoa transmititzen ditu."
    )

    add_h2("5.2. Identitate bisuala, kolore korporatiboak eta diseinu sistema", "sec_5_2")
    add_p(
        "KuraLink-en nortasun bisualak zorroztasun zientifikoa eta gertutasun klinikoa transmititzen ditu. Gure kolore-paleta "
        "arlo medikoaren eskakizunetara egokituta dago:"
    )
    add_bullet("Urdin Teknologikoa (#0A2540 / #1E3A8A): ", "Konfiantza, egonkortasuna, zorroztasun instituzionala eta zibersegurtasuna adierazten ditu.")
    add_bullet("Zian Klinikoa (#00D4B2 / #06B6D4): ", "Teknologia berria, seinale biomedikoen bizitasuna, berrikuntza eta argitasuna.")
    add_bullet("Berde Fisiologikoa (#10B981): ", "Pazientearen egonkortasun kardiakoa, berreskurapen arrakastatsua eta osasuna.")
    add_bullet("Gorri Kardiakoa (#EF4444): ", "Larrialdi-alertak, arritmiak eta berehalako arreta medikoa behar duten kasuak seinalatzeko.")
    add_bullet("Zuri Garbia (#FFFFFF) eta Grisa (#64748B): ", "Irakurgarritasun maximoa, garbitasun klinikoa eta begien atseden bisuala.")

    add_p(
        "Tipografiari dagokionez, letra-tipo garbiak, humanistak eta lerro zuzenekoak aukeratu ditugu (Inter eta Arial). "
        "Horrela, pantaila txikietan zein inprimatutako txostenetan datu medikoak eta zenbakiak akatsik gabe irakurtzea bermatzen da."
    )

    add_h2("5.3. Mugikorreko aplikazioa eta sentsore biomedikoen integrazioa", "sec_5_3")
    add_p(
        "KuraLink aplikazio mugikorrak lau eremu nagusi eskaintzen dizkio pazienteari:"
    )
    add_bullet("Paziente Ataria: ", "Identifikazio segurua (OTI osasun-txartelarekin edo emailarekin) eta fitxa kliniko pertsonala.")
    add_bullet("Telemetria Zuzenekoa: ", "Bihotz-maiztasuna (BPM), denbora errealeko EKG trazatua, tenperatura eta oxigeno-maila.")
    add_bullet("Errehabilitazio Gidatua: ", "Egunero egin beharreko ariketen bideoak, denbora-kontagailua eta mugimenduaren egiaztapena.")
    add_bullet("Ezarpenak eta Hizkuntza: ", "Interfaze eleaniztuna (Euskara, Español, English), itxura modua (Argia/Iluna) eta jakinarazpenen doikuntza.")

    # Image 5: Mugikorreko App eta Sentsorea
    add_figure(
        "03_irudiak_eta_mockupak/kuralink_app_mockup.jpg",
        "5. Irudia: KuraLink mugikorreko aplikazioa eta BioPatch BLE sentsore mediko eramangarria."
    )

    # -------------------------------------------------------------
    # 6. BIDERAGARRITASUN PLANA ETA ONDORIOAK
    # -------------------------------------------------------------
    doc.add_page_break()
    add_h1("6. BIDERAGARRITASUN PLANA ETA ONDORIOAK", "sec_6")
    add_p(
        "Negozio-ideia orok bideragarritasun-azterketa zorrotz bat behar du paperetik errealitatera igarotzeko. "
        "KuraLink proiektuak hiru bideragarritasun-zutabe nagusi ditu:"
    )

    add_h2("6.1. Bideragarritasun teknikoa, ekonomikoa eta araudizkoa", "sec_6_1")
    add_bullet(
        "Bideragarritasun Teknikoa: ",
        "DAM zikloan ikasitako arkitektura multiplataformak (Kotlin Android, Web zerbitzuak, REST APIak eta HL7 FHIR zubiak) "
        "oinarri sendo eta eskalagarria eskaintzen dute. BLE sentsoreekin egindako probek zero-touch konexioaren fidagarritasuna egiaztatu dute."
    )
    add_bullet(
        "Bideragarritasun Ekonomikoa: ",
        "B2B SaaS ereduak diru-sarrera errepikakor eta aurreikusgarriak sortzen ditu (hileko 60 € gaixo aktibo bakoitzeko). "
        "Ospitaleentzat KuraLink kontratatzea aurrezpen garbia da: 100 pazienteko kohorte batean urtean 35 berrospitaleratze ekiditeak "
        "122.500 €-ko aurrezpena dakar, plataformaren kostua (72.000 €) erraz gaindituz (ROI = 1,7x lehen urtean, eta 4,2x hedapen-fasean)."
    )
    add_bullet(
        "Bideragarritasun Araudizkoa eta Legala: ",
        "Hasiera-hasieratik CE MDR IIa homologazioa eta ISO 13485 araudia diseinuan txertatu izanak bide administratiboa azkartzen du, "
        "Euskal Autonomia Erkidegoko Bioosasun Klusterraren babesarekin."
    )

    add_h2("6.2. Ikaskuntza-prozesua eta etorkizuneko garapena", "sec_6_2")
    add_p(
        "Enplegagarritasunerako Ibilbide Pertsonala II (EIP II) moduluaren baitan garatutako lan honek agerian utzi du "
        "ekintzailetza ez dela ideia distiratsu bat izatea soilik, baizik eta ideia hori metodo zientifiko eta estrategiko batekin "
        "garatzea: arazoa aztertu, bezeroa eta target-a zehaztasunez segmentatu, balio-proposamen bereizle bat eraiki eta marka profesional bat sortu."
    )
    add_p(
        "Etorkizunari begira, KuraLink-ek bi garapen-lerro nagusi ditu: lehena, arnas-patologien (BGBK) eta diabetesaren monitorizazioa txertatzea; "
        "eta bigarrena, adimen artifizial prediktiboa hobetzea tokiko ikerketa-zentroekin (BioGipuzkoa eta Vicomtech) lankidetzan, "
        "Euskadi telemedikuntza aurreratuaren mapan erreferente bilakatuz."
    )
    add_p(
        "Laburbilduz, KuraLink Health Technologies proiektuak erakusten du Goierri Eskolan jasotako jakintza teknikoak "
        "gizartearen erronka handienei erantzuteko eta balio ekonomiko zein sozial handiko enpresa bideragarriak sortzeko ahalmen osoa duela."
    )

    # Output file paths
    output_filename = "Negozio_Ideiaren_Garapena.docx"
    doc.save(output_filename)
    print(f"Document successfully created: {output_filename}")
    
    # Also copy to memory folder
    mem_folder = "01_negozio_proiektua_memoria"
    if os.path.exists(mem_folder):
        target_path = os.path.join(mem_folder, output_filename)
        shutil.copy2(output_filename, target_path)
        print(f"Document copied to: {target_path}")

if __name__ == "__main__":
    create_document()
