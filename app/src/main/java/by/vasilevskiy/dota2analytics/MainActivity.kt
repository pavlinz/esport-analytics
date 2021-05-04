package by.vasilevskiy.dota2analytics

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import by.vasilevskiy.dota2analytics.helpers.ConnectionLiveData
import by.vasilevskiy.dota2analytics.utils.show
import by.vasilevskiy.dota2analytics.utils.showNoNetworkConnectionToast
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var toolbar: Toolbar

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        checkTheme()
        checkLanguage()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        connectionLiveData.observe(this) { isNetworkAvailable ->
            when (isNetworkAvailable) {
                false -> showNoNetworkConnectionToast()
            }
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        val navController = findNavController(R.id.fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.teamsFragment,
                R.id.gamesFragment,
                R.id.settingsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("restore")) {
                showActivityComponent()
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val languageString = getString(R.string.language_key)
        val darkModeString = getString(R.string.dark_mode)
        when (key) {
            languageString -> {
                key.let {
                    sharedPreferences?.let { pref ->
                        val languageValues = resources.getStringArray(R.array.language_values)
                        when (pref.getString(languageString, languageValues[0])) {
                            languageValues[0] -> {
                                setLocale("en")
                                recreate()
                            }
                            languageValues[1] -> {
                                setLocale("ru")
                                recreate()
                            }
                        }
                    }
                }
            }
            darkModeString -> {
                key.let {
                    if (it == darkModeString) sharedPreferences?.let { pref ->
                        val darkModeValues = resources.getStringArray(R.array.dark_mode_values)
                        when (pref.getString(darkModeString, darkModeValues[0])) {
                            darkModeValues[0] -> AppCompatDelegate.setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_NO
                            )
                            darkModeValues[1] -> AppCompatDelegate.setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_YES
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkLanguage() {
        val languageValues = resources.getStringArray(R.array.language_values)

        when (PreferenceManager.getDefaultSharedPreferences(this)
            .getString(getString(R.string.language_key), languageValues[0])) {
            languageValues[0] -> {
                setLocale("en")
            }
            languageValues[1] -> {
                setLocale("ru")
            }
        }
    }

    private fun checkTheme() {
        val darkModeValues = resources.getStringArray(R.array.dark_mode_values)

        when (PreferenceManager.getDefaultSharedPreferences(this)
            .getString(getString(R.string.dark_mode), "MODE_NIGHT_YES")) {
            darkModeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            darkModeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun showActivityComponent() {
        toolbar.show()
        bottom_nav_menu?.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("restore", true)
    }

    private fun setLocale(lang: String) {
        val myLocale = Locale(lang)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }
}