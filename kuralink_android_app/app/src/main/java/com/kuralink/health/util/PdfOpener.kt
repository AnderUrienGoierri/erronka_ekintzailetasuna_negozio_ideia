@file:Suppress("unused")

package com.kuralink.health.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

object PdfOpener {

    private const val PDF_ASSET_NAME = "KuraLink_Negozio_Txostena.pdf"
    private const val ONLINE_PDF_URL = "https://anderuriengoierri.github.io/erronka_ekintzailetasuna_negozio_ideia/assets/docs/KuraLink_Negozio_Txostena.pdf"

    fun openOrDownloadPdf(context: Context) {
        try {
            val cacheFile = File(context.cacheDir, PDF_ASSET_NAME)
            if (!cacheFile.exists()) {
                context.assets.open(PDF_ASSET_NAME).use { input ->
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

            val chooser = Intent.createChooser(intent, "Ireki KuraLink Negozio Txostena (PDF)")
            chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(chooser)
        } catch (_: Exception) {
            // Fallback to online browser viewer
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, ONLINE_PDF_URL.toUri()).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(browserIntent)
                Toast.makeText(context, "Txostena nabigatzailean irekitzen...", Toast.LENGTH_SHORT).show()
            } catch (err: Exception) {
                Toast.makeText(context, "Errorea PDF-a irekitzean: ${err.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
