package co.com.ceiba.mobile.pruebadeingreso.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.core.BaseViewHolder
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.databinding.UserListItemBinding

class MainActivityAdapter(
    private val userList: MutableList<User>,
    private val onClickListener: OnClickListenerCardView,
    private var userListTemp: List<User> = userList.toList()
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainActivityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainActivityViewHolder -> holder.bind(userList[position])
        }
    }

    override fun getItemCount(): Int = userList.size

    fun userFilter(filterText: String): List<User> {
        if (filterText.isEmpty()) return userList
        userList.clear()
        for (user in userListTemp) {
            if (user.name!!.lowercase().contains(filterText.lowercase())) {
                userList.add(user)
            }
        }
        notifyDataSetChanged()
        return userList
    }

    private inner class MainActivityViewHolder(
        val binding: UserListItemBinding
    ) : BaseViewHolder<User>(binding.root) {
        override fun bind(item: User) {
            binding.name.text = item.name
            binding.phone.text = item.phone
            binding.email.text = item.email
            binding.btnViewPost.setOnClickListener {
                onClickListener.onClick(item)
            }
        }

    }

}