package in.task;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void registerUser(User user);

    @Query("SELECT * from users where emailId=(:email) and password=(:password)")
    User login(String email, String password);


}
