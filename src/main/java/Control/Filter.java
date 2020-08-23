package Control;

public enum Filter {
    SOURCE("public.\"Deal\".\"id_source\""), CLIENT_TYPE("public.\"ClientType\".\"id_clientType\""), BRAND("public.\"Deal\".\"id_brand\""), CATEGORY("public.\"Deal\".\"id_productCategory\""), DATE("\"date\"");

    String longName;
    Filter(String longName) {
        this.longName = longName;
    }
    public String getLongName() {
        return longName;
    }
}
