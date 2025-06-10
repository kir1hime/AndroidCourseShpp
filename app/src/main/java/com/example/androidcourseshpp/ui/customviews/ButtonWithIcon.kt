package com.example.androidcourseshpp.ui.customviews


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toColor
import com.example.androidcourseshpp.R
import kotlin.math.abs
import kotlin.properties.Delegates

class ButtonWithIcon(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
) : AppCompatButton(context, attributesSet, defStyleAttr) {

    constructor(context: Context, attributesSet: AttributeSet?) : this(context, attributesSet, 0)
    constructor(context: Context) : this(context, null)

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

    init {
        if (attributesSet != null) {
            initAttributes(attributesSet, defStyleAttr)
        }
        initPaints()
    }

    private fun initPaints(){
        iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        if (iconColor != -1){
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
        if (priorityPadding == 0f && oppositePadding == 0f) {
            return originWithoutUserPaddings
        } else if (priorityPadding != 0f && oppositePadding == 0f) {
            return originWithPriorityPadding
        } else if (priorityPadding == 0f) {
            return originWithOppositePadding
        } else {
            return originWithAllPaddings
        }
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text.toString(), textOriginX, textOriginY, paint)

        icon?.let {
            val rect =
                RectF(iconOriginX, iconOriginY, iconOriginX + iconWidth, iconOriginY + iconHeight)
            canvas.drawBitmap(it, null, rect, iconPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        defineTextXOrigin()
        defineTextYOrigin()

        defineIconXOrigin()
        defineIconYOrigin()
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

    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int) {
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
            iconColor = typedArray.getColor(R.styleable.ButtonWithIcon_iconColor, -1)
        }

        iconPaddingStart = typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingStart, 0f)
        iconPaddingEnd = typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingEnd, 0f)
        iconPaddingTop = typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingTop, 0f)
        iconPaddingBottom =
            typedArray.getDimension(R.styleable.ButtonWithIcon_iconPaddingBottom, 0f)

        textPaddingStart = typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingStart, 0f)
        textPaddingEnd = typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingEnd, 0f)
        textPaddingTop = typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingTop, 0f)
        textPaddingBottom =
            typedArray.getDimension(R.styleable.ButtonWithIcon_textPaddingBottom, 0f)

        typedArray.recycle()
    }
}