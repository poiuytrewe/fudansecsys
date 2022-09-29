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

public class DtoTemplateGenerate {

    private static final String modelTemplate = "Dto.ftl";

    private ClassInfo classInfo;

    private MetaDataInfo metaDataInfo;


    public DtoTemplateGenerate(ClassInfo classInfo, MetaDataInfo metaDataInfo) {
        this.metaDataInfo = metaDataInfo;
        this.classInfo = classInfo;
    }


    public void generate() throws IOException, TemplateException {
        FileOutputStream fos = null;
        Writer out = null;
        try {
            String modelName = classInfo.getClassSimpleName() + "Dto.java";
            Template template = FreeMarkerTemplate.getTemplate(modelTemplate);
            File dir = new File(classInfo.getSaveDirPath(), "/dto");
            if (!dir.exists()) {
                if (!dir.mkdirs()){
                    throw new RuntimeException("创建文件夹失败:" + dir.getAbsolutePath());
                }
            }

            File modelFile = new File(dir, modelName);
            fos = new FileOutputStream(modelFile, false);
            Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
            dataMap.putAll(BeanExUtils.introspect(metaDataInfo));
            dataMap.put("idType", metaDataInfo.getPrimaryColumnClass().getJavaType());
            dataMap.put("id", metaDataInfo.getPrimaryColumnClass().getColumnProperty());
            System.out.println("DtoFile:" + modelFile.getAbsoluteFile());
            out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
            template.process(dataMap, out);
        } finally {
            CloseableUtils.close(out);
            CloseableUtils.close(fos);
        }

    }


}
