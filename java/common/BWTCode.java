import java.util.Arrays;

/**
 * BWT编码
 */
public class BWTCode {
    public static void main(String[] args) {
        //String str = "GATCAAATGGGGGATCGATC";
        String str = "GATCAAATGGGGGATCGATCGATCGATCCCCCATTTGATC";
        System.out.println("输入的字符串是：" + str);
        String enCodeStr = enCode(str);
        System.out.println("编码后的字符串是：" + enCodeStr);
        System.out.println("解码后的字符串是：" + deCode(enCodeStr));
    }
    public static String enCode(String line) {
        String str = line + "&";
        int len = str.length();
        char[] charArray = str.toCharArray();
        char[][] ch = new char[len][len];
        for (int i = 0; i < len; i++) {
            char[] c_tmp = charArray.clone();
            for (int j = 0; j < len; j++) {
                ch[i][j] = c_tmp[j];
                if (j <= len - 2) charArray[j + 1] = c_tmp[j];
            }
            charArray[0] = c_tmp[len - 1];
        }
        String[] strings = new String[len];
        for (int i = 0; i < len; i++) {
            StringBuffer chline = new StringBuffer();
            for (char c : ch[i]) {
                chline.append(c);
            }
            strings[i] = chline.toString();
        }
        Arrays.sort(strings);
        StringBuffer sBuffer = new StringBuffer();
        for (String s : strings) {
            sBuffer.append(s.substring(len - 1, len));
        }
        return sBuffer.toString();
    }

    public static String deCode(String str) {
        int len = str.length();
        String[] strArr = new String[len];
        for (int i = 0; i < len; i++) {
            strArr[i] = str.substring(i, i + 1) + ":" + i;
        }
        Arrays.sort(strArr);
        StringBuffer sb = new StringBuffer();
        int num = 0;
        int corr = Integer.parseInt(strArr[0].split(":")[1]);
        while (num < len - 1) {
            sb.append(strArr[corr].split(":")[0]);
            corr = Integer.parseInt(strArr[corr].split(":")[1]);
            num++;
        }
        return sb.toString();

    }
}