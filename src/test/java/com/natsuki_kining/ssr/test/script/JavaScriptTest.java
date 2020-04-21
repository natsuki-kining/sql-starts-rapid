package com.natsuki_kining.ssr.test.script;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import com.natsuki_kining.ssr.intercept.script.JavaScriptIntercept;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试javascript
 *
 * @Author natsuki_kinig
 * @Date 2020/4/20 10:42
 **/
public class JavaScriptTest {

    /**
     * 测试基础数据类型
     */
    @Test
    public void baseDataType(){
        QueryScriptIntercept scriptIntercept = new JavaScriptIntercept();
        String script = "ssrResult = ssrParams + 1";
        int ssrParams = 2;
        //只能是double类型接收
        double ssrResult = scriptIntercept.executeScript(script, ssrParams);
        System.out.println(ssrResult);


        String script2 = "ssrResult = ssrParams > 1";
        int ssrParams2 = 2;
        //只能是double类型接收
        Object ssrResult2 =  scriptIntercept.executeScript(script2, ssrParams2);
        System.out.println(ssrResult2);

        String script3 = "if(ssrParams > 1){ssrResult = false}else{ssrResult = true}";
        int ssrParams3 = 2;
        //只能是double类型接收
        Object ssrResult3 =  scriptIntercept.executeScript(script3, ssrParams3);
        System.out.println(ssrResult3);

        String charScript = "ssrResult = ssrParams + 1";
        char charSsrParams = 'a';
        //只能是double类型接收
        Object charResult =  scriptIntercept.executeScript(charScript, charSsrParams);
        System.out.println(charResult);
    }

    /**
     * 传对象跟接收对象类型
     */
    @Test
    public void objectType(){
        QueryScriptIntercept scriptIntercept = new JavaScriptIntercept();
        QueryParams params = new QueryParams();
        params.setCode("query-user");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name","zhangsan");
        paramMap.put("age",18);
        params.setParams(paramMap);

        String script = "ssrResult = ssrParams";
        QueryParams result = scriptIntercept.executeScript(script, params);
        System.out.println(params == result);

        String script2 = "ssrParams.code='query-user-list';ssrResult = ssrParams;";
        QueryParams result2 = scriptIntercept.executeScript(script2, params);
        System.out.println(params == result2);

        //返回自定义类型
        String script3 = "ssrResult = {count:11,pageSize:10,pageNo:1}";
        Map result3 = scriptIntercept.executeScript(script3, params);
        System.out.println(result3.get("count"));
    }
}
