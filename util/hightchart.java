	@RequestMapping("/classTrack/saveAsImage")
	public void saveAsImage(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		request.setCharacterEncoding("utf-8");//设置编码，解决乱码问题
		response.setCharacterEncoding("utf-8");
		String type = request.getParameter("type");
		String svg = request.getParameter("_XSS_ORI_svg");
		if(StringUtils.isBlank(svg)){
			 svg = request.getParameter("svg");
		}
		
		String filename = request.getParameter("filename");
		filename = filename==null?"chart":filename;
		ServletOutputStream out = response.getOutputStream();
		if (null != type && null != svg) {
		    svg = svg.replaceAll(":rect", "rect");
		    String ext = "";
		    Transcoder t = null;
		    if (type.equals("image/png")) {
				ext = "png";
				t = new PNGTranscoder();
		    } else if (type.equals("image/jpeg")) {
				ext = "jpg";
				t = new JPEGTranscoder();
		    } else if (type.equals("application/pdf")) {
                ext = "pdf";
                t =new PDFTranscoder();
	        } else if(type.equals("image/svg+xml")) 
	            ext = "svg";   
		    String agent = request.getHeader("User-Agent");         //获取浏览器头文件
		    boolean isMSIE = ((agent != null && agent.indexOf("MSIE") != -1 ) || ( null != agent && -1 != agent.indexOf("like Gecko")));    //判断版本,后边是判断IE11的
		    if ( isMSIE ) {
		    try {
		    	filename  = java.net.URLEncoder.encode(filename, "UTF8");
		    } catch (Exception e) {
		    	filename="class-booking-chart";
		    }
		    } else {
			    try {
			    	filename  = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			    } catch (Exception e) {
			   
			    	filename="class-booking-chart";
			    }
		    }
		    response.addHeader("Content-Disposition", "attachment; filename="+ filename + "."+ext);
		    response.addHeader("Content-Type", type);
		    if (null != t) {
				TranscoderInput input = new TranscoderInput(new StringReader(svg));
				TranscoderOutput output = new TranscoderOutput(out);
				
				try {
				    t.transcode(input, output);
				} catch (TranscoderException e) {
				    out.print("Problem transcoding stream. See the web logs for more details.");
				    e.printStackTrace();
				}
		    } else if (ext.equals("svg")) {
		    //	out.print(svg);
		    	OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
		    	writer.append(svg);
		    	writer.close();
		    } else 
		    	out.print("Invalid type: " + type);
		} else {
		    response.addHeader("Content-Type", "text/html");
		    out.println("Usage:\n\tParameter [svg]: The DOM Element to be converted." +
		    		"\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");
		}
		out.flush();
		out.close();
	}