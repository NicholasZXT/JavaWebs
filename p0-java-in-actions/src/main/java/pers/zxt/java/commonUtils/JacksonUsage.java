package pers.zxt.java.commonUtils;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
// 常用注解
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Jackson 使用
 *
 * (1) 入口类 ObjectMapper，相当于 FastJSON 里的 JSON.
 *   反序列化方法：
 *     - readValue() 系列重载方法: 将 JSON 字符串/文件 反序列化为指定类型的 Java 对象。
 *     - readTree() 系列重载方法: 将 JSON 字符串/文件 转换为 JsonNode。
 *   序列化方法：
 *     - writeValue(File, Object): 将 Java 对象序列化为 JSON 并写入文件。
 *     - writeValueAsString(Object): 将 Java 对象序列化为 JSON 字符串。
 *     - writeTree(File, JsonNode): 将 JsonNode 序列化为 JSON 并写入文件。
 *   其他方法：
 *     - valueToTree(Object): 将 Java 对象转换为 JsonNode。
 *     - treeToValue(JsonNode, Class<T>): 将 JsonNode 转换为指定类型的 Java 对象。
 *     - convertValue(Object, Class<T>): 将 Java 对象转换为指定类型的 Java 对象。
 *
 * (2) JsonNode: JSON 节点，相当于 FastJSON 里的 JSONObject。
 *
 * (3) 常用注解：
 *   - @JsonProperty: 作用在属性上，用来为 JSON Key 指定一个别名
 *   - @JsonIgnore: 作用在属性上，用来忽略此属性，它将不参与 JSON 的序列化与反序列化。
 *   - @JsonIgnoreProperties: 定义在类上，忽略一组属性。
 *   - @JsonGetter: 对 Java 对象进行 JSON 序列化时自定义属性名称。
 *   - @JsonSetter: 对 JSON 进行反序列化时设置 JSON 中的 key 与 Java 属性的映射关系。
 *   - @JsonAnySetter: 标记在某个方法上，此方法接收 Key、Value，用于 Jackson 在反序列化过程中，未找到的对应属性都调用此方法。通常这个方法用一个 Map 来实现
 *   - @JsonAnyGetter: 标注在一个返回 Map 的方法上，Jackson 会取出 Map中的每一个值进行序列化
 *   - @JsonFormat: 自定义时间格式
 *
 * (4) 反序列化/序列化配置枚举类
 *   - SerializationFeature: 序列化配置枚举类
 *   - DeserializationFeature: 反序列化配置枚举类
 *
 */
public class JacksonUsage {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private String name;
        private Integer age;
        private List<String> skillList;
    }

    static void basicUsage() throws IOException {
        String jsonStr1 = "{\"k1\": \"v1\", \"k2\": 2, \"k3\": \"v3\"}";
        byte[] jsonStr1Bytes = jsonStr1.getBytes();
        String jsonStr2 = "[{\"k1\": \"v1\", \"k2\": 2}, {\"k3\": \"v3\", \"k4\": 4}]";
        String jsonPer1 = "{\"name\": \"Ming\", \"age\": 20, \"skillList\": [\"C++\", \"Python\"]}";
        String jsonPer2 = "[{\"name\": \"Hong\", \"age\": 18, \"skillList\": [\"Java\", \"Rust\"]}]";

        // 入口类，相当于 FastJSON 里的 JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON -> JsonNode
        System.out.println("JSON -> JsonNode");
        JsonNode jsonNode1 = objectMapper.readTree(jsonStr1);
        JsonNode jsonNode2 = objectMapper.readTree(jsonStr1Bytes);
        JsonNode jsonNode3 = objectMapper.readTree(jsonStr2);
        JsonNode jsonNode4 = objectMapper.readTree(jsonPer1);
        JsonNode jsonNode5 = objectMapper.readTree(jsonPer2);
        System.out.println(jsonNode1);
        System.out.println(jsonNode1.get("k1").asText());
        System.out.println(jsonNode2);
        System.out.println(jsonNode3);
        System.out.println(jsonNode4);
        System.out.println(jsonNode5);

        // JSON -> Java Bean
        System.out.println("JSON -> Java Bean");
        Person person1 = objectMapper.readValue(jsonPer1, Person.class);
        // 读取对象数组需要使用类型参数
        List<Person> persons = objectMapper.readValue(jsonPer2, new TypeReference<List<Person>>(){});
        System.out.println(person1);
        System.out.println(persons);

        // JsonNode -> JSON
        System.out.println("JsonNode -> JSON");
        String json1 = objectMapper.writeValueAsString(jsonNode4);
        System.out.println(json1);

        // Java Bean -> JSON
        System.out.println("Java Bean -> JSON");
        String json2 = objectMapper.writeValueAsString(person1);
        System.out.println(json2);

        // JsonNode -> Java Bean
        System.out.println("JsonNode -> Java Bean");
        Person person2 = objectMapper.treeToValue(jsonNode4, Person.class);
        System.out.println(person2);

        // Java Bean -> JsonNode
        System.out.println("Java Bean -> JsonNode");
        JsonNode jsonNode6 = objectMapper.valueToTree(person1);
        System.out.println(jsonNode6);
    }

    static void advancedUsage() throws JsonProcessingException {
    }

    static void annotationUsage() throws JsonProcessingException {
    }

    public static void main(String[] args) throws IOException {
        basicUsage();
        advancedUsage();
        annotationUsage();
    }
}
