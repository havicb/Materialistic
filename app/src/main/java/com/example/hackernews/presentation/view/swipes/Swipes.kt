package com.example.hackernews.presentation.view.swipes

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.core.callbacks.OnSwipe

class Swipes(val context: Context, val onSwipe: OnSwipe) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    var swipeLeft: Boolean = false

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (swipeLeft) {
            onSwipe.swipeOnLeft(viewHolder.adapterPosition)
        } else {
            onSwipe.swipeOnRight(viewHolder.adapterPosition)
        }
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
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        viewHolder.itemView
        if (dX > 0) // SWIPING TO THE RIGHT
            swipeLeft = false
        else if (dX < 0)
            swipeLeft = true
    }
}
