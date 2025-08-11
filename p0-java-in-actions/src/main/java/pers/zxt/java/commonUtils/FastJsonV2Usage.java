package pers.zxt.java.commonUtils;

import java.util.List;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.JSONReader;  // 其中的 Feature 用于配置解析时的配置项
import com.alibaba.fastjson2.JSONWriter;  // 其中的 Feature 用于配置序列化时的配置项
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;

/**
 * FastJSON-2 的用法和 1 的用法基本相同，大多数情况下直接替换包名就可以了.
 * (1) JSON 接口提供了一系列的默认实现的静态方法，常用如下：
 *   反序列化方法：
 *     - parse() 系列重载方法，用于将 JSON 字符串/bytes 等解析为 JSONObject 或者 JSONArray
 *     - parseObject() 系列重载方法，用于将 JSON 字符串/bytes 等解析为 JSONObject 或者 Java Bean
 *     - parseArray() 系列重载方法，用于将 JSON 字符串/bytes 等解析为 JSONArray 或者 Java Bean 的列表
 *   序列化方法：
 *     - toJSONString()
 *     - toJSONBytes()
 *     - writeTo()
 *     - toJSON(): 将指定对象转换成 JSONObject 或者 JSONArray
 *     - to()，用于将 JSON 值转换为指定类型的对象
 *   其他方法：
 *     - isValid()
 *     - isValidObject()
 *     - isValidArray()
 *
 * (2) JSONObject: 继承了JSON类，相当于一个JSON对象，本质上是一个Map<String, Object>, 提供了更为丰富便捷的方法，方便对象属性的操作。
 *
 * (3) JSONArray: 也继承了JSON类，相当于一个JSON数组，本质上是一个 List<Object>
 *
 * (4) 常用注解：
 *     - @JSONField: 用于指定属性的序列化和反序列化时的配置项，配置单个字段
 *     - @JSONType: 用于指定对象序列化和反序列化时的配置项，配置在类/接口上的注解
 */
public class FastJsonV2Usage {
    public static void main(String[] args) {
        basicUsage();
        advancedUsage();
        annotationUsage();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Student {
        private String name;
        private int age;
        private LocalDate birthday;
    }

    public static void basicUsage() {
        String jsonStr1 = "{\"k1\": \"v1\", \"k2\": 2, \"k3\": \"v3\"}";
        byte[] jsonStr1Bytes = jsonStr1.getBytes();
        String jsonStr2 = "[{\"k1\": \"v1\", \"k2\": 2}, {\"k3\": \"v3\", \"k4\": 4}]";
        String jsonStu1 = "{\"name\": \"Ming\", \"age\": 20, \"birthday\": \"2022-06-15\"}";
        String jsonStu2 = "[{\"name\": \"Hong\", \"age\": 18, \"birthday\": \"2024-06-15\"}]";

        // JSON -> JSONObject
        System.out.println("JSON -> JSONObject");
        JSONObject jsonObj1 = JSON.parseObject(jsonStr1);
        JSONObject jsonObj2 = JSON.parseObject(jsonStr1Bytes);

        // JSON -> JSONArray
        System.out.println("JSON -> JSONArray");
        JSONArray jsonArray1 = JSON.parseArray(jsonStr2);
        JSONArray jsonArray2 = JSON.parseArray(jsonStu2);

        // JSON -> Java Bean
        System.out.println("JSON -> Java Bean");
        Student student1 = JSON.parseObject(jsonStu1, Student.class);
        List<Student> students = JSON.parseArray(jsonStu2, Student.class);

        // JSONObject -> Java Bean
        JSONObject jsonObj3 = new JSONObject();
        jsonObj3.put("name", "Some");
        jsonObj3.put("age", "19");
        jsonObj3.put("birthday", "2020-12-12");
        // 方法1:
        Student s1 = jsonObj3.to(Student.class);
        // 方法2：
        Student s2 = JSON.to(Student.class, jsonObj3);
        // 方法3：
        Student s3 = JSON.parseObject(jsonObj3.toString(), Student.class);

        // Java Bean -> JSONObject : 这个场景比较少见
        System.out.println("Java Bean -> JSONObject");
        Student stu = new Student("Wang", 17, LocalDate.parse("2021-06-15"));
    }

    public static void advancedUsage() {
    }

    public static void annotationUsage() {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JSONType(
        typeName = "SomeBean",
        orders = {"id", "name", "birthday"},
        ignores = {"password"}
    )
    public static class SomeBean {
        @JSONField(name = "ID", ordinal = 1)
        private String id;

        @JSONField(name = "name", ordinal = 2)
        private String name;

        @JSONField(format = "yyyy/mm/dd", ordinal = 3)
        private LocalDate birthday;

        @JSONField(serialize = false)
        private String password;
    }

}
