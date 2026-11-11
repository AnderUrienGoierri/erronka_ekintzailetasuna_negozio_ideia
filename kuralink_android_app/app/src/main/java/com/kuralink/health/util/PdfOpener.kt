@file:Suppress("unused")

package com.kuralink.health.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.kuralink.health.ui.screens.AppLanguage
import java.io.File
import java.io.FileOutputStream

object PdfOpener {

    fun openOrDownloadPdf(context: Context, language: AppLanguage = AppLanguage.EUSKARA) {
        val assetName = when (language) {
            AppLanguage.EUSKARA -> "KuraLink_Negozio_Txostena.pdf"
            AppLanguage.ESPANOL -> "KuraLink_Plan_Negocio_ES.pdf"
            AppLanguage.ENGLISH -> "KuraLink_Business_Report_EN.pdf"
        }

        val onlineUrl = "https://anderuriengoierri.github.io/erronka_ekintzailetasuna_negozio_ideia/assets/docs/$assetName"

        try {
            val cacheFile = File(context.cacheDir, assetName)
            if (!cacheFile.exists()) {
                context.assets.open(assetName).use { input ->
                    FileOutputStream(cacheFile).use { output ->
                        input.copyTo(output)
                    }
                }
            }

            val contentUri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                cacheFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, "application/pdf")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            val title = when (language) {
                AppLanguage.EUSKARA -> "Ireki KuraLink Negozio Txostena (PDF)"
                AppLanguage.ESPANOL -> "Abrir Plan de Negocio KuraLink (PDF)"
                AppLanguage.ENGLISH -> "Open KuraLink Business Plan (PDF)"
            }

            val chooser = Intent.createChooser(intent, title)
            chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(chooser)
        } catch (_: Exception) {
            // Fallback to online browser viewer
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, onlineUrl.toUri()).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(browserIntent)
                val msg = when (language) {
                    AppLanguage.EUSKARA -> "Txostena nabigatzailean irekitzen..."
                    AppLanguage.ESPANOL -> "Abriendo informe en el navegador..."
                    AppLanguage.ENGLISH -> "Opening report in browser..."
                }
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            } catch (err: Exception) {
                Toast.makeText(context, "Errorea PDF-a irekitzean: ${err.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
