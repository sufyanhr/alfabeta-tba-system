package com.alfabeta.core.helper;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
/**
 * تحميل الملفات من الموارد (resources/) بشكل آمن وسريع.
 */

public class RuleSpelHelper {
    private static final ExpressionParser parser = new SpelExpressionParser();
    public static Object evaluate(String expression, Object contextObject) {
        StandardEvaluationContext context = new StandardEvaluationContext(contextObject);
        return parser.parseExpression(expression).getValue(context);
    }
}
