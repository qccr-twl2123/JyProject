package com.tianer.util;
import java.io.ByteArrayOutputStream; 
import java.security.Key; 
import java.security.MessageDigest; 

import javax.crypto.Cipher; 
import javax.crypto.SecretKeyFactory; 
import javax.crypto.spec.DESKeySpec; 

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



/** 
 * Security 提供了一个安全算法类,其中包括对称密码算法和散列算法
 * 对字符进行加密解密
 *  
 */ 
public final class EbotongSecurity { 
// The length of Encryptionstring should be 8 bytes and not be 
// a weak key 
private final static BASE64Encoder base64encoder = new BASE64Encoder(); 
private final static BASE64Decoder base64decoder = new BASE64Decoder(); 
private final static String encoding = "UTF-8"; 

/** 
 * 加密字符串 
 */ 
public static String ebotongEncrypto(String str) { 
String result = str; 
if (str != null && str.length() > 0) { 
try { 
byte[] encodeByte = symmetricEncrypto(str.getBytes(encoding)); 
result = base64encoder.encode(encodeByte); 
} catch (Exception e) { 
e.printStackTrace(); 
} 
} 
return result; 
} 

/** 
 * 解密字符串 
 */ 
public static String ebotongDecrypto(String str) { 
	String result = str; 
	if (str != null && str.length() > 0) { 
	try { 
	byte[] encodeByte = base64decoder.decodeBuffer(str); 
	byte[] decoder = EbotongSecurity.symmetricDecrypto(encodeByte); 
	result = new String(decoder, encoding); 
	} catch (Exception e) { 
	e.printStackTrace(); 
	} 
	} 
	return result; 
} 

/** 
 * 对称加密方法 
 *  
 * @param byteSource 
 *            需要加密的数据 
 * @return 经过加密的数据 
 * @throws Exception 
 */ 
public static byte[] symmetricEncrypto(byte[] byteSource) throws Exception { 
ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
try { 
int mode = Cipher.ENCRYPT_MODE; 
SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
byte[] keyData = { 1, 9, 8, 2, 0, 8, 2, 1 }; 
DESKeySpec keySpec = new DESKeySpec(keyData); 
Key key = keyFactory.generateSecret(keySpec); 
Cipher cipher = Cipher.getInstance("DES"); 
cipher.init(mode, key); 
byte[] result = cipher.doFinal(byteSource); 
// int blockSize = cipher.getBlockSize(); 
// int position = 0; 
// int length = byteSource.length; 
// boolean more = true; 
// while (more) { 
// if (position + blockSize <= length) { 
// baos.write(cipher.update(byteSource, position, blockSize)); 
// position += blockSize; 
// } else { 
// more = false; 
// } 
// } 
// if (position < length) { 
// baos.write(cipher.doFinal(byteSource, position, length 
// - position)); 
// } else { 
// baos.write(cipher.doFinal()); 
// } 
// return baos.toByteArray(); 
return result; 
} catch (Exception e) { 
throw e; 
} finally { 
baos.close(); 
} 
} 

/** 
 * 对称解密方法 
 *  
 * @param byteSource 
 *            需要解密的数据 
 * @return 经过解密的数据 
 * @throws Exception 
 */ 
public static byte[] symmetricDecrypto(byte[] byteSource) throws Exception { 
ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
try { 
int mode = Cipher.DECRYPT_MODE; 
SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
byte[] keyData = { 1, 9, 8, 2, 0, 8, 2, 1 }; 
DESKeySpec keySpec = new DESKeySpec(keyData); 
Key key = keyFactory.generateSecret(keySpec); 
Cipher cipher = Cipher.getInstance("DES"); 
cipher.init(mode, key); 
byte[] result = cipher.doFinal(byteSource); 
// int blockSize = cipher.getBlockSize(); 
// int position = 0; 
// int length = byteSource.length; 
// boolean more = true; 
// while (more) { 
// if (position + blockSize <= length) { 
// baos.write(cipher.update(byteSource, position, blockSize)); 
// position += blockSize; 
// } else { 
// more = false; 
// } 
// } 
// if (position < length) { 
// baos.write(cipher.doFinal(byteSource, position, length 
// - position)); 
// } else { 
// baos.write(cipher.doFinal()); 
// } 
// return baos.toByteArray(); 
return result; 
} catch (Exception e) { 
throw e; 
} finally { 
baos.close(); 
} 
} 

/** 
 * 散列算法 
 *  
 * @param byteSource 
 *            需要散列计算的数据 
 * @return 经过散列计算的数据 
 * @throws Exception 
 */ 
public static byte[] hashMethod(byte[] byteSource) throws Exception { 
try { 
MessageDigest currentAlgorithm = MessageDigest.getInstance("SHA-1"); 
currentAlgorithm.reset(); 
currentAlgorithm.update(byteSource); 
return currentAlgorithm.digest(); 
} catch (Exception e) { 
throw e; 
} 
} 


public static void main(String[] args) {
	String s1="ass133391593";
	System.out.println(ebotongEncrypto(s1));
	String s2="pMRIyrvoq1KD12lRL+9mGIxST7m+IJUP";
 	System.out.println(ebotongDecrypto(s2));
}





} 