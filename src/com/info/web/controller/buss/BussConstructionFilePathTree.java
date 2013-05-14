/**
 * @Title		: BussConstructionFilePathTree.java
 * @Date		: 2013-03-20 18-57
 * @Author		: liwx
 * @Description	: 读取特定目录下的文件树形结构
 * @TODO List	: 
 * 遍历某一目录下的所有目录结构  at 2013-03-20 18-57  by liwx

 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.web.controller.buss;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.StringUtil;
import com.info.web.controller.util.BaseController;

/**   
 * @ClassName	: BussConstructionFilePathTree   
 * @Description	: 构造特定目录下的文档结构树
 * @Author		: liwx
 * @Date		: 2013-03-20 18-58   
 */
@Controller
public class BussConstructionFilePathTree extends BaseController{	
	// 指定遍历目录路径
	String DirPath = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
	
	@RequestMapping(value = "/buss/vedio/treepath/")
	public void getFileTree(HttpServletResponse response){
		CurrentResponse = response;
		DirPath = getServletContext().getRealPath("/userfiles/video");
		try {
			readDirTree(DirPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Description	: 遍历特定目录下的所有文件及文件夹，形成tree数据
	 * @Author		: liwx
	 * @Date		: 2013-03-20 19-28
	 * @param dir
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void readDirTree(String dir) throws Exception {
		File file = new File(dir);
		if (!file.exists()) {
			throw new IllegalAccessError("指定目录或文件不存在。");
		}
		Map<Object,Object> map = new HashMap();
		map.put("id", StringUtil.getUUID());
		map.put("parent", "");
		map.put("path", "/userfiles/video");
		map.put("isDir", true);
		map.put("text", "视频目录");
		
		map.put("size", toKByte(getDirSize(file)));
		map.put("time", sdf.format(new Date(file.lastModified())));
		map.put("leaf", false);
		map.put("children", printFileListTree(file));		
		List tree = new ArrayList();
		tree.add(map);
		writeJsonString(getJsonFromArray(tree));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List printFileListTree(File file) {
		List listMap = new ArrayList();
		if (file.isFile()) {
			Map<String, Object> map = new HashMap();
			map.put("id", StringUtil.getUUID());
			map.put("parent", file.getParent());
			map.put("path", getPathUrl(file.getPath()));
			map.put("isDir", false);
			map.put("text", file.getName());
			map.put("size", toKByte(file.length()));
			map.put("time", sdf.format(new Date(file.lastModified())));
			map.put("leaf", false);
			listMap.add(map);
		}
		if (file.isDirectory()) {			
			File[] list = file.listFiles();
			for (File obj : list) {
				Map<String, Object> map = new HashMap();
				if (obj.isDirectory()) {
					map.put("id", StringUtil.getUUID());
					map.put("parent", obj.getParent());
					map.put("path", getPathUrl(obj.getPath()));
					map.put("isDir", true);
					map.put("text", obj.getName());
					map.put("size", toKByte(getDirSize(obj)));
					map.put("time", sdf.format(new Date(obj.lastModified())));
					map.put("leaf", false);
					map.put("children", printFileListTree(obj));
					listMap.add(map);
				}
				if (obj.isFile()) {
					map.put("id", StringUtil.getUUID());
					map.put("parent", obj.getParent());
					map.put("path", getPathUrl(obj.getPath()));
					map.put("isDir", false);
					map.put("text", obj.getName());
					map.put("size", toKByte(obj.length()));
					map.put("time", sdf.format(new Date(obj.lastModified())));
					map.put("leaf", false);
					listMap.add(map);
				}
			}
		}
		return listMap;
	}
	
	private String getPathUrl(String path) {
		path = path.substring(path.indexOf("userfiles"));
		path = path.replace("\\", "/");
		return "/"+path;
	}
	/**
	 * @Description	: 遍历计算文件夹大小
	 * @Author		: liwx
	 * @Date		: 2013-03-21 09-45
	 * @param dir
	 * @return
	 */
    public long getDirSize(File dir) {  
        if (dir == null) {  
            return 0;  
        }  
        if (!dir.isDirectory()) {  
            return 0;  
        }  
        long dirSize = 0;  
        File[] files = dir.listFiles();  
        for (File file : files) {  
            if (file.isFile()) {  
                dirSize += file.length();  
            } else if (file.isDirectory()) {  
                dirSize += file.length();  
                dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计  
            }  
        }  
        return dirSize;  
    } 
    /**
     * @Description	: 文件大小字节转换
     * @Author		: liwx
     * @Date		: 2013-03-21 09-47
     * @param size
     * @return
     */
	private String toKByte(long size) {
		DecimalFormat df = new DecimalFormat("0.##");
        String localSize = "";
        if (size < 1024) {
        	localSize = df.format((double) size) + " B";
        } else if (size < 1048576) {
        	localSize = df.format((double) size / 1024) + " Kb";
        } else if (size < 1073741824) {
        	localSize = df.format((double) size / 1048576) + " Mb";
        } else {
        	localSize = df.format((double) size / 1073741824) + " Gb";
        }
        return localSize;
		
	}
}
