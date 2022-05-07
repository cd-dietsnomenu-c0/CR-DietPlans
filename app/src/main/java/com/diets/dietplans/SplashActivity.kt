package com.diets.dietplans

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.RenderMode
import com.amplitude.api.Amplitude
import com.diets.dietplans.common.DBHolder
import com.diets.dietplans.common.FBWork.Companion.getFCMToken
import com.diets.dietplans.common.GlobalHolder
import com.diets.dietplans.common.db.entities.DietPlanEntity
import com.diets.dietplans.model.Global
import com.diets.dietplans.premium.PremiumHostActivity
import com.diets.dietplans.utils.ABConfig
import com.diets.dietplans.utils.LangChoicer
import com.diets.dietplans.utils.PreferenceProvider
import com.diets.dietplans.utils.ad.AdWorker.init
import com.diets.dietplans.utils.analytics.Ampl
import com.diets.dietplans.utils.analytics.Ampl.Companion.openFromPush
import com.diets.dietplans.utils.inapp.SubscriptionProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.squareup.moshi.Moshi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.splash_activity.*
import java.util.*


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    lateinit var scale: Animation
    lateinit var alpha: Animation
    lateinit var alphaText: Animation
    var goCounter = 0
    var maxGoCounter = 4
    var openFrom = ""
    var isFirstTime = false

    fun post() {
        goCounter += 1
        if (goCounter >= maxGoCounter) {
            var intent = if (isFirstTime && PreferenceProvider.isNeedPrem == ABConfig.PREM_NEED) {
                Intent(this, PremiumHostActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java).putExtra(Config.PUSH_TAG, openFrom)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLocale()
        bindFCM()
        bindTest()
        if (intent.extras != null && intent.extras!!.getString(Config.PUSH_TAG) != null && intent.extras!!.getString(Config.PUSH_TAG) == Config.OPEN_FROM_PUSH) {
            openFrom = Config.OPEN_FROM_PUSH
            openFromPush()
        }
        PreferenceProvider.setLastEnter(Calendar.getInstance().timeInMillis)
        loadAnimations()
        playAnim()
        loadData()
        setFirstTime()
        SubscriptionProvider.startGettingPrice()
    }

    private fun bindFCM() {
        var locale = PreferenceProvider.locale

        if (locale == PreferenceProvider.DEFAULT_LOCALE) {
            locale = resources.configuration.locale.toString().split("_")[0]
        }

        when (locale) {
            LangChoicer.RU -> {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS_EN).addOnSuccessListener { }
                FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS_RU).addOnSuccessListener { }
            }
            LangChoicer.EN -> {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS_RU).addOnSuccessListener { }
                FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS_EN).addOnSuccessListener { }
            }
            else -> {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS_EN).addOnSuccessListener { }
                FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS_RU).addOnSuccessListener { }
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic(Config.EAT_TOPIC).addOnSuccessListener { }
        getFCMToken()
    }

    private fun bindLocale() {
        if (PreferenceProvider.locale != PreferenceProvider.DEFAULT_LOCALE) {
            val res: Resources = resources
            val dm: DisplayMetrics = res.displayMetrics
            val conf: Configuration = res.configuration
            conf.locale = Locale(PreferenceProvider.locale.toLowerCase())
            res.updateConfiguration(conf, dm)
        }
    }


    private fun bindTest() {
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        var config = FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600).build()
        firebaseRemoteConfig.setConfigSettingsAsync(config)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.default_config)
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Amplitude.getInstance().logEvent("norm_ab")
            } else {
                Amplitude.getInstance().logEvent("crash_ab")
            }
            setABTestConfig(
                    firebaseRemoteConfig.getString(ABConfig.PREM_TAG), firebaseRemoteConfig.getString(ABConfig.PREM_VER)
            )
        }
    }

    private fun setABTestConfig(version: String, premVer : String) {
        var defaultVer = ABConfig.PREM_NEED
        var defaultPremVer = ABConfig.PREM_OLD
        if (version != null && version != "") {
            defaultVer = version
        }
        if (premVer != null && premVer != "") {
            defaultPremVer = premVer
        }
        Log.e("LOL", defaultVer)
        PreferenceProvider.isNeedPrem = defaultVer
        PreferenceProvider.premVer= defaultPremVer
        Ampl.setABVersion(defaultVer, defaultPremVer)
        Ampl.setVersion()
        post()
    }

    private fun setFirstTime() {
        if (PreferenceProvider.getFirstTime() == "") {
            isFirstTime = true
            val calendar = Calendar.getInstance()
            var date = "${"%02d".format(calendar.get(Calendar.DAY_OF_MONTH))}.${"%02d".format(calendar.get(Calendar.MONTH) + 1)}.${calendar.get(Calendar.YEAR)}"
            PreferenceProvider.setFirstTime(date)
        }
    }

    private fun loadAnimations() {
        scale = AnimationUtils.loadAnimation(this, R.anim.scale_splash)
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_splash)
        alphaText = AnimationUtils.loadAnimation(this, R.anim.alpha_splash)

        alpha.fillAfter = true
        alpha.isFillEnabled = true
        scale.fillAfter = true
        scale.isFillEnabled = true

        scale.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                ivLogo.startAnimation(alpha)
                tvText.startAnimation(alphaText)
                tvText.visibility = View.VISIBLE
                ivLogo.visibility = View.VISIBLE
                init(this@SplashActivity)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        alphaText.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                post()
                Log.e("LOL", "anim")
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }


    private fun playAnim() {
        lavLetter.setRenderMode(RenderMode.SOFTWARE)
        lavLetter.setAnimation("splash_letter.json")
        lavLetter.speed = 2.5f
        lavLetter.playAnimation()
        lavLetter.addAnimatorUpdateListener {
            if ((it.animatedValue as Float * 100).toInt() == 99) {
                lavLetter.startAnimation(scale)
            }
        }
    }

    private fun loadData() {
        loadDietData()
        loadDB()
    }

    private fun loadDB() {
        Single.fromCallable {
            var dietPlanEntity = App.getInstance().db.dietDAO().getAll()[0]
            dietPlanEntity
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> saveDiet(t) }) { _: Throwable -> saveEmptyDiet() }
    }

    private fun saveDiet(t: DietPlanEntity) {
        DBHolder.set(t)
        post()
        Log.e("LOL", "db")
    }

    private fun saveEmptyDiet() {
        DBHolder.setEmpty()
        post()
        Log.e("LOL", "empty db")
    }

    private fun loadDietData() {
        Single.fromCallable {
            val global: Global = getAsyncDietData()!!
            global
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: Global -> save(t) }) { obj: Throwable -> obj.printStackTrace() }
    }

    private fun save(t: Global) {
        GlobalHolder.setGlobal(t)
        post()
        Log.e("LOL", "aset")
    }

    private fun getAsyncDietData(): Global? {
        var json: String
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(Global::class.java)
        try {
            var inputStream = assets.open(getString(R.string.adb_path))
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
        }
        return null
    }


    companion object {
        private const val CHANGE_LANG_TAG = "CHANGE_LANG_TAG"

        fun getIntent(isChangedLang: Boolean, context: Context): Intent {
            return Intent(context, SplashActivity::class.java).apply {
                putExtra(CHANGE_LANG_TAG, true)
            }
        }
    }
}