package com.example.easydictionary

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easydictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MeaningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchBtn.setOnClickListener {
            val word = binding.searchInput.text.toString()
            getMeaning(word)
        }

        adapter = MeaningAdapter(emptyList())
        binding.meaningRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meaningRecyclerView.adapter = adapter
    }

    private fun getMeaning(word : String){
        setInProgress(true)
        GlobalScope.launch {
            try{
                val response = RetrofitInstance.dictionaryAPI.getMeaning(word)
                if(response.body() == null){
                    throw(Exception())
                }
                //Log.i("Response from API", response.body().toString())
                runOnUiThread {
                    setInProgress(false)
                    response.body()?.first()?.let{
                        //if present, will display word & phonetics
                        setUI(it)
                    }
                }
            }
            catch (e : Exception){
                runOnUiThread {
                    setInProgress(false)
                    Toast.makeText(applicationContext,"Word has no meaning. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUI(response : WordResult){
        binding.wordTextview.text = response.word
        binding.phoneticTextview.text = response.phonetic

        adapter.updateNewData(response.meanings)
    }

    private fun setInProgress(inProgress : Boolean){
        if(inProgress){
            binding.searchBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.searchBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}