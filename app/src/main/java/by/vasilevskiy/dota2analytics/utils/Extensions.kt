package by.vasilevskiy.dota2analytics.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import by.vasilevskiy.dota2analytics.R

fun Context.showNoNetworkConnectionToast() =
    Toast.makeText(
        this,
        this.getString(R.string.no_network_connection),
        Toast.LENGTH_SHORT
    ).show()

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.remove() {
    visibility = View.GONE
}

fun String.removeForbiddenCharacters() = this.replace(".", "")