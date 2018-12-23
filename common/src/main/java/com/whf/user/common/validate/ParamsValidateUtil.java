/*
 * @author caojiayao 2017年7月14日 下午6:25:42
 */
package com.whf.user.common.validate;

import com.google.common.base.Function;
import com.whf.user.common.annotation.Numeric;
import com.whf.user.common.annotation.NumericConstraint;
import com.whf.user.common.exception.CommonException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.collect.Lists.transform;
import static com.whf.user.common.enums.ResponseCode.ILLEGAL_ARGUMENT;
import static com.whf.user.common.enums.ResponseCode.MOBILE_FORMAT_ERROR;
import static java.lang.String.format;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import jodd.util.StringUtil;
import jodd.vtor.Check;
import jodd.vtor.Violation;
import jodd.vtor.Vtor;
import jodd.vtor.constraint.MaxLength;
import jodd.vtor.constraint.MaxLengthConstraint;
import jodd.vtor.constraint.NotNull;
import jodd.vtor.constraint.NotNullConstraint;

import static jodd.vtor.Vtor.create;
/**
 * <p>
 * 参数校验工具
 * <P>
 * 
 * @author caojiayao
 * @version $Id: ParamsValidateUtil.java, v 0.1 2017年7月14日 下午6:25:42 caojiayao
 *          Exp $
 */
public class ParamsValidateUtil {

    /**  **/
    public static final String VTOR_FORMAT = "%s(%s)字段%s,原值[%s]";

    /**
     * 校验入口
     * 
     * @param params
     * @throws CommonException
     */
    public static void validate(Object params) throws CommonException {

        validate(params, null);

    }

    /**
     * 校验入口
     * 
     * @param params
     * @param profile
     * @throws CommonException
     */
    public static void validate(Object params, String profile) throws CommonException {

        final Vtor vtor = create();
        vtor.useProfile(profile);

        final List<Violation> violations = vtor.validate(params);

        if (isEmpty(violations))
            return;

        final List<String> mags = transform(violations, new Function<Violation, String>() {
            @Override
            public String apply(Violation input) {
                return buildMsg(input.getCheck(),input.getInvalidValue());
            }
        });

        if (isNotEmpty(mags))
            throw new CommonException(ILLEGAL_ARGUMENT, mags.toString());

    }

    /**
     * 构建错误信息
     * 
     * @param check
     * @return
     */
    public static String buildMsg(Check check,Object oriValue) {
        if (check.getConstraint() instanceof MaxLengthConstraint)
            return msgFormat(check.getMessage(), check.getName(), "长度超限",fromNullable(oriValue).orNull());
        else if (check.getConstraint() instanceof NotNullConstraint)
            return msgFormat(check.getMessage(), check.getName(), "不能为空",fromNullable(oriValue).orNull());
        else if (check.getConstraint() instanceof NumericConstraint)
            return msgFormat(check.getMessage(), check.getName(), "只能是数值类型",fromNullable(oriValue).orNull());
        else if (check.getConstraint() instanceof ValidateTimeConstraint)
            return msgFormat(check.getMessage(), check.getName(), "日期格式不正确",fromNullable(oriValue).orNull());
        return check.getMessage();
    }

    /**
     * 国内手机号的正则效验
     * @param phone
     * @return
     */
    public static  void validateChinaPhone(String phone){
        if(StringUtil.isBlank(phone)){
            throw new CommonException(ILLEGAL_ARGUMENT, "手机号不能为空");
        }
        //移动
        Pattern patternYd = Pattern.compile("^1(3[4-9]|47|5[012789]|78|8[23478]|98)\\d{8}$|^170[356]\\d{7}$|^10648\\d{8}$");
        Matcher matcherYd = patternYd.matcher(phone);
        if(matcherYd.matches()){
            return ;
        }
        //联通
        Pattern patternLt = Pattern.compile("^1(3[0-2]|45|5[56]|7[156]|8[56]|66)\\d{8}|^170[4789]\\d{7}$|^10646\\d{8}$");
        Matcher matcherLt = patternLt.matcher(phone);
        if(matcherLt.matches()){
            return ;
        }
        //电信
        Pattern patternDx = Pattern.compile("^1([35]3|7[37]|8[019]|99)\\d{8}$|^170[012]\\d{7}$|^10649\\d{8}$");
        Matcher matcherDx = patternDx.matcher(phone);
        if(matcherDx.matches()){
            return ;
        }
        throw new CommonException(ILLEGAL_ARGUMENT, MOBILE_FORMAT_ERROR.getMsg());
    }

    /**
     * 报错文案
     * <p>
     * 示例:凭照号(field)字段长度超限
     * <p>
     * <p>
     * 格式:%s(%s)字段%s
     * <p>
     * 
     * @param args
     * @return
     */
    public static String msgFormat(Object... args) {
        return format(VTOR_FORMAT, args);
    }

    private static class TestVTor {
        @MaxLength(value = 14, message = "日期", profiles = { "1" })
        @NotNull(message = "日期", profiles = { "1" })
        @Numeric(message = "日期", profiles = { "1" })
//        @ValidateTime(message = "日期", format = "yyyy-MM-dd", profiles = { "1" })
        private String date;

        public TestVTor(String date) {
            this.date = date;
        }
    }

    public static void main(String[] args) {
        TestVTor testVTor = new TestVTor("2017-100812041");

        validate(testVTor,"1");
    }

}
