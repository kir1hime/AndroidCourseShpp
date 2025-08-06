package com.example.androidcourseshpp.ui.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.androidcourseshpp.R
import kotlin.math.abs
import kotlin.math.max
import kotlin.properties.Delegates


@SuppressLint("ResourceType")
class ViewWithIcon @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attributesSet, defStyleAttr, defStyleRes) {

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

    private var text by Delegates.notNull<String>()
    private var textSize by Delegates.notNull<Float>()
    private var textStyle by Delegates.notNull<Int>()
    private lateinit var textFontFamily: Any
    private var textColor by Delegates.notNull<Int>()
    private var textLetterSpacing by Delegates.notNull<Float>()


    private val textWidth by lazy {
        textPaint.measureText(text)
    }
    private val textHeight by lazy {
        (abs(textMetrics.ascent) + textMetrics.descent)
    }

    private val textMetrics by lazy {
        textPaint.fontMetrics
    }

    private lateinit var iconPaint: Paint
    private lateinit var textPaint: Paint

    private lateinit var iconSpace: RectF

    init {
        if (attributesSet != null) {
            initAttributes(attributesSet, defStyleAttr, defStyleRes)
        } else {
            initAttributesByDefault()
        }
        initPaints()
    }

    private fun initPaints() {
        initIconPaint()
        initTextPaint()
    }

    private fun initIconPaint(){
        iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        if (iconColor != DEFAULT_INT_VALUE) {
            iconPaint.colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
        }
    }

    private fun initTextPaint(){
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = this@ViewWithIcon.textSize
            color = this@ViewWithIcon.textColor
            letterSpacing = textLetterSpacing

            typeface = if (textFontFamily is Int) {
                Typeface.create(
                    ResourcesCompat.getFont(context, (textFontFamily as Int)),
                    textStyle
                )
            } else {
                Typeface.create(textFontFamily.toString(), Typeface.NORMAL)
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, textOriginX, textOriginY, textPaint)

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val paddingStart = max(iconPaddingStart, textPaddingStart)
        val paddingEnd = max(iconPaddingEnd, textPaddingEnd)
        val paddingTop = max(iconPaddingTop, textPaddingTop)
        val paddingBottom = max(iconPaddingBottom, textPaddingBottom)

        val maxContentWidth = max(iconWidth, textWidth)
        val maxContentHeight = max(iconHeight, textHeight)

        val desiredWith = maxContentWidth + paddingStart + paddingEnd
        val desiredHeight = maxContentHeight + paddingTop + paddingBottom

        setMeasuredDimension(
            resolveSize(desiredWith.toInt(), widthMeasureSpec),
            resolveSize(desiredHeight.toInt(), heightMeasureSpec)
        )
    }


