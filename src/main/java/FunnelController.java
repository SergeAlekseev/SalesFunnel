import java.sql.ResultSet;
import java.sql.SQLException;

public class FunnelController {

    private static DatabaseConnect dbConnect;
    //private DatabaseConnect dbConnect;

    public FunnelController(String dbName, String dbPassword) throws SQLException {
        dbConnect = new DatabaseConnect("SalesFunnel", "333178");
        dbConnect.select("SELECT \"date\", \"nameBrand\", \"fullname\", \"nameType\", \"nameCategory\", \"nameResult\", \"namePoint\", \"nameSource\"  FROM public.\"Deal\" \n" +
                "left join public.\"Brand\" on public.\"Deal\".\"id_brand\" = public.\"Brand\".\"id_brand\"\n" +
                "left join public.\"Client\" on public.\"Deal\".\"id_client\" = public.\"Client\".\"id_client\"\n" +
                "left join public.\"ClientType\" on public.\"Client\".\"id_clientType\" = public.\"ClientType\".\"id_clientType\"\n" +
                "left join public.\"ProductCategory\" on public.\"Deal\".\"id_productCategory\" = public.\"ProductCategory\".\"id_productCategory\"\n" +
                "left join public.\"Result\" on public.\"Deal\".\"id_result\" = public.\"Result\".\"id_result\"\n" +
                "left join public.\"SalePoint\" on public.\"Deal\".\"id_salePoint\" = public.\"SalePoint\".\"id_salePoint\"\n" +
                "left join public.\"Source\" on public.\"Deal\".\"id_source\" = public.\"Source\".\"id_source\";");
    }

    public static void main(String[] args) throws SQLException {
        dbConnect = new DatabaseConnect("SalesFunnel", "333178");
        ResultSet rs = dbConnect.select("select *  from (SELECT \"nameResult\", COUNT(\"id_deal\") FROM public.\"Deal\" \n" +
                "left join public.\"Brand\" on public.\"Deal\".\"id_brand\" = public.\"Brand\".\"id_brand\"\n" +
                "left join public.\"Client\" on public.\"Deal\".\"id_client\" = public.\"Client\".\"id_client\"\n" +
                "left join public.\"ClientType\" on public.\"Client\".\"id_clientType\" = public.\"ClientType\".\"id_clientType\"\n" +
                "left join public.\"ProductCategory\" on public.\"Deal\".\"id_productCategory\" = public.\"ProductCategory\".\"id_productCategory\"\n" +
                "left join public.\"Result\" on public.\"Deal\".\"id_result\" = public.\"Result\".\"id_result\"\n" +
                "left join public.\"SalePoint\" on public.\"Deal\".\"id_salePoint\" = public.\"SalePoint\".\"id_salePoint\"\n" +
                "left join public.\"Source\" on public.\"Deal\".\"id_source\" = public.\"Source\".\"id_source\"\n" +
                "where (\"nameBrand\" = 'LG' OR \"nameBrand\" = 'LG1') AND (\"nameCategory\"='Óòþã') AND (\"date\" < '01.08.20' AND \"date\" > '01.03.19') group by \"nameResult\") as \"counts\"");
        rs.next();
        System.out.println(rs.getString("nameResult"));

    }
}
