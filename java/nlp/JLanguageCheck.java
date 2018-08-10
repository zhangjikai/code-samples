import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.MultiThreadedJLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.AbstractPatternRule;
import org.languagetool.rules.patterns.PatternRuleLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 使用JLanguageTool 检测文本语法和语义错误, 可自定义规则
 * Created by zhangjikai on 16-3-30.
 */
public class JLanguageCheck {
    public void addOtherRules() throws IOException {
        String sentence = "When the spell checker finds an questionable word \n bed English lalah";
        MultiThreadedJLanguageTool languageTool = new MultiThreadedJLanguageTool(new BritishEnglish(), new AmericanEnglish());

        // 添加其他规则
        PatternRuleLoader ruleLoader = new PatternRuleLoader();
        try(InputStream in = JLanguageCheckTest.class.getClassLoader().getResourceAsStream("rules/rules-en-English.xml")) {
            List<AbstractPatternRule> externalRules = ruleLoader.getRules(in, "");
            for(AbstractPatternRule externalRule : externalRules) {
                languageTool.addRule(externalRule);
            }
        }

        List<RuleMatch> matches = null;
        matches = languageTool.check(sentence);
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());
        for (RuleMatch match : matches) {

            System.out.println("ErrorMsg: " + sentence.substring(match.getFromPos(), match.getToPos()));
            System.out.println("Column: " + match.getColumn());
            System.out.println("EndColumn: " + match.getEndColumn());
            System.out.println("FromPos: " + match.getFromPos());
            System.out.println("ToPos: " + match.getToPos());
            System.out.println("Line: " + match.getLine());
            System.out.println("EndLine: " + match.getEndLine());
            System.out.println("Message: " + match.getMessage());
            System.out.println("ShortMessage: " + match.getShortMessage());
            System.out.println("Suggestion: " + match.getSuggestedReplacements());

            System.out.println("CorrectExamples: " + match.getRule().getCorrectExamples());
            System.out.println("InCorrectExamples: " + match.getRule().getIncorrectExamples());
            System.out.println("Description: " + match.getRule().getDescription());
            System.out.println("Category: " + match.getRule().getCategory());

            System.out.println();

        }

    }
}
