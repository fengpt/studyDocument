	public static <T> List<T> resolveCsv( ZipFile zip , String csvPath , Class<T> clazz) throws Exception{
		List<T> returnList = new ArrayList<T>();
		InputStreamReader inputStreamReader = new InputStreamReader(zip.getInputStream(zip.getEntry(csvPath) ) , "UTF-8");
		CSVReader csvReader = new CSVReader(inputStreamReader,',');  
		//∂¡»°±ÌÕ∑
		List<String> keyList = new ArrayList<String>();
		String[] ss = csvReader.readNext();
		for(String str : ss) keyList.add(str);
		List<String[]> list = csvReader.readAll();  
		for(String[] array : list){  
			T t = clazz.getDeclaredConstructor(new Class[]{}).newInstance();
			for(int i=0;i<array.length;i++){
				BeanUtils.setProperty(t, keyList.get(i), array[i]);
			}  
			returnList.add(t);
		}  
		csvReader.close();  
		return returnList;
	}