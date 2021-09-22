package co.com.ceiba.mobile.pruebadeingreso.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.core.BaseViewHolder
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.databinding.PostListItemBinding

class PostActivityAdapter(
    private val postList: List<Post>
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostActivityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PostActivityViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size

    private inner class PostActivityViewHolder(
        private val binding: PostListItemBinding
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            binding.title.text = item.title
            binding.body.text = item.body
        }

    }
}