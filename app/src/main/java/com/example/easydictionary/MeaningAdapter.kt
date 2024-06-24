package com.example.easydictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easydictionary.databinding.MeaningRecyclerRowBinding

class MeaningAdapter(private var meaningList : List<Meaning>): RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>(){
    class MeaningViewHolder(private val binding: MeaningRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(meaning: Meaning){
            //bind all the meanings here - set UI here
            binding.partOfSpeechTextview.text = meaning.partOfSpeech
            binding.defintionsTextview.text = meaning.definitions.joinToString("\n\n") {
                //to number the definitions
                var currentIndex = meaning.definitions.indexOf(it)
                (currentIndex + 1).toString()+") " +it.definition.toString()
            }
            //synonyms can be null
            if(meaning.synonyms.isEmpty()){
                binding.synonymsTitleTextview.visibility = View.GONE
                binding.synonymsTextview.visibility = View.GONE
            }
            else{
                binding.synonymsTitleTextview.visibility = View.VISIBLE
                binding.synonymsTextview.visibility = View.VISIBLE
                binding.synonymsTextview.text = meaning.synonyms.joinToString(",")
            }
            //antonyms can be null
            if(meaning.antonyms.isEmpty()){
                binding.antonymsTitleTextview.visibility = View.GONE
                binding.antonymsTextview.visibility = View.GONE
            }
            else{
                binding.antonymsTitleTextview.visibility = View.VISIBLE
                binding.antonymsTextview.visibility = View.VISIBLE
                binding.antonymsTextview.text = meaning.antonyms.joinToString(",")
            }
        }
    }
    fun updateNewData(newMeaningList : List<Meaning>){
        meaningList = newMeaningList
        notifyDataSetChanged() //when there is a change in data that happens
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meaningList[position])
    }
}