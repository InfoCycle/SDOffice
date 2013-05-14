package com.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.common.dao.IBaseDao;
import com.info.common.util.AppSEQHelper;
import com.info.domain.TAppFunction;

@Service
@Transactional
public class SystemFunctionService {
	@Autowired
	IBaseDao<TAppFunction> functionDao;
	
	@Autowired
	AppSEQHelper SEQHelper;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getFunctionMenu() {
		List<TAppFunction> listFunction = getAllFunctionMenuData();
		List parentid = new ArrayList();
		for (int i = 0; i < listFunction.size(); i++) {
			TAppFunction function = (TAppFunction) listFunction.get(i);
			if (!parentid.contains(function.getFParentId())) {
				parentid.add(function.getFParentId());
			}
		}
		List tree = buildAllTree(listFunction, parentid, 0);
		return tree;		
	}
	
	public List<TAppFunction> getAllFunctionMenuData() {		
		 String SQL="select o from TAppFunction o order by o.FSort"; 
		 return functionDao.Query(SQL);		 
	}
	public TAppFunction saveOrUpdate(TAppFunction obj) {
		try {
			if(obj.getFId()>0){
				functionDao.Update(obj);
			}else {
				obj.setFId(SEQHelper.getCurrentVal("SEQ_FUNCTION"));
				functionDao.Persist(obj);
			}
			return obj;
		} catch (Exception e) {
			return null;
		}		
	}
	
	public boolean delete(Integer FId) {
		return functionDao.Delete(TAppFunction.class, FId);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildAllTree(List<TAppFunction> FunctionMenu, List parentid,
			Integer rootid) {
		List list = new ArrayList();
		for (int i = 0; i < FunctionMenu.size(); i++) {
			Map<String, Object> map = new HashMap();
			TAppFunction appFunction = (TAppFunction) FunctionMenu.get(i);
			if (appFunction.getFParentId().equals(rootid)) {
				if (parentid.contains(appFunction.getFId())) {
					map.put("id", appFunction.getFId().toString());
					map.put("parent", appFunction.getFParentId());
					map.put("text", appFunction.getFName());
					map.put("state", appFunction.getFState());
					map.put("sort", appFunction.getFSort());
					map.put("expanded", true);
					map.put("leaf", false);
					map.put("children",
							buildAllTree(FunctionMenu, parentid,
									appFunction.getFId()));
					list.add(map);
				} else {
					map.put("id", appFunction.getFId().toString());
					map.put("parent", appFunction.getFParentId());
					map.put("text", appFunction.getFName());
					map.put("state", appFunction.getFState());
					map.put("sort", appFunction.getFSort());
					map.put("expanded", true);
					map.put("link", appFunction.getFFunctionUrl());
					map.put("leaf", true);
					list.add(map);
				}
			}
		}
		return list;
	}
}
