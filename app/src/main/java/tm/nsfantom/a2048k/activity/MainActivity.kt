package tm.nsfantom.a2048k.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tm.nsfantom.a2048k.fragment.MainFragment
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.fragment.GameFragment

class MainActivity : AppCompatActivity(), MainFragment.Listener, GameFragment.Listener {


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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatsClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackToMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
