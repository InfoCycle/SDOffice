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
import com.info.domain.TAppOrg;

@Service
@Transactional
public class SystemOrgService {
	@Autowired
	IBaseDao<TAppOrg> orgDao;
	@Autowired
	AppSEQHelper SEQHelper;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getOrgData() {
		List<TAppOrg> listOrg = getAllOrgData();
		List parentid = new ArrayList();
		for (int i = 0; i < listOrg.size(); i++) {
			TAppOrg org = (TAppOrg) listOrg.get(i);
			if (!parentid.contains(org.getFParentId())) {
				parentid.add(org.getFParentId());
			}
		}
		List tree = buildAllTree(listOrg, parentid, 0);
		return tree;
	}
	
	public TAppOrg saveOrUpdate(TAppOrg object) {
		
		try {
			if(object.getFId()>0){
				orgDao.Update(object);				
			}else {
				object.setFId(SEQHelper.getCurrentVal("SEQ_ORG"));
				orgDao.Persist(object);
			}
			return object;
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean delete(Integer FId) {
		return orgDao.Delete(TAppOrg.class, FId);
	}
	
	private List<TAppOrg> getAllOrgData() {
		String JPQL="select o from TAppOrg o order by o.FSort";
		return orgDao.Query(JPQL);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildAllTree(List<TAppOrg> Org, List parentid,
			Integer rootid) {
		List list = new ArrayList();
		for (int i = 0; i < Org.size(); i++) {
			Map<String, Object> map = new HashMap();
			TAppOrg appOrg = (TAppOrg) Org.get(i);
			if (appOrg.getFParentId().equals(rootid)) {
				if (parentid.contains(appOrg.getFId())) {
					map.put("id", appOrg.getFId().toString());
					map.put("parent", appOrg.getFParentId());
					map.put("text", appOrg.getFName());
					map.put("ano", appOrg.getFAno());
					map.put("state", appOrg.getFState());
					map.put("sort", appOrg.getFSort());
					map.put("expanded", true);
					map.put("leaf", false);
					map.put("children",
							buildAllTree(Org, parentid,
									appOrg.getFId()));
					list.add(map);
				} else {
					map.put("id", appOrg.getFId().toString());
					map.put("parent", appOrg.getFParentId());
					map.put("text", appOrg.getFName());
					map.put("ano", appOrg.getFAno());
					map.put("state", appOrg.getFState());
					map.put("sort", appOrg.getFSort());
					map.put("expanded", true);
					map.put("leaf", true);
					list.add(map);
				}
			}
		}
		return list;
	}
}
