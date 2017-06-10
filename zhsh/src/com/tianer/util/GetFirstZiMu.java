
package com.tianer.util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.pinyin4j.PinyinHelper;
/**
 * @author shiyz
 * @creation date 2012-10-25 上午13:01:21
 *
 */
public class GetFirstZiMu {
	public static void main(String[] args) {
				GetFirstZiMu obj1 = new GetFirstZiMu();
				System.out.println("======================");
				PageData pd=new PageData();
				ArrayList<PageData> list=new ArrayList<PageData>();
				pd.put("city_name", "上神");
				list.add(pd);
				pd=new PageData();
				pd.put("city_name", "土豆");
				list.add(pd);
				pd=new PageData();
				pd.put("city_name", "萍乡市");
				list.add(pd);
				pd=new PageData();
				pd.put("city_name", "杭州市");
				list.add(pd);
				pd=new PageData();
				pd.put("city_name", "西安市");
 				list.add(pd);
 				pd=new PageData();
				pd.put("city_name", "莆田市");
				list.add(pd);
				Map<String,List<PageData>> map=obj1.sortTwo(list,"city_name");
// 				System.out.println("-------分组后的输出-----------");
//		        System.out.println(map.get("A"));
//		        System.out.println(map.get("B"));
//		        System.out.println(map.get("C"));
//		        System.out.println(map.get("D"));
//		        System.out.println(map.get("Y"));
		        //map排序
 				Map<String, List<PageData>> resultMap = sortMapByKey(map);	//按Key进行排序
 				for (Map.Entry<String, List<PageData>> entry : resultMap.entrySet()) {
					System.out.println(entry.getKey() + " " + entry.getValue());
 				}
  				 
  	  } 
	
	
        public GetFirstZiMu() {
 	 
        }
	     //字母Z使用了两个标签，这里有２７个值
	 	 //i, u, v都不做声母, 跟随前面的字母
        public char[] chartable =
           {
             '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
             '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
             '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'
            };
	    public static char[] alphatableb =
          {
 	         'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
             'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
 	       };
	    public static char[] alphatables =
          {
 	         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
             'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
 	       };
	    public static int[] table = new int[27];  //初始化
	      {
	 	         for (int i = 0; i < 27; ++i) {
	 	             table[i] = gbValue(chartable[i]);
	 	         }
	       }
		//主函数,输入字符,得到他的声母,
		//英文字母返回对应的大小写字母
	    //其他非简体汉字返回 '0'  按参数
	     public static char Char2Alpha(char ch,String type) {
	          if (ch >= 'a' && ch <= 'z')
	              return (char) (ch - 'a' + 'A');//为了按字母排序先返回大写字母
	           // return ch;
	          if (ch >= 'A' && ch <= 'Z')
	              return ch;

	             int gb = gbValue(ch);
	 	         if (gb < table[0])
	              return '0';
	  
	          int i;
	 	         for (i = 0; i < 26; ++i) {
	              if (match(i, gb))
	 	                 break;
	          }
	 	 
	 	         if (i >= 26){
	              return '0';}
	 	         else{
	 	        	 if("b".equals(type)){//大写
	                     return alphatableb[i];
	                 }else{//小写
	                	 return alphatables[i];
	                 }
	 	         }
	      }
	     
	 //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
     public static String String2Alpha(String SourceStr,String type) {
         String Result = "";
         int StrLength = SourceStr.length();
         int i;
      try {
          for (i = 0; i < StrLength; i++) {
                 Result += Char2Alpha(SourceStr.charAt(i),type);
             }
         } catch (Exception e) {
          Result = "";
         }
      return Result;
    }
     
   //根据一个包含汉字的字符串返回第一个汉字拼音首字母的字符串
     public  static String String2AlphaFirst(String SourceStr,String type) {
           String Result = "";
         try {
           Result += Char2Alpha(SourceStr.charAt(0),type);
          } catch (Exception e) {
           Result = "";
         }
      return Result;
    }
     private static boolean match(int i, int gb) {
            if (gb < table[i])
               return false;
 	         int j = i + 1;
  	 
 	         //字母Z使用了两个标签
  	         while (j < 26 && (table[j] == table[i]))
 	             ++j;
 	         if (j == 26)
 	             return gb <= table[j];
            else
 	             return gb < table[j];
  	      }
    	  	 
     //取出汉字的编码
     private static  int gbValue(char ch) {
	     String str = new String();
	     str += ch;
	     try {
	         byte[] bytes = str.getBytes("GBK");
	             if (bytes.length < 2)
	                 return 0;
	             return (bytes[0] << 8 & 0xff00) + (bytes[1] &
	                     0xff);
	         } catch (Exception e) {
	           return 0;
	         }
	     }
     
     public Map sort(List list){
    	 Map map=new HashMap();
    	 ArrayList arraylist=new ArrayList();
    	 String[] alphatableb =
             {
    	        "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    	       };
    		 for(String a:alphatableb){
    			 for(int i=0;i<list.size();i++){//为了排序都返回大写字母
	    			 if(a.equals(String2AlphaFirst(list.get(i).toString(),"b"))){
	    				 arraylist.add(list.get(i).toString());
	    			 }
    		     }
    			 map.put(a,arraylist);
    			 arraylist=new ArrayList();
    	 }
    	 return map;
     }
     
     /**
      * 
     * 方法名称:：sortTwo 
     * 方法描述：获取集合的排序
     * 创建人：魏汉文
     * 创建时间：2016年8月27日 下午2:15:47
      */
     public static Map<String,List<PageData>> sortTwo(List<PageData> list,String key){
    	 Map<String,List<PageData>> map=new HashMap<String,List<PageData>>();
    	 ArrayList<PageData> arraylist=new ArrayList<PageData>();
    	 String[] alphatableb =
             {
    	        "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    	       };
    		 for(String a : alphatableb){
    			 for(int i=0;i<list.size();i++){//为了排序都返回大写字母
    				 if(list.get(i).getString(key) != null){
    					 String w=list.get(i).getString(key).toString().trim();
         				 String w1=String2AlphaFirst(w,"b");
      	    			 if(a.equals(w1)){
    	    				 arraylist.add(list.get(i));
    	    			 }
    				 }
     		     }
    			 map.put(a,arraylist);
    			 arraylist=new ArrayList();
    		 }
    		 //按Key进行排序
    		 Map<String, List<PageData>> resultMap = sortMapByKey(map);	
    	 return resultMap;
     }
     
     
     
     /**
 	 * 使用 Map按key进行排序
 	 * @param map
 	 * @return
 	 */
 	public static Map<String, List<PageData>> sortMapByKey(Map<String, List<PageData>> map) {
 		if (map == null || map.isEmpty()) {
 			return null;
 		}
  		Map<String, List<PageData>> sortMap = new TreeMap<String, List<PageData>>(new MapKeyComparator());
  		sortMap.putAll(map);
  		return sortMap;
 	}
 	
 	 
 	
 
     
}

/**
 * 比较器
* 类名称：MapKeyComparator   
* 类描述：   
* 创建人：魏汉文  
* 创建时间：2016年8月27日 下午2:25:35
 */
class MapKeyComparator implements Comparator<String>{

		@Override
		public int compare(String str1, String str2) {
			
			return str1.compareTo(str2);
		}
	}


 