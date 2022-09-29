package generate.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxUtils {

    public static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) &&
                Character.isUpperCase(name.charAt(0))){
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 下划线转驼峰法，如果是以下划线+数字结尾则移除该分表后缀
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String removeShardSuffix(String line) {
        int index = line.lastIndexOf('_');
        if (index > 0) {
            String suffix = line.substring(index+1);
            if (StringUtils.isNumeric(suffix)) {
                return line.substring(0, index);
            }
        }
        return line;
    }

    public static String removePrefix(String line) {
        int index = line.indexOf('_');
        if (index > 0) {
            return line.substring(index + 1);
        }
        return line;
    }

    /**
     * 驼峰法转下划线
     *
     * @param camel 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String camel) {
        if (camel == null || "".equals(camel)) {
            return "";
        }
        camel = String.valueOf(camel.charAt(0)).toUpperCase().concat(camel.substring(1));
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(camel);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == camel.length() ? "" : "_");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String line = "uk_order_id_app_id";
        String camel = underline2Camel(line, true);
        System.out.println(camel);
        System.out.println(underline2Camel(line, false));
        System.out.println(camel2Underline(camel));
        System.out.println(underline2Camel("uk_order_id_app_id", false));

        //System.out.println(underline2Camel("iHaveAnIpang3Pig"));
    }

}
