import java.sql.ResultSet;
import java.sql.SQLException;

public class FunnelController {

    private static DatabaseConnect dbConnect;
    //private DatabaseConnect dbConnect;

    private static String GENERAL_SQL = ", COUNT(\"id_deal\") FROM public.\"Deal\"\n" +
            "                left join public.\"Brand\" on public.\"Deal\".\"id_brand\" = public.\"Brand\".\"id_brand\"\n" +
            "                left join public.\"Client\" on public.\"Deal\".\"id_client\" = public.\"Client\".\"id_client\"\n" +
            "                left join public.\"ClientType\" on public.\"Client\".\"id_clientType\" = public.\"ClientType\".\"id_clientType\"\n" +
            "                left join public.\"ProductCategory\" on public.\"Deal\".\"id_productCategory\" = public.\"ProductCategory\".\"id_productCategory\"\n" +
            "                left join public.\"Result\" on public.\"Deal\".\"id_result\" = public.\"Result\".\"id_result\"\n" +
            "                left join public.\"SalePoint\" on public.\"Deal\".\"id_salePoint\" = public.\"SalePoint\".\"id_salePoint\"\n" +
            "                left join public.\"Source\" on public.\"Deal\".\"id_source\" = public.\"Source\".\"id_source\"\n" +
            "                where ";

    private static String FROM_SQL = " from (";
    private static String COUNT_CLIENT_SELECT_SQL = ", count, (count*100.0/(select sum(count) ";
    private static String SELECT_RESULT_SQL = "select \"nameResult\"";
    private static String GROUP_RESULT_SQL = "group by \"nameResult\") as \"counts\" ";
    private static String SELECT_SOURCE_SQL = "select \"nameSource\"";
    private static String GROUP_SOURCE_SQL = "group by \"nameSource\") as \"counts\" ";
    private static String NAME_PERCENT_SQL = " )) as \"percent\" ";
    private static String TEST_FILTR_SQL = " (\"nameBrand\" = 'LG' OR \"nameBrand\" = 'LG1') AND (\"nameCategory\"='Óòþã') AND (\"date\" < '01.08.20' AND \"date\" > '01.03.19') ";


    public FunnelController(String dbName, String dbPassword) throws SQLException {
        dbConnect = new DatabaseConnect("SalesFunnel", "333178");
    }

    public static void main(String[] args) throws SQLException {
        dbConnect = new DatabaseConnect("SalesFunnel", "333178");
        ResultSet rs = dbConnect.select(SELECT_RESULT_SQL + COUNT_CLIENT_SELECT_SQL
                + FROM_SQL + SELECT_RESULT_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_RESULT_SQL + NAME_PERCENT_SQL
                + FROM_SQL + SELECT_RESULT_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_RESULT_SQL);
        while (rs.next()){
            System.out.print(rs.getString("nameResult") + " | ");
            System.out.print(rs.getString("count") + " | ");
            System.out.println(rs.getDouble("percent")+"%");
        }

        System.out.println();

        rs = dbConnect.select(SELECT_SOURCE_SQL + COUNT_CLIENT_SELECT_SQL
                + FROM_SQL + SELECT_SOURCE_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_SOURCE_SQL + NAME_PERCENT_SQL
                + FROM_SQL + SELECT_SOURCE_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_SOURCE_SQL);
        while (rs.next()){
            System.out.print(rs.getString("nameSource") + " | ");
            System.out.print(rs.getString("count") + " | ");
            System.out.println(rs.getDouble("percent")+"%");
        }

    }
}
