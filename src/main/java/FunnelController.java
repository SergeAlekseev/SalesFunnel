import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FunnelController {

    private static final String OPEN_BRACKET = " ( ";
    private static final String QUOTE = "\"";
    private static final String EQUALLS = " = ";
    private static final String ONE_QUOTE = "\'";
    private static final String OR = " OR ";
    private static final String CLOSE_BRACKET = " ) ";
    private static final String AND = " AND ";
    private static final String SMALLER = " < ";
    private static final String LARGER = " > ";

    private static DatabaseConnect dbConnect;
    //private DatabaseConnect dbConnect;

    private static final String GENERAL_SQL = ", COUNT(\"id_deal\") FROM public.\"Deal\"\n" +
            "                left join public.\"Brand\" on public.\"Deal\".\"id_brand\" = public.\"Brand\".\"id_brand\"\n" +
            "                left join public.\"Client\" on public.\"Deal\".\"id_client\" = public.\"Client\".\"id_client\"\n" +
            "                left join public.\"ClientType\" on public.\"Client\".\"id_clientType\" = public.\"ClientType\".\"id_clientType\"\n" +
            "                left join public.\"Result\" on public.\"Deal\".\"id_result\" = public.\"Result\".\"id_result\"\n" +
            "                left join public.\"ProductCategory\" on public.\"Deal\".\"id_productCategory\" = public.\"ProductCategory\".\"id_productCategory\"\n" +
            "                left join public.\"Source\" on public.\"Deal\".\"id_source\" = public.\"Source\".\"id_source\"\n" +
            "                where ";

    private static final String FROM_SQL = " from (";
    private static final String COUNT_CLIENT_SELECT_SQL = ", count, (count*100.0/(select sum(count) ";
    private static final String SELECT_RESULT_SQL = "select \"nameResult\"";
    private static final String GROUP_RESULT_SQL = "group by \"nameResult\") as \"counts\" ";
    private static final String SELECT_SOURCE_SQL = "select \"nameSource\"";
    private static final String GROUP_SOURCE_SQL = "group by \"nameSource\") as \"counts\" ";
    private static final String NAME_PERCENT_SQL = " )) as \"percent\" ";

    private static final String TEST_FILTR_SQL = " (\"nameBrand\" = 'LG' OR \"nameBrand\" = 'LG1') AND (\"nameCategory\"='Óòþã') AND (\"date\" " + SMALLER + " '01.08.20' AND \"date\" " + LARGER + " '01.03.19') ";


    public FunnelController(String dbName, String dbPassword) throws SQLException {
        dbConnect = new DatabaseConnect("SalesFunnel", "333178");
    }

    public static void main(String[] args) throws SQLException {
        dbConnect = new DatabaseConnect("SalesFunnel", "333178");
        ResultSet rs = dbConnect.select(SELECT_RESULT_SQL + COUNT_CLIENT_SELECT_SQL
                + FROM_SQL + SELECT_RESULT_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_RESULT_SQL + NAME_PERCENT_SQL
                + FROM_SQL + SELECT_RESULT_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_RESULT_SQL);
        while (rs.next()) {
            System.out.print(rs.getString("nameResult") + " | ");
            System.out.print(rs.getString("count") + " | ");
            System.out.println(rs.getDouble("percent") + "%");
        }

        System.out.println();

        rs = dbConnect.select(SELECT_SOURCE_SQL + COUNT_CLIENT_SELECT_SQL
                + FROM_SQL + SELECT_SOURCE_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_SOURCE_SQL + NAME_PERCENT_SQL
                + FROM_SQL + SELECT_SOURCE_SQL + GENERAL_SQL + TEST_FILTR_SQL + GROUP_SOURCE_SQL);
        while (rs.next()) {
            System.out.print(rs.getString("nameSource") + " | ");
            System.out.print(rs.getString("count") + " | ");
            System.out.println(rs.getDouble("percent") + "%");
        }


    }

    public String getResultStatistic(List<String> sources, ClientType clientType, List<String> brands, List<String> categories, Date startDate, Date endDate) throws SQLException {
        return getStatistic(SELECT_RESULT_SQL, GROUP_RESULT_SQL, sources,clientType,brands,categories,startDate,endDate);
    }

    public String getSourceStatistic(List<String> sources, ClientType clientType, List<String> brands, List<String> categories, Date startDate, Date endDate) throws SQLException {
        return getStatistic(SELECT_RESULT_SQL, GROUP_RESULT_SQL, sources,clientType,brands,categories,startDate,endDate);
    }

    private String getStatistic(String select, String group, List<String> sources, ClientType clientType, List<String> brands, List<String> categories,
                                Date startDate, Date endDate) throws SQLException {
        String filterSql = getFilterSQL(sources, clientType,brands,categories,startDate,endDate);
        ResultSet rs = dbConnect.select(select + COUNT_CLIENT_SELECT_SQL
                + FROM_SQL + select + GENERAL_SQL + filterSql + group + NAME_PERCENT_SQL
                + FROM_SQL + select + GENERAL_SQL + filterSql + group);

        return "";
    }

    private String getFilterSQL(List<String> sources, ClientType clientType, List<String> brands, List<String> categories, Date startDate, Date endDate) throws SQLException {
        String filterSql = listStringToFilter(sources, Filter.SOURCE);
        filterSql += AND;
        filterSql += listStringToFilter(brands, Filter.BRAND);
        filterSql += AND;
        filterSql += listStringToFilter(categories, Filter.CATEGORY);
        filterSql += AND;
        filterSql += typeClientFilter(clientType);
        filterSql += AND;
        filterSql += filterDate(startDate, endDate);

        return filterSql;
    }



    private String typeClientFilter(ClientType clientType) {
        return OPEN_BRACKET + QUOTE + Filter.CLIENT_TYPE + QUOTE + EQUALLS + ONE_QUOTE + clientType.getLongName() + ONE_QUOTE + CLOSE_BRACKET;
    }

    private String filterDate(Date start, Date end) {
        return (OPEN_BRACKET + QUOTE + Filter.DATE + QUOTE + SMALLER + ONE_QUOTE + start.toString() + ONE_QUOTE
                + AND + QUOTE + Filter.DATE + QUOTE + SMALLER + ONE_QUOTE + end.toString() + ONE_QUOTE + CLOSE_BRACKET);

    }

    private String listStringToFilter(List<String> list, Filter filterName) {
        String sqlFilter = OPEN_BRACKET;
        for (String str : list) {
            sqlFilter += QUOTE + filterName.getLongName() + QUOTE + EQUALLS + ONE_QUOTE + str + ONE_QUOTE + OR;
        }
        sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 3);
        sqlFilter += CLOSE_BRACKET;
        return sqlFilter;
    }
}
