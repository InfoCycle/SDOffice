package com.info.web.controller.util.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class AutoID {
	public int autoId(List<Integer> list) {
		if(list.size()>1){
			for(int i=1;i<list.size();i++){
				if((list.get(i)-list.get(i)-1)>1){
					return list.get(i-1)+1;
				}
			}
		}else {
			if(list.get(0)>1){
				return 1;
			}else {
				return list.get(0)+1;
			}
		}
		return list.get(list.size()-1)+1;
	}
	public String autoCord(List<String> list) {
		SimpleDateFormat sm=new SimpleDateFormat("yyyy");
		String year=sm.format(new Date());
		if (list.size()>0) {
				String scord=list.get(list.size()-1);
				int icord=Integer.parseInt(scord.substring(4));
				return year+cord(icord+1);
		}else {
			return year+"001";
		}
		
	}
	private String cord(int icord) {
		String scord="";
		if (icord<10) {
			scord+="00"+icord;
		} else if(10<icord&&icord<100){
			scord+="0"+icord;
		}else {
			scord+=icord;
		}
		return scord;
	}
	public String autoInduCode(Integer prientId,List<String> codes) {
		List<Integer> cos=new ArrayList();
		String myCode="";
		for (String code : codes) {
			int co=Integer.parseInt(code.substring(3, 5));
			cos.add(co);
		}
		
		myCode+=intToStr(prientId);
		for(int i=1;i<cos.size();i++){
			if(cos.get(i)-cos.get(i-1)>1){
				return myCode+=intToStr(cos.get(i-1)+1);
			}
		}
		return myCode+=intToStr(cos.size());
	}
	private String intToStr(int numb) {
		String string="";
		if(numb<10){
			string+="00";
		}else if (numb<100) {
			string+="0";
		}
		return string+numb;
	}
}
