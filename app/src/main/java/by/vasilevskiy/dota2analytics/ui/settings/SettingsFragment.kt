package by.vasilevskiy.dota2analytics.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.utils.show
import kotlinx.android.synthetic.main.main_activity.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}