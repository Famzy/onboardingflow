package com.nowocode.lib

import android.app.Activity
import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.util.Log
import android.widget.FrameLayout
import com.nowocode.lib.ui.model.OnboardingAction
import com.nowocode.lib.ui.OnboardingScaffold

internal class OnboardingManagerImpl(context: Context) : OnboardingManager {
    private var onBoardingView: OnboardingScaffold = OnboardingScaffold(context)
    private lateinit var activity: Activity

    override fun setActivity(activity: Activity): OnboardingManager {
        this.activity = activity
        return this
    }

    override fun setFadeIn(
        durationInMs: Long,
        fromAlpha: Float,
        toAlpha: Float
    ): OnboardingManager {
        onBoardingView.shouldFadeIn = true
        onBoardingView.fadeInStartAlpha = fromAlpha
        onBoardingView.fadeInStopAlpha = toAlpha
        onBoardingView.fadeInDuration = durationInMs
        return this
    }

    override fun setContinueHintText(text: String): OnboardingManager {
        onBoardingView.continueHintText = text
        return this
    }


    override fun addAction(
        action: OnboardingAction,
    ): OnboardingManager {
        onBoardingView.addOnBoardingAction(
            action
        )
        return this
    }

    override fun start() {
        if (activity.resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            Log.e(this.javaClass.name, "Landscape mode is currently not supported.")
            return
        }
        onBoardingView.initAnimator()
        activity.addContentView(
            onBoardingView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        onBoardingView.bringToFront()
        onBoardingView.invalidate()
    }

    override fun onDone(callback: () -> Unit): OnboardingManager {
        onBoardingView.onboardingDoneCallback = callback

        return this
    }

}