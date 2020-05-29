package com.ectosense.contactsbook.ui.rv

interface RecyclerViewClickListener {
    fun onFavoriteClicked(position: Int, favorite: Boolean)
    fun onRowClicked(position: Int)
}