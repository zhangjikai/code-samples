import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 将读入的文件一行行的进行排序, 使用TreeMap
 */
public class SortFile {
    Map<String, FileData> sortMap = new ExtendTreeMap(new ExtendComparator());

    public void sort(String fileName) throws Exception {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String lineValue = "";
        StringBuilder builder = null;
        FileData fileData = null;
        while ((lineValue = br.readLine()) != null) {
            fileData = new FileData();
            fileData.value = lineValue;
            builder = new StringBuilder(lineValue);
            sortMap.put(builder.toString(), fileData);
        }
        br.close();
        fr.close();
        File outputFile = new File(fileName + "_sort");
        if (!outputFile.exists())
            outputFile.createNewFile();
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);

        for (Map.Entry<String, FileData> entry : sortMap.entrySet()) {
            for (int i = 0; i < entry.getValue().count; i++) {
                bw.write(String.valueOf(entry.getValue()));
            }
        }
        bw.close();
        fw.close();
    }

    public static void main(String[] args) throws Exception {
        SortFile sortFile = new SortFile();
        String filePath = "data.txt";
        sortFile.sort(filePath);
    }
}

class FileData {
    String value = "";
    int count = 1;

    @Override
    public String toString() {
        return value + "\n";
    }
}

/**
 * 自定义比较类
 * 主要作用就是12aa 和 2aa 比时, 2aa在前面
 */
class ExtendComparator implements Comparator<String> {


    private boolean isNumber(char c) {
        int num = (int) c;
        if (num > 47 && num < 58) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compare(String o1, String o2) {

        int len1 = o1.length();
        int len2 = o2.length();
        int lim = Math.min(len1, len2);

        int k = 0;
        int num1, num2;
        char c1, c2;
        for (int i = 0; i < lim; i++) {

            c1 = o1.charAt(i);
            c2 = o2.charAt(i);

            if (c1 == c2) {
                continue;
            }

            if (isNumber(c1)) {
                num1 = 256 + c1;
            } else {
                num1 = c1;
            }

            if (isNumber(c2)) {
                num2 = 256 + c2;
            } else {
                num2 = c2;
            }

            if (i == 0) {
                return num1 - num2;
            }


            if (isNumber(c1) && isNumber(c2)) {
                if (i + 1 >= lim) {
                    return num1 - num2;
                }

                c1 = o1.charAt(i + 1);
                c2 = o2.charAt(i + 1);

                if (isNumber(c1) && !isNumber(c2)) {
                    return 1;
                }

                if (isNumber(c2) && !isNumber(c1)) {
                    return -1;
                }
            }
            return num1 - num2;




        }
        return len1 - len2;
    }
}

/**
 * 处理两行内容相同的情况
 */
class ExtendTreeMap extends TreeMap {

    public ExtendTreeMap() {}

    public ExtendTreeMap(Comparator comparator) {
        super(comparator);
    }

    @Override
    public Object put(Object key, Object value) {
        if (this.containsKey(key)) {
            FileData fileData = (FileData) this.get(key);
            fileData.count++;
            return null;
        }
        return super.put(key, value);
    }
}
