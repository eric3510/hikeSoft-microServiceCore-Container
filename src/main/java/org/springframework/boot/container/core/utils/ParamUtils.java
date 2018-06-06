package org.springframework.boot.container.core.utils;

import org.springframework.boot.container.core.annotation.ApiModelProperty;
import org.springframework.boot.container.core.constant.Constant;
import org.springframework.boot.container.core.pojo.BaseDTO;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王强 eric3510@foxmail.com
 * @version 创建时间：2017/09/27 13:50
 * BusinessException
 **/
public class ParamUtils{
    private static final String ERROR_FIELD_NULL = "参数%s不能为空或者空字符串";

    /**
     * 出现异常时没有code的dto参数判空,没有code的时候，默认取常量的参数null异常code
     *
     * @param dto
     * @throws IllegalAccessException
     */
    public static void isNullDto(Object dto){
        ifNull(dto.getClass().getDeclaredFields(), ifObjectNull(dto), null, Constant.Resule.FAILURE.code());
    }

    /**
     * 出现异常时有code的dto参数判空
     *
     * @param dto
     * @throws IllegalAccessException
     */
    public static void isNullDto(Object dto, int code){
        ifNull(dto.getClass().getDeclaredFields(), ifObjectNull(dto), null, code);
    }

    /**
     * 综合功能的参数判断
     *
     * @param dto            dto实例化对象
     * @param nullFieldNames 可以为空的字段名
     */
    public static void isNullDto(Object dto, Map<String, String> nullFieldNames){
        ifNull(dto.getClass().getDeclaredFields(), ifObjectNull(dto), nullFieldNames, Constant.Resule.FAILURE.code());
    }

    /**
     * 判断dto是否实例化，是否为BaseDTO对象
     *
     * @param dto
     * @return
     */
    private static Object ifObjectNull(Object dto){
        if(dto == null){
            throw new BusinessException("DTO不能为null");
        }

        if(!(dto instanceof BaseDTO)){
            throw new BusinessException("参数必须为继承了BaseDTO的DTO实例化对象");
        }
        return dto;
    }

    /**
     * 综合功能的参数判断
     *
     * @param fields
     * @param dto
     * @param nullFieldNames
     * @param code
     */
    private static void ifNull(Field[] fields, Object dto, Map<String, String> nullFieldNames, int code){
        if(nullFieldNames == null){
            nullFieldNames = new HashMap<>();
        }
        for(Field field : fields){
            field.setAccessible(true);
            Object val = null;
            try{
                val = field.get(dto);
            }catch(IllegalAccessException e){
                e.printStackTrace();
            }
            String fieldName = field.getName();
            String nullFieldName = nullFieldNames.get(fieldName);
            //忽略不进行判断的字符
            if(nullFieldName == null || ("".equals(nullFieldName))){
                if(val == null || "".equals(val)){
                    String exceptionStr = " 参数 " + fieldName + " 不能为空或者空字符串";
                    BusinessException businessException = new BusinessException(exceptionStr);
                    if(code != -1){
                        exceptionStr = "code = " + code + exceptionStr;
                        businessException = new BusinessException(exceptionStr);
                        businessException.setCode(code);
                    }
                    throw businessException;
                }
            }
        }
    }

    /***
     * 判断不可为空的字段是否为空
     * @param dto 业务传输对象
     */
    @Deprecated
    public static void isApiDtoNull(Object dto){
        Class c = dto.getClass();
        Field[] fields = c.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if(apiModelProperty == null){
                continue;
            }
            //是否不可为空
            if(apiModelProperty.required() == true){
                if(BaseUtils.ObjectUtils.invokeMethod(dto, "get" + BaseUtils.StringUtilsSon.firstLetterUpperCase(field.getName())) == null){
                    throw new BusinessException(String.format(ParamUtils.ERROR_FIELD_NULL, field.getName()));
                }
            }
        }
    }

    /***
     * 判断不可为空的字段是否为空
     * @param dto 业务传输对象
     */
    public static void isApiDtoBlank(Object dto){
        Class c = dto.getClass();
        Field[] fields = c.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if(apiModelProperty == null){
                continue;
            }
            //是否不可为空
            if(apiModelProperty.required() == true){
                Object obj;
                BusinessException returnBusinessException = new BusinessException(String.format(ParamUtils.ERROR_FIELD_NULL, field.getName()));
                BusinessException businessException = (obj = BaseUtils.ObjectUtils.invokeMethod(dto, "get" + BaseUtils.StringUtilsSon.firstLetterUpperCase(field.getName()))) == null ? returnBusinessException : (obj instanceof String ? (BaseUtils.isBlank((String) obj)) ? returnBusinessException : null : null);
                if(businessException != null){
                    throw returnBusinessException;
                }
            }
        }
    }
}
