package com.tianer.util;

 import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
 

/**
 * 上传文件
 * 创建人：cyr
 * 创建时间：2014年12月23日
 * @version
 */
public class FileUpload {

	/**
	 * @param MultipartFile 			//文件对象
	 * @param filePath		//上传路径
	 * @param fileName		//文件名
	 * @return  文件名
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName){
//		System.out.println("method in :"+filePath);
		String extName = ".png"; // 扩展名格式：
 		try {
//			if (file.getOriginalFilename().lastIndexOf(".") >= 0){
//				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//			}
 			copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
		} catch (IOException e) {
//			System.out.println(e);
			e.printStackTrace();
		}
		return fileName+extName;
	}
	
	/**
	 * @param filestr 			//文件地址
	 * @param filePath		//上传路径(服务器地址)
	 * @param fileName		//文件名(不带.后缀的)
	 * @return  文件名
	 */
	public static String fileUpFile(String  filestr, String filePath, String fileName){
 		String extName = ".png"; // 扩展名格式：
 		try {
 			  File file=new File(filestr);
			  InputStream input = new FileInputStream(file);  
 			  copyFile(input, filePath, fileName+extName).replaceAll("-", "");
		} catch (IOException e) {
 			e.printStackTrace();
		}
		return fileName+extName;
	}
 
	//===
	public static void main(String[] args) throws IOException {
		getImageByUrl("https://q.qlogo.cn/qqapp/1105492816/4F4A5579A4035637F89BF7734D9CF7F7/100","E://","ll");
	}
	
	
	/**
	 * @param file 			//文件对象
	 * @param filePath		//上传路径
	 * @param fileName		//文件名
	 * @return  文件名
	 */
	public static String fileUpExcel(MultipartFile file, String filePath, String fileName){
//		System.out.println("method in :"+filePath);
		String extName = ""; // 扩展名格式：
  		try {
 			String x=file.getOriginalFilename();
			if (x.lastIndexOf(".") > 0){
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
 			}
			if(!fileName.contains(".")){
				fileName=fileName+extName;
			}
			copyFile(file.getInputStream(), filePath, fileName).replaceAll("-", "");
		} catch (IOException e) {
//			System.out.println(e);
			e.printStackTrace();
		}
		return fileName;
	}
	
	
	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private static String copyFile(InputStream in, String dir, String realName) throws IOException {
 		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				 file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}
	
	// 验证字符串是否为正确路径名的正则表达式  
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*"; 
	
	private static boolean flag=true;
	private static File file;
	/** 
	 *  根据路径删除指定的目录或文件，无论存在与否 
	 *@param sPath  要删除的目录或文件 
	 *@return 删除成功返回 true，否则返回 false。 
	 */  
	public static boolean DeleteFolder(String sPath) {  
	    flag = false;  
	    file = new File(sPath);  
	    // 判断目录或文件是否存在  
	    if (!file.exists()) {  // 不存在返回 false  
	        return flag;  
	    } else {  
	        // 判断是否为文件  
	        if (file.isFile()) {  // 为文件时调用删除文件方法  
	            return deleteFile(sPath);  
	        } else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(sPath);  
	        }  
	    }  
	}  
	
	

	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
	    flag = false;  
	    file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public static boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
	
	
	/**
	* 图片下载到本地服务器
	*/
	public static String getImageByUrl(String imageurl, String savepath, String name) {
		try {
			// 构造URL
			URL url = new URL(imageurl);
			// 打开连接
			URLConnection con = url.openConnection();
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// Map<String, Object> property =
			// getProperties("/gbtags.properties");
			File file = new File(savepath);// (String) property.get("ewmPath"));
			if (!file.exists()) {
			file.mkdirs();
			}
			// 输出的文件流
			OutputStream os = new FileOutputStream(savepath + "/"+name+".png");
			// 开始读取
			while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
			return "success";
		} catch (Exception e) {
				return "error";
		}
	}
	
}
