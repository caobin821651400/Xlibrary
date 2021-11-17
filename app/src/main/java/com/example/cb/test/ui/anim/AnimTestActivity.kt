package com.example.cb.test.ui.anim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_anim_test.*

/**
 * 属性动画练习
 */
class AnimTestActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_anim_test
    }


    override fun initEvent() {
    }


    override fun initUI() {
        setHeaderTitle("属性动画")
        button.setOnClickListener {
            translation()
        }

        //
        iv_image.setOnClickListener {
            toast("图片点击了")
        }
    }

    /**
     * 渐变
     */
    private fun alphaAnim() {
        ObjectAnimator.ofFloat(iv_image, "alpha", 1f, 0f, 1f, 0.5f).setDuration(2000).start()
    }

    /**
     * 缩放
     */
    private fun scaleAnim() {
//        ObjectAnimator.ofFloat(iv_image,"scaleX",1f,0f,1f,0.5f).setDuration(2000).start()
        ObjectAnimator.ofFloat(iv_image, "scaleY", 1f, 0f, 1f, 0.5f).setDuration(2000).start()
    }

    /**
     * 平移
     */
    private fun translation() {
        ObjectAnimator.ofFloat(iv_image, "translationX", 0f, 400f).setDuration(2000).start()
        ObjectAnimator.ofFloat(iv_image, "translationY", 0f, 400f).setDuration(2000).start()
    }

    /**
     * 旋转
     */
    private fun ratationAnim() {
        iv_image.pivotX = iv_image.width / 2f
        iv_image.pivotY = iv_image.height / 2f
        ObjectAnimator.ofFloat(iv_image, "rotation", 0f, 360f).setDuration(2000).start()
    }

    /**
     * 动画集合
     */
    private fun ainmList() {
        var set = AnimatorSet()
        var anim1 = ObjectAnimator.ofFloat(iv_image, "translationX", 0f, 400f)
        var anim2 = ObjectAnimator.ofFloat(iv_image, "translationY", 0f, 400f)
        var anim3 = ObjectAnimator.ofFloat(iv_image, "rotation", 0f, 360f)
        var anim4 = ObjectAnimator.ofFloat(iv_image, "alpha", 1f, 0f, 1f)
        set.playSequentially(anim1, anim2, anim3, anim4)
        var overshootInterpolator = DecelerateInterpolator()
        set.interpolator = overshootInterpolator
        set.duration = 2000
        set.start()

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(animation: Animator?) {
                XLogUtils.d("onAnimationCancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                XLogUtils.d("onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                XLogUtils.d("onAnimationEnd")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                XLogUtils.d("onAnimationRepeat")
            }
        })
    }

    /**
     *valueAnim
     */
    private fun valueAnim() {
        var anim = ValueAnimator.ofInt(0, 400)
        anim.duration = 4000
        anim.start()

        anim.addUpdateListener { animation ->
            var value: Int = animation!!.animatedValue as Int
            iv_image.layout(value, value, value + iv_image.width, value + iv_image.height)
        }
    }
}
