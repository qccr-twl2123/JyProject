package com.tianer.util.RedPackage;    
  
import java.util.ArrayList;  
import java.util.List;  

import com.alibaba.fastjson.JSON;
  
     
public class IsRedPacketUtil {  
    //最小红包额度  
    private static final int MINMONEY = 1;  
    //最大红包额度  
    private static final int MAXMONEY = 200 * 100;  
    //每个红包最大是平均值的倍数  
    private static final double TIMES = 2.1;  
     
     
    /** 
     * @param money 
     * @param count 
     * @return 
     * @Author:lulei   
     * @Description: 此种红包是否合法 
     */  
    public static boolean isRight(int money, int count) {  
        double avg = money / count;  
        if (avg < MINMONEY) {  
            return false;  
        }  
        if (avg > MAXMONEY) {  
            return false;  
        }  
        return true;  
    }  
  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub   
    	
     }  
}  