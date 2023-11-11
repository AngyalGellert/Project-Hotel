package hu.progmasters.hotel.config;

public enum Role {

    USER("User"),

    ADMIN("Admin");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }


    public String getRoleName() {
        return roleName;
    }


}
