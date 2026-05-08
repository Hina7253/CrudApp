package com.example.crudapp.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.example.crudapp.database.AppDatabase;
import com.example.crudapp.models.Person;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersonRepository {
    private AppDatabase database;
    private ExecutorService executorService;
    private Handler mainHandler;

    public PersonRepository(Context context) {
        database = AppDatabase.getInstance(context);
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface OnPersonsLoadedListener {
        void onLoaded(List<Person> persons);
    }

    public interface OnPersonActionListener {
        void onSuccess();
        void onError(String error);
    }

    public void getAllPersons(OnPersonsLoadedListener listener) {
        executorService.execute(() -> {
            List<Person> persons = database.personDao().getAllPersons();
            mainHandler.post(() -> listener.onLoaded(persons));
        });
    }

    public void insertPerson(Person person, OnPersonActionListener listener) {
        executorService.execute(() -> {
            try {
                database.personDao().insertPerson(person);
                mainHandler.post(listener::onSuccess);
            } catch (Exception e) {
                mainHandler.post(() -> listener.onError(e.getMessage()));
            }
        });
    }

    public void updatePerson(Person person, OnPersonActionListener listener) {
        executorService.execute(() -> {
            try {
                database.personDao().updatePerson(person);
                mainHandler.post(listener::onSuccess);
            } catch (Exception e) {
                mainHandler.post(() -> listener.onError(e.getMessage()));
            }
        });
    }

    public void deletePerson(Person person, OnPersonActionListener listener) {
        executorService.execute(() -> {
            try {
                database.personDao().deletePerson(person);
                mainHandler.post(listener::onSuccess);
            } catch (Exception e) {
                mainHandler.post(() -> listener.onError(e.getMessage()));
            }
        });
    }

    public void searchPersons(String query, OnPersonsLoadedListener listener) {
        executorService.execute(() -> {
            List<Person> persons = database.personDao().searchPersons(query);
            mainHandler.post(() -> listener.onLoaded(persons));
        });
    }
}