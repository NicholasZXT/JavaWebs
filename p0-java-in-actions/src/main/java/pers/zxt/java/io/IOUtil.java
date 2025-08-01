package pers.zxt.java.io;

import java.io.*;

public class IOUtil {

	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
	}

	public static void test1(){
		try {
			IOUtil.printHex("e:\\javaio\\FileUtils.java");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test2(){
		try {
			long start = System.currentTimeMillis();
			//IOUtil.printHexByByteArray("e:\\javaio\\FileUtils.java");
			//IOUtil.printHex("e:\\javaio\\1.mp3");
			IOUtil.printHexByByteArray("e:\\javaio\\1.mp3");
			System.out.println();
			long end = System.currentTimeMillis();
			System.out.println(end - start);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test3(){
		try {
			IOUtil.copyFile(new File("e:\\javaio\\imooc.txt"), new File(
					"e:\\javaio\\imooc1.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test4(){
		try {
			long start = System.currentTimeMillis();
			/*IOUtil.copyFileByByte(new File("e:\\javaio\\1.mp3"), new File(
					"e:\\javaio\\2.mp3"));*/  //两万多毫秒
			/*IOUtil.copyFileByBuffer(new File("e:\\javaio\\1.mp3"), new File(
					"e:\\javaio\\3.mp3"));//一万多毫秒*/
			IOUtil.copyFile(new File("e:\\javaio\\1.mp3"), new File(
					"e:\\javaio\\4.mp3"));//7毫秒
			long end = System.currentTimeMillis();
			System.out.println(end - start );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取指定文件内容，按照16进制输出到控制台
	 * 并且每输出10个byte换行
	 * @param fileName
	 * 单字节读取不适合大文件，大文件效率很低
	 */
	public static void printHex(String fileName)throws IOException{
		//把文件作为字节流进行读操作
		FileInputStream in = new FileInputStream(fileName);
		int b ;
		int i = 1;
		while((b = in.read())!=-1){
//			b <= 0xf，说明只有一位，需要补一个0
			if(b <= 0xf){
				//单位数前面补0
				System.out.print("0");
			}
//			将整型b转换为16进制表示的字符串
			System.out.print(Integer.toHexString(b)+"  ");
//			每10个byte换行
			if(i++%10==0){
				System.out.println();
			}
		}
		in.close();
	}
	/**
	 * 批量读取，对大文件而言效率高，也是我们最常用的读文件的方式
	 * @param fileName
	 * @throws IOException
	 */
	public static void printHexByByteArray(String fileName)throws IOException{
		FileInputStream in = new FileInputStream(fileName);
		byte[] buf = new byte[8 * 1024];
		/*从in中批量读取字节，放入到buf这个字节数组中，
		 * 从第0个位置开始放，最多放buf.length个 
		 * 返回的是读到的字节的个数
		*/
		/*int bytes = in.read(buf,0,buf.length);//一次性读完，说明字节数组足够大
		int j = 1; 
		for(int i = 0; i < bytes;i++){
			System.out.print(Integer.toHexString(buf[i] & 0xff)+"  ");
			if(j++%10==0){
				System.out.println();
			}
		}*/
	  int bytes = 0;
	  int j = 1;
	  while((bytes = in.read(buf,0,buf.length))!=-1){
		  for(int i = 0 ; i < bytes;i++){
			  System.out.print(Integer.toHexString(buf[i] & 0xff)+"  ");
			  if(j++%10==0){
				  System.out.println();
			  }
		  }
	  }
	  in.close();
	}
	/**
	 * 文件拷贝，字节批量读取
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File srcFile,File destFile)throws IOException{
		if(!srcFile.exists()){
			throw new IllegalArgumentException("文件:"+srcFile+"不存在");
		}
		if(!srcFile.isFile()){
			throw new IllegalArgumentException(srcFile+"不是文件");
		}
		FileInputStream in = new FileInputStream(srcFile);
		FileOutputStream out = new FileOutputStream(destFile);
		byte[] buf = new byte[8*1024];
		int b ;
	    while((b = in.read(buf,0,buf.length))!=-1){
	    	out.write(buf,0,b);
	    	out.flush();//最好加上
	    }
	    in.close();
	    out.close();
		
	}
	/**
	 * 进行文件的拷贝，利用带缓冲的字节流
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFileByBuffer(File srcFile,File destFile)throws IOException{
		if(!srcFile.exists()){
			throw new IllegalArgumentException("文件:"+srcFile+"不存在");
		}
		if(!srcFile.isFile()){
			throw new IllegalArgumentException(srcFile+"不是文件");
		}
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(srcFile));
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(destFile));
		int c ;
		while((c = bis.read())!=-1){
			bos.write(c);
			bos.flush();//刷新缓冲区
		}
		bis.close();
		bos.close();
	}
	/**
	 * 单字节，不带缓冲进行文件拷贝
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFileByByte(File srcFile,File destFile)throws IOException{
		if(!srcFile.exists()){
			throw new IllegalArgumentException("文件:"+srcFile+"不存在");
		}
		if(!srcFile.isFile()){
			throw new IllegalArgumentException(srcFile+"不是文件");
		}
		FileInputStream in = new FileInputStream(srcFile);
		FileOutputStream out = new FileOutputStream(destFile);
		int c ;
		while((c = in.read())!=-1){
			out.write(c);
			out.flush();
		}
		in.close();
		out.close();
	}
	
}
