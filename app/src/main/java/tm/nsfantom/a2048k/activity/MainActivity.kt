package tm.nsfantom.a2048k.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tm.nsfantom.a2048k.fragment.MainFragment
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.fragment.GameFragment
import tm.nsfantom.a2048k.fragment.GameSettingsFragment
import tm.nsfantom.a2048k.fragment.GameStatsFragment

class MainActivity : AppCompatActivity(), MainFragment.Listener, GameFragment.Listener, GameSettingsFragment.Listener, GameStatsFragment.Listener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, MainFragment.newInstance())
                    .commit()
        }
    }

    override fun onStartClicked() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(android.R.id.content, GameFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun onSettingsClicked() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(android.R.id.content, GameSettingsFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun onStatsClicked() {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(android.R.id.content, GameStatsFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun onBackToMain() {
        onBackPressed()
    }
}
