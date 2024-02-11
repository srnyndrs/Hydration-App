package hu.bme.aut.android.hydrationapp.data.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: RoomUser)

    @Query("SELECT * FROM user WHERE id == 1")
    fun getUserData(): LiveData<RoomUser?>

    @Query("SELECT COUNT(*) FROM user")
    fun getCount(): LiveData<Int?>

    @Query("SELECT goal FROM user WHERE id == 1")
    fun getGoal(): LiveData<Int?>

    @Update
    fun updateUser(user: RoomUser): Int

    @Query("DELETE FROM user")
    fun deleteUser()

}