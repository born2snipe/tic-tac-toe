package b2s.tictactoe.trophy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class TrophyContext {
    private Map<String, Object> context = new LinkedHashMap<String, Object>();

    public List<String> keys() {
        return new ArrayList(context.keySet());
    }

    public List<Object> values() {
        return new ArrayList(context.values());
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void put(String key, Object obj) {
        context.put(key, obj);
    }

    public <T> T get(String key, Class<T> type) {
        return (T) get(key);
    }
}
