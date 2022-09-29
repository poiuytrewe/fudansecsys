package generate;

import generate.internal.generate.AutoGenerate;
import generate.internal.model.ClassInfo;
import generate.internal.model.DBInfo;
import generate.utils.DateExUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class MainGeneral {

    public static void main(String[] args) throws IOException {
        Properties properties = load();
        DBInfo dbInfo = dbInfo(properties);
        ClassInfo classInfo = classInfo(properties);
        log.info("Generate Path: {}", classInfo.getSaveDirPath());
        new AutoGenerate(dbInfo, classInfo).generate();
    }

    private static Properties load() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(MainGeneral.class.getClassLoader().getResourceAsStream("user.conf"));
        } catch (Exception e) {
            properties.load(MainGeneral.class.getClassLoader().getResourceAsStream("default.conf"));
        }

        return properties;
    }

    private static DBInfo dbInfo(Properties properties) {
        String dbName = properties.getProperty("db.name");
        String url = "jdbc:mysql://" + properties.getProperty("mysql.address")
                + "/" + dbName
                + "?useUnicode=true&characterEncoding=UTF-8&useInformationSchema=true";
        String[] tables = Arrays.stream(properties.getProperty("tables").split(","))
                .map(String::trim)
                .toArray(String[]::new);
        return DBInfo.builder()
                .url(url)
                .name(properties.getProperty("mysql.username"))
                .password(properties.getProperty("mysql.password"))
                .driver(properties.getProperty("mysql.driver"))
                .dbName(dbName)
                .tableNames(tables)
                .tableNamePrefix(properties.getProperty("table.name.suffix"))
                .build();
    }

    private static ClassInfo classInfo(Properties properties) {
        String dbSchema = properties.getProperty("db.schema");
        String packagePrefix = properties.getProperty("package.prefix");
        if (StringUtils.isNotBlank(dbSchema)) {
            packagePrefix = packagePrefix + "." + dbSchema;
        }
        ClassInfo classInfo = new ClassInfo();
        classInfo.setPackagePrefix(packagePrefix);
        classInfo.setSaveDirPath(System.getProperty("user.home") + "/Downloads/DaoGenerate/");
        classInfo.setDatetime(DateExUtils.formatDatetime(new Date()));
        //classInfo.setUser(System.getProperty("user.name"));
        classInfo.setUser("Xinyue.Tang");
        classInfo.setQuerySuffix("Query");
        return classInfo;
    }
}
