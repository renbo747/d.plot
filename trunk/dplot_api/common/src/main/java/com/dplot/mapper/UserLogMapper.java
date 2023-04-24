package com.dplot.mapper;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

/**
 * The Interface UserLogMapper.
 */
@MapperInterface
public interface UserLogMapper {
	
	/**
	 * Insert user log.
	 *
	 * @param params the params
	 * @return the integer
	 */
	Integer insertUserLog(SOMap userLog);

	/**
	 *
	 * @param params
	 * @return
	 */
	int insertUserLogArray(SOMap params);
	
	/**
	 * Delete user log by user no.
	 *
	 * @param params the params
	 * @return the integer
	 */
	Integer deleteUserLogByUserNo(SOMap user);

}
