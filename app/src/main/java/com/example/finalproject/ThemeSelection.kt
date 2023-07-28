package com.example.finalproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar

class ThemeSelection : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_selection)

        val nightSwitch : SwitchCompat = findViewById(R.id.switchCompat)

        val receivedIntent = intent
        val isNightModeEnabled = receivedIntent.getBooleanExtra("night_mode", false)
        println(isNightModeEnabled)
        if (isNightModeEnabled) {
            // Aktifkan mode malam
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            nightSwitch.isChecked = true
        } else {
            // Aktifkan mode light
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            nightSwitch.isChecked = false
        }

        // Referensi ke Toolbar yang sudah didefinisikan di layout XML
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        // Mengatur Toolbar sebagai ActionBar
        setSupportActionBar(toolbar)

        // Tambahkan judul untuk ActionBar (Opsional)
        supportActionBar?.title = "Pilih Tema"

        // Tambahkan icon panah kembali di Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nightSwitch.setOnCheckedChangeListener { _, isChecked ->
            val intent = Intent()
            intent.putExtra("night_mode", isChecked)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Menangani aksi ketika tombol di toolbar ditekan
        when (item.itemId) {
            android.R.id.home -> {
                // Aksi ketika tombol kembali ditekan
                onBackPressed()
                return true
            }
            // Tambahkan aksi lain jika diperlukan
            else -> return super.onOptionsItemSelected(item)
        }
    }
}