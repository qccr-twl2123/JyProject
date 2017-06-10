<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.util.*, org.apache.commons.fileupload.*, java.util.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*, org.apache.commons.fileupload.servlet.*" %>
<%!
	
	String PATH = "/uploadify/uploads/";
	public void upload(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String savePath = this.getServletConfig().getServletContext().getRealPath("");
		//savePath = "C://Program Files (x86)//Apache Software Foundation//Tomcat 7.0//webapps//FileSave//File//goods";
		File f1 = new File(savePath);
		System.out.println("savepath:"+savePath);
		//这里接收了name的值
		//System.out.println(request.getParameter("name"));
		if (!f1.exists()) {
			f1.mkdirs();
		}
	
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				name = item.getName();
				long size = item.getSize();
				String type = item.getContentType();
				//System.out.println(size + " " + type);
				if (name == null || name.trim().equals("")) {
					continue;
				}
	
				// 扩展名格式：
				if (name.lastIndexOf(".") >= 0) {
					extName = name.substring(name.lastIndexOf("."));
				}
	
				File file = null;
				do {
					// 生成文件名：
					//name = UUID.randomUUID().toString();
					name = new java.text.SimpleDateFormat("yyyyMMddhhmmss").format(new Date());	//获取当前日期
					name = name + (int)(Math.random()*9000+1000);
					file = new File(savePath + name + extName);
				} while (file.exists());
	
				File saveFile = new File(savePath + name + extName);
				try {
					item.write(saveFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		response.getWriter().print((name.trim() + extName.trim()).trim());
	}
%>
<%
	upload(request, response);
%>