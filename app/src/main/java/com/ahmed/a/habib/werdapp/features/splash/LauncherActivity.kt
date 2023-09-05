package   com.ahmed.a.habib.werdapp.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.ahmed.a.habib.werdapp.HomeActivity
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.databinding.ActivityLauncherBinding
import com.ahmed.a.habib.werdapp.utils.PublicKeys
import com.ahmed.a.habib.werdapp.utils.setTypeFace
import com.ahmed.a.habib.werdapp.utils.startUiAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherActivity : AppCompatActivity() {

    private var text = ""
    private lateinit var launcherModel: LauncherModel
    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher)

        init()
        delay()
    }

    private fun init() {
        launcherModel = LauncherModel()
        binding.appNameTxt.startUiAnimation(2300)
        binding.content.startUiAnimation(2300)

        text = launcherModel.getText()
        binding.content.text = text

        this.setTypeFace(listOf(binding.content, binding.appNameTxt), font = PublicKeys.fontPath)
    }

    private fun delay() {
        lifecycleScope.launch {
            delay(2800)
            navigate()
        }
    }

    private fun navigate() {
        startActivity(Intent(this, HomeActivity::class.java).putExtra(PublicKeys.title, text))
        finish()
    }
}