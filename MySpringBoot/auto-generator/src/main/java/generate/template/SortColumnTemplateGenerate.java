package generate.template;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import generate.internal.generate.FreeMarkerTemplate;
import generate.internal.model.ClassInfo;
import generate.utils.BeanExUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SortColumnTemplateGenerate {

    private static final String baseQueryTemplate = "SortColumn.ftl";

    private ClassInfo classInfo;


    public SortColumnTemplateGenerate(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }


    public void generate() throws IOException, TemplateException {
        String fileName = "SortColumn.java";
        Template template = FreeMarkerTemplate.getTemplate(baseQueryTemplate);

        File queryDir = new File(classInfo.getSaveDirPath(), "query");
        if (!queryDir.exists()) {
            if (!queryDir.mkdirs()) {
                throw new RuntimeException("创建文件夹失败:" + queryDir.getAbsolutePath());
            }
        }

        File sortColumnFile = new File(queryDir, fileName);
        FileOutputStream fos = new FileOutputStream(sortColumnFile, false);
        Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
        System.out.println("SortColumnFile:" + sortColumnFile.getAbsoluteFile());
        //Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
        template.process(dataMap, out);
    }
}
