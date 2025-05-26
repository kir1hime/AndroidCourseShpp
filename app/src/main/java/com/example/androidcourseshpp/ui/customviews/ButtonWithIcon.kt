package com.example.androidcourseshpp.ui.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.example.androidcourseshpp.R
import kotlin.properties.Delegates

class ButtonWithIcon(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
) : AppCompatButton(context, attributesSet, defStyleAttr) {

    private var icon: Drawable? = null
    private var iconSize by Delegates.notNull<Float>()
    private var iconStartPadding by Delegates.notNull<Float>()
    private var iconEndPadding by Delegates.notNull<Float>()
    private var iconTopPadding by Delegates.notNull<Float>()
    private var iconBottomPadding by Delegates.notNull<Float>()

    constructor(context: Context, attributesSet: AttributeSet?) : this(context, attributesSet, 0)
    constructor(context: Context) : this(context, null)


    init {
        if (attributesSet != null) {
            initAttributes(attributesSet, defStyleAttr)
        }
    }

    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ButtonWithIcon,
            defStyleAttr,
            0
        )

        icon = typedArray.getDrawable(R.styleable.ButtonWithIcon_icon)
        iconSize = typedArray.getDimension(R.styleable.ButtonWithIcon_iconSize, 0f)
        iconStartPadding = typedArray.getDimension(R.styleable.ButtonWithIcon_iconStartPadding, 0f)
        iconEndPadding = typedArray.getDimension(R.styleable.ButtonWithIcon_iconEndPadding, 0f)
        iconTopPadding = typedArray.getDimension(R.styleable.ButtonWithIcon_iconTopPadding, 0f)
        iconBottomPadding = typedArray.getDimension(R.styleable.ButtonWithIcon_iconBottomPadding, 0f)

        typedArray.recycle()
    }
}