package proj.test.com.articles.storage.database;


import java.util.ArrayList;
import java.util.List;

public class DataBaseObserver {
    private List<DataBaseChangeListener> listeners;

    private static DataBaseObserver instance;

    public static DataBaseObserver getInstance() {
        if (instance == null) {
            instance = new DataBaseObserver();
        }
        return instance;
    }

    private DataBaseObserver() {
        this.listeners = new ArrayList<>();
    }

    public void onDataBaseChange() {
        for (DataBaseChangeListener listener : listeners) {
            listener.onChange();
        }
    }

    public void addDataBaseChangeListener(DataBaseChangeListener listener) {
        listeners.add(listener);
    }

    public void removeDataBaseChangeListener(DataBaseChangeListener listener) {
        listeners.remove(listener);
    }

    public interface DataBaseChangeListener {
        void onChange();
    }
}
