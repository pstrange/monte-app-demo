package com.monte.appdemo.views.base.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.fromHtml
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import com.monte.appdemo.R
import com.monte.domain.models.common.FormValidator
import com.monte.appdemo.views.base.adapters.BaseViewHolder
import java.io.File
import kotlin.properties.Delegates


infix fun <T : View> T.onClick(f: (T) -> Unit) =
    setOnClickListener(
        SafeClickListener { f(this) }
    )

@BindingAdapter(value = ["imageUrl", "placeHolder", "imageLocal"], requireAll = false)
fun loadImage(view: ImageView, imageUrl: String?, placeHolder: Drawable?, imageLocal: Int?) {
    if (imageUrl?.isNotEmpty() == true) {
        view.loadImage(url = imageUrl, placeholder = placeHolder)
    } else if(imageLocal != null) {
        view.loadImage(imageLocal)
    }
}

@BindingAdapter(value = ["imageUrlRounded", "placeHolder", "imageLocal", "imageRadius"], requireAll = false)
fun loadImageRounded(view: ImageView, imageUrlRounded: String?, placeHolder: Drawable?, imageLocal: Int?, imageRadius: Int? = 24) {
    if (imageUrlRounded?.isNotEmpty() == true) {
        view.loadImageRounded(url = imageUrlRounded, placeholder = placeHolder, imageRadius)
    } else if(imageLocal != null) {
        view.loadImage(imageLocal)
    }
}

@BindingAdapter(value = ["formValidator"], requireAll = false)
fun setErrorValidator(
    textInputLayout: TextInputLayout?,
    formValidator: FormValidator?) {
    formValidator?.message?.let {
        textInputLayout?.error = it
    } ?: run {
        textInputLayout?.error = ""
    }
}

@BindingAdapter(value = ["validatorMessage"], requireAll = false)
fun setErrorMessage(
    textView: AppCompatTextView?,
    formValidator: FormValidator?) {
    formValidator?.message?.let {
        textView?.visibility = View.VISIBLE
        textView?.text = it
    } ?: run {
        textView?.visibility = View.GONE
        textView?.text = ""
    }
}

@BindingAdapter(value = ["validatorInput", "isEnabled"], requireAll = false)
fun setErrorInput(
    editText: AppCompatEditText?,
    formValidator: FormValidator?,
    isEnabled: Boolean?) {
    if(isEnabled == false){
        editText?.isEnabled = false
        editText?.setTextColor(ContextCompat.getColor(editText.context, R.color.white))
        editText?.setBackgroundResource(R.drawable.edittext_white_disabled)
        return
    }
    editText?.isEnabled = true
    editText?.setTextColor(ContextCompat.getColor(editText.context, R.color.black))
    formValidator?.message?.let {
        editText?.setBackgroundResource(R.drawable.edittext_white_error)
    } ?: run {
        editText?.setBackgroundResource(R.drawable.edittext_white)
    }
}

@BindingAdapter(value = ["validatorIcon", "isEnabled"], requireAll = false)
fun setErrorIcon(
    imageView: AppCompatImageView?,
    formValidator: FormValidator?,
    isEnabled: Boolean?) {
    if(isEnabled == false){
        imageView?.isEnabled = false
        imageView?.setColorFilter(ContextCompat.getColor(imageView.context, R.color.white))
        return
    }

    imageView?.isEnabled = true
    formValidator?.message?.let {
        imageView?.setColorFilter(ContextCompat.getColor(imageView.context, R.color.red_error))
    } ?: run {
        imageView?.setColorFilter(ContextCompat.getColor(imageView.context, R.color.blue))
    }
}

@BindingAdapter(value = ["validatorTogglePass"], requireAll = false)
fun toggleTransformationPass(
    editText: AppCompatEditText?,
    show: Boolean?) {
    editText?.transformationMethod =
        if(show == true) null else
            PasswordTransformationMethod()
}

fun TextView.setSpanText(fulltext: String, subtexts: Array<String>, color: Int = R.color.blue_span) {
    this.setText(fulltext, TextView.BufferType.SPANNABLE)
    val str = this.text as Spannable

    subtexts.forEach { subLabel ->
        val i = fulltext.indexOf(subLabel)
        str.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this.context, color)),
            i, i + subLabel.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun TextView.setSpanTextWithEvent(fulltext: String, subtexts: Map<String, () -> Unit>, color: Int = R.color.blue_span) {
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(fulltext, TextView.BufferType.SPANNABLE)
    this.highlightColor = Color.TRANSPARENT

    val context = this.context
    val str = this.text as Spannable

    subtexts.forEach { subLabel ->
        val i = fulltext.indexOf(subLabel.key)
        str.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color)),
            i, i + subLabel.key.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        str.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                subLabel.value()
            }
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = ContextCompat.getColor(context, color)
                textPaint.isUnderlineText = false
            }
        }, i, i + subLabel.key.length, 0)

    }
}

fun <VH: BaseViewHolder<T, B>, T, B> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T> = emptyList(),
    areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
) =
    Delegates.observable(initialValue){ _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback(){
            override fun getOldListSize(): Int = old.size
            override fun getNewListSize(): Int = new.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])
        }).dispatchUpdatesTo(this)
    }

fun Activity.showListPopUp(anchorView: View, elements: Array<String?>,
                           onClickListener: AdapterView.OnItemClickListener) : ListPopupWindow {
    val listPopupWindow = ListPopupWindow(this)
    listPopupWindow.setAdapter(
        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            elements
        )
    )
    listPopupWindow.anchorView = anchorView
    listPopupWindow.width = anchorView.width
    listPopupWindow.setOnItemClickListener{ adapterView, _, position, id ->
        listPopupWindow.dismiss()
        onClickListener.onItemClick(adapterView, anchorView, position, id)
    }
    listPopupWindow.show()
    return listPopupWindow
}

fun Activity.showListPopUpSpannable(anchorView: View, elements: Array<Spanned?>,
                           onClickListener: AdapterView.OnItemClickListener) : ListPopupWindow {
    val listPopupWindow = ListPopupWindow(this)
    listPopupWindow.setAdapter(
        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            elements
        )
    )
    listPopupWindow.anchorView = anchorView
    listPopupWindow.width = anchorView.width
    listPopupWindow.setOnItemClickListener{ adapterView, _, position, id ->
        listPopupWindow.dismiss()
        onClickListener.onItemClick(adapterView, anchorView, position, id)
    }
    listPopupWindow.show()
    return listPopupWindow
}

fun String.getHtml(): Spanned = fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

fun ImageView.loadImageRounded(
    url: String,
    placeholder: Drawable? = null,
    radius: Int? = 10
) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.optionalTransform(
        RoundedCorners(radius?.toDp ?: 10.toDp)
    )

    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .centerCrop()
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadImage(
    url: String,
    placeholder: Drawable? = null
) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadImage(uri: String, placeholder: Int = R.drawable.place_holder) {
    Glide.with(this)
        .load(uri)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadImage(uri: Uri, placeholder: Int = R.drawable.place_holder) {
    Glide.with(this)
        .load(uri)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadImage(@DrawableRes url: Int, placeholder: Int = R.drawable.place_holder) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.loadImageFromPath(path: String, placeholder: Int = R.drawable.place_holder) {
    Glide.with(this)
        .load(File(path))
        .placeholder(placeholder)
        .into(this)
}