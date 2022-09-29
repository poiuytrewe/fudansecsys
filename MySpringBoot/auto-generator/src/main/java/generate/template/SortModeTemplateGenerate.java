package generate.template;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import generate.internal.generate.FreeMarkerTemplate;
import generate.internal.model.ClassInfo;
import generate.utils.BeanExUtils;

import java.io.*;
import java.util.Map;

public class SortModeTemplateGenerate {

    private static final String baseQueryTemplate = "SortMode.ftl";

    private ClassInfo classInfo;


    public SortModeTemplateGenerate(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }


    public void generate() throws IOException, TemplateException {
        String fileName = "SortMode.java";
        Template template = FreeMarkerTemplate.getTemplate(baseQueryTemplate);

        File fileDir = new File(classInfo.getSaveDirPath(), "query");
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                throw new RuntimeException("创建文件夹失败:" + fileDir.getAbsolutePath());
            }
        }

        File sortModeFile = new File(fileDir, fileName);
        FileOutputStream fos = new FileOutputStream(sortModeFile, false);
        Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
        System.out.println("SortModeFile:" + sortModeFile.getAbsoluteFile());
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
    }
}
