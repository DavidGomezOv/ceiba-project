package co.com.ceiba.mobile.pruebadeingreso.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

@Entity
data class PostEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String
)

fun List<PostEntity>.toPostList(): List<Post> {
    val result = mutableListOf<Post>()
    this.forEach{
        result.add(it.toPost())
    }
    return result
}

fun PostEntity.toPost(): Post = Post(
    this.id,
    this.userId,
    this.title,
    this.body
)

fun Post.toPostEntity(): PostEntity = PostEntity(
    this.id,
    this.userId,
    this.title,
    this.body
)