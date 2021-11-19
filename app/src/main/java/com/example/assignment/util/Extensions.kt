import androidx.recyclerview.widget.ListAdapter
import com.example.assignment.util.RecyclerViewHolder

fun <T, VH : RecyclerViewHolder> ListAdapter<T, VH>.appendList(tList: List<T>) {
    submitList(
        currentList.toMutableList().apply {
            addAll(tList)
        }
    )
}