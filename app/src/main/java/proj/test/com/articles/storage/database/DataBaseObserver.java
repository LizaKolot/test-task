package proj.test.com.articles.storage.database;


import java.util.ArrayList;
import java.util.List;

public class DataBaseObserver {
    private List<DataBaseChangeListener> listeners;

    private static DataBaseObserver instance = new DataBaseObserver();

    public static DataBaseObserver getInstance() {
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
