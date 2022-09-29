package generate.template;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import generate.internal.generate.FreeMarkerTemplate;
import generate.internal.model.ClassInfo;
import generate.internal.model.MetaDataInfo;
import generate.utils.BeanExUtils;
import generate.utils.CloseableUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MybatisXMLTemplateGenerate {

    private static final String fieldListId = "_field_list";
    private static final String valueListId = "_value_list";
    private static final String commonWhereId = "_common_where";
    private static final String commonSortsId = "_common_sorts";

    /**
     * 换行
     */
    private static final String CHANGE_LINE = "\r\n";

    private static final String TAB = "\t";

    private static final Set<String> commonWhereColTypeIgnore = new HashSet<String>();

    private static final Set<String> commonWhereFiledNameIgnore = new HashSet<String>();

    private static String formatMapperTemplate = "Mybatis.ftl";

    // 默认排除Date类型 不在commonWhere条件中
    static {
        commonWhereColTypeIgnore.add("datetime");
    }


    private File generateMapperDir(String path) {
        File resultDir = new File(path);
        if (!resultDir.exists()) {
            resultDir.mkdirs();
        }
        return resultDir;
    }


    public void generateMybatisXML(File templateDir, MetaDataInfo metaDataInfo, ClassInfo classInfo) throws IOException, TemplateException {
        FileOutputStream fos = null;
        OutputStreamWriter write = null;
        Writer out = null;
        try {
            Template template = FreeMarkerTemplate.getTemplate(formatMapperTemplate);
            File dir = new File(classInfo.getSaveDirPath(), "/mybatis");
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new RuntimeException("创建文件夹失败:" + dir.getAbsolutePath());
                }
            }

            File mapperDir = generateMapperDir(dir.getPath());
            String mybatisXmlFileName = classInfo.getLogicTableName() + ".mapper.xml";

            File mybatisXmlFile = new File(mapperDir, mybatisXmlFileName);
            if (mybatisXmlFile.exists()) {
                mybatisXmlFile.delete();
            }

            System.out.println("mybatisXmlFile = [" + mybatisXmlFile.getAbsolutePath() + "]");
            fos = new FileOutputStream(mybatisXmlFile, false);
            write = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
            dataMap.putAll(BeanExUtils.introspect(metaDataInfo));
            dataMap.put("idType", metaDataInfo.getPrimaryColumnClass().getJavaType());
            dataMap.put("id", metaDataInfo.getPrimaryColumnClass().getColumnProperty());

            out = new BufferedWriter(write, 10240);
            template.process(dataMap, out);
        } finally {
            CloseableUtils.close(out);
            CloseableUtils.close(write);
            CloseableUtils.close(fos);
        }


    }


}
