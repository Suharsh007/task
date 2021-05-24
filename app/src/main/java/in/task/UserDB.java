package in.task;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class}, version = 1)
public abstract class UserDB extends RoomDatabase {
    private static final String dbName = "user";
    private static UserDB userDB;

    static synchronized UserDB getUserDB(Context context)
    {
        if(userDB == null)
        {
            userDB = Room.databaseBuilder(context,UserDB.class,dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return userDB;
    }
    public abstract UserDao userDao();
}

