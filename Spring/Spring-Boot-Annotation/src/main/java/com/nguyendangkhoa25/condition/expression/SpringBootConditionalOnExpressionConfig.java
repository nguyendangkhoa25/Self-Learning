package com.nguyendangkhoa25.condition.expression;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootConditionalOnExpressionConfig {
    @Bean
    @ConditionalOnExpression(value = "${on.expression.enabled:true}")
    public ExpressionModule expressionModule() {
        return new ExpressionModule();
    }
}
