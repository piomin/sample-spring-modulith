package pl.piomin.services;

public class OrganizationAddEvent {
    private Long id;

    public OrganizationAddEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
