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
import java.util.Map;

public class QueryTemplateGenerate {

    private static final String baseQueryTemplate = "Query.ftl";

    private ClassInfo classInfo;

    private MetaDataInfo metaDataInfo;


    public QueryTemplateGenerate(ClassInfo classInfo, MetaDataInfo metaDataInfo) {
        this.classInfo = classInfo;
        this.metaDataInfo = metaDataInfo;
    }


    public void generate() throws IOException, TemplateException {
        Writer out = null;
        FileOutputStream fos = null;
        try {
            String query = classInfo.getClassSimpleName() + classInfo.getQuerySuffix() + ".java";
            Template template = FreeMarkerTemplate.getTemplate(baseQueryTemplate);

            File queryDir = new File(classInfo.getSaveDirPath(), "query");
            if (!queryDir.exists()) {
                if (!queryDir.mkdirs()) {
                    throw new RuntimeException("创建文件夹失败:" + queryDir.getAbsolutePath());
                }
            }

            File queryFile = new File(queryDir, query);
            fos = new FileOutputStream(queryFile, false);
            Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
            dataMap.putAll(BeanExUtils.introspect(metaDataInfo));
            dataMap.put("idType", metaDataInfo.getPrimaryColumnClass().getJavaType());
            dataMap.put("id", metaDataInfo.getPrimaryColumnClass().getColumnProperty());
            System.out.println("QueryFile:" + queryFile.getAbsoluteFile());
            out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
            template.process(dataMap, out);
        } finally {
            CloseableUtils.close(out);
            CloseableUtils.close(fos);
        }

    }


}
