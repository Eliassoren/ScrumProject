package com.CardiacArray.templateService.AuthFilter;

import com.CardiacArray.restService.AuthFilter.Role;
import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 *
 * Interface for the SecuredTpl filter used by the template service.
 *
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface SecuredTpl {
    Role[] value() default {};
}
