package br.com.ms_gestao_itens.cargaMassiva;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CondicaoParaExecutar implements Condition {

        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String property = context.getEnvironment().getProperty("batch.execute");
            return "true".equalsIgnoreCase(property);
        }
}
