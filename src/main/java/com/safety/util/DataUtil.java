/**
 * 
 */
package com.safety.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;


/**
 * @author Administrator
 * 
 */
public class DataUtil {

	/**
	 * 每位允许的字符
	 */
	private static final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final String AGENT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final String ORDER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * 生产一个指定长度的随机字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS.length())));
		}
		return sb.toString();
	}
	
	public static String getOrderNum() {
		StringBuilder sb = new StringBuilder();
		SecureRandom random = new SecureRandom();
		sb.append(ORDER_CHARS.charAt(random.nextInt(ORDER_CHARS.length())));
		sb.append(ORDER_CHARS.charAt(random.nextInt(ORDER_CHARS.length())));
		sb.append(ORDER_CHARS.charAt(random.nextInt(ORDER_CHARS.length())));
		sb.append(ORDER_CHARS.charAt(random.nextInt(ORDER_CHARS.length())));
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}
	
	public static String generateRandomAgentNo(Integer id) {
		StringBuilder sb = new StringBuilder(5);
		char start = '@';
		int m = id.intValue()%26;
		if(m==0) {
			m = 26;
		}
		sb.append(start+=m);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < 4; i++) {
			sb.append(AGENT_CHARS.charAt(random.nextInt(AGENT_CHARS.length())));
		}
		return sb.toString();
	}

	public static String formatData(int number, int width) {
		if (number <= 0)
			return null;
		String sNum = number + "";
		if (sNum.length() < width) {
			int w = width - sNum.length();
			for (int i = 0; i < w; i++) {
				sNum = "0" + sNum;
			}
		}
		return sNum;
	}

	public static String encodeStr(String str) {
		if (str == null)
			return null;
		try {
			return new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encodeStrUTF8(String str) {
		if (StringUtils.isBlank(str))
			return null;
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };

	public static String _10_to_62(long number) {
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest != 0) {
			stack.add(array[new Long((rest - (rest / 62) * 62)).intValue()]);
			rest = rest / 62;
		}
		for (; !stack.isEmpty();) {
			result.append(stack.pop());
		}
		return result.toString();
	}

	public static long _62_to_10(String sixty_str) {
		int multiple = 1;
		long result = 0;
		Character c;
		for (int i = 0; i < sixty_str.length(); i++) {
			c = sixty_str.charAt(sixty_str.length() - i - 1);
			result += _62_value(c) * multiple;
			multiple = multiple * 62;
		}
		return result;
	}

	private static int _62_value(Character c) {
		for (int i = 0; i < array.length; i++) {
			if (c == array[i]) {
				return i;
			}
		}
		return -1;
	}





	/**
	 * 小文件和传
	 * 
	 * @param req
	 * @param name
	 * @param createThumb
	 * @return
	 * @throws Exception
	 */
	public static List<String> uploadFile(HttpServletRequest req, String name, boolean createThumb) throws Exception {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtils.getCommonSetting("upload.file.path");// 存放文件文件夹名称

		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(array[random.nextInt(array.length)]);
			sb.append(array[random.nextInt(array.length)]);

			subDir.append(sb.toString());
		}

		File dirPath = new File(fileDir + sep + subDir.toString());
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		List<MultipartFile> mfs = multiRequest.getFiles(name);
		List<String> files = new ArrayList<String>();
		for (MultipartFile mft : mfs) {
			CommonsMultipartFile mf = (CommonsMultipartFile) mft;
			byte[] bytes = mf.getBytes();
			StringBuffer newFileName = new StringBuffer();
			if (bytes.length != 0) {
				String fileTrueName = mf.getOriginalFilename();
				String ext = fileTrueName.substring(fileTrueName.lastIndexOf("."));
				newFileName.append(System.currentTimeMillis());
				newFileName.append(ext);
				String fileName = fileDir + sep + subDir.toString() + sep + newFileName.toString();

				File uploadedFile = new File(fileName);
				try {
					FileCopyUtils.copy(bytes, uploadedFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				files.add(subDir.toString() + sep + newFileName.toString());
			}

		}

		return files;
	}



	/**
	 * 上传图片至临时目录
	 * 
	 * @param req
	 * @param name
	 * @param createThumb
	 * @return
	 * @throws Exception
	 */
	public static String uploadImgToTempDir(HttpServletRequest req, String name) throws Exception {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtils.getCommonSetting("upload.file.temp.path");// 存放文件文件夹名称

		File dirPath = new File(fileDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		MultipartFile mft = multiRequest.getFile(name);
		CommonsMultipartFile mf = (CommonsMultipartFile) mft;
		byte[] bytes = mf.getBytes();
		StringBuffer newFileName = new StringBuffer();
		if (bytes.length != 0) {
			String fileTrueName = mf.getOriginalFilename();
			String ext = fileTrueName.substring(fileTrueName.lastIndexOf("."));
			if (!".jpg/.jpeg/.gif/.bmp/.png".contains(ext)) {
				throw new Exception("格式错误！");
			}
			newFileName.append(System.currentTimeMillis());
			newFileName.append(ext);
			String fileName = fileDir + sep + newFileName.toString();

			File uploadedFile = new File(fileName);

			try {
				FileCopyUtils.copy(bytes, uploadedFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return newFileName.toString();
	}

	/**
	 * 存放终端上传的图片到临时目录
	 * 
	 * @param filename
	 * @param image
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String uploadImageToTmp(String filename, String image) {
		FileOutputStream fos = null;
		try {
			String fileDir = SettingUtils.getCommonSetting("upload.file.temp.path");// 存放文件文件夹名称

			sun.misc.BASE64Decoder a = new sun.misc.BASE64Decoder();
			byte[] buffer = a.decodeBuffer(image); // 对android传过来的图片字符串进行解码
			String type = getImageType(buffer);
			if (!StringUtils.equalsIgnoreCase(type, "jpg") && !StringUtils.equalsIgnoreCase(type, "jpeg") && !StringUtils.equalsIgnoreCase(type, "png")) {
				return "-1";
			}
			File destDir = new File(fileDir);
			if (!destDir.exists())
				destDir.mkdirs();
			fos = new FileOutputStream(new File(destDir, filename)); // 保存图片
			fos.write(buffer);
			fos.flush();
			fos.close();
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 存放终端上传的图片
	 * 
	 * @param filename
	 * @param image
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String uploadImage(String filename, String image) {
		FileOutputStream fos = null;
		try {
			String dirName = buildDirName();
			String toDir = SettingUtils.getCommonSetting("upload.image.path"); // 存储路径
			toDir += dirName;
			sun.misc.BASE64Decoder a = new sun.misc.BASE64Decoder();
			byte[] buffer = a.decodeBuffer(image); // 对android传过来的图片字符串进行解码
			String type = getImageType(buffer);
			if (!StringUtils.equalsIgnoreCase(type, "jpg") && !StringUtils.equalsIgnoreCase(type, "jpeg") && !StringUtils.equalsIgnoreCase(type, "png")) {
				return "-1";
			}
			File destDir = new File(toDir);
			if (!destDir.exists())
				destDir.mkdirs();
			fos = new FileOutputStream(new File(destDir, filename)); // 保存图片
			fos.write(buffer);
			fos.flush();
			fos.close();
			return dirName + filename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取图片格式
	 * 
	 * @param img
	 * @return
	 * @throws IOException
	 */
	public static String getImageType(byte[] img) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(img);
		MemoryCacheImageInputStream is = new MemoryCacheImageInputStream(bais);
		Iterator<ImageReader> it = ImageIO.getImageReaders(is);
		ImageReader r = null;
		while (it.hasNext()) {
			r = it.next();
			break;
		}
		if (r == null) {
			return null;
		}
		return r.getFormatName().toLowerCase();
	}

	public static String buildDirName() {
		String sep = System.getProperty("file.separator");
		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);

			subDir.append(sb.toString());
		}

		return subDir.toString();
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double distance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	

}
