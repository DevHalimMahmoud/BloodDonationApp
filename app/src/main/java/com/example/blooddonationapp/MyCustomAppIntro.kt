package com.example.blooddonationapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.blooddonationapp.Activitys.LoginActivity
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class MyCustomAppIntro : AppIntro2() {

    private val currentUser = FirebaseAuthSingleton.instance!!.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make sure you don't call setContentView!
        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment

        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.welcome),
                getString(R.string.create_account),
                imageDrawable = R.drawable.ic_undraw_mobile_login,
                backgroundDrawable = R.drawable.back_slide1,

                )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.platform),
                description = getString(R.string.available_android_web),
                imageDrawable = R.drawable.ic_undraw_devices,
                backgroundDrawable = R.drawable.back_slide2,
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.many_org),
                description = getString(R.string.you_can_choose),
                imageDrawable = R.drawable.ic_undraw_choose,
                backgroundDrawable = R.drawable.back_slide3,

                )
        )

        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.trusted_org),
                description = getString(R.string.centers_managed),
                imageDrawable = R.drawable.ic_undraw_security,
                backgroundDrawable = R.drawable.back_slide4,

                )
        )
        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.req_statues),
                getString(R.string.view_requests),
                imageDrawable = R.drawable.ic_undraw_confirmed,
                backgroundDrawable = R.drawable.back_slide5,

                )
        )
        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.navigation),
                getString(R.string.can_navigate),
                imageDrawable = R.drawable.ic_undraw_navigation,
                backgroundDrawable = R.drawable.back_slide6,

                )
        )

        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.you_matters),
                getString(R.string.save_people),
                imageDrawable = R.drawable.ic_undraw_injured_9757,
                backgroundDrawable = R.drawable.back_slide8,

                )
        )

        addSlide(
            AppIntroFragment.newInstance(
                getString(R.string.professional),
                getString(R.string.top_doc),
                imageDrawable = R.drawable.ic_undraw_medicine_b1ol,
                backgroundDrawable = R.drawable.back_slide7,

                )
        )

        setImmersiveMode()

        setTransformer(
            AppIntroPageTransformerType.Parallax(
                titleParallaxFactor = 1.0,
                imageParallaxFactor = -1.0,
                descriptionParallaxFactor = 2.0
            )
        )
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        checkUserStatues();


        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"

        checkUserStatues()

        finish()
    }

    private fun checkUserStatues() {
        //no user logged in
        if (currentUser == null) {
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
            finish()
        } else {
            //user exists
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }


}
