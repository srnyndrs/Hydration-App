package hu.bme.aut.android.hydrationapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.hydrationapp.data.user.RoomUser
import hu.bme.aut.android.hydrationapp.data.user.UserDao
import hu.bme.aut.android.hydrationapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    fun getUserData(): LiveData<User?> {
        return userDao.getUserData()
            .map {
                roomUser -> roomUser?.toDomainModel()
            }
    }

    fun getCount(): LiveData<Int?> {
        return userDao.getCount()
    }

    fun getGoal(): LiveData<Int?> {
        return userDao.getGoal()
    }

    private fun RoomUser.toDomainModel(): User {
        return User (
            id = id,
            name = name,
            email = email,
            weight = weight,
            goal = goal
        )
    }

    suspend fun deleteUser() = withContext(Dispatchers.IO) {
        userDao.deleteUser()
    }

    suspend fun insert(user: User) = withContext(Dispatchers.IO) {
        userDao.insertUser(user.toRoomModel())
    }

    suspend fun update(user: User) = withContext(Dispatchers.IO) {
        userDao.updateUser(user.toRoomModel())
    }

    private fun User.toRoomModel(): RoomUser {
        return RoomUser (
            id = id,
            name = name,
            email = email,
            weight = weight,
            goal = goal
        )
    }
}