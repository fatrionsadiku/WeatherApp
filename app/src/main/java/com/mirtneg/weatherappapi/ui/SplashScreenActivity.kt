package com.mirtneg.weatherappapi.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mirtneg.weatherappapi.MainActivity
import com.mirtneg.weatherappapi.R
import com.mirtneg.weatherappapi.databinding.SplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding : SplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            weatherLogo.alpha = 0f
            weatherLogo.animate().setDuration(1000).alpha(1f).withEndAction {
                Intent(this@SplashScreenActivity, MainActivity::class.java).also {
                    startActivity(it)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }
        }
    }
}