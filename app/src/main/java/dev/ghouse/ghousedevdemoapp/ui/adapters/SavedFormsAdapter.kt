package dev.ghouse.ghousedevdemoapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dev.ghouse.ghousedevdemoapp.R
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormEntity
import kotlinx.android.synthetic.main.view_saved_form_view.view.*

class SavedFormsAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<SavedFormsAdapter.ViewHolder>() {
    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var forms = emptyList<FormEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the card view as the view holder
        val infoCard = inflater.inflate(R.layout.view_saved_form_view, parent, false) as CardView
        return ViewHolder(infoCard)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind the view holder
        holder.cardView.display_input_one.text = forms[position].inputOne
    }

    internal fun setForms(forms: List<FormEntity>) {
        // Get the list of objects to display
        this.forms = forms
        // Notify the adapter of changes to the data set
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        // Get the size of the list of objects
        return forms.size
    }
}