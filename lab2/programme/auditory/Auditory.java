package auditory;

public class Auditory {
    private static int nextId = 1;
    
    private int id;
    private int floor;
    private String forSubject; 
    private int capacity;

    public class Equipment {
        private String name;
        private boolean active;

        public Equipment(String name, boolean active) {
            this.name = name;
            this.active = active;
        }

        public String getStatus() {
            return name + " is " + (active ? "active" : "inactive") + " in auditory " + id;
        }
    }
    
    public Auditory() {
        id = nextId++;
        this.floor = -1;
        this.forSubject = "Unknown";
        this.capacity = 0;
    }

    public Auditory(int floor, String forSubject, int capacity) {
        id = nextId++;
        this.floor = floor;
        this.forSubject = forSubject;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        if (floor < 0) {
            throw new IllegalArgumentException("Floor cannot be negative.");
        }
        this.floor = floor;
    }

    public String getForSubject() {
        return forSubject;
    }

    public void setForSubject(String forSubject) {
        if (forSubject == null || forSubject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty.");
        } else if (forSubject.length() > 100 || forSubject.length() < 2) {
            throw new IllegalArgumentException("Subject must be between 2 and 100 characters.");
        }
        this.forSubject = forSubject;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        this.capacity = capacity;
    }

}
