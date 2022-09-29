package generate.internal.generate;

import generate.internal.model.ClassInfo;
import generate.internal.model.DBInfo;
import generate.internal.model.MetaDataInfo;
import generate.template.*;
import generate.utils.CloseableUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AutoGenerate {


    /**
     * 数据库连接信息
     */
    private DBInfo dBInfo;

    /**
     * 表元数据
     */
    private MetaDataInfo metaDataInfo;

    /**
     * 生成代码类信息
     */
    private ClassInfo classInfo;

    public AutoGenerate(DBInfo dBInfo, ClassInfo classInfo) {
        this.dBInfo = dBInfo;
        this.classInfo = classInfo;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        //创建连接
        Class.forName(dBInfo.getDriver());
        return DriverManager.getConnection(dBInfo.getUrl(), dBInfo.getName(), dBInfo.getPassword());
    }


    public void generate() {
        String[] tablenames = dBInfo.getTableNames();
        if (null == tablenames || tablenames.length == 0) {
            throw new IllegalArgumentException("tablenames must not null and length > 0");
        }

        String path = AutoGenerate.class.getClassLoader().getResource("").getPath();
        Connection conn = null;
        try {
            conn = getConnection();
            for (String tablename : tablenames) {
                this.metaDataInfo = new MetaDataInfoGenerate().getMetaDataInfo(conn, dBInfo, tablename, classInfo);
                File templateDir = new File(path);
                new MybatisXMLTemplateGenerate().generateMybatisXML(templateDir, metaDataInfo, classInfo);
//                new UkTemplateGenerate(templateDir, dBInfo, classInfo, metaDataInfo).generate();
//                new ConverterTemplateGenerate(templateDir, dBInfo, classInfo, metaDataInfo).generate();
                new MapperTemplateGenerate(classInfo, metaDataInfo).generate();
                new DaoTemplateGenerate(classInfo, metaDataInfo).generate();
                new DaoImplTemplateGenerate(classInfo, metaDataInfo).generate();
                new ServiceTemplateGenerate(classInfo, metaDataInfo).generate();
                new ServiceImplTemplateGenerate(classInfo, metaDataInfo).generate();
//                new BaseQueryTemplateGenerate(templateDir, dBInfo, classInfo).generate();
//                SortColumn.createTemplateGenerate(templateDir, dBInfo, classInfo).generate();
//                new SortModeTemplateGenerate(templateDir, dBInfo, classInfo).generate();
                new EntityTemplateGenerate(classInfo, metaDataInfo).generate();
                new DtoTemplateGenerate(classInfo, metaDataInfo).generate();
                new VoTemplateGenerate(classInfo, metaDataInfo).generate();
                new QueryTemplateGenerate(classInfo, metaDataInfo).generate();
                new QueryDtoTemplateGenerate(classInfo, metaDataInfo).generate();
                new QueryVoTemplateGenerate(classInfo, metaDataInfo).generate();
                new ControllerTemplateGenerate(classInfo, metaDataInfo).generate();
                new FacadeTemplateGenerate(classInfo, metaDataInfo).generate();
                new FacadeImplTemplateGenerate(classInfo, metaDataInfo).generate();

                // 测试类
//                new TestTemplateGenerate(templateDir, dBInfo, classInfo, metaDataInfo).generate();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeConn(conn);
        }

    }
}