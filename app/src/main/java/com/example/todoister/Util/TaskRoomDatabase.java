package com.example.todoister.Util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoister.Data.TaskDao;
import com.example.todoister.Model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities ={Task.class},version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public  abstract class TaskRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS= 4;
    public static  volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriterExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final String DATABASE_NAME="todoister_database";

    public static final RoomDatabase.Callback sRoomDatabaseCallback=
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriterExecutor.execute(()->{
                        //invoke Dao and write
                        TaskDao taskDao= INSTANCE.taskDao();
                        taskDao.deleteAll();    //clean database!!

                    });
                }
            };

    public static TaskRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (TaskRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TaskRoomDatabase.class,DATABASE_NAME).addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();
}
