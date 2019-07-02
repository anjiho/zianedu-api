package com.zianedu.api.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	//static String path = "/Users/anjiho/Downloads/";
	//static String path = "/home/ec2-user/bird-admin-logs";
	public static boolean fileDel(String path, String fileNm){
		File f = new File(path+fileNm);

		if(f.exists()){
			boolean result = f.delete();
			return result;
		}else{
			return false;
		}
	}

	/**
	 * 파일 압축하기
	 * @param targetUrl : 압축 할 파일이 있는 폴더 경로(C:/tar/get)
	 * @param crtFileName : 생성 할 압축 파일 이름(file.zip)
	 * @return
	 * @throws Exception
	 */
	public static boolean setFileZip(String targetUrl, String crtFileName) throws Exception {
		boolean isYn = false;

		String szInputFile1 = targetUrl;
		String szGZipTemp = targetUrl + "/" + crtFileName;

		File inFile1 = new File(szInputFile1);
		inFile1.mkdirs();

		File inDirectory ;

		if (inFile1.isDirectory()){
			inDirectory = new File(szInputFile1);
		}
		else{
			inDirectory = new File(inFile1.getParent());
		}

		File[] inFile = inDirectory.listFiles();
		FileInputStream fis = null;

		File gzipFile = new File(szGZipTemp);
		FileOutputStream fos = new FileOutputStream(gzipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		ZipEntry ze = null;

		byte[] buffer = new byte[1024 * 8];
		int nRead;

		try {
			for ( int i=0; i < inFile.length ; i++){
				fis = new FileInputStream( inFile[i]);
				ze = new ZipEntry(inFile[i].getName());

				//System.out.println( "압축할 파일명 : " + inFile[i].getName() + ", 파일사이즈 : " + fis.available());
				zos.putNextEntry(ze);

				zos.setLevel(9);
				buffer = new byte[1024 * 8];
				nRead = 0;

				while ((nRead = fis.read(buffer)) != -1){
					zos.write(buffer, 0, nRead);
				}

				fis.close();
				zos.closeEntry();
			}

			zos.close();
			fos.close();

			isYn = true;
		}
		catch (IOException e){
			e.printStackTrace();
			isYn = false;
		}
		finally {
			try {fis.close();}catch(Exception e){}
			try {zos.close();}catch(Exception e){}
			try {fos.close();}catch(Exception e){}
		}

		return isYn;
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
	     InputStream is = new FileInputStream(file);
	     long length = file.length();

	     if (length > Integer.MAX_VALUE) {
	         System.out.println("File is too large");
	     }

	     byte[] bytes = new byte[(int)length];

	     int offset = 0;
	     int numRead = 0;
	     while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	         offset += numRead;
	     }

	     if (offset < bytes.length) {
	         throw new IOException("Could not completely read file "+file.getName());
	     }

	     is.close();
	     return bytes;
	}

	//파일을 생성하는 메소드
	public static void fileMake(String makeFileName) {
		File f1 = new File(makeFileName);

		try {
			f1.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//파일을 삭제하는 메소드
	public static void fileDelete(String deleteFileName) {
		File file = new File(deleteFileName);

		if(file.exists() == true){
			file.delete();
		}
	}

    //파일 이동
	public static void fileMove(String inFileName, String outFileName) {
		File file = new File(inFileName);
		File file2 = new File(outFileName);// 이동

		if (file.exists()) {
			file.renameTo(file2); // 변경
		}
	}

	public static String concatPath(String... paths) {
		String separator = File.separator;
		StringBuilder buffer = new StringBuilder();
		for(String path : paths){
			if(path == null) continue;
			path = path.trim();
			if(!path.startsWith(separator) && !path.startsWith("http")){
				path = separator + path;
			}
			if(path.endsWith(separator)){
				int length = path.length();
				path = path.substring(0, length-1);
			}
			buffer.append(path);
		}
		return buffer.toString();
	}

	public static String removeFileExtension(String fileName) {
		String extensionFileName = null;
		int idx = fileName.lastIndexOf(".");
		extensionFileName = fileName.substring(0, idx);
		return extensionFileName;
	}

	public static void main(String args[]) throws Exception {
		System.out.print(removeFileExtension("TEST.mp4"));
	}
}
