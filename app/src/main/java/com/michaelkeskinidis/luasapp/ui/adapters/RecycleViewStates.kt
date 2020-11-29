package com.michaelkeskinidis.luasapp.ui.adapters

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

object RecycleViewStates {
    const val STATE_EMPTY = 110
    const val STATE_LOADING = 120
    const val STATE_ERROR = 130
    const val STATE_NORMAL = 140
    const val VIEW_TYPE_HEADER = 1110
    const val VIEW_TYPE_ITEM = 1120

    @IntDef(*[STATE_EMPTY, STATE_LOADING, STATE_ERROR, STATE_NORMAL])
    @Retention(RetentionPolicy.SOURCE)
    annotation class State

    @IntDef(*[VIEW_TYPE_HEADER, VIEW_TYPE_ITEM])
    @Retention(RetentionPolicy.SOURCE)
    annotation class ViewType
}