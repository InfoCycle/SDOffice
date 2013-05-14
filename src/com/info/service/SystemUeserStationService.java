package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.common.dao.IBaseDao;
import com.info.domain.TAppGroup;

@Service
public class SystemUeserStationService {

	@Autowired
	IBaseDao<TAppGroup> stationDao;
	public List<TAppGroup> getStationData() {
		String sql="select o from TAppGroup o where o.FState=1 order by o.FSort";
		List<TAppGroup> stations=stationDao.Query(sql);
		return stations;
	}
}
