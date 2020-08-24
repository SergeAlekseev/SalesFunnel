package Control;

import Model.Brand;
import Model.Setting;
import Model.Stat;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class FunnelController {

    private static final String PASSWORD = "333178";

    private static final String OPEN_BRACKET = " ( ";
    private static final String EQUALLS = " = ";
    private static final String ONE_QUOTE = "\'";
    private static final String OR = " OR ";
    private static final String CLOSE_BRACKET = " ) ";
    private static final String AND = " AND ";
    private static final String SMALLER = " < ";
    private static final String LARGER = " > ";
    private static final String GET_SOURCES_SQL = "select * from public.\"Source\"";
    private static final String GET_TYPES_SQL = "select * from public.\"ClientType\"";
    private static final String GET_CATEGORYES_SQL = "select * from public.\"ProductCategory\"";
    private static final String GET_BRANDS_SQL = "select distinct public.\"Brand\".\"id_brand\", \"nameBrand\", public.\"Deal\".\"id_productCategory\" from public.\"Brand\" \n" +
            "left join public.\"Deal\" on public.\"Deal\".\"id_brand\" = public.\"Brand\".\"id_brand\" \n" +
            "left join public.\"ProductCategory\" on public.\"Deal\".\"id_productCategory\" = public.\"ProductCategory\".\"id_productCategory\" order by public.\"Brand\".\"id_brand\" ";


    private DatabaseConnect dbConnect;

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


    public FunnelController() throws SQLException {
        dbConnect = new DatabaseConnect("SalesFunnel", PASSWORD);
    }

    public String getSettings() throws SQLException, IOException {
        Setting setting = new Setting();
        ResultSet rs = dbConnect.select(GET_SOURCES_SQL);
        while (rs.next()) {
            setting.addOutlet(rs.getInt(1), rs.getString(2));
        }
        rs = dbConnect.select(GET_TYPES_SQL);
        while (rs.next()) {
            setting.addTarget(rs.getInt(1), rs.getString(2));
        }
        rs = dbConnect.select(GET_CATEGORYES_SQL);
        while (rs.next()) {
            setting.addCategorie(rs.getInt(1), rs.getString(2));
        }
        rs = dbConnect.select(GET_BRANDS_SQL);
        while (rs.next()) {
            Brand brand = setting.findBrand(rs.getInt(1));
            if (brand != null) {
                Integer idCat = rs.getInt(3);
                brand.addCategory(idCat);
            } else {
                brand = new Brand(rs.getInt(1), rs.getString(2));
                if (rs.getObject(3) != null) {
                    brand.addCategory(rs.getInt(3));
                }
                setting.addBrand(brand);
            }
        }

        StringWriter stringWriter = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(stringWriter, setting);
        return stringWriter.toString();

    }

    public String getStats(List<String> sources, String clientType, List<String> brands, String category, String startDate, String endDate) throws SQLException, IOException {
        Stat stat = new Stat();

        ResultSet rs = getResultStatistic(sources, clientType, brands, category, startDate, endDate);

        while (rs.next()) {
            stat.addSale(rs.getString(1), rs.getDouble(3), rs.getInt(2));
        }

        rs = getSourceStatistic(sources, clientType, brands, category, startDate, endDate);

        while (rs.next()) {
            stat.addSource(rs.getString(1), rs.getDouble(3));
        }

        StringWriter stringWriter = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(stringWriter, stat);
        return stringWriter.toString();
    }

    private ResultSet getResultStatistic(List<String> sources, String clientType, List<String> brands, String category, String startDate, String endDate) throws SQLException {
        return getStatistic(SELECT_RESULT_SQL, GROUP_RESULT_SQL, sources, clientType, brands, category, startDate, endDate);
    }

    private ResultSet getSourceStatistic(List<String> sources, String clientType, List<String> brands, String category, String startDate, String endDate) throws SQLException {
        return getStatistic(SELECT_SOURCE_SQL, GROUP_SOURCE_SQL, sources, clientType, brands, category, startDate, endDate);
    }

    private ResultSet getStatistic(String select, String group, List<String> sources, String clientType, List<String> brands, String category,
                                   String startDate, String endDate) throws SQLException {
        String filterSql = getFilterSQL(sources, clientType, brands, category, startDate, endDate);
        ResultSet rs = dbConnect.select(select + COUNT_CLIENT_SELECT_SQL
                + FROM_SQL + select + GENERAL_SQL + filterSql + group + NAME_PERCENT_SQL
                + FROM_SQL + select + GENERAL_SQL + filterSql + group);

        return rs;
    }

    private String getFilterSQL(List<String> sources, String clientType, List<String> brands, String category, String startDate, String endDate) throws SQLException {
        String filterSql = listStringToFilter(sources, Filter.SOURCE);
        filterSql += AND;
        filterSql += listStringToFilter(brands, Filter.BRAND);
        filterSql += AND;
        filterSql += stringTotFilter(category, Filter.CATEGORY);
        filterSql += AND;
        filterSql += stringTotFilter(clientType, Filter.CLIENT_TYPE);
        filterSql += AND;
        filterSql += filterDate(startDate, endDate);

        return filterSql;
    }


    private String stringTotFilter(String str, Filter filterName) {
        return OPEN_BRACKET +  filterName.getLongName()  + EQUALLS + str + CLOSE_BRACKET;
    }

    private String filterDate(String start, String end) {
        return (OPEN_BRACKET +  Filter.DATE.getLongName() + LARGER + ONE_QUOTE + start + ONE_QUOTE
                + AND +  Filter.DATE.getLongName()  + SMALLER + ONE_QUOTE + end + ONE_QUOTE + CLOSE_BRACKET);

    }

    private String listStringToFilter(List<String> list, Filter filterName) {
        String sqlFilter = OPEN_BRACKET;
        for (String str : list) {
            sqlFilter +=  filterName.getLongName() + EQUALLS + str + OR;
        }
        sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 3);
        sqlFilter += CLOSE_BRACKET;
        return sqlFilter;
    }
}
