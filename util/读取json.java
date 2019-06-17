/**
	 * 读取json文件内容
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public static byte[] resolveFile(ZipFile zip,String path){
		InputStream is = null;
		try {
			is = zip.getInputStream(zip.getEntry(path));
			return IOUtils.toByteArray(is);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}