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
import java.util.List;
import java.util.Map;

public class UkTemplateGenerate {

    private static final String modelTemplate = "Uk.ftl";

    private ClassInfo classInfo;

    private MetaDataInfo metaDataInfo;


    public UkTemplateGenerate(ClassInfo classInfo, MetaDataInfo metaDataInfo) {
        this.metaDataInfo = metaDataInfo;
        this.classInfo = classInfo;
    }


    public void generate() throws IOException, TemplateException {
        if (!classInfo.isSupportUk()) {
            return;
        }

        FileOutputStream fos = null;
        Writer out = null;
        List<UkClass> ukColumnList = metaDataInfo.getUkColumnList();
        for (UkClass ukClass : ukColumnList) {
            try {
                String ukName = ukClass.getUkName();
                String camelTableName = ukClass.getCamelTableName();
                ukName = camelTableName + String.valueOf(ukName.charAt(0)).toUpperCase().concat(ukName.substring(1));
                String modelName = ukName + classInfo.getQuerySuffix() + ".java";
                Template template = FreeMarkerTemplate.getTemplate(modelTemplate);
                File dir = new File(classInfo.getSaveDirPath(), classInfo.getQuerySuffix());
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        throw new RuntimeException("创建文件夹失败:" + dir.getAbsolutePath());
                    }
                }

                File modelFile = new File(dir, modelName);
                fos = new FileOutputStream(modelFile, false);
                Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
                dataMap.putAll(BeanExUtils.introspect(metaDataInfo));
                dataMap.put("idType", metaDataInfo.getPrimaryColumnClass().getJavaType());
                dataMap.put("id", metaDataInfo.getPrimaryColumnClass().getColumnProperty());
                System.out.println("UkFile:" + modelFile.getAbsoluteFile());
                out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
                template.process(dataMap, out);

            } finally {
                CloseableUtils.close(out);
                CloseableUtils.close(fos);
            }
        }

    }


}
