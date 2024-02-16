package com.example.photogallery

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.db.PhotoDetailDao
import com.example.photogallery.db.PhotoGalleryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseViewFragment : Fragment() {

    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var photoDetailDao: PhotoDetailDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Инициализируйте ваш DAO для работы с базой данных
        photoDetailDao = PhotoGalleryDatabase.getInstance(requireContext()).photoDetailDao()
        // Инициализируйте адаптер и установите его для RecyclerView
        photoAdapter = PhotoAdapter()
        photoRecyclerView.adapter = photoAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Получите сохраненные фотографии из базы данных и установите их в адаптер
        lifecycleScope.launch(Dispatchers.IO) {
            val photoList = photoDetailDao.getAllPhoto()
            withContext(Dispatchers.Main) {
                photoAdapter.submitList(photoList)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_database_view, menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_return_to_main -> {
                val intent = Intent(requireContext(), PhotoGalleryActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_item_delete_all_records -> {
                // Логика для удаления всех записей из базы данных
                lifecycleScope.launch(Dispatchers.IO) {
                    photoDetailDao.deleteAllPhoto()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Все записи успешно удалены", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    companion object {
        fun newInstance() = DatabaseViewFragment()
    }
}
