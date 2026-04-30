package managers;
import auditory.Auditory;

import java.util.HashMap;
import java.util.Map;

public class AuditoriesMap {
    private Map<Integer, Auditory> auditoriesMap;

    public AuditoriesMap() {
        auditoriesMap = new HashMap<>();
    }

    public void addAuditory(Auditory a) {
        auditoriesMap.put(a.getId(), a);
    }

    public Map<Integer, Auditory> getAuditoriesMap() {
        return auditoriesMap;
    }
}
