package employee;

import auditory.Auditory;

public class Laborant extends Employee {
    private int masterKeyId;

    public Laborant() {
        super();
        this.masterKeyId = -1;
    }

    public Laborant(String name, double salary, Auditory[] auditories, int masterKeyId) {
        super(name, salary, auditories);
        checkMasterKeyId(masterKeyId);
        this.masterKeyId = masterKeyId;
    }

    public int getMasterKeyId() {
        return masterKeyId;
    }

    public void setMasterKeyId(int masterKeyId) {
        checkMasterKeyId(masterKeyId);
        this.masterKeyId = masterKeyId;
    }

    private void checkMasterKeyId(int masterKeyId) {
        Auditory[] auditories = this.getAuditories();
        boolean hasMasterAuditory = false;

        if (masterKeyId <= 0) {
            throw new IllegalArgumentException("Master key ID must be a positive integer.");
        }

        if (auditories != null) {
            for (Auditory a : auditories) {
                if (a.getId() == masterKeyId) {
                    hasMasterAuditory = true;
                    break;
                }
            }
        }

        if (!hasMasterAuditory) {
            throw new IllegalArgumentException("Master key ID must correspond to one of the assigned auditories.");
        }
    }

    @Override
    public String getEmployeeData() {
        return super.getEmployeeData() + ", Role: Laborant" + (masterKeyId > -1 ? ", Master Key: " + masterKeyId : "");
    }
}
