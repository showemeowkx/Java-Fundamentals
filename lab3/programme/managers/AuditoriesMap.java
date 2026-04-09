package managers;
import auditory.Auditory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

    public Map<Integer, Auditory> filterAuditoriesByNumber(int min) {
        Map<Integer, Auditory> returnMap = new HashMap<>();

        for (Map.Entry<Integer, Auditory> entry : auditoriesMap.entrySet()) {
            int entryKey = entry.getKey();
            if (entryKey >= min) returnMap.put(entryKey, entry.getValue());
        }

        return returnMap;
    }

    public void removeAuditoryBySubject(String subject) {
        Map<Integer, Auditory> outputMap = new HashMap<>(auditoriesMap);

        for (Map.Entry<Integer, Auditory> entry : auditoriesMap.entrySet()) {
            if (entry.getValue().getForSubject().equals(subject)) {
                outputMap.remove(entry.getKey());
                break;
            }
        }

        auditoriesMap = outputMap;
    }

    public int getTotalCapacity() {
        int totalCapacity = 0;

        for (Map.Entry<Integer, Auditory> entry : auditoriesMap.entrySet()) {
            totalCapacity += entry.getValue().getCapacity();
        }

        return totalCapacity;
    }

    public Map<Integer, Auditory> sortAuditoriesByCapacityAC(int sortMethod) {
        if (sortMethod != 0 && sortMethod != 1) {
            throw new IllegalArgumentException("Invalid sort method argument. Use 0 for DESC and 1 for ASC.");
        }

        List<Map.Entry<Integer, Auditory>> list = new ArrayList<>(auditoriesMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Auditory>>() {
            @Override
            public int compare(Map.Entry<Integer, Auditory> e1, Map.Entry<Integer, Auditory> e2) {
                if (e1.getValue().getCapacity() == e2.getValue().getCapacity()) {
                    return 0;
                }
                if (sortMethod == 1) {
                    return e1.getValue().getCapacity() > e2.getValue().getCapacity() ? 1 : -1;
                } else {
                    return e1.getValue().getCapacity() > e2.getValue().getCapacity() ? -1 : 1;
                }
            }
        });

        Map<Integer, Auditory> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Auditory> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }

    public Map<Integer, Auditory> sortAuditoriesByCapacityLambda(int sortMethod) {
        if (sortMethod != 0 && sortMethod != 1) {
            throw new IllegalArgumentException("Invalid sort method argument. Use 0 for DESC and 1 for ASC.");
        }

        List<Map.Entry<Integer, Auditory>> list = new ArrayList<>(auditoriesMap.entrySet());

        if (sortMethod == 1) {
            list.sort((e1, e2) -> Integer.compare(e1.getValue().getCapacity(), e2.getValue().getCapacity()));
        } else {
            list.sort((e1, e2) -> Integer.compare(e2.getValue().getCapacity(), e1.getValue().getCapacity()));
        }

        Map<Integer, Auditory> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Auditory> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }

    public Map<Integer, Auditory> sortAuditoriesByCapacityMR(int sortMethod) {
        if (sortMethod != 0 && sortMethod != 1) {
            throw new IllegalArgumentException("Invalid sort method argument. Use 0 for DESC and 1 for ASC.");
        }

        List<Map.Entry<Integer, Auditory>> list = new ArrayList<>(auditoriesMap.entrySet());

        if (sortMethod == 1) {
            list.sort(Map.Entry.comparingByValue(Comparator.comparingInt(Auditory::getCapacity)));
        } else {
            list.sort(Map.Entry.<Integer, Auditory>comparingByValue(Comparator.comparingInt(Auditory::getCapacity)).reversed());
        }

        Map<Integer, Auditory> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Auditory> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }
}
