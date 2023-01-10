package suvorov.coin.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@SuppressLint("ClickableViewAccessibility")
abstract class SwipeHelper(
    context: Context?, recyclerView: RecyclerView, animate: Boolean
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val recyclerView: RecyclerView
    private lateinit var buttons: MutableList<UnderlayButton>
    private var swipedPos = -1
    private var swipeThreshold = 0.5f
    private val buttonsBuffer: MutableMap<Int, MutableList<UnderlayButton>>
    private var animate: Boolean? = null

    private val recoverQueue = object : LinkedList<Int>() {
        override fun add(element: Int): Boolean {
            return if (contains(element)) false else super.add(element)
        }
    }

    private val gestureListener: GestureDetector.SimpleOnGestureListener =
        object: GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            for (button in buttons) {
                if (button.onClick(e.x, e.y)) break
            }
            return true
        }
    }

    private val gestureDetector = GestureDetector(context, gestureListener)

    private val onTouchListener = View.OnTouchListener { _, e ->
        if (swipedPos < 0) {
            return@OnTouchListener false
        }

        val point = Point(e.rawX.toInt(), e.rawY.toInt())
        val swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos)
        val swipedItem = swipedViewHolder?.itemView
        val rect = Rect()
        swipedItem?.getGlobalVisibleRect(rect)

        if (e.action == MotionEvent.ACTION_DOWN ||
            e.action == MotionEvent.ACTION_UP ||
            e.action == MotionEvent.ACTION_MOVE
        ) {
            if (rect.top < point.y && rect.bottom > point.y) {
                gestureDetector.onTouchEvent(e)
            } else {
                recoverQueue.add(swipedPos)
                swipedPos = -1
                recoverSwipedItem()
            }
        }
        false
    }

    init {
        this.animate = animate
        this.recyclerView = recyclerView
        buttons = ArrayList()
        this.recyclerView.setOnTouchListener(onTouchListener)
        buttonsBuffer = HashMap()
        attachSwipe()
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if (swipedPos != pos) recoverQueue.add(swipedPos)
        swipedPos = pos
        if (buttonsBuffer.containsKey(swipedPos))
            buttons = buttonsBuffer[swipedPos] ?: arrayListOf()
        else
            buttons.clear()

        buttonsBuffer.clear()
        swipeThreshold = 0.5f * buttons.size * BUTTON_WIDTH
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipedPos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: MutableList<UnderlayButton> = ArrayList()
                if (!buttonsBuffer.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer)
                    buttonsBuffer[pos] = buffer
                } else {
                    buffer = buttonsBuffer[pos] ?: arrayListOf()
                }
                translationX = dX * buffer.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            translationX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            val pos = recoverQueue.poll()
            if (pos!! > -1) {
                recyclerView.adapter?.notifyItemChanged(pos)
            }
        }
    }

    private fun drawButtons(
        c: Canvas,
        itemView: View,
        buffer: List<UnderlayButton>,
        pos: Int,
        dX: Float
    ) {
        var right = itemView.width.toFloat()

        for (button in buffer) {
            val left = right - (-1 * dX / buffer.size)
            button.onDraw(
                c,
                RectF(
                    left,
                    itemView.top.toFloat(),
                    right,
                    itemView.bottom.toFloat()
                ),
                pos
            )
            right = left
        }
    }

    private fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    abstract fun instantiateUnderlayButton(
        viewHolder: RecyclerView.ViewHolder?,
        underlayButtons: MutableList<UnderlayButton>?
    )

    class UnderlayButton(
        private val imageResId: Drawable?,
        private val buttonBackgroundColor: Int,
        private val clickListener: UnderlayButtonClickListener
    ) {
        private var pos = 0
        private var clickRegion: RectF? = null

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x, y)) {
                clickListener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(canvas: Canvas, rect: RectF, pos: Int) {
            val p = Paint()
            p.color = buttonBackgroundColor
            canvas.drawRect(rect, p)
            if (!animate) {
                val cHeight = rect.height()

                if (imageResId != null) {
                    imageResId.setBounds(
                        (rect.left + 50).toInt(),
                        (rect.top + cHeight / 2f).toInt(),
                        (rect.right - 50).toInt(),
                        (rect.bottom - cHeight / 10f).toInt()
                    )
                    imageResId.draw(canvas)
                }
            } else {
                if (imageResId != null) {
                    imageResId.setBounds(
                        (rect.left + 64).toInt(),
                        (rect.top + 58).toInt(),
                        (rect.right - 64).toInt(),
                        (rect.bottom - 58).toInt()
                    )
                    imageResId.draw(canvas)
                }
                canvas.save()
                canvas.restore()
            }
            clickRegion = rect
            this.pos = pos
        }
    }

    interface UnderlayButtonClickListener {
        fun onClick(position: Int)
    }

    companion object {
        const val BUTTON_WIDTH = 200
        const val animate: Boolean = true
    }
}