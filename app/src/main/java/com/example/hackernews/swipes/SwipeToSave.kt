package com.example.hackernews.swipes

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R

abstract class SwipeToSave(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private val saveIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_save_alt_24)
    private val intrinsicWidth = saveIcon?.intrinsicWidth
    private val intrinsicHeight = saveIcon?.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.BLUE
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        background.color = backgroundColor
        background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
        )
        background.draw(c)

        // Calculate position of saved icon
        val iconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
        val iconMargin = (itemHeight - intrinsicHeight) / 2
        val iconLeft = itemView.right - iconMargin - intrinsicWidth!!
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + intrinsicHeight

        // Draw the delete icon
        saveIcon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        saveIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }



}