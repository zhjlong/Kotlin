package com.zjl.rengwuxian.kotlindemo.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.zjl.rengwuxian.kotlindemo.R
import com.zjl.rengwuxian.kotlindemo.utils.Utils

/**
 * created by zhujianlong on 2019-07-18.
 */
class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    companion object {
        val TEXT_SIZE = Utils.dp2px(12f)
        val TEXT_MARGIN = Utils.dp2px(8f)
        val VERTICAL_OFFSET = Utils.dp2px(38f)
        val VERTICAL_OFFSET_EXTRA = Utils.dp2px(16f)
        val HORIZONTAL_OFFSET = Utils.dp2px(5f)
    }

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var backgroundPadding: Rect = Rect()
    private var floatingShown = false
    private var useFloatingLabel = true
    private var floatingFraction = 0f
    private var animator: ObjectAnimator? = null

    init {
        paint.style = Paint.Style.FILL
        paint.textSize = TEXT_SIZE
        paint.color = ContextCompat.getColor(context, R.color.colorHint)

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel = typeArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true) //todo: index: MaterialEditText_useFloatingLabel
        typeArray.recycle()

        background.getPadding(backgroundPadding)
        setPadding(
            backgroundPadding.left,
            if (useFloatingLabel) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN).toInt() else backgroundPadding.top,
            backgroundPadding.right,
            backgroundPadding.bottom
        )
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                when {
                    floatingShown && TextUtils.isEmpty(s) -> getAnimator().reverse()
                    !floatingShown && !TextUtils.isEmpty(s) -> getAnimator().start()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun getAnimator(): ObjectAnimator {
        floatingShown = !floatingShown
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this@MaterialEditText, "floatingFraction", 0f, 1f)
        }
        return animator!!
    }

    fun setFloatingFraction(floatingFraction: Float) {
        this.floatingFraction = floatingFraction
        invalidate()
    }

    fun setUseFloatingShown(useFloatingShown: Boolean) {
        if (this.useFloatingLabel != useFloatingShown) {
            this.useFloatingLabel = useFloatingShown
            background.getPadding(backgroundPadding)
            setPadding(
                backgroundPadding.left,
                if (useFloatingShown) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN).toInt() else backgroundPadding.top,
                backgroundPadding.right,
                backgroundPadding.bottom
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (useFloatingLabel) {
            paint.alpha = (floatingFraction * 0xff).toInt()
            canvas?.drawText(hint.toString(), HORIZONTAL_OFFSET, VERTICAL_OFFSET - VERTICAL_OFFSET_EXTRA * floatingFraction, paint)
        }
    }
}