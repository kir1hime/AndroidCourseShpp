package com.example.androidcourseshpp.ui.customviews


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toBitmap
import com.example.androidcourseshpp.R
import kotlin.math.abs
import kotlin.properties.Delegates

class ButtonWithIcon @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatButton(context, attributesSet, defStyleAttr) {

    private var icon: Bitmap? = null

    private var iconWidth by Delegates.notNull<Float>()
    private var iconHeight by Delegates.notNull<Float>()
    private var iconColor by Delegates.notNull<Int>()

    private var iconPaddingStart by Delegates.notNull<Float>()
    private var iconPaddingEnd by Delegates.notNull<Float>()
    private var iconPaddingTop by Delegates.notNull<Float>()
    private var iconPaddingBottom by Delegates.notNull<Float>()

    private var textPaddingStart by Delegates.notNull<Float>()
    private var textPaddingEnd by Delegates.notNull<Float>()
    private var textPaddingTop by Delegates.notNull<Float>()
    private var textPaddingBottom by Delegates.notNull<Float>()

    private var textOriginX by Delegates.notNull<Float>()
    private var textOriginY by Delegates.notNull<Float>()

    private var iconOriginX by Delegates.notNull<Float>()
    private var iconOriginY by Delegates.notNull<Float>()

    private val textWidth by lazy {
        paint.measureText(text.toString())
    }
    private val textHeight by lazy {
        (abs(textMetrics.ascent) + textMetrics.descent)
    }

    private val textMetrics by lazy {
        paint.fontMetrics
    }

    private lateinit var iconPaint : Paint

    private lateinit var iconSpace : RectF

    init {
        if (attributesSet != null) {
            initAttributes(attributesSet, defStyleAttr)
        } else {
            initAttributesByDefault()
        }
        initPaints()
    }

    private fun initPaints(){
        iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        if (iconColor != DEFAULT_COLOR_VALUE){
           iconPaint.colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
        }
    }

    private fun getOriginCoordinate(
        priorityPadding: Float,
        oppositePadding: Float,
        originWithoutUserPaddings: Float,
        originWithPriorityPadding: Float,
        originWithOppositePadding: Float,
        originWithAllPaddings: Float
    ): Float {
        return if (priorityPadding == 0f && oppositePadding == 0f) {
            originWithoutUserPaddings
        } else if (priorityPadding != 0f && oppositePadding == 0f) {
            originWithPriorityPadding
        } else if (priorityPadding == 0f) {
            originWithOppositePadding
        } else {
            originWithAllPaddings
        }
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text.toString(), textOriginX, textOriginY, paint)

        icon?.let { canvas.drawBitmap(it, null, iconSpace, iconPaint) }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        defineTextXOrigin()
        defineTextYOrigin()

