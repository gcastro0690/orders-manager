package com.accsa.sgot.web.configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion para hacer Spring DI de la clase {@code Logger} de slf4j. Esta anotacion es escaneada
 * por el procesador {@link LoggablePostProcessor} para DI.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Loggable {

}
