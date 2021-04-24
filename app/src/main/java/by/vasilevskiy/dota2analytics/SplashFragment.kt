package by.vasilevskiy.dota2analytics

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Property
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var rootView: View

    // View Elements
    private lateinit var icon: ImageView
    private lateinit var title: TextView

    // Animation Value
    private val animDuration = 500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false)

        icon = rootView.findViewById(R.id.img_splash_icon)
        title = rootView.findViewById(R.id.tv_splash_title)

        icon.alpha = 0f
        title.alpha = 0f

        icon.translationY = 50f
        icon.translationY = 50f

        startAnimation()

        return rootView
    }

    private fun startAnimation() {
        val imageSet = AnimatorSet().apply {
            play(createObjectAnimator(icon, View.ALPHA, 1f))
                .with(createObjectAnimator(icon, View.TRANSLATION_Y, 0f))
        }

        val textSet = AnimatorSet().apply {
            play(createObjectAnimator(title, View.ALPHA, 1f))
                .with(createObjectAnimator(title, View.TRANSLATION_Y, 0f))
        }

        val animSet = AnimatorSet().apply {
            play(imageSet).before(textSet)
            start()
        }

        animSet.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                Thread(Runnable {
                    Thread.sleep(500)
                    CoroutineScope(Dispatchers.Main).launch {
                        findNavController().navigate(R.id.action_splashFragment_to_gamesFragment, null,
                            NavOptions.Builder()
                                .setPopUpTo(R.id.splashFragment,
                                    true).build())
                    }
                }).start()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })
    }

    private fun createObjectAnimator(
        view: View,
        type: Property<View, Float>,
        value: Float,
        delay: Long = 1000
    ): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(view, type, value).apply {
            startDelay = delay
            duration = animDuration
            interpolator = FastOutSlowInInterpolator()

        }
        return animator
    }

}