package jsCombine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String... args) {
		//https://jscompress.com/
		String jspath = "D:\\private\\PPS.Portal\\WebContent\\js\\";
		String outputpath = jspath + "main.js";
		List<String> exceptionFile = new ArrayList<>();
		exceptionFile.add(jspath + "login.js");
		exceptionFile.add(jspath + "common.js");
		exceptionFile.add(jspath + "main.js");
		exceptionFile.add(jspath + "vendor\\mdb.min.js");
		exceptionFile.add(jspath + "vendor\\morris-data.js");
		exceptionFile.add(jspath + "vendor\\sb-admin-charts.js");
		exceptionFile.add(jspath + "vendor\\sb-admin-datatables.js");
		exceptionFile.add(jspath + "vendor\\sb-admin.js");

		List<File> list = getAllFileList(jspath, exceptionFile);
		StringBuffer sb = new StringBuffer();
		for (File t : list) {
			System.out.println(t.getAbsolutePath());
			byte[] data = new byte[(int) t.length()];
			try (FileInputStream stream = new FileInputStream(t.getAbsolutePath())) {
				stream.read(data, 0, data.length);
				sb.append(new String(data));
				sb.append("\r\n");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		File file = new File(outputpath);
		if(file.exists()) {
			file.delete();
		}
		byte[] data = sb.toString().getBytes();
		try (FileOutputStream stream = new FileOutputStream(file)) {
			stream.write(data);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static List<File> getAllFileList(String path, List<String> exceptionFile) {
		List<File> ret = new ArrayList<>();
		File con = new File(path);

		for (File con2 : con.listFiles()) {
			if (con2.isDirectory()) {
				ret.addAll(getAllFileList(con2.getAbsolutePath(), exceptionFile));
				continue;
			}
			if (exceptionFile.contains(con2.getAbsolutePath())) {
				continue;
			}
			ret.add(con2);
		}
		return ret;
	}
}
