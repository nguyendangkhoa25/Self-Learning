package com.nguyendangkhoa25.condition.onmissingclass;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingClass(value = "com.nguyendangkhoa25.condition.onmissingclass.OnRequiredClass")
public class ConditionalOnMissingClassConfiguration {
}
