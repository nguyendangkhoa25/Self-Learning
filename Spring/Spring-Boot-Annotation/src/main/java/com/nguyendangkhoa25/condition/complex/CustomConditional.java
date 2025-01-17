package com.nguyendangkhoa25.condition.complex;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class CustomConditional extends SpringBootCondition {

    private static final String DATASOURCE_URL_PROPERTY = "conditional.datasource.url";
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder message = ConditionMessage.forCondition("ConditionalDataSource");
        if (hasDataSourceUrlProperty(context)) {
            return ConditionOutcome.match();
        }
        return ConditionOutcome.noMatch(message.because(DATASOURCE_URL_PROPERTY + " is NOT set"));
    }
    private boolean hasDataSourceUrlProperty(ConditionContext context) {
        Environment environment = context.getEnvironment();
        if (environment.containsProperty(DATASOURCE_URL_PROPERTY)) {
            try {
                return StringUtils.hasText(environment.getProperty(DATASOURCE_URL_PROPERTY));
            }
            catch (IllegalArgumentException ex) {
                // Ignore unresolvable placeholder errors
            }
        }
        return false;
    }
}
