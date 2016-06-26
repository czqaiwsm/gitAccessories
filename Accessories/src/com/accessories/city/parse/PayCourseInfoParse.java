package com.accessories.city.parse;

import com.accessories.city.bean.PayCourseInfo;
import com.google.gson.reflect.TypeToken;
import com.accessories.city.utils.URLConstants;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

/**
 * @desc 验证码
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class PayCourseInfoParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getResult())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<PayCourseInfo>>() {
            }.getType());
        }
        return result;
    }
}
