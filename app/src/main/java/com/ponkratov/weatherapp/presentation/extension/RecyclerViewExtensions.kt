package com.ponkratov.weatherapp.presentation.extension

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ponkratov.weatherapp.R

fun RecyclerView.addVerticalSpace(@DimenRes spaceRes: Int = R.dimen.list_vertical_space) {
    val space = context.resources.getDimensionPixelSize(spaceRes)
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position != parent.adapter?.itemCount?.dec()) {
                outRect.bottom = space
            }
        }
    })
}