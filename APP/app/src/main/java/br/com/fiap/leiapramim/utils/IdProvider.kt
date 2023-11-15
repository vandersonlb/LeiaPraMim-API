package br.com.fiap.leiapramim.utils

import android.content.Context
import android.provider.Settings.Secure

fun getUserId(context: Context) : String {
    return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
}
