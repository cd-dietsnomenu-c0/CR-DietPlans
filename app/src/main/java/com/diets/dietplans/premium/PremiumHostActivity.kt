package com.diets.dietplans.premium

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diets.dietplans.MainActivity
import com.diets.dietplans.R
import com.diets.dietplans.utils.PreferenceProvider
import com.diets.dietplans.utils.analytics.Ampl
import kotlinx.android.synthetic.main.premium_host_activity.*

class PremiumHostActivity : AppCompatActivity(R.layout.premium_host_activity) {

    var from = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Ampl.showPremScreen()
        from = intent.getIntExtra(TAG_OPEN_FROM_PROFILE, -1)
        PreferenceProvider.isSawPremium = true

        supportFragmentManager.beginTransaction().add(R.id.flFragmentHost, PremiumFragment()).commit()

        ivClose.setOnClickListener {
            if (from == FROM_PROFILE){
                finish()
            }else{
                openMainActivity()
            }
        }
    }

    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        if (from == FROM_PROFILE){
            finish()
        }else{
            openMainActivity()
        }
    }

    companion object{

        private const val TAG_OPEN_FROM_PROFILE = "TAG_OPEN_FROM_PROFILE"
        private const val FROM_PROFILE = 1

        fun getIntentProfile(context: Context) : Intent{
            return Intent(context, PremiumHostActivity::class.java).also {
                it.putExtra(TAG_OPEN_FROM_PROFILE, FROM_PROFILE)
            }
        }
    }
}