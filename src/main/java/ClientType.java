public enum ClientType {
    INDIVIDUAL("Individual"),
    ENTITY("Entity");

    String longName;
    ClientType(String longName) {
        this.longName = longName;
    }
    public String getLongName() {
        return longName;
    }
}
