package com.example.androidcourseshpp.ui.customviews


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.Rect
import android.graphics.RectF

import android.util.AttributeSet
import android.util.Log

import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toBitmap
import com.example.androidcourseshpp.R
import kotlin.math.abs
import kotlin.properties.Delegates

class ButtonWithIcon(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
) : AppCompatButton(context, attributesSet, defStyleAttr) {

    private var icon: Bitmap? = null

    private var iconWidth by Delegates.notNull<Float>()
    private var iconHeight by Delegates.notNull<Float>()

    private var iconPaddingStart by Delegates.notNull<Float>()
    private var iconPaddingEnd by Delegates.notNull<Float>()
    private var iconPaddingTop by Delegates.notNull<Float>()
    private var iconPaddingBottom by Delegates.notNull<Float>()

    private var textPaddingStart by Delegates.notNull<Float>()
    private var textPaddingEnd by Delegates.notNull<Float>()
    private var textPaddingTop by Delegates.notNull<Float>()
    private var textPaddingBottom by Delegates.notNull<Float>()


    private lateinit var textPaint: Paint
    private lateinit var textMetrics: FontMetrics

    private var textOriginX by Delegates.notNull<Float>()
    private var textOriginY by Delegates.notNull<Float>()

    private var iconOriginX by Delegates.notNull<Float>()
    private var iconOriginY by Delegates.notNull<Float>()

    private val textWidth by lazy {
        textPaint.measureText(text.toString())
    }
    private val textHeight by lazy {
        (abs(textMetrics.ascent) + textMetrics.descent)
    }


    constructor(context: Context, attributesSet: AttributeSet?) : this(context, attributesSet, 0)
    constructor(context: Context) : this(context, null)


    init {

        if (attributesSet != null) {
            initAttributes(attributesSet, defStyleAttr)
        }

        initPaints()


    }


    private fun defineTextOriginX() {
        if (textPaddingStart == 0f && textPaddingEnd == 0f) {
            textOriginX = width / 2f - textWidth / 2f

        } else if (textPaddingStart != 0f && textPaddingEnd == 0f) {
            textOriginX = textPaddingStart

        } else if (textPaddingStart == 0f) {
            textOriginX = width - textWidth - textPaddingEnd

        } else {
            val contentSpaceByX = width - textPaddingEnd - textPaddingStart
            textOriginX = textPaddingStart + contentSpaceByX / 2f - textWidth / 2f
        }
    }


    private fun defineTextOriginY() {
        if (textPaddingTop == 0f && textPaddingBottom == 0f) {
            textOriginY = height / 2f + textHeight / 4f

        } else if (textPaddingTop != 0f && textPaddingBottom == 0f) {
            textOriginY = textPaddingTop + textMetrics.ascent

        } else if (textPaddingTop == 0f) {
            textOriginY = height - textHeight - textPaddingBottom

        } else {
            val contentSpaceByY = height - textPaddingBottom - textPaddingTop
            textOriginY = textPaddingTop + contentSpaceByY / 2f + textHeight / 4f
        }
    }

    private fun defineIconOriginX() {
        if (iconPaddingStart == 0f && iconPaddingEnd == 0f) {
            iconOriginX = width / 3f
        } else if (textPaddingStart != 0f && iconPaddingEnd == 0f) {
            iconOriginX = iconPaddingStart
        } else if (iconPaddingStart == 0f) {
            iconOriginX = width - iconWidth - iconPaddingEnd
        } else {
            val contentSpaceByX = width - iconPaddingStart - iconPaddingEnd
            iconOriginX = iconPaddingStart + contentSpaceByX / 2f - iconWidth / 2f
        }
    }
    private fun defineIconOriginY(){
        if (iconPaddingTop == 0f && iconPaddingBottom == 0f) {
            iconOriginY = height / 2f - iconHeight/2f

        } else if (iconPaddingTop != 0f && iconPaddingBottom == 0f) {
            iconOriginY = iconPaddingTop

        } else if (iconPaddingTop == 0f) {
            iconOriginY = height - iconHeight - iconPaddingTop

        } else {
            val contentSpaceByY = height - iconPaddingBottom - iconPaddingTop
            iconOriginY = iconPaddingTop + contentSpaceByY / 2f + iconHeight / 2f
        }
    }


    private fun initPaints() {
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.typeface = typeface
        textPaint.textSize = textSize
        textPaint.color = currentTextColor
        textPaint.letterSpacing = letterSpacing

        textMetrics = textPaint.fontMetrics

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

        canvas.drawText(text.toString(), textOriginX, textOriginY, textPaint)

        val rect = icon?.let {
            val iconWidth = if (iconWidth != 0f) iconWidth else it.width.toFloat()
            val iconHeight = if (iconHeight != 0f) iconHeight else it.width.toFloat()

            RectF(iconOriginX, iconOriginY, iconOriginX + iconWidth, iconOriginY + iconHeight)
        }
            ?: RectF(0f, 0f, 0f, 0f)

        icon?.let {
            canvas.drawBitmap(it, null, rect, null)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        defineTextOriginX()
        defineTextOriginY()

        defineIconOriginX()
        defineIconOriginY()

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