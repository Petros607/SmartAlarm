package com.example.smartalarm.colorGame.widget


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.smartalarm.R


class FlowLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    ViewGroup(context, attributeSet, defStyle) {
    // horizontal
    private var availableWidth = 0
    private var lineChildIndex: MutableList<List<Int>?>? = null
    private var lineHeightList: MutableList<Int>? = null
    private var lineWidthList: MutableList<Int>? = null
    private var currentLineWidth = 0
    private var currentLineHeight = 0
    private var maxWidth = 0
    private var currentLineChildIndex: MutableList<Int>? = null

    // vertical
    private var availableHeight = 0
    private var rowChildIndex: MutableList<List<Int>?>? = null
    private var rowWidthList: MutableList<Int>? = null
    private var rowHeightList: MutableList<Int>? = null
    private var currentRowWidth = 0
    private var currentRowHeight = 0
    private var maxHeight = 0
    private var currentRowChildIndex: MutableList<Int>? = null

    // common
    private var weightChildList: MutableList<Int>? = null
    private var totalWeight = 0f
    private var orientation = ORIENTATION_HORIZONTAL
    private var gravity = GRAVITY_NONE
    private var lineNum = LayoutParam.LINE_NUM_INVALID
    var horizontalSpacing = 0
    var verticalSpacing = 0
    // variable for efficient mode
    /**
     * flag whether child placing more efficient ,
     * if true ,less place will be used
     */
    private val efficientMode = false

    init {
        initAttr(context, attributeSet, defStyle)
    }

