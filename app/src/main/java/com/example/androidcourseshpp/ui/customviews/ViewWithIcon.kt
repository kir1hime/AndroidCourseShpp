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
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.toBitmap
import com.example.androidcourseshpp.R
import kotlin.math.abs
import kotlin.math.max


@SuppressLint("ResourceType")
class ViewWithIcon @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attributesSet, defStyleAttr, defStyleRes) {

    private var icon: Bitmap? = null

    private var iconWidth = DEFAULT_FLOAT_VALUE
    private var iconHeight = DEFAULT_FLOAT_VALUE
    private var iconColor: Int = DEFAULT_INT_VALUE

    private var iconPaddingStart = DEFAULT_FLOAT_VALUE
    private var iconPaddingEnd = DEFAULT_FLOAT_VALUE
    private var iconPaddingTop = DEFAULT_FLOAT_VALUE
    private var iconPaddingBottom = DEFAULT_FLOAT_VALUE

    private var textPaddingStart = DEFAULT_FLOAT_VALUE
    private var textPaddingEnd = DEFAULT_FLOAT_VALUE
    private var textPaddingTop = DEFAULT_FLOAT_VALUE
    private var textPaddingBottom = DEFAULT_FLOAT_VALUE

    private var textOriginX = DEFAULT_FLOAT_VALUE
    private var textOriginY = DEFAULT_FLOAT_VALUE

    private var iconOriginX = DEFAULT_FLOAT_VALUE
    private var iconOriginY = DEFAULT_FLOAT_VALUE

    private var text = ""

    private var textSize = DEFAULT_FLOAT_VALUE
    private var textStyle: Int = DEFAULT_INT_VALUE
    private lateinit var textFontFamily: Any
    private var textColor: Int = DEFAULT_INT_VALUE
    private var textLetterSpacing = DEFAULT_FLOAT_VALUE


    private val textWidth by lazy {
        textPaint.measureText(text)
    }
    private val textHeight by lazy {
        (abs(textMetrics.ascent) + textMetrics.descent)
    }

    private val textMetrics by lazy {
        textPaint.fontMetrics
    }

    private val iconPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            if (iconColor != DEFAULT_INT_VALUE) {
                colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
            }
        }
    }
    private val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
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
    private lateinit var iconSpace: RectF

    init {
        if (attributesSet != null) {
            initAttributes(attributesSet, defStyleAttr, defStyleRes)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, textOriginX, textOriginY, textPaint)

        icon?.let { canvas.drawBitmap(it, null, iconSpace, iconPaint) }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
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

        context.withStyledAttributes(attributesSet, attributes) {

            textSize = getDimension(0, DEFAULT_FLOAT_VALUE)
            textColor = getColor(1, DEFAULT_INT_VALUE)

            val textFontFamilyRes = getResourceId(2, DEFAULT_INT_VALUE)
            textFontFamily =
                if (textFontFamilyRes != DEFAULT_INT_VALUE) textFontFamilyRes
                else getString(2).toString()

            textLetterSpacing = getFloat(3, DEFAULT_FLOAT_VALUE)

        }

        context.withStyledAttributes(attributesSet, intArrayOf(android.R.attr.textStyle)) {
            textStyle = getInt(0, DEFAULT_INT_VALUE)
        }

        context.withStyledAttributes(attributesSet, intArrayOf(android.R.attr.text)) {
            text = getString(0) ?: ""
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun initCustomAttributes(
        attributesSet: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.withStyledAttributes(
            attributesSet,
            R.styleable.ButtonWithIcon,
            defStyleAttr,
            defStyleRes
        ) {

            icon = getDrawable(R.styleable.ButtonWithIcon_icon)?.toBitmap()

            icon?.let {
                iconWidth =
                    getDimension(R.styleable.ButtonWithIcon_iconWidth, it.width.toFloat())
                iconHeight =
                    getDimension(R.styleable.ButtonWithIcon_iconHeight, it.width.toFloat())
            }
            iconColor =
                getColor(R.styleable.ButtonWithIcon_iconColor, DEFAULT_INT_VALUE)

            iconPaddingStart = getDimension(
                R.styleable.ButtonWithIcon_iconPaddingStart, DEFAULT_FLOAT_VALUE
            )

            iconPaddingEnd = getDimension(
                R.styleable.ButtonWithIcon_iconPaddingEnd, DEFAULT_FLOAT_VALUE
            )

            iconPaddingTop = getDimension(
                R.styleable.ButtonWithIcon_iconPaddingTop, DEFAULT_FLOAT_VALUE
            )
            iconPaddingBottom = getDimension(
                R.styleable.ButtonWithIcon_iconPaddingBottom, DEFAULT_FLOAT_VALUE
            )

            textPaddingStart = getDimension(
                R.styleable.ButtonWithIcon_textPaddingStart, DEFAULT_FLOAT_VALUE
            )

            textPaddingEnd = getDimension(
                R.styleable.ButtonWithIcon_textPaddingEnd, DEFAULT_FLOAT_VALUE
            )

            textPaddingTop = getDimension(
                R.styleable.ButtonWithIcon_textPaddingTop, DEFAULT_FLOAT_VALUE
            )

            textPaddingBottom = getDimension(
                R.styleable.ButtonWithIcon_textPaddingBottom, DEFAULT_FLOAT_VALUE
            )

        }
    }

    companion object {
        const val DEFAULT_FLOAT_VALUE = -1f
        const val DEFAULT_INT_VALUE = -1
    }
}