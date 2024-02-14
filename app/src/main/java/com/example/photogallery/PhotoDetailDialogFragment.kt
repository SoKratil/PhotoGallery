package com.example.photogallery

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.photogallery.db.PhotoDetail
import com.example.photogallery.db.PhotoDetailDao
import com.example.photogallery.db.PhotoGalleryDatabase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailDialogFragment : DialogFragment() {

    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private var title: String? = null
    private var url: String? = null

    private lateinit var photoDetailDao: PhotoDetailDao

    fun setPhotoDetails(title: String, url: String) {
        this.title = title
        this.url = url
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoDetailDao = PhotoGalleryDatabase.getInstance(requireContext()).photoDetailDao()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_photo_details, null)

        imageView = view.findViewById(R.id.imageViewdialog)
        saveButton = view.findViewById(R.id.buttonSave)

        // Загрузка изображения по ссылке и установка его в ImageView с помощью Picasso
        url?.let { Picasso.get().load(it).into(imageView) }

        // Установка title вместо description
        view.findViewById<TextView>(R.id.textViewDescription)?.text = title

        // Обработчик нажатия кнопки "Сохранить"
        saveButton.setOnClickListener {
            title?.let { title ->
                url?.let { url ->
                    val photoDetail = PhotoDetail(title = title, url = url)
                    // Сохранение информации о фотографии в базе данных с использованием корутин
                    lifecycleScope.launch(Dispatchers.IO) {
                        photoDetailDao.insert(photoDetail)
                    }
                    dismiss()

                    Toast.makeText(requireContext(), "Фотография успешно добавлена", Toast.LENGTH_SHORT).show()
                }
            }
        }

        builder.setView(view)
        return builder.create()
    }
}