        icon?.let {
            defineIconXOrigin()
            defineIconYOrigin()
            createIconSpace()
        }
    }

    private fun createIconSpace(){
        icon?.let {
           iconSpace = RectF(iconOriginX, iconOriginY, iconOriginX + iconWidth, iconOriginY + iconHeight)
        }
    }

    private fun defineTextXOrigin() {
        val textXOriginWithoutUserPaddings = width / 2f - textWidth / 2f
        val textXOriginWithEndPadding = width - textWidth - textPaddingEnd
        val textXSpace = width - textPaddingEnd - textPaddingStart
        val textXOriginWithBothUserPaddings =
            textPaddingStart + textXSpace / 2f - textWidth / 2f

        textOriginX = getOriginCoordinate(
            textPaddingStart,
            textPaddingEnd,
            textXOriginWithoutUserPaddings,
            textPaddingStart,
            textXOriginWithEndPadding,
            textXOriginWithBothUserPaddings
        )
    }

    private fun defineTextYOrigin() {
        val textYOriginWithoutUserPaddings = height / 2f + textHeight / 4f
        val textYOriginWithTopPadding = textPaddingTop + textMetrics.ascent
        val textYOriginWithBottomPadding = height - textHeight - textPaddingBottom
        val textYSpace = height - textPaddingBottom - textPaddingTop
        val textYOriginWithBothUserPaddings =
            textPaddingTop + textYSpace / 2f + textHeight / 4f

        textOriginY = getOriginCoordinate(
            textPaddingTop,
            textPaddingBottom,
            textYOriginWithoutUserPaddings,
            textYOriginWithTopPadding,
            textYOriginWithBottomPadding,
            textYOriginWithBothUserPaddings
        )
    }

    private fun defineIconXOrigin() {
        val iconXOriginWithoutUserPaddings = width / 3f
        val iconXOriginWithEndPadding = width - iconWidth - iconPaddingEnd
        val iconXSpace = width - iconPaddingStart - iconPaddingEnd
        val iconXOriginWithBothUserPaddings =
            iconPaddingStart + iconXSpace / 2f - iconWidth / 2f

        iconOriginX = getOriginCoordinate(
            iconPaddingStart,
            iconPaddingEnd,
            iconXOriginWithoutUserPaddings,
            iconPaddingStart,
            iconXOriginWithEndPadding,
            iconXOriginWithBothUserPaddings
        )
    }

    private fun defineIconYOrigin() {
        val iconYOriginWithoutUserPaddings = height / 2f - iconHeight / 2f
        val iconYOriginWithBottomPadding = height - iconHeight - iconPaddingTop
        val iconYSpace = height - iconPaddingBottom - iconPaddingTop
        val iconYOriginWithBothUserPaddings =
            iconPaddingTop + iconYSpace / 2f + iconHeight / 2f

        iconOriginY = getOriginCoordinate(
            iconPaddingTop,
            iconPaddingBottom,
            iconYOriginWithoutUserPaddings,
            iconPaddingTop,
            iconYOriginWithBottomPadding,
            iconYOriginWithBothUserPaddings
        )
    }



    private fun initAttributes(attributesSet: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ButtonWithIcon,
            defStyleAttr,
            0
        )

        icon = typedArray.getDrawable(R.styleable.ButtonWithIcon_icon)?.toBitmap()

        icon?.let {
            iconWidth =
                typedArray.getDimension(R.styleable.ButtonWithIcon_iconWidth, it.width.toFloat())
            iconHeight =
                typedArray.getDimension(R.styleable.ButtonWithIcon_iconHeight, it.width.toFloat())
        }
        iconColor = typedArray.getColor(R.styleable.ButtonWithIcon_iconColor, DEFAULT_COLOR_VALUE)

        iconPaddingStart = typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingStart, DEFAULT_MEASURE_VALUE)
        iconPaddingEnd = typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingEnd, DEFAULT_MEASURE_VALUE)
        iconPaddingTop = typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingTop, DEFAULT_MEASURE_VALUE)
        iconPaddingBottom =
            typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingBottom, DEFAULT_MEASURE_VALUE)

        textPaddingStart = typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingStart, DEFAULT_MEASURE_VALUE)
        textPaddingEnd = typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingEnd, DEFAULT_MEASURE_VALUE)
        textPaddingTop = typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingTop, DEFAULT_MEASURE_VALUE)
        textPaddingBottom =
            typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingBottom, DEFAULT_MEASURE_VALUE)

        typedArray.recycle()
    }

    private fun initAttributesByDefault(){
        iconWidth = DEFAULT_MEASURE_VALUE
        iconHeight = DEFAULT_MEASURE_VALUE

        iconColor = DEFAULT_COLOR_VALUE

        iconPaddingStart = DEFAULT_MEASURE_VALUE
        iconPaddingEnd = DEFAULT_MEASURE_VALUE
        iconPaddingTop = DEFAULT_MEASURE_VALUE
        iconPaddingBottom = DEFAULT_MEASURE_VALUE

        textPaddingStart = DEFAULT_MEASURE_VALUE
        textPaddingEnd = DEFAULT_MEASURE_VALUE
        textPaddingTop = DEFAULT_MEASURE_VALUE
        textPaddingBottom = DEFAULT_MEASURE_VALUE
    }

    companion object {
        const val DEFAULT_MEASURE_VALUE = 0f
        const val DEFAULT_COLOR_VALUE = -1
    }
}