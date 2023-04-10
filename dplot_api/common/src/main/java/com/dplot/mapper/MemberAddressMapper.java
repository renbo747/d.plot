package com.dplot.mapper;

import java.util.List;

import com.dplot.annontation.MapperInterface;
import com.dplot.common.SOMap;

@MapperInterface
public interface MemberAddressMapper {
    /**
     * 회원 주소록 INSERT OR DUPLICATE KEY UPDATE
     * @param params
     * @return
     */
    int mergeMemberAddressByKey(SOMap params);

    /**
     * 회원 배송지 목록 조회
     * @param param
     * @return
     */
	List<SOMap> selectAddressList(SOMap param);
	
	/**
	 * 회원 배송지 조회
	 * @param param
	 * @return
	 */
	SOMap selectAddress(SOMap param) throws Exception;
	
	/**
     * 회원 기본배송지 조회
     * @param param
     * @return
     */
	SOMap selectDefaultAddress(SOMap param);

	/**
	 * 회원 배송지 등록
	 * @param param
	 * @return
	 */
	int insertAddress(SOMap param);
	
	/**
	 * 회원 배송지 수정
	 * @param param
	 * @return
	 */
	int updateAddress(SOMap param);

	/**
	 * 회원 배송지 삭제
	 * @param param
	 * @return
	 */
	int delAddressInfo(SOMap param);

	/**
	 * 기본 배송지 수정
	 * @param param
	 * @return
	 */
	int updateisdefaultCancel(SOMap param);

	/**
	 * 기본배송지 있는지 체크
	 * @param param
	 * @return
	 */
	int selectAddressCnt(SOMap param);
}
