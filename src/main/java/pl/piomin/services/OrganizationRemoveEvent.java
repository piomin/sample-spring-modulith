package pl.piomin.services;

public class OrganizationRemoveEvent {
    private Long id;

    public OrganizationRemoveEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