    private fun initAttr(context: Context, attributeSet: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout, defStyle, 0)
        try {
            orientation = a.getInt(R.styleable.FlowLayout_orientation, ORIENTATION_HORIZONTAL)
            gravity = a.getInt(R.styleable.FlowLayout_gravity, GRAVITY_NONE)
            verticalSpacing =
                a.getDimensionPixelSize(R.styleable.FlowLayout_verticalSpacing, SPACING_NONE)
            horizontalSpacing =
                a.getDimensionPixelSize(R.styleable.FlowLayout_horizontalSpacing, SPACING_NONE)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        when (orientation) {
            ORIENTATION_HORIZONTAL -> measureHorizontally(widthMeasureSpec, heightMeasureSpec)
            ORIENTATION_VERTICAL -> measureVertically(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun measureHorizontally(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        availableWidth = widthSize - paddingLeft - paddingRight
        /**
         * two kind of type should be taken into account:
         * 1. dimen = 0 && weight !=0
         * 2. MATCH_PARENT
         */
        lineChildIndex = ArrayList()
        lineHeightList = ArrayList()
        lineWidthList = ArrayList()
        weightChildList = ArrayList()
        maxWidth = 0
        lineNum = LayoutParam.LINE_NUM_INVALID
        newLine()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as LayoutParam
            val childWidthMode = MeasureSpec.EXACTLY
            var childHeightMode = MeasureSpec.EXACTLY
            var childWidthSize = lp.width
            var childHeightSize = lp.height
            val childHorizontalSpacing = getChildHorizontalSpacing(child)
            val childVerticalSpacing = getChildVerticalSpacing(child)
            if (heightMode == MeasureSpec.UNSPECIFIED && childHeightSize == 0) {
                childHeightMode = MeasureSpec.UNSPECIFIED
            }
            if (lp.lineNum != lineNum) {
                endLine(availableWidth - currentLineWidth)
                newLine()
                lineNum = lp.lineNum
            }
            if (lp.width == LayoutParams.MATCH_PARENT) {
                if (currentLineWidth + lp.leftMargin + lp.rightMargin + childHorizontalSpacing <= availableWidth) {
                    // take place all the extra spacing
                    childWidthSize =
                        (availableWidth - currentLineWidth - lp.leftMargin - lp.rightMargin
                                - childHorizontalSpacing)
                    currentLineChildIndex!!.add(i)
                    currentLineWidth = widthSize - paddingRight
                    // child with WRAP_CONTENT has been measured
                    if (lp.width != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    // set line height after measure ,in case that height is WRAP_CONTENT
                    currentLineHeight = Math.max(
                        currentLineHeight, childHeightSize + lp.topMargin + lp.bottomMargin
                                + childVerticalSpacing
                    )

                    // end last line
                    endLine(0)
                    newLine()
                } else {
                    // end last line
                    endLine(0)
                    newLine()
                    childWidthSize =
                        availableWidth - lp.leftMargin - lp.rightMargin - childHorizontalSpacing
                    currentLineChildIndex!!.add(i)
                    currentLineWidth = widthSize - paddingRight
                    // child with WRAP_CONTENT has been measured
                    if (lp.width != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    // set line height after measure ,in case that height is WRAP_CONTENT
                    currentLineHeight = Math.max(
                        currentLineHeight, childHeightSize + lp.topMargin + lp.bottomMargin
                                + childVerticalSpacing
                    )
                    endLine(0)
                    newLine()
                }
            } else if (lp.width == 0 && lp.weight != 0f) {
                // add to weight child list ,waiting for measure when whole line end ,so it will has extra
                // place to be placed
                totalWeight += lp.weight
                (weightChildList as ArrayList<Int>).add(i)
                //?????????????????????????????????????????????
                currentLineChildIndex!!.add(i)
            } else {
                if (childWidthSize == LayoutParams.WRAP_CONTENT) {
//                    child.measure(
//                        MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
//                        MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
//                    )
                    childWidthSize = child.measuredWidth
                    childHeightSize = child.measuredHeight
                }
                if ((currentLineWidth + lp.leftMargin + childWidthSize + lp.rightMargin
                            + childHorizontalSpacing) <= availableWidth
                ) {
                    // current line
                    currentLineChildIndex!!.add(i)
                    currentLineWidth += childWidthSize + lp.leftMargin + lp.rightMargin + childHorizontalSpacing
                    // child with WRAP_CONTENT has been measured
                    if (lp.width != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    // set line height after measure ,in case that height is WRAP_CONTENT
                    if (childWidthSize + lp.leftMargin + lp.rightMargin != 0) {
                        currentLineHeight = Math.max(
                            currentLineHeight, childHeightSize + lp.topMargin + lp.bottomMargin
                                    + childVerticalSpacing
                        )
                    }
                } else {
                    // end last line
                    endLine(availableWidth - currentLineWidth)
                    newLine()
                    currentLineChildIndex!!.add(i)
                    currentLineWidth =
                        childWidthSize + lp.leftMargin + lp.rightMargin + childHorizontalSpacing
                    // child with WRAP_CONTENT has been measured
                    if (lp.width != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    // set line height after measure ,in case that height is WRAP_CONTENT
                    currentLineHeight = Math.max(
                        currentLineHeight, childHeightSize + lp.topMargin + lp.bottomMargin
                                + childVerticalSpacing
                    )
                }
            }
        }

        // end last line
        endLine(availableWidth - currentLineWidth)

        // then set position for all child
        var totalHeight = paddingTop
        for (i in (lineChildIndex as ArrayList<List<Int>?>).indices) {
            val currentLineIndexList = (lineChildIndex as ArrayList<List<Int>?>).get(i)
            currentLineHeight = (lineHeightList as ArrayList<Int>).get(i)
            val currentLineTotalWidth = (lineWidthList as ArrayList<Int>).get(i)
            currentLineWidth = when (gravity) {
                GRAVITY_CENTER -> paddingLeft + (availableWidth - currentLineTotalWidth) / 2
                GRAVITY_RIGHT, GRAVITY_BOTTOM -> paddingLeft + (availableWidth - currentLineTotalWidth)
                else -> paddingLeft
            }
            for (childIndex in currentLineIndexList!!) {
                val child = getChildAt(childIndex)
                val lp = child.layoutParams as LayoutParam
                val childWidth = child.measuredWidth
                lp.top = totalHeight + lp.topMargin + getChildVerticalSpacing(child) / 2
                lp.left = currentLineWidth + lp.leftMargin + getChildHorizontalSpacing(child) / 2
                currentLineWidth += lp.leftMargin + childWidth + lp.rightMargin
            }
            totalHeight += currentLineHeight
        }
        val measuredWidth =
            if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) maxWidth + paddingRight + paddingLeft else widthSize
        val measuredHeight =
            if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) totalHeight + paddingBottom else heightSize
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun endLine(extraSpacing: Int) {
        // when every line ends , child in weight list will be calculated
        measureWeightChildHorizontal(
            weightChildList, extraSpacing,
            totalWeight
        )
        var currentLineTotalWidth = 0
        for (childIndex in currentLineChildIndex!!) {
            val child = getChildAt(childIndex)
            currentLineTotalWidth += child.measuredWidth
        }
        lineWidthList!!.add(currentLineTotalWidth)
        lineChildIndex!!.add(currentLineChildIndex)
        lineHeightList!!.add(currentLineHeight)
        maxWidth = Math.max(currentLineWidth, maxWidth)
    }

    private fun newLine() {
        // create new
        currentLineChildIndex = ArrayList()
        currentLineHeight = 0
        currentLineWidth = paddingLeft
        totalWeight = 0f
    }

    private fun measureWeightChildHorizontal(
        weightChildList: MutableList<Int>?, extraSpacing: Int,
        totalWeight: Float
    ) {
        while (weightChildList!!.size > 0) {
            val childIndex = weightChildList[0]
            val weightChild = getChildAt(childIndex)
            val lp = weightChild.layoutParams as LayoutParam
            val childWidthMode = MeasureSpec.EXACTLY
            val childHeightMode = MeasureSpec.EXACTLY
            val childWidthSize = (lp.weight / totalWeight * extraSpacing).toInt()
            val childHeightSize = lp.height
            if (childWidthSize + lp.leftMargin + lp.rightMargin != 0) {
                currentLineHeight =
                    Math.max(currentLineHeight, childHeightSize + lp.topMargin + lp.bottomMargin)
            }
            weightChild.measure(
                MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
            )
            weightChildList.removeAt(0)
        }
    }

    private fun measureVertically(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        availableHeight = heightSize - paddingTop - paddingBottom
        weightChildList = ArrayList()
        rowChildIndex = ArrayList()
        rowWidthList = ArrayList()
        rowHeightList = ArrayList()
        maxHeight = 0
        newRow()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as LayoutParam
            var childWidthMode = MeasureSpec.EXACTLY
            val childHeightMode = MeasureSpec.EXACTLY
            var childWidthSize = lp.width
            var childHeightSize = lp.height
            val childHorizontalSpacing = getChildHorizontalSpacing(child)
            val childVerticalSpacing = getChildVerticalSpacing(child)
            if (widthMode == MeasureSpec.UNSPECIFIED && childWidthSize == 0) {
                childWidthMode = MeasureSpec.UNSPECIFIED
            }
            if (lp.lineNum != lineNum) {
                endRow(availableHeight - currentRowHeight)
                newRow()
                lineNum = lp.lineNum
            }
            if (lp.height == LayoutParams.MATCH_PARENT) {
                if (currentRowHeight + lp.topMargin + lp.height + lp.bottomMargin + childVerticalSpacing <= availableHeight) {
                    // take place all the extra spacing
                    childHeightSize =
                        (availableHeight - currentRowHeight - lp.topMargin - lp.bottomMargin
                                - childVerticalSpacing)
                    currentRowChildIndex!!.add(i)
                    currentRowHeight = heightSize - paddingBottom
                    if (lp.height != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    currentRowWidth = Math.max(
                        currentRowWidth, childWidthSize + lp.leftMargin + lp.rightMargin
                                + childHorizontalSpacing
                    )
                    endRow(0)
                    newRow()
                } else {
                    endRow(0)
                    newRow()
                    childHeightSize =
                        availableHeight - lp.topMargin - lp.bottomMargin - childVerticalSpacing
                    currentRowChildIndex!!.add(i)
                    currentRowHeight = heightSize - paddingBottom
                    if (lp.height != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    currentRowWidth = Math.max(
                        currentRowWidth, childWidthSize + lp.leftMargin + lp.rightMargin
                                + childHorizontalSpacing
                    )
                    endRow(0)
                    newRow()
                }
            } else if (lp.height == 0 && lp.weight != 0f) {
                // add to weight child list ,waiting for measure when whole row end ,so it will has extra
                // place to be placed
                currentRowChildIndex!!.add(i)
                totalWeight += lp.weight
                (weightChildList as ArrayList<Int>).add(i)
                //?????????????????????????????
            } else {
                if (childHeightSize == LayoutParams.WRAP_CONTENT) {
//                    child.measure(
//                        MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
//                        MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
//                    )
                    childWidthSize = child.measuredWidth
                    childHeightSize = child.measuredHeight
                }
                if ((currentRowHeight + lp.topMargin + childHeightSize + lp.bottomMargin
                            + childVerticalSpacing) <= availableHeight
                ) {
                    // current row
                    currentRowChildIndex!!.add(i)
                    currentRowHeight += childHeightSize + lp.topMargin + lp.bottomMargin + childVerticalSpacing
                    if (lp.height != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    if (childHeightSize + lp.topMargin + lp.bottomMargin != 0) {
                        currentRowWidth = Math.max(
                            currentRowWidth, childWidthSize + lp.leftMargin + lp.rightMargin
                                    + childHorizontalSpacing
                        )
                    }
                } else {
                    endRow(availableHeight - currentRowHeight)
                    newRow()
                    currentRowChildIndex!!.add(i)
                    currentRowHeight =
                        childHeightSize + lp.topMargin + lp.bottomMargin + childVerticalSpacing
                    if (lp.height != LayoutParams.WRAP_CONTENT) {
                        child.measure(
                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                            MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
                        )
                        childWidthSize = child.measuredWidth
                        childHeightSize = child.measuredHeight
                    }
                    currentRowWidth = Math.max(
                        currentRowWidth, childWidthSize + lp.leftMargin + lp.rightMargin
                                + childHorizontalSpacing
                    )
                }
            }
        }
        // end last row
        endRow(availableHeight - currentRowHeight)

        // then set position for all child
        var totalWidth = paddingLeft
        for (i in (rowChildIndex as MutableList<List<Int>?>).indices) {
            val currentRowIndexList = (rowChildIndex as ArrayList<List<Int>?>).get(i)
            currentRowWidth = (rowWidthList as ArrayList<Int>).get(i)
            val currentRowTotalHeight = (rowHeightList as ArrayList<Int>).get(i)
            currentRowHeight = when (gravity) {
                GRAVITY_CENTER -> paddingTop + (availableHeight - currentRowTotalHeight) / 2
                GRAVITY_RIGHT, GRAVITY_BOTTOM -> paddingTop + (availableHeight - currentRowTotalHeight)
                else -> paddingTop
            }
            for (childIndex in currentRowIndexList!!) {
                val child = getChildAt(childIndex)
                val lp = child.layoutParams as LayoutParam
                val childHeight = child.measuredHeight
                lp.top = currentRowHeight + lp.topMargin + getChildVerticalSpacing(child) / 2
                lp.left = totalWidth + lp.leftMargin + getChildHorizontalSpacing(child) / 2
                currentRowHeight += lp.topMargin + childHeight + lp.bottomMargin
            }
            totalWidth += currentRowWidth
        }
        setMeasuredDimension(
            if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) totalWidth + paddingRight else widthSize,
            if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) maxHeight + paddingLeft + paddingBottom else heightSize
        )
    }

    private fun endRow(extraSpacing: Int) {
        // end last row
        measureWeightChildVertically(weightChildList, extraSpacing, totalWeight)
        var currentRowTotalHeight = 0
        for (childIndex in currentRowChildIndex!!) {
            val child = getChildAt(childIndex)
            currentRowTotalHeight += child.measuredHeight
        }
        rowHeightList!!.add(currentRowTotalHeight)
        rowChildIndex!!.add(currentRowChildIndex)
        rowWidthList!!.add(currentRowWidth)
        maxHeight = Math.max(currentRowHeight, maxHeight)
    }

    private fun newRow() {
        // take new row
        currentRowChildIndex = ArrayList()
        currentRowWidth = 0
        currentRowHeight = paddingTop
        totalWeight = 0f
    }

    private fun measureWeightChildVertically(
        weightChildList: MutableList<Int>?, extraSpacing: Int,
        totalWeight: Float
    ) {
        while (weightChildList!!.size > 0) {
            val childIndex = weightChildList[0]
            val weightChild = getChildAt(childIndex)
            val lp = weightChild.layoutParams as LayoutParam
            val childWidthMode = MeasureSpec.EXACTLY
            val childHeightMode = MeasureSpec.EXACTLY
            val childHeightSize = (lp.weight / totalWeight * extraSpacing).toInt()
            val childWidthSize = lp.width
            if (childHeightSize + lp.topMargin + lp.bottomMargin != 0) {
                currentRowWidth =
                    Math.max(currentRowWidth, childWidthSize + lp.leftMargin + lp.rightMargin)
            }
            weightChild.measure(
                MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
            )
            weightChildList.removeAt(0)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutChild(changed, l, t, r, b)
    }

    private fun layoutChild(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) {
                continue
            }
            val lp = child.layoutParams as LayoutParam
            val left = lp.left
            val top = lp.top
            child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
        }
    }

    fun setGravity(gravity: Int) {
        val changed = gravity == this.gravity
        this.gravity = gravity
        if (changed) {
            requestLayout()
        }
    }

    private fun getChildHorizontalSpacing(child: View): Int {
        val lp = child.layoutParams as LayoutParam
        var childHorizontalSpacing = if (horizontalSpacing == SPACING_NONE) 0 else horizontalSpacing
        childHorizontalSpacing =
            if (lp.horizontalSpacing == SPACING_NONE) childHorizontalSpacing else lp.horizontalSpacing
        return childHorizontalSpacing
    }

    private fun getChildVerticalSpacing(child: View): Int {
        val lp = child.layoutParams as LayoutParam
        var childVerticalSpacing = if (verticalSpacing == SPACING_NONE) 0 else verticalSpacing
        childVerticalSpacing =
            if (lp.verticalSpacing == SPACING_NONE) childVerticalSpacing else lp.verticalSpacing
        return childVerticalSpacing
    }

    override fun generateDefaultLayoutParams(): LayoutParam {
        return LayoutParam(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParam {
        return LayoutParam(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParam {
        return LayoutParam(context, attrs)
    }

    class LayoutParam : MarginLayoutParams {
        var weight = -1f
        var lineNum = LINE_NUM_INVALID
        var horizontalSpacing = 0
        var verticalSpacing = 0
        var left = -1
        var top = -1

        constructor(width: Int, height: Int) : super(width, height)
        constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
            val a = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.FlowLayout, 0, 0
            )
            try {
                weight = a.getInt(R.styleable.FlowLayout_weight, 0).toFloat()
                lineNum = a.getInt(R.styleable.FlowLayout_lineNum, LINE_NUM_INVALID)
                horizontalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_childHorizontalSpacing,
                    SPACING_NONE
                )
                verticalSpacing = a.getDimensionPixelSize(
                    R.styleable.FlowLayout_childVerticalSpacing,
                    SPACING_NONE
                )
            } finally {
                a.recycle()
            }
        }

        constructor(source: LayoutParams?) : super(source)

        companion object {
            const val LINE_NUM_INVALID = Int.MIN_VALUE
        }
    }

    companion object {
        const val ORIENTATION_HORIZONTAL = 2
        const val ORIENTATION_VERTICAL = 3
        const val GRAVITY_NONE = -1
        const val GRAVITY_LEFT = 1
        const val GRAVITY_CENTER = 2
        const val GRAVITY_RIGHT = 3
        const val GRAVITY_TOP = 4
        const val GRAVITY_BOTTOM = 5
        private const val SPACING_NONE = -1
    }
}