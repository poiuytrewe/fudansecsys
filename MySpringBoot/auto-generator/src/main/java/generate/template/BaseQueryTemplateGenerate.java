package generate.template;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import generate.internal.generate.FreeMarkerTemplate;
import generate.internal.model.ClassInfo;
import generate.utils.BeanExUtils;
import generate.utils.CloseableUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class BaseQueryTemplateGenerate {

    private static final String baseQueryTemplate = "BaseQuery.ftl";

    private ClassInfo classInfo;


    public BaseQueryTemplateGenerate(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }


    public void generate() throws IOException, TemplateException {
        Writer out = null;
        FileOutputStream fos = null;
        try {
            String query = "Base" + classInfo.getQuerySuffix() + ".java";
            Template template = FreeMarkerTemplate.getTemplate(baseQueryTemplate);
            File queryDir = new File(classInfo.getSaveDirPath(), classInfo.getQuerySuffix());
            if (!queryDir.exists()) {
                if (!queryDir.mkdirs()) {
                    throw new RuntimeException("创建文件夹失败:" + queryDir.getAbsolutePath());
                }
            }

            File queryFile = new File(queryDir, query);
            fos = new FileOutputStream(queryFile, false);
            Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
            System.out.println("BaseQueryFile:" + queryFile.getAbsoluteFile());
            out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
            template.process(dataMap, out);
        } finally {
            CloseableUtils.close(out);
            CloseableUtils.close(fos);
        }

    }
}
