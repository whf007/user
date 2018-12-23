package com.whf.user.common.validate;

import com.whf.user.common.annotation.NotNullWithDeclaredField;
import jodd.bean.BeanException;
import jodd.bean.BeanUtil;
import jodd.vtor.ValidationConstraint;
import jodd.vtor.ValidationConstraintContext;
import jodd.vtor.VtorException;
import lombok.Getter;
import lombok.Setter;

public class NotNullWithDeclaredFieldConstraint implements ValidationConstraint<com.whf.user.common.annotation.NotNullWithDeclaredField> {

    @Setter
    @Getter
    protected String fieldName;

    @Override
    public void configure(NotNullWithDeclaredField annotation) {
        this.fieldName = annotation.value();
    }

    @Override
    public boolean isValid(ValidationConstraintContext vcc, Object value) {
        return validate(vcc.getTarget(), value, this.fieldName);
    }

    public static boolean validate(Object target, Object value, String fieldName) {
        if (value != null) {
            return true;
        } else {
            Object valueToCompare;
            try {
                valueToCompare = BeanUtil.declared.getProperty(target, fieldName);
            } catch (BeanException arg4) {
                throw new VtorException("Invalid value: " + fieldName, arg4);
            }
            return valueToCompare == null ? false : true;
        }
    }
}
