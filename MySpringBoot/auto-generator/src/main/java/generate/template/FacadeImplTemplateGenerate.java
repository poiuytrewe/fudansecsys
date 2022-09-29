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

public class FacadeImplTemplateGenerate {

    private static final String FACADE_IMPL_FORMAT_FTL = "FacadeImplFormat.ftl";

    private ClassInfo classInfo;

    private MetaDataInfo metaDataInfo;

    public FacadeImplTemplateGenerate(ClassInfo classInfo, MetaDataInfo metaDataInfo) {
        this.classInfo = classInfo;
        this.metaDataInfo = metaDataInfo;
    }


    public void generate() throws IOException, TemplateException {
        FileOutputStream fos = null;
        Writer out = null;
        try {
            String facadeName = classInfo.getClassSimpleName() + "FacadeImpl.java";
            Template template = FreeMarkerTemplate.getTemplate(FACADE_IMPL_FORMAT_FTL);
            File dir = new File(classInfo.getSaveDirPath(), "/facade/impl");
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new RuntimeException("创建文件夹失败:" + dir.getAbsolutePath());
                }
            }

            File daoFile = new File(dir, facadeName);
            fos = new FileOutputStream(daoFile, false);
            Map<String, Object> dataMap = BeanExUtils.introspect(classInfo);
            dataMap.putAll(BeanExUtils.introspect(metaDataInfo));
            dataMap.put("idType", metaDataInfo.getPrimaryColumnClass().getJavaType());
            dataMap.put("id", metaDataInfo.getPrimaryColumnClass().getColumnProperty());
            System.out.println("facadeImplFile:" + daoFile.getAbsoluteFile());
            out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
            template.process(dataMap, out);
        } finally {
            CloseableUtils.close(out);
            CloseableUtils.close(fos);
        }

    }
}
