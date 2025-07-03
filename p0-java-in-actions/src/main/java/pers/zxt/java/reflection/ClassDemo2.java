package pers.zxt.java.reflection;

public class ClassDemo2 {
	public static void main(String[] args) {
		Class c1 = int.class;     //int 的类类型
		Class c2 = String.class;  //String类的类类型，String类字节码（自己发明的)
		//下面这两个不是同一回事
		Class c3 = double.class;
		Class c4 = Double.class;
		Class c5 = void.class;
		System.out.println(c1.getName());
		System.out.println(c2.getName());
		System.out.println(c2.getSimpleName());//不包含包名的类的名称
		System.out.println(c5.getName());
		System.out.println("=============");

		String s = "hello";
		Integer n1 = 1;
		ClassUtil.printClassMethodMessage(s);
		ClassUtil.printClassMethodMessage(n1);
		System.out.println("=============");
		ClassUtil.printFieldMessage("s");
		ClassUtil.printFieldMessage(n1);
		System.out.println("=============");
		ClassUtil.printConMessage("s");
		ClassUtil.printConMessage(n1);

	}

}
