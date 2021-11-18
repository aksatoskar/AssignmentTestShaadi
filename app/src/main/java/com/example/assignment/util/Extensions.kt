import androidx.recyclerview.widget.ListAdapter
import com.example.assignment.util.RecyclerViewHolder

fun <T, VH : RecyclerViewHolder> ListAdapter<T, VH>.appendItem(t: T) {
    submitList(
        currentList.toMutableList().apply {
            add(t)
        }
    )
}

fun <T, VH : RecyclerViewHolder> ListAdapter<T, VH>.appendList(tList: List<T>) {
    submitList(
        currentList.toMutableList().apply {
            addAll(tList)
        }
    )
}

fun <T, VH : RecyclerViewHolder> ListAdapter<T, VH>.updateItem(position: Int, t: T) {
    if (position in 0 until currentList.size) {
        submitList(currentList.toMutableList().apply {
            this[position] = t
        })
    } else {
        throw IndexOutOfBoundsException("Position $position out of the range of currentList with size ${currentList.size}")
    }
}

fun <T, VH : RecyclerViewHolder> ListAdapter<T, VH>.removeItemAtPosition(position: Int) {
    if (position in 0 until currentList.size) {
        submitList(currentList.toMutableList().apply {
            removeAt(position)
        })
    } else {
        throw IndexOutOfBoundsException("Position $position out of the range of currentList with size ${currentList.size}")
    }
}