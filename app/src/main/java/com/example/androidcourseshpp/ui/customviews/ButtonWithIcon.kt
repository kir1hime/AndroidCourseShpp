package com.example.androidcourseshpp.ui.customviews


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.Rect
import android.os.Build

import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi

import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toBitmap
import com.example.androidcourseshpp.R
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
            textOriginX = width / 2f - textPaint.measureText(text.toString()) / 2f
        } else if (textPaddingStart != 0f && textPaddingEnd == 0f){
            textOriginX = textPaddingStart
        } else if (textPaddingStart == 0f && textPaddingEnd != 0f){
            textOriginX = width - textPaint.measureText(text.toString()) - textPaddingEnd
        } else {
            val contentLeft = textPaddingStart
            val contentRight = width - textPaddingEnd
            val contentWidth = contentRight - contentLeft
            textOriginX = contentLeft + contentWidth / 2f - textPaint.measureText(text.toString()) / 2f
        }
    }

    private fun defineTextOriginY() {
        if (textPaddingTop == 0f && textPaddingBottom == 0f) {
            textOriginY =
                height / 2f + ((textMetrics.descent - textMetrics.ascent) / 2 - textMetrics.descent)
        } else {
            textOriginY = textPaddingTop - textPaddingBottom
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

        defineTextOriginX()
        defineTextOriginY()

        canvas.drawText(text.toString(), textOriginX, textOriginY, textPaint)

        val rect = icon?.let { Rect(0, 0, it.width, it.height) } ?: Rect(0, 0, 0, 0)

        icon?.let {
            canvas.drawBitmap(it, null, rect, null)
        }


    }

    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ButtonWithIcon,
            defStyleAttr,
            0
        )

        icon = typedArray.getDrawable(R.styleable.ButtonWithIcon_icon)?.toBitmap()

        iconWidth = typedArray.getDimension(R.styleable.ButtonWithIcon_iconWidth, 0f)
        iconHeight = typedArray.getDimension(R.styleable.ButtonWithIcon_iconHeight, 0f)

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