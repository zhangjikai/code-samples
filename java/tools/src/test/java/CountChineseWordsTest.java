import com.zhangjikai.tools.CountChineseWords;
import org.junit.Test;

/**
 * @author Jikai Zhang
 * @date 2018-01-22
 */
public class CountChineseWordsTest {
    
    @Test
    public void testIsChineseChar() {
        String text = "中文测试1234haha，,";
        for (char c : text.toCharArray()) {
            System.out.println(c + ": " + CountChineseWords.isChineseChar(c));
        }
        
        for (char c : text.toCharArray()) {
            System.out.println(c + ": " + CountChineseWords.isChinesePunctuation(c));
        }
    }
    
}
