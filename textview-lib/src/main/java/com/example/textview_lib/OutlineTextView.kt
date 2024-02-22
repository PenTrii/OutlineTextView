package com.example.textview_lib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class OutlineTextView: AppCompatTextView {
    private var strokeWidth = 0f
    private var strokeColor: Int = ContextCompat.getColor(context, R.color.black)
    private var strokeJoin = Paint.Join.ROUND
    private var strokeMiter = 0f

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.OutlineTextView, 0, 0)
            if (a.hasValue(R.styleable.OutlineTextView_strokeColor)) {
                val strokeWidth = a.getDimensionPixelSize(R.styleable.OutlineTextView_strokeWidth, R.dimen.stroke_outline_view).toFloat()
                val strokeColor = a.getColor(R.styleable.OutlineTextView_strokeColor, this.strokeColor)
                val strokeMiter = a.getDimensionPixelSize(R.styleable.OutlineTextView_strokeMiter, 10).toFloat()
                var strokeJoin = this.strokeJoin
                when (a.getInt(R.styleable.OutlineTextView_strokeJoinStyle, 0)) {
                    0 -> strokeJoin = Paint.Join.MITER
                    1 -> strokeJoin = Paint.Join.BEVEL
                    2 -> strokeJoin = Paint.Join.ROUND
                }
                setStroke(strokeWidth, strokeColor, strokeJoin, strokeMiter)
            }
        }
    }

    private fun setStroke(width: Float, color: Int, join: Paint.Join, miter: Float) {
        strokeWidth = width
        strokeColor = color
        strokeJoin = join
        strokeMiter = miter
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val restoreColor = this.currentTextColor
        val paint = this.paint
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = strokeJoin
        paint.strokeMiter = strokeMiter
        this.setTextColor(strokeColor)
        paint.strokeWidth = strokeWidth
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        this.setTextColor(restoreColor)
    }
}