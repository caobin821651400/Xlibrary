package cn.sccl.xlibrary.view.loading

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Interpolator
import androidx.annotation.RequiresApi
import com.cb.xlibrary.R

/**
 * @author: caobin
 * @date: 2022/2/22 9:47
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2022/2/22 9:47
 * @updateRemark: 更新说明
 */
class MutlColorLoadingView : View {
    // 画笔
    private var mPaint: Paint? = null

    //动画进度更新器
    private lateinit var mLoadingAnimator: ValueAnimator

    //圆弧路径，被截取
    private var mArcPath: Path? = null

    // 测量Path 并截取部分的工具
    private var mMeasure: PathMeasure? = null
    private var mMeasureLength = 0f

    // 默认的动效周期 2s
    private var defaultDuration = 2000

    // 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
    private var mAnimatorValue = 0f

    //存储宽高
    private var mLayer: RectF? = null

    // 分 3个阶段,以中间颜色为基准
    private val stagemMid = 1.0f
    private val stageOne = 1.21f
    private val stageTwo = 1.42f
    private var firstColor = Color.WHITE
    private var secondColor = Color.BLUE
    private var threeColor = Color.YELLOW
    private var strokeWidth = 15f
    private var startAngle = -90
    private var rateOfFirstRound = 0.45f //第一回合所占时间比例

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttr(context, attrs)
        initAll()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initAttr(context, attrs)
        initAll()
    }

    fun initAll() {
        mLayer = RectF()
        mPaint = Paint()
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.color = Color.WHITE
        mPaint!!.strokeWidth = strokeWidth
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.isAntiAlias = true
        mArcPath = Path()
        mMeasure = PathMeasure()
        mLoadingAnimator = ValueAnimator.ofFloat(0f, 2f).setDuration(defaultDuration.toLong())
        mLoadingAnimator.setRepeatCount(Animation.INFINITE)
        mLoadingAnimator.setInterpolator(MutlColorInterpolator(rateOfFirstRound))
        mLoadingAnimator.addUpdateListener(mUpdateListener)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mLoadingAnimator!!.start()
    }

    private fun initAttr(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MutlColorLoadingView)
        defaultDuration = ta.getInteger(R.styleable.MutlColorLoadingView_mclv_duration, defaultDuration)
        firstColor = ta.getColor(R.styleable.MutlColorLoadingView_mclv_first_color, firstColor)
        secondColor = ta.getColor(R.styleable.MutlColorLoadingView_mclv_second_color, secondColor)
        threeColor = ta.getColor(R.styleable.MutlColorLoadingView_mclv_three_color, threeColor)
        rateOfFirstRound = ta.getFloat(R.styleable.MutlColorLoadingView_mclv_rate_first_round, rateOfFirstRound)
        strokeWidth = ta.getDimension(R.styleable.MutlColorLoadingView_mclv_stroke_width, strokeWidth)
        startAngle = ta.getInt(R.styleable.MutlColorLoadingView_mclv_start_angle, startAngle)
        ta.recycle()
    }

    var mUpdateListener = AnimatorUpdateListener { animation ->
        mAnimatorValue = animation.animatedValue as Float
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mArcPath!!.reset()
        val len = if (w > h) h.toFloat() else w.toFloat()
        val halfOfStrokeWidth = strokeWidth / 2.0f
        val paddingLeft = paddingLeft.toFloat()
        val paddingRight = paddingRight.toFloat()
        val paddingTop = paddingTop.toFloat()
        val paddingBottom = paddingBottom.toFloat()
        val offset: Float
        if (w > h) {
            offset = (w - h) / 2f
            mLayer!![halfOfStrokeWidth + paddingLeft + offset, halfOfStrokeWidth + paddingTop, w - halfOfStrokeWidth - paddingRight - offset] =
                h - halfOfStrokeWidth - paddingBottom
        } else {
            offset = (h - w) / 2f
            mLayer!![halfOfStrokeWidth + paddingLeft, halfOfStrokeWidth + paddingTop + offset, w - halfOfStrokeWidth - paddingRight] =
                h - halfOfStrokeWidth - paddingBottom - offset
        }
        mArcPath!!.addArc(mLayer!!, startAngle.toFloat(), 360f)
        mMeasure!!.setPath(mArcPath, true)
        mMeasureLength = mMeasure!!.length
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSearch(canvas)
    }

    private fun drawSearch(canvas: Canvas) {
        val progress = mAnimatorValue
        if (progress >= stagemMid) {
            //第二回合的进度
            val curProgress = progress - stagemMid
            if (progress < stageOne) { //1-1.3
                //画蓝色
                drawSecondSegment(canvas, curProgress)
            } else if (progress < stageTwo) { //1.3-1.6
                drawThreeSegment(canvas, curProgress)
            } else {
                //1.6-2.0
                drawFourSegment(canvas, curProgress)
            }
            //绘制第三条segment，白色 segment
            drawFirstSegment(canvas, curProgress)
        } else {
            //第一回合的进度
            mPaint!!.color = Color.WHITE
            val path = Path()
            val start = 0f
            //用了抛物线和直线的公式，区别同一时间的路程差
            mMeasure!!.getSegment(start, (1 / 2f * progress * (3 - progress) * mMeasureLength), path, true)
            canvas.drawPath(path, mPaint!!)
        }
    }

    //蓝色 黄色 位置1.42-2.0
    private fun drawFourSegment(canvas: Canvas, curProgress: Float) {
        //画蓝色
        val whiteProgress = conveterToRealProgress(curProgress, 0.6f) //蓝色 start进度 在总进度 0.6 跑完
        val blueProgress = conveterToRealProgress(curProgress, 0.7f) //蓝色 end 进度 在总进度 0.7 跑完
        mPaint!!.color = secondColor
        val start = mapRang(0.3f, blueProgress) * mMeasureLength
        val end = whiteProgress * mMeasureLength // end 和白色的进度走
        val path = Path()
        mMeasure!!.getSegment(start, end, path, true)
        canvas.drawPath(path, mPaint!!)

        //画黄色
        val yellowProgress = conveterToRealProgress(curProgress, 0.95f) //黄色 start 进度 在总进度 0.95 跑完
        mPaint!!.color = threeColor
        val start1 = mapRang(0.42f, curProgress) * mMeasureLength
        val end1 = mapRang(0.21f / 0.95f, yellowProgress) * mMeasureLength
        val path1 = Path()
        mMeasure!!.getSegment(start1, end1, path1, true)
        canvas.drawPath(path1, mPaint!!)
    }

    //蓝色 黄色 位置1.21-1.42
    private fun drawThreeSegment(canvas: Canvas, curProgress: Float) {
        //画蓝色
        val whiteProgress = conveterToRealProgress(curProgress, 0.6f) //蓝色 start进度 在总进度 0.6 跑完
        val blueProgress = conveterToRealProgress(curProgress, 0.7f) //蓝色 end 进度 在总进度 0.7 跑完
        mPaint!!.color = secondColor
        val start = mapRang(0.3f, blueProgress) * mMeasureLength
        val end = whiteProgress * mMeasureLength // end 和白色的进度走
        val path = Path()
        mMeasure!!.getSegment(start, end, path, true)
        canvas.drawPath(path, mPaint!!)

        //画黄色
        val yellowProgress = conveterToRealProgress(curProgress, 0.95f) //黄色 start 进度 在总进度 0.95 跑完
        mPaint!!.color = threeColor
        val start1 = 0f
        val end1 = mapRang(0.21f / 0.95f, yellowProgress) * mMeasureLength
        val path1 = Path()
        mMeasure!!.getSegment(start1, end1, path1, true)
        canvas.drawPath(path1, mPaint!!)
    }

    //蓝色位置 1-1.21
    private fun drawSecondSegment(canvas: Canvas, curProgress: Float) {
        //第二回合 蓝色生命周期只有 0.7倍
        var curProgress = curProgress
        curProgress = conveterToRealProgress(curProgress, 0.6f)
        mPaint!!.color = secondColor
        val start = 0f
        val end = curProgress * mMeasureLength
        val path = Path()
        mMeasure!!.getSegment(start, end, path, true)
        canvas.drawPath(path, mPaint!!)
    }

    //白色位置 1-2.0
    private fun drawFirstSegment(canvas: Canvas, curProgress: Float) {
        var curProgress = curProgress
        mPaint!!.color = firstColor
        curProgress = conveterToRealProgress(curProgress, 0.6f) // 白色loading 占第二回合的 0.6 个生命周期
        val start = curProgress * mMeasureLength
        val end = mMeasureLength
        val path = Path()
        mMeasure!!.getSegment(start, end, path, true)
        canvas.drawPath(path, mPaint!!)
    }

    fun conveterToRealProgress(progress: Float, rate: Float): Float {
        var curProgress = progress / rate
        if (curProgress > 1) {
            curProgress = 1f
        }
        return curProgress
    }

    //输入 startX-1 线性映射 0-1
    fun mapRang(startX: Float, x: Float): Float {
        val k = 1.0f / (1.0f - startX)
        return k * x + (1 - k)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mLoadingAnimator != null && mLoadingAnimator!!.isStarted) {
            mLoadingAnimator!!.cancel()
        }
    }

    /**
     * 自定义加速器，控制第一回合和总时间的比率
     */
    inner class MutlColorInterpolator(var rate: Float) : Interpolator {
        override fun getInterpolation(time: Float): Float {
            return if (time < rate) {
                time * (0.5f / rate)
            } else {
                val k = 0.5f / (1 - rate)
                k * time + (1 - k)
            }
        }
    }
    //<com.mutlcolorloadingview.MutlColorLoadingView
    //        android:id="@+id/mclv"
    //        android:layout_width="120dp"
    //        android:layout_height="105dp"
    //        android:padding="28dp"
    //        android:background="@drawable/bg_loading"
    //        app:mclv_duration="2000"
    //        app:mclv_first_color="@color/color_FFFFFFFF"
    //        app:mclv_second_color="@color/color_FF4D6BFF"
    //        app:mclv_three_color="@color/color_FFFF8400"
    //        app:mclv_stroke_width="4dp"
    //        />
    //
}