public enum Filter {
    SOURCE("nameSource"), CLIENT_TYPE("nameType"), BRAND("nameBrand"), CATEGORY("nameCategory"), DATE("date");

    String longName;
    Filter(String longName) {
        this.longName = longName;
    }
    public String getLongName() {
        return longName;
    }
}
