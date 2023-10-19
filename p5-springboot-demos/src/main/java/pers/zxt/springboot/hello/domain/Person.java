package pers.zxt.springboot.hello.domain;

// 普通的 Java Bean 对象，在 springconf/applicationContext.xml 中被引入
public class Person {

    private String cardId;
    private String name;
    private Integer age;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person {" +
                "cardId='" + cardId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
