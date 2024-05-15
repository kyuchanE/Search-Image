package chanq.search_image.ui.main.fragment.result

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class SearchResultViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {

    open fun bindView(position: Int) {}
}