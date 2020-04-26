package com.natsuki_kining.ssr.test.script;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryResult;
import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import com.natsuki_kining.ssr.intercept.script.GroovyScriptIntercept;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试Groovy
 *
 * @Author natsuki_kinig
 * @Date 2020/4/20 10:42
 **/
public class GroovyScriptTest {

    /**
     * 测试基础数据类型
     */
    @Test
    public void baseDataType() {
        QueryScriptIntercept scriptIntercept = new GroovyScriptIntercept();
        String script = "ssrResult = ssrParams + 1";
        int ssrParams = 2;
        int ssrResult = scriptIntercept.executeScript(script, ssrParams, Integer.class);
        System.out.println(ssrResult);


        String script1Plus = "ssrResult = ssrParams + 1.1";
        double ssrParams1Plus = 2.2;
        double ssrResult1Plus = scriptIntercept.executeScript(script1Plus, ssrParams1Plus, Double.class);
        System.out.println(ssrResult1Plus);



        String script2 = "ssrResult = ssrParams > 1";
        int ssrParams2 = 2;
        boolean ssrResult2 = scriptIntercept.executeScript(script2, ssrParams2, Boolean.class);
        System.out.println(ssrResult2);

        String script3 = "if (ssrParams > 1){" +
                "ssrResult = false}" +
                "else{" +
                "ssrResult = true}";
        int ssrParams3 = 2;
        Object ssrResult3 = scriptIntercept.executeScript(script3, ssrParams3);
        System.out.println(ssrResult3);

        String charScript = "ssrResult = ssrParams + \"b\"";
        String charSsrParams = "a";
        Object charResult = scriptIntercept.executeScript(charScript, charSsrParams);
        System.out.println(charResult);
    }

    /**
     * 传对象跟接收对象类型
     */
    @Test
    public void objectType() {
        QueryScriptIntercept scriptIntercept = new GroovyScriptIntercept();
        QueryParams params = new QueryParams();
        params.setCode("query-user");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "zhangsan");
        paramMap.put("age", 18);
        params.setParams(paramMap);

        String script = "ssrResult = ssrParams";
        QueryParams result = scriptIntercept.executeScript(script, params, QueryParams.class);
        System.out.println(params == result);

        String script2 = "ssrParams.code='query-user-list';ssrResult = ssrParams;";
        QueryParams result2 = scriptIntercept.executeScript(script2, params, QueryParams.class);
        System.out.println(params == result2);

        //返回自定义类型
        String script3 = "class SsrResultTest{int count;int pageSize;int pageNo;List<Object> data;}\nssrResult = new SsrResultTest();ssrResult.count=11;ssrResult.pageSize=10;ssrResult.pageNo=1;ssrResult.data=['a','b','c','d'];";
        QueryResult queryResult3 = scriptIntercept.executeScript(script3, params,QueryResult.class);
        System.out.println("result.count:"+queryResult3.getCount());
        System.out.println("result.data:"+queryResult3.getData().get(0));
    }
}
