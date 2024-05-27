package com.mvvmmovies.notesappusingroomdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvmmovies.notesappusingroomdb.R
import com.mvvmmovies.notesappusingroomdb.databinding.NotesListBinding
import com.mvvmmovies.notesappusingroomdb.room.entity.Notes
import java.util.Random

class NotesListAdapter(
    context: Context, private var list: MutableList<Notes>, private var listener: NotesClickListener
) : RecyclerView.Adapter<NotesListAdapter.NotesViewHolder>() {
    lateinit var binding: NotesListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.notes_list, parent, false
        )
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    fun filteredList(filterList: MutableList<Notes>) {
        list = filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        var pos = list[position]
        holder.title.text = pos.title
        holder.notes.text = pos.notes
        holder.date.text = pos.date

        holder.title.isSelected = true
        holder.date.isSelected = true

        if (pos.pinned) {
            holder.pinimage.setImageResource(R.drawable.pin)
        } else {
            holder.pinimage.setImageResource(0)
        }
        holder.cardview.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                getRandomColor(), null
            )
        )

        holder.cardview.setOnClickListener {
            listener!!.onClick(list.get(holder.adapterPosition))
        }
        holder.cardview.setOnLongClickListener(View.OnLongClickListener {
            listener!!.onLongClick(list.get(holder.adapterPosition), holder.cardview)
            return@OnLongClickListener true
        })

        holder.ivMore.setOnClickListener {
            listener!!.onImageClick(list.get(holder.adapterPosition), holder.cardview)
        }

    }

    inner class NotesViewHolder(binding: NotesListBinding) : RecyclerView.ViewHolder(binding.root) {
        var cardview = binding.notesContainer
        var title = binding.textviewTitle
        var notes = binding.textviewNotes
        var date = binding.textviewDate
        var pinimage = binding.pinIV
        var ivMore = binding.ivMore
    }

    interface NotesClickListener {
        fun onClick(notes: Notes)
        fun onLongClick(notes: Notes, container: CardView)
        fun onImageClick(notes: Notes, container: CardView)
    }

    private fun getRandomColor(): Int {
        val colorCode: MutableList<Int> = mutableListOf()
        colorCode.add(R.color.color1)
        colorCode.add(R.color.color2)
        colorCode.add(R.color.color3)
        colorCode.add(R.color.color4)
        colorCode.add(R.color.color5)
        val random = Random()
        var random_color = random.nextInt(colorCode.size)
        return colorCode.get(random_color)
    }

}