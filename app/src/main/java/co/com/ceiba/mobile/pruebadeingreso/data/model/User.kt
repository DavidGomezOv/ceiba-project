package co.com.ceiba.mobile.pruebadeingreso.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    val id: Int?,
    val name: String?,
    val email: String?,
    val address: Address?,
    val phone: String?,
    val website: String?,
    val company: Company?
) : Parcelable

@Parcelize
data class Address(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: Geo?
) : Parcelable

@Parcelize
data class Geo(
    val lat: String?,
    val lnh: String?,
) : Parcelable

@Parcelize
data class Company(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
) : Parcelable


@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "phone")
    val phone: String?,
    @ColumnInfo(name = "website")
    val website: String?,
)

fun List<UserEntity>.toUserList(): List<User> {
    val result = mutableListOf<User>()
    this.forEach {
        result.add(it.toUser())
    }
    return result
}

fun UserEntity.toUser(): User = User(
    this.id,
    this.name,
    this.email,
    null,
    this.phone,
    this.website,
    null
)

fun User.toUserEntity(): UserEntity = UserEntity(
    this.id,
    this.name,
    this.email,
    this.phone,
    this.website,
)