    private fun createIconSpace() {
        icon?.let {
            iconSpace =
                RectF(iconOriginX, iconOriginY, iconOriginX + iconWidth, iconOriginY + iconHeight)
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

    private fun getOriginCoordinate(
        priorityPadding: Float,
        oppositePadding: Float,
        originWithoutUserPaddings: Float,
        originWithPriorityPadding: Float,
        originWithOppositePadding: Float,
        originWithAllPaddings: Float
    ): Float {
        return if (priorityPadding == DEFAULT_FLOAT_VALUE && oppositePadding == DEFAULT_FLOAT_VALUE) {
            originWithoutUserPaddings
        } else if (priorityPadding != DEFAULT_FLOAT_VALUE && oppositePadding == DEFAULT_FLOAT_VALUE) {
            originWithPriorityPadding
        } else if (priorityPadding == DEFAULT_FLOAT_VALUE) {
            originWithOppositePadding
        } else {
            originWithAllPaddings
        }
    }


    private fun initAttributes(attributesSet: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        initCustomAttributes(attributesSet, defStyleAttr, defStyleRes)
        initAndroidAttributes(attributesSet)
    }

    private fun initAndroidAttributes(attributesSet: AttributeSet) {
        val attributes = intArrayOf(
            android.R.attr.textSize,
            android.R.attr.textColor,
            android.R.attr.fontFamily,
            android.R.attr.letterSpacing,
        )

        val typedArray = context.obtainStyledAttributes(attributesSet, attributes)

        textSize = typedArray.getDimension(0, DEFAULT_FLOAT_VALUE)
        textColor = typedArray.getColor(1, DEFAULT_INT_VALUE)

        val textFontFamilyRes = typedArray.getResourceId(2, DEFAULT_INT_VALUE)
        textFontFamily =
            if (textFontFamilyRes != -1) textFontFamilyRes
            else typedArray.getString(2).toString()

        textLetterSpacing = typedArray.getFloat(3, DEFAULT_FLOAT_VALUE)

        typedArray.recycle()

        val styleAttr =
            context.obtainStyledAttributes(attributesSet, intArrayOf(android.R.attr.textStyle))
        textStyle = styleAttr.getInt(0, DEFAULT_INT_VALUE)
        styleAttr.recycle()

        val textAttr =
            context.obtainStyledAttributes(attributesSet, intArrayOf(android.R.attr.text))
        text = textAttr.getString(0) ?: ""
        textAttr.recycle()
    }

    @SuppressLint("CustomViewStyleable")
    private fun initCustomAttributes(
        attributesSet: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val typedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ButtonWithIcon,
            defStyleAttr,
            defStyleRes
        )

        icon = typedArray.getDrawable(R.styleable.ButtonWithIcon_icon)?.toBitmap()

        icon?.let {
            iconWidth =
                typedArray.getDimension(R.styleable.ButtonWithIcon_iconWidth, it.width.toFloat())
            iconHeight =
                typedArray.getDimension(R.styleable.ButtonWithIcon_iconHeight, it.width.toFloat())
        }
        iconColor =
            typedArray.getColor(R.styleable.ButtonWithIcon_iconColor, DEFAULT_INT_VALUE)

        iconPaddingStart = typedArray.getDimension(
            R.styleable.ButtonWithIcon_iconPaddingStart, DEFAULT_FLOAT_VALUE
        )

        iconPaddingEnd = typedArray.getDimension(
            R.styleable.ButtonWithIcon_iconPaddingEnd, DEFAULT_FLOAT_VALUE
        )

        iconPaddingTop = typedArray.getDimension(
            R.styleable.ButtonWithIcon_iconPaddingTop, DEFAULT_FLOAT_VALUE
        )
        iconPaddingBottom = typedArray.getDimension(
            R.styleable.ButtonWithIcon_iconPaddingBottom, DEFAULT_FLOAT_VALUE
        )

        textPaddingStart = typedArray.getDimension(
            R.styleable.ButtonWithIcon_textPaddingStart, DEFAULT_FLOAT_VALUE
        )

        textPaddingEnd = typedArray.getDimension(
            R.styleable.ButtonWithIcon_textPaddingEnd, DEFAULT_FLOAT_VALUE
        )

        textPaddingTop = typedArray.getDimension(
            R.styleable.ButtonWithIcon_textPaddingTop, DEFAULT_FLOAT_VALUE
        )

        textPaddingBottom = typedArray.getDimension(
            R.styleable.ButtonWithIcon_textPaddingBottom, DEFAULT_FLOAT_VALUE
        )

        typedArray.recycle()
    }

    private fun initAttributesByDefault() {
        iconWidth = DEFAULT_FLOAT_VALUE
        iconHeight = DEFAULT_FLOAT_VALUE

        iconColor = DEFAULT_INT_VALUE

        iconPaddingStart = DEFAULT_FLOAT_VALUE
        iconPaddingEnd = DEFAULT_FLOAT_VALUE
        iconPaddingTop = DEFAULT_FLOAT_VALUE
        iconPaddingBottom = DEFAULT_FLOAT_VALUE

        textPaddingStart = DEFAULT_FLOAT_VALUE
        textPaddingEnd = DEFAULT_FLOAT_VALUE
        textPaddingTop = DEFAULT_FLOAT_VALUE
        textPaddingBottom = DEFAULT_FLOAT_VALUE
    }

    companion object {
        const val DEFAULT_FLOAT_VALUE = -1f
        const val DEFAULT_INT_VALUE = -1
    }
}