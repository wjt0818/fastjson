package com.alibaba.json.bvt.bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by wenshao on 11/03/2017.
 */
public class Issue1063_date extends TestCase {
    public void test_for_issue() throws Exception {
        long currentMillis = System.currentTimeMillis();
        TimestampBean bean = new TimestampBean();
        bean.setTimestamp(new Date(currentMillis));
        String timestampJson = JSON.toJSONString(bean);

        // 这里能转换成功
        TimestampBean beanOfJSON = JSON.parseObject(timestampJson, TimestampBean.class);

        // 这里抛异常 java.lang.NumberFormatException
        JSONObject jsonObject = JSON.parseObject(timestampJson);
        Timestamp timestamp2 = jsonObject.getObject("timestamp", Timestamp.class);
        assertEquals(currentMillis/1000, timestamp2.getTime() / 1000);
    }

    public static class TimestampBean {
        private Date timestamp = null;

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
    }
}
