package com.natsuki_kining.ssr.test.script;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryResult;
import com.natsuki_kining.ssr.intercept.AbstractQueryScriptIntercept;
import com.natsuki_kining.ssr.intercept.script.PythonScriptIntercept;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试python
 *
 * @Author natsuki_kinig
 * @Date 2020/4/20 10:42
 **/
public class PythonScriptTest {

    /**
     * 测试基础数据类型
     */
    @Test
    public void baseDataType() {
        AbstractQueryScriptIntercept scriptIntercept = new PythonScriptIntercept();
        String script = "ssrResult = ssrParams + 1";
        int ssrParams = 2;
        int ssrResult = scriptIntercept.executeScript(script, ssrParams, Integer.class);
        System.out.println(ssrResult);


        String script2 = "ssrResult = ssrParams > 1";
        int ssrParams2 = 2;
        boolean ssrResult2 = scriptIntercept.executeScript(script2, ssrParams2, Boolean.class);
        System.out.println(ssrResult2);

        String script3 = "if ssrParams > 1:\n" +
                "\tssrResult = False\n" +
                "else:\n" +
                "\tssrResult = True";
        int ssrParams3 = 2;
        Object ssrResult3 = scriptIntercept.executeScript(script3, ssrParams3);
        System.out.println(ssrResult3);

        String charScript = "ssrResult = ssrParams + 'b'";
        char charSsrParams = 'a';
        Object charResult = scriptIntercept.executeScript(charScript, charSsrParams);
        System.out.println(charResult);
    }

    /**
     * 传对象跟接收对象类型
     */
    @Test
    public void objectType() {
        AbstractQueryScriptIntercept scriptIntercept = new PythonScriptIntercept();
        QueryParams params = new QueryParams();
        params.setQueryCode("query-user");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "zhangsan");
        paramMap.put("age", 18);
        params.setParams(paramMap);

        String script = "ssrResult = ssrParams";
        QueryParams result = scriptIntercept.executeScript(script, params, QueryParams.class);
        System.out.println(params == result);

        String script2 = "ssrParams.queryCode='query-user-list';ssrResult = ssrParams;";
        QueryParams result2 = scriptIntercept.executeScript(script2, params, QueryParams.class);
        System.out.println(params == result2);

        //返回自定义类型
        String script3 = "class SsrResultTest: \n\tpass\n\nssrResult = SsrResultTest()\nssrResult.count=11\nssrResult.pageSize=10\nssrResult.pageNo=1\nssrResult.data=['a','b','c','d']";
        QueryResult queryResult3 = scriptIntercept.executeScript(script3, params, QueryResult.class);
        System.out.println("result.count:" + queryResult3.getCount());
        System.out.println("result.data:" + queryResult3.getData().get(0));
    }

}
