package generate.template;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import generate.internal.generate.FreeMarkerTemplate;
import generate.internal.model.ClassInfo;
import generate.internal.model.MetaDataInfo;
import generate.utils.BeanExUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FacadeTemplateGenerate {

    private static final String FACADE_FORMAT_FTL = "FacadeFormat.ftl";

    private ClassInfo classInfo;

    private MetaDataInfo metaDataInfo;


    public FacadeTemplateGenerate(ClassInfo classInfo, MetaDataInfo metaDataInfo) {
        this.classInfo = classInfo;
        this.metaDataInfo = metaDataInfo;
    }


    public void generate() throws IOException, TemplateException {
        String facadeName = classInfo.getClassSimpleName() + "Facade.java";
        Template template = FreeMarkerTemplate.getTemplate(FACADE_FORMAT_FTL);
        File dir = new File(classInfo.getSaveDirPath(), "/facade");
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("创建文件夹失败:" + dir.getAbsolutePath());
            }
        }

        File facadeFile = new File(dir, facadeName);
        FileOutputStream fos = new FileOutputStream(facadeFile, false);
        Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
        dataMap.putAll(BeanExUtils.introspect(metaDataInfo));
        dataMap.put("idType", metaDataInfo.getPrimaryColumnClass().getJavaType());
        dataMap.put("id", metaDataInfo.getPrimaryColumnClass().getColumnProperty());
        System.out.println("facadeFile:" + facadeFile.getAbsoluteFile());
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
        template.process(dataMap, out);
    }
}
