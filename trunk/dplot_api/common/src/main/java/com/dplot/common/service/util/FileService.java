package com.dplot.common.service.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tika.Tika;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dplot.common.CMConst;
import com.dplot.common.SOMap;
import com.dplot.mapper.FileMapper;
import com.dplot.util.DateTimeUtil;
import com.dplot.util.Util;

import net.coobird.thumbnailator.Thumbnails;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class FileService {
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	private String accessKey;

	private String secretKey;

	private String bucketName;

	private String s3Url;
	
	private S3Client s3Client = null;

	private String PREFIX_GOODS_IMG = "img/item/"; // 상품이미지
	
	private String PREFIX_GIFT_IMG = "img/gift/"; // 사은품이미지
	
	private String PREFIX_PARTNER_IMG = "img/partner/"; //파트너 사업자등록증,통장사본,통신판매업신고증
	
	private String PREFIX_BRAND_IMG = "img/brand/"; //브랜드 이미지
	
	private String PREFIX_CATEGORY_IMG = "img/category/"; //이벤트 이미지
	
	private String PREFIX_EXP_IMG = "img/exp/"; //기획전 이미지
	
	private String PREFIX_EVENT_IMG = "img/event/"; //이벤트 이미지
		
	private String PREFIX_QNA_IMG = "img/qna/"; //이미지 QNA
	
	private String PREFIX_ETC_IMG = "img/etc/"; //이미지 기타
	
	private String PREFIX_BOARD_ATTACH = "brd/post/"; //게시판 게시물 첨부파일
	
	private String PREFIX_GOODS_ATTACH = "brd/goods/"; //상품 첨부파일
	
	private String PREFIX_BOARD_EDITOR = "brd/editor/"; //게시판 에디터 이미지파일
	
	private String PREFIX_BOARD_ETC = "brd/etc/"; //첨부파일 기타
	
	private String PREFIX_EXP_MOV = "mov/exp"; //기획전 동영상
	
	private String PREFIX_EVENT_MOV = "mov/exp"; //이벤트 동영상
	
	private String PREFIX_REVIEW_IMG = "img/review/"; //리뷰 이미지
	
	private String PREFIX_REVIEW_MOV = "mov/review/"; //리뷰 동영상
	
	private String PREFIX_INQUIRY_IMG = "img/inquiry/"; //이미지 1:1문의
	
	private String PREFIX_INQUIRY_MOV = "mov/inquiry/"; //문의 동영상
	
	private String PREFIX_AS_INQUIRY_IMG = "img/as/inquiry/"; //AS신청 미이지
	
	private String PREFIX_AS_INQUIRY_MOV = "mov/as/inquiry/"; //AS신청 동영상
	
	private String PREFIX_CLAIM_IMG = "img/claim/"; //클레임 이미지
	
	private String PREFIX_CLAIM_MOV = "mov/claim/"; //클레임 동영상
	
	private String EPFILE = "ep/"; // EP 파일 생성

	@Autowired
	private FileMapper fileMapper;
	
	@Resource(name="propertiesFactory")
	private Properties prop;
	
	@PostConstruct
	public void AWSS3ServiceInit() {
		this.accessKey = prop.getProperty("cloud.aws.s3.accessKey");
		this.secretKey = prop.getProperty("cloud.aws.s3.secretKey");
		this.bucketName = prop.getProperty("cloud.aws.s3.bucket");
		this.s3Url = prop.getProperty("cloud.aws.s3.url");
		
		s3Client = S3Client.builder()
				.region(Region.AP_NORTHEAST_2)
				.credentialsProvider(
						StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
				.build();
	}
	
	/**
	 * 통합 파일 목록 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public SOMap selectFileList(SOMap params) throws Exception {
		SOMap result = new SOMap();
		result.put("uploadedfile", fileMapper.selectFileList(params));
		return result;
	}

	/**
	 * 파일 상세 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public SOMap selectFile(SOMap params) throws Exception {
		SOMap result = new SOMap();
		result.put("file", fileMapper.selectFile(params));
		return result;
	}

//	/**
//	 * OrgIdx 기준으로 Sort 최대값 + 1
//	 *
//	 * @param params
//	 * @return
//	 * @throws Exception
//	 */
//	public int selectMaxSortNumByOrgIdx(SOMap params) throws Exception {
//		return fileMapper.selectMaxSortNumByOrgIdx(params);
//	}
	
	/**
	 * S3파일 다운로드
	 * @param fileIdx
	 * @return
	 * @throws Exception
	 */
	public byte[] get(int fileIdx) throws Exception {
		SOMap params = new SOMap();
		params.put("idx", fileIdx);
		
		return this.get(params);
	}
	
	/**
	 * S3파일 다운로드
	 * @param fileIdx
	 * @return
	 * @throws Exception
	 */
	public byte[] get(SOMap params) throws Exception {
		SOMap file = fileMapper.selectFile(params);
		
		String keyName = file.getDbStr("imgpath") + file.getDbStr("imgfname");
		
		return this.getS3(keyName);
	}
	
	/**
	 * S3파일 다운로드
	 * @param keyName
	 *            (filepath)
	 * @return
	 * @throws IOException
	 * 
	 */
	private byte[] getS3(String keyName) throws IOException {
		ResponseBytes<GetObjectResponse> s3Object = s3Client.getObject(
				GetObjectRequest.builder().bucket(bucketName).key(keyName).build(),
				ResponseTransformer.toBytes());
		final byte[] bytes = s3Object.asByteArray();
		return bytes;
	}
	
	/**
	 * 첨부파일 복사
	 * @param fileIdx
	 * @param orgIdx
	 * @param imgType
	 * @return
	 * @throws Exception
	 */
	public SOMap copyAttach(int fileIdx, int orgIdx, String imgType) throws Exception {
		SOMap params = new SOMap();
		params.put("idx", fileIdx);
		SOMap result = fileMapper.selectFile(params);
		if(result == null) {
			throw new  Exception("파일이 존재하지 않습니다");
		}
		
		byte[] byteImage = this.getS3(result.getDbStr("imgpath") + result.getDbStr("imgfname"));
		
		if(byteImage == null || byteImage.length == 0) {
			throw new  Exception("파일이 존재하지 않습니다");
		}
		
        String contentType = new Tika().detect(byteImage);
		MultipartFile multipartFile = new MockMultipartFile(result.getDbStr("imgfname"), result.getDbStr("imgforiname"), contentType, byteImage);
		
		return this.uploadAttach(multipartFile, orgIdx, imgType);
	}
	
	/**
	 * 상품이지미 복사
	 * @param fileIdx
	 * @param orgIdx
	 * @param imgType
	 * @return
	 * @throws Exception
	 */
	public List<SOMap> copyGoodsImage(int fileIdx, int orgIdx, String imgType) throws Exception {
		SOMap params = new SOMap();
		params.put("idx", fileIdx);
		SOMap result = fileMapper.selectFile(params);
		if(result == null) {
			throw new  Exception("파일이 존재하지 않습니다");
		}
		
		byte[] byteImage = this.getS3(result.getDbStr("imgpath") + result.getDbStr("imgfname"));
		
		if(byteImage == null || byteImage.length == 0) {
			throw new  Exception("파일이 존재하지 않습니다");
		}
		
        String contentType = new Tika().detect(byteImage);
		MultipartFile multipartFile = new MockMultipartFile(result.getDbStr("imgfname"), result.getDbStr("imgforiname"), contentType, byteImage);
		
		return this.uploadGoodsImage(multipartFile, orgIdx, imgType);
	}
	
	/**
	 * 상품 이미지 S3Upload
	 * @param file
	 * @param orgIdx
	 * @param imgType
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class})
	public List<SOMap> uploadGoodsImage(MultipartFile file, int orgIdx, String imgType) throws Exception {
		// 이미지 ContentType
		String contentType = file.getContentType();
		String[] mimeInfo = contentType.split("/");
		
		// 이미지타입 
		if (mimeInfo.length > 1 
				&& !"image".equalsIgnoreCase(mimeInfo[0])) {
			throw new Exception("이미지파일이 아닙니다.");
		}
		
		List<SOMap> result = new ArrayList<SOMap>();
		
		//상품 추가이미지타입
		String addImgTypes1[] = {"IGT001", "IGT004", "IGT032", "IGT035", "IGT038", "IGT041", "IGT044", "IGT047", "IGT050", "IGT053", "IGT056", "IGT059"};
		String addImgTypes2[] = {"IGT002", "IGT005", "IGT033", "IGT036", "IGT039", "IGT042", "IGT045", "IGT048", "IGT051", "IGT054", "IGT057", "IGT060"};
		String addImgTypes3[] = {"IGT003", "IGT006", "IGT034", "IGT037", "IGT040", "IGT043", "IGT046", "IGT049", "IGT052", "IGT055", "IGT058", "IGT061"};
		
		String orgIdxStr = String.valueOf(orgIdx); //상품코드 문자열
		String orgFileName = file.getOriginalFilename(); //원본파일명
		String ext = StringUtils.getFilenameExtension(orgFileName); //파일확장자
		String path = PREFIX_GOODS_IMG + orgIdxStr.substring(orgIdxStr.length() - 2, orgIdxStr.length()) + "/";
		
		int idx = ArrayUtils.indexOf(addImgTypes1, imgType);
		
		String fileName = "";
		if(idx == 0) 
		{ 
			// PC 상품이미지
			fileName = orgIdxStr + "P";
		} else if(idx == 1) 
		{ 
			// 모바일 상품이미지
			fileName = orgIdxStr + "M";
		} else if(idx > 1) 
		{ 
			//추가 상품이미지
			fileName = orgIdxStr + (char)(63 + idx); //알파벳A부터 J까지
					
		} else {
			throw new Exception("[" + imgType + "]상품이미지구분이 아닙니다.");
		}
		
		//원본파일 업로드
		SOMap param1 = new SOMap();
		param1.put("orgidx", orgIdx);
		param1.put("filetype", CMConst.FILE_TYPE_IMG);
		param1.put("imgtype", imgType);
		param1.put("imgpath", path);
		param1.put("imgfname", fileName + "_" +  + System.currentTimeMillis() + "." + ext);
		param1.put("sort", 1);
		param1.put("imgforiname", orgFileName);
		param1.put("host", s3Url);
		param1.put("reguserid", "test");
		param1.put("fullpath", s3Url + path + param1.getDbStr("imgfname")); 
		if(!this.putS3(file, path + param1.getDbStr("imgfname"))){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		fileMapper.insertFile(param1);
		result.add(param1);
		
		//상품메인이미지일경우 중,소이미지 생성
		if(CMConst.IMG_TYPE_GOODS_IMG_PC_B.equals(imgType) || CMConst.IMG_TYPE_GOODS_IMG_MO_B.equals(imgType)) {
			//중간이미지 업로드(400 X 400)
			ImageIO.setUseCache(false);
			BufferedImage imageM = this.resizeImage(ImageIO.read(file.getInputStream()), 400, 400);
			SOMap param2 = new SOMap(param1);
			param2.put("parentidx", param1.getDbInt("idx"));
			param2.put("imgtype", addImgTypes2[idx]);
			param2.put("imgfname", fileName + "M_" +  + System.currentTimeMillis() + "." + ext);
			param2.put("fullpath", s3Url + path + param2.getDbStr("imgfname"));
			if(!this.putS3(imageM, ext, path + param2.getDbStr("imgfname"))){
				throw new Exception("파일업로드 중 에러가 발생하였습니다.");
			}
			fileMapper.insertFile(param2);
			result.add(param2);
			
			//작은이미지 업로드(200 X 200)
			ImageIO.setUseCache(false);
			BufferedImage imageS = this.resizeImage(ImageIO.read(file.getInputStream()), 200, 200);
			SOMap param3 = new SOMap(param1);
			param3.put("parentidx", param1.getDbInt("idx"));
			param3.put("imgtype", addImgTypes3[idx]);
			param3.put("imgfname", fileName + "S_" + System.currentTimeMillis() +  "." + ext);
			param3.put("fullpath", s3Url + path + param3.getDbStr("imgfname"));
			if(!this.putS3(imageS, ext, path + param3.getDbStr("imgfname"))){
				throw new Exception("파일업로드 중 에러가 발생하였습니다.");
			}
			fileMapper.insertFile(param3);
			result.add(param3);
		}
		
		return result;
	}

	public List<SOMap> uploadGoodsImage(File file, int orgIdx, String imgType) throws Exception {
		// 이미지 ContentType
		String contentType = new Tika().detect(file);
		String[] mimeInfo = contentType.split("/");
		
		// 이미지타입 
		if (mimeInfo.length > 1 
				&& !"image".equalsIgnoreCase(mimeInfo[0])) {
			throw new Exception("이미지파일이 아닙니다.");
		}
		
		List<SOMap> result = new ArrayList<SOMap>();
		
		//상품 추가이미지타입
		String addImgTypes1[] = {"IGT001", "IGT004", "IGT032", "IGT035", "IGT038", "IGT041", "IGT044", "IGT047", "IGT050", "IGT053", "IGT056", "IGT059"};
		String addImgTypes2[] = {"IGT002", "IGT005", "IGT033", "IGT036", "IGT039", "IGT042", "IGT045", "IGT048", "IGT051", "IGT054", "IGT057", "IGT060"};
		String addImgTypes3[] = {"IGT003", "IGT006", "IGT034", "IGT037", "IGT040", "IGT043", "IGT046", "IGT049", "IGT052", "IGT055", "IGT058", "IGT061"};
		
		String orgIdxStr = String.valueOf(orgIdx); //상품코드 문자열
		String orgFileName = file.getName(); //원본파일명
		String ext = StringUtils.getFilenameExtension(orgFileName); //파일확장자
		String path = PREFIX_GOODS_IMG + orgIdxStr.substring(orgIdxStr.length() - 2, orgIdxStr.length()) + "/";
		
		int idx = ArrayUtils.indexOf(addImgTypes1, imgType);
		
		String fileName = "";
		if(idx == 0) 
		{ 
			// PC 상품이미지
			fileName = orgIdxStr + "P";
		} else if(idx == 1) 
		{ 
			// 모바일 상품이미지
			fileName = orgIdxStr + "M";
		} else if(idx > 1) 
		{ 
			//추가 상품이미지
			fileName = orgIdxStr + (char)(63 + idx); //알파벳A부터 J까지
					
		} else {
			throw new Exception("[" + imgType + "]상품이미지구분이 아닙니다.");
		}
		
		//원본파일 업로드
		SOMap param1 = new SOMap();
		param1.put("orgidx", orgIdx);
		param1.put("filetype", CMConst.FILE_TYPE_IMG);
		param1.put("imgtype", imgType);
		param1.put("imgpath", path);
		param1.put("imgfname", fileName + "_" +  + System.currentTimeMillis() + "." + ext);
		param1.put("sort", 1);
		param1.put("imgforiname", orgFileName);
		param1.put("host", s3Url);
		param1.put("reguserid", "test");
		param1.put("fullpath", s3Url + path + param1.getDbStr("imgfname")); 
		if(!this.putS3(file, path + param1.getDbStr("imgfname"))){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		fileMapper.insertFile(param1);
		result.add(param1);
		
		//상품메인이미지일경우 중,소이미지 생성
		if(CMConst.IMG_TYPE_GOODS_IMG_PC_B.equals(imgType) || CMConst.IMG_TYPE_GOODS_IMG_MO_B.equals(imgType)) {
			//중간이미지 업로드(400 X 400)
			ImageIO.setUseCache(false);
			BufferedImage imageM = this.resizeImage(ImageIO.read(file), 400, 400);
			SOMap param2 = new SOMap(param1);
			param2.put("parentidx", param1.getDbInt("idx"));
			param2.put("imgtype", addImgTypes2[idx]);
			param2.put("imgfname", fileName + "M_" +  + System.currentTimeMillis() + "." + ext);
			param2.put("fullpath", s3Url + path + param2.getDbStr("imgfname"));
			if(!this.putS3(imageM, ext, path + param2.getDbStr("imgfname"))){
				throw new Exception("파일업로드 중 에러가 발생하였습니다.");
			}
			fileMapper.insertFile(param2);
			result.add(param2);
			
			//작은이미지 업로드(200 X 200)
			ImageIO.setUseCache(false);
			BufferedImage imageS = this.resizeImage(ImageIO.read(file), 200, 200);
			SOMap param3 = new SOMap(param1);
			param3.put("parentidx", param1.getDbInt("idx"));
			param3.put("imgtype", addImgTypes3[idx]);
			param3.put("imgfname", fileName + "S_" + System.currentTimeMillis() +  "." + ext);
			param3.put("fullpath", s3Url + path + param3.getDbStr("imgfname"));
			if(!this.putS3(imageS, ext, path + param3.getDbStr("imgfname"))){
				throw new Exception("파일업로드 중 에러가 발생하였습니다.");
			}
			fileMapper.insertFile(param3);
			result.add(param3);
		}
		
		return result;
	}
	
	/**
	 * 사은품 이미지 S3Upload
	 * @param file
	 * @param orgIdx
	 * @param imgType
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class})
	public List<SOMap> uploadGiftImage(MultipartFile file, int orgIdx, String imgType) throws Exception {
		// 이미지 ContentType
		String contentType = file.getContentType();
		String[] mimeInfo = contentType.split("/");
		
		// 이미지타입 
		if (mimeInfo.length > 1 
				&& !"image".equalsIgnoreCase(mimeInfo[0])) {
			throw new Exception("이미지파일이 아닙니다.");
		}
		
		List<SOMap> result = new ArrayList<SOMap>();
		
		//사은품 추가이미지타입
		String addImgTypes1[] = {"IGT012", "IGT015"};
		String addImgTypes2[] = {"IGT013", "IGT016"};
		String addImgTypes3[] = {"IGT014", "IGT017"};
		
		String orgIdxStr = String.valueOf(orgIdx); //상품코드 문자열
		String orgFileName = file.getOriginalFilename(); //원본파일명
		String ext = StringUtils.getFilenameExtension(orgFileName); //파일확장자
		String path = PREFIX_GIFT_IMG + orgIdxStr.substring(orgIdxStr.length() - 2, orgIdxStr.length()) + "/";
		
		int idx = ArrayUtils.indexOf(addImgTypes1, imgType);
		
		String fileName = "";
		if(idx == 0) 
		{ 
			// PC 사은품이미지
			fileName = orgIdxStr + "P";
		} else if(idx == 1) 
		{ 
			// 모바일 사은품이미지
			fileName = orgIdxStr + "M";
		} else {
			throw new Exception("[" + imgType + "]사은품이미지구분이 아닙니다.");
		}
		
		//원본파일 업로드
		SOMap param1 = new SOMap();
		param1.put("orgidx", orgIdx);
		param1.put("filetype", CMConst.FILE_TYPE_IMG);
		param1.put("imgtype", imgType);
		param1.put("imgpath", path);
		param1.put("imgfname", fileName + "_" +  + System.currentTimeMillis() + "." + ext);
		param1.put("sort", 1);
		param1.put("imgforiname", orgFileName);
		param1.put("host", s3Url);
		param1.put("reguserid", "test");
		param1.put("fullpath", s3Url + path + param1.getDbStr("imgfname")); 
		if(!this.putS3(file, path + param1.getDbStr("imgfname"))){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		fileMapper.insertFile(param1);
		result.add(param1);
		
		if(CMConst.IMG_TYPE_GIFT_IMG_MO_B.equals(imgType)) {
			//중간이미지 업로드(360 X 360)
			ImageIO.setUseCache(false);
			BufferedImage imageM = this.resizeImage(ImageIO.read(file.getInputStream()), 332, 200);
			SOMap param2 = new SOMap(param1);
			param2.put("parentidx", param1.getDbInt("idx"));
			param2.put("imgtype", addImgTypes2[idx]);
			param2.put("imgfname", fileName + "M_" +  + System.currentTimeMillis() + "." + ext);
			param2.put("fullpath", s3Url + path + param2.getDbStr("imgfname"));
			if(!this.putS3(imageM, ext, path + param2.getDbStr("imgfname"))){
				throw new Exception("파일업로드 중 에러가 발생하였습니다.");
			}
			fileMapper.insertFile(param2);
			result.add(param2);
		} else {
			//중간이미지 업로드(360 X 360)
			ImageIO.setUseCache(false);
			BufferedImage imageM = this.resizeImage(ImageIO.read(file.getInputStream()), 360, 360);
			SOMap param2 = new SOMap(param1);
			param2.put("parentidx", param1.getDbInt("idx"));
			param2.put("imgtype", addImgTypes2[idx]);
			param2.put("imgfname", fileName + "M_" +  + System.currentTimeMillis() + "." + ext);
			param2.put("fullpath", s3Url + path + param2.getDbStr("imgfname"));
			if(!this.putS3(imageM, ext, path + param2.getDbStr("imgfname"))){
				throw new Exception("파일업로드 중 에러가 발생하였습니다.");
			}
			fileMapper.insertFile(param2);
			result.add(param2);
			
			//작은이미지 업로드(159 X 159)
			ImageIO.setUseCache(false);
			BufferedImage imageS = this.resizeImage(ImageIO.read(file.getInputStream()), 159, 159);
			SOMap param3 = new SOMap(param1);
			param3.put("parentidx", param1.getDbInt("idx"));
			param3.put("imgtype", addImgTypes3[idx]);
			param3.put("imgfname", fileName + "S_" + System.currentTimeMillis() +  "." + ext);
			param3.put("fullpath", s3Url + path + param3.getDbStr("imgfname"));
			if(!this.putS3(imageS, ext, path + param3.getDbStr("imgfname"))){
				throw new Exception("파일업로드 중 에러가 발생하였습니다.");
			}
			fileMapper.insertFile(param3);
			result.add(param3);
		}
		
		
		return result;
	}
	

	
	/**
	 * 썸네일이미지 S3Upload
	 * @param file
	 * @param orgIdx
	 * @param imgType
	 * @return
	 */
	public SOMap uploadThumbImage(MultipartFile file, SOMap map, int width, int height) throws Exception {
		// 이미지 ContentType
		String contentType = file.getContentType();
		String[] mimeInfo = contentType.split("/");
		
		// 이미지타입 
		if (mimeInfo.length > 1 
				&& !"image".equalsIgnoreCase(mimeInfo[0])) {
			throw new Exception("이미지파일이 아닙니다.");
		}
		
		SOMap param = new SOMap(map);
		String fileName = param.getDbStr("imgfname"); 
		String ext = StringUtils.getFilenameExtension(fileName);
		fileName = fileName.replace(ext, "") + "_thumb";
		param.put("imgfname", fileName + "." + ext);

		ImageIO.setUseCache(false);
		BufferedImage image = this.resizeImage(ImageIO.read(file.getInputStream()), width, height);
		
		param.put("imgfname", fileName + "_" +  + System.currentTimeMillis() + "." + ext);
		param.put("fullpath", s3Url + param.getDbStr("path") + param.getDbStr("imgfname")); 
		
		if(!this.putS3(image, ext, s3Url + param.getDbStr("path") + param.getDbStr("imgfname"))){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		fileMapper.insertFile(param);
		
		return param;
	}
	
	/**
	 * 동영상 파일 업로드
	 * @param file
	 * @param orgIdx
	 * @param imgType
	 * @return
	 * @throws Exception
	 */
	public SOMap uploadMov(MultipartFile file, int orgIdx, String imgType) throws Exception {
		// 이미지 ContentType
		String contentType = file.getContentType();
		String[] mimeInfo = contentType.split("/");
		
		if (mimeInfo.length > 1 && !"video".equalsIgnoreCase(mimeInfo[0])) {
			throw new Exception("동영상파일이 아닙니다.");
		}
		
		String orgIdxStr = String.valueOf(orgIdx);
		String orgFileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(orgFileName);
		String fileName = orgIdxStr + "_" + System.currentTimeMillis();
		
		String path = "";
		switch (imgType) {
		case CMConst.IMG_TYPE_EVENT_REPLY_VIDEO :    //이벤트 댓글  동영상
			path = PREFIX_EVENT_MOV + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_EVENTEXP_REPLY_VIDEO : //체험단 댓글 동영상
			path = PREFIX_EXP_MOV + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_REVIEW_MOV :           //리뷰 동영상
			path = PREFIX_REVIEW_MOV + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_INQUIRY :           //문의 동영상
			path = PREFIX_INQUIRY_MOV + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_AS_INQUIRY :
			path = PREFIX_AS_INQUIRY_MOV + orgIdxStr + "/";
			break;			
		case CMConst.IMG_TYPE_CLAIM :
			path = PREFIX_CLAIM_MOV + orgIdxStr + "/"; // 클렝미 동영상
			break;
		default:
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		
		
		SOMap param = new SOMap();
		param.put("orgidx", orgIdx);
		param.put("filetype", CMConst.FILE_TYPE_VIDEO);
		param.put("imgtype", imgType);
		param.put("imgpath", path);
		param.put("imgfname", fileName + "." + ext);
		param.put("sort", 1);
		param.put("imgforiname", orgFileName);
		param.put("host", s3Url);
		param.put("reguserid", "test");
		param.put("fullpath", s3Url + path + param.getDbStr("imgfname"));
		
		String rootpath = prop.getProperty("base.temp.template.path");
		
    	File movFile = new File(rootpath + fileName);
    	
    	// 최초 업로드시 업로드 저장용 폴더를 생성
		if(!movFile.exists()) {
			movFile.mkdirs();
		}
		//임시파일 저장
		file.transferTo(movFile);
		
		//동영상 썸네일 추출
		BufferedImage bufferedImage = null;
		try {
			FileChannelWrapper fcw =  NIOUtils.readableChannel(movFile);
			FrameGrab fg = FrameGrab.createFrameGrab(fcw);
			
			fg.seekToSecondPrecise(1);
			Picture picture = fg.getNativeFrame();
			bufferedImage = AWTUtil.toBufferedImage(picture);
			//임시 사이즈 변경(360*360)
			bufferedImage = this.resizeImage(bufferedImage, 360, 360);
			
			this.putS3(bufferedImage, "jpg", path + fileName + ".jpg");
			
			NIOUtils.closeQuietly(fcw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}	
		
		//동영상 파일 업로드
		if(!this.putS3(movFile, path + param.getDbStr("imgfname"))){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
				
		//db저장처리
		fileMapper.insertFile(param);
		
		//임시파일삭제
		movFile.delete();
		
		return param;
	}
	
	/**
	 * 이미지 S3Upload
	 * @param file
	 * @param orgIdx
	 * @param imgType
	 * @return
	 */
	public SOMap uploadImage(MultipartFile file, int orgIdx, String imgType) throws Exception {
		// 이미지 ContentType
		String contentType = file.getContentType();
		String[] mimeInfo = contentType.split("/");
		
		// 이미지타입 
		if (mimeInfo.length > 1 
				&& !"image".equalsIgnoreCase(mimeInfo[0])) {
			throw new Exception("이미지파일이 아닙니다.");
		}
		
		String orgIdxStr = String.valueOf(orgIdx);
		String orgFileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(orgFileName);
		String fileName = orgIdxStr + "_" + System.currentTimeMillis();
		
		String path = "";
		switch (imgType) {
		case CMConst.IMG_TYPE_PARTNER1 :
		case CMConst.IMG_TYPE_PARTNER2 :
		case CMConst.IMG_TYPE_PARTNER3 :
			//입점업체 사업자등록증,통장사본,통신판매어신고증
			path = PREFIX_PARTNER_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_EXP_PC :
		case CMConst.IMG_TYPE_EXP_MO :
			//기획전 리스트 이미지
			path = PREFIX_EXP_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_BRAND_LOGO :
		case CMConst.IMG_TYPE_BRAND_IMG_PC :
		case CMConst.IMG_TYPE_BRAND_IMG_MO :
			path = PREFIX_BRAND_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_CATEGORY_IMG_PC :
		case CMConst.IMG_TYPE_CATEGORY_IMG_MO :
			path = PREFIX_CATEGORY_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_EVENT_IMG_MAIN_PC :
		case CMConst.IMG_TYPE_EVENT_IMG_MAIN_MO :
		case CMConst.IMG_TYPE_EVENT_IMG_REPLY :
			path = PREFIX_EVENT_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_INQUIRY :
			path = PREFIX_INQUIRY_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_AS_INQUIRY:
			path = PREFIX_AS_INQUIRY_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_QNA :
			path = PREFIX_QNA_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_REVIEW_IMG :
			path = PREFIX_REVIEW_IMG + orgIdxStr + "/";
			break;
		case CMConst.IMG_TYPE_CLAIM :
			path = PREFIX_CLAIM_IMG + orgIdxStr + "/";
			break;	
		default:
			// 기타 이미지
			path = PREFIX_ETC_IMG + orgIdxStr + "/";
			break;
		}
		
		
		SOMap param = new SOMap();
		param.put("orgidx", orgIdx);
		param.put("filetype", CMConst.FILE_TYPE_IMG);
		param.put("imgtype", imgType);
		param.put("imgpath", path);
		param.put("imgfname", fileName + "." + ext);
		param.put("sort", 1);
		param.put("imgforiname", orgFileName);
		param.put("host", s3Url);
		param.put("reguserid", "test");
		param.put("fullpath", s3Url + path + param.getDbStr("imgfname"));
		
		if(!this.putS3(file, path + param.getDbStr("imgfname"))){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		fileMapper.insertFile(param);
		
		return param;
	}

	/**
	 * 첨부파일  S3Upload
	 * @param file
	 * @param orgIdx
	 * @param imgType
	 * @return
	 */
	public SOMap uploadAttach(MultipartFile file, int orgIdx) throws Exception {
		return this.uploadAttach(file, orgIdx, null);
	}
	
	/**
	 * 첨부파일  S3Upload
	 * @param file
	 * @param orgIdx
	 * @param imgType
	 * @return
	 */
	public SOMap uploadAttach(MultipartFile file, int orgIdx, String fileType) throws Exception {
			
		if("params".equals(file.getName()))
			return null;
		
		String orgIdxStr = String.valueOf(orgIdx);
		String orgFileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(orgFileName);
		String fileName = orgIdxStr + "_" + System.currentTimeMillis();
		
		String path = "";
		if(fileType == null) {
			path = PREFIX_BOARD_ATTACH + orgIdxStr + "/";
		} else if(CMConst.IMG_TYPE_GOODS_ATTACH.equals(fileType)){
			path = PREFIX_GOODS_ATTACH + orgIdxStr + "/";
		} else {
			path = PREFIX_BOARD_ETC + orgIdxStr + "/";
		}
		
		SOMap param = new SOMap();
		param.put("orgidx", orgIdx);
		param.put("filetype", CMConst.FILE_TYPE_ATTACH);
		param.put("imgtype", fileType);
		param.put("imgpath",  path);
		param.put("imgfname", fileName + "." + ext);
		param.put("sort", 1);
		param.put("imgforiname", orgFileName);
		param.put("host", s3Url);
		param.put("reguserid", "test");
		param.put("fullpath", s3Url + path + param.getDbStr("imgfname")); 
		
		if(!this.putS3(file, path + param.getDbStr("imgfname"))){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		fileMapper.insertFile(param);
		
		return param;
	}
	
	/**
	 * 에디터 이미지 S3Upload
	 */
	public String uploadEditFile(MultipartFile file) throws Exception {
			
		if("params".equals(file.getName()))
			return null;
		
		String orgFileName = file.getOriginalFilename();
		String ext = StringUtils.getFilenameExtension(orgFileName);
		String path = PREFIX_BOARD_EDITOR + DateTimeUtil.getNowDatePartShortStr() + "/";
		String fileName = Util.getGUID() + "." + ext;
		
		if(!this.putS3(file, path + fileName)){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		}
		
		return s3Url + path + fileName;
	}
	
	/**
	 * ep파일 S3Upload
	 */
	public String uploadEpFile(String file, String fileName, String path) throws Exception {
		String bucketName = EPFILE;

		if(path.contains(prop.getProperty("base.upload.meta.ep.path"))) {
			bucketName += "meta_ep/"; 
		} else if(path.contains(prop.getProperty("base.upload.google.ep.path"))) {
			bucketName += "google_ep/"; 
		}
		
		if(!this.putEpS3(file, fileName, bucketName)){
			throw new Exception("파일업로드 중 에러가 발생하였습니다.");
		} 
		
		return s3Url + bucketName + fileName;
	}
	
	/**
	 * epFile이 존재하는지 체크
	 * */
	private boolean getS3Yn(String keyName) {
		boolean fileYn = true;
		try {
			ResponseBytes<GetObjectResponse> s3Object = s3Client.getObject(
				GetObjectRequest.builder().bucket(bucketName).key(keyName).build(),
				ResponseTransformer.toBytes());
		} catch (AwsServiceException ase) {
			fileYn = false;
		}
		return fileYn;
	}
	
	/**
	 * epFile을 S3에 업로드
	 * @param file
	 * @param key
	 * @return
	 * @throws IOException
	 */
	private boolean putEpS3(String file, String fileName, String path) throws IOException {
		boolean uploadSuccess = true;
		InputStream is = null;
		String newDir = String.format("%s%s", path, DateTimeUtil.getNowDatePartShortStr());
		String key = path + fileName;
			
		try {
			boolean fileYn = this.getS3Yn(key);
			logger.error("============= EP파일 생성 =============");
			logger.error("============= EP파일 존재여부 :" + fileYn + "  =============");
			
			// 파일이 있으면 새로운 경로에 백업파일로 생성
			if(fileYn == true) {
				String newFilePath = String.format("%s/%s_%s",newDir, fileName, DateTimeUtil.getNowFullShortStr());
				CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder().sourceBucket(bucketName).sourceKey(key).destinationBucket(bucketName).destinationKey(newFilePath).build();
				s3Client.copyObject(copyObjectRequest);
				DeleteObjectRequest del = DeleteObjectRequest.builder().bucket(bucketName).key(key).build();
				s3Client.deleteObject(del);
				logger.error("============= EP파일 원본파일 key :" + key + "  =============");
				logger.error("============= EP파일 백업파일 newFilePath :" + newFilePath + "  =============");
			}
			PutObjectRequest objectRequest = getPutObjectRequest(key);
			is = new ByteArrayInputStream(file.getBytes("UTF-8"));
			RequestBody rb = RequestBody.fromInputStream(is, file.getBytes("UTF-8").length);
			s3Client.putObject(objectRequest, rb);
			
			logger.error("============= EP파일 생성완료 =============");
		} catch (AwsServiceException ase) {
			logger.error("============= AmazonServiceException =============");
			logger.error("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("===================================================");
			uploadSuccess = false;
		} catch (Exception e) {
			logger.error("FileService.putEpS3 :: ", e);
			uploadSuccess = false;
		} finally {
			if(is != null) {
				try{is.close();} catch(Exception ex) {logger.error("", ex);}
			}
		}
		return uploadSuccess;
	}
	
	/**
	 * MultipartFile을 S3에 업로드
	 * @param file
	 * @param key
	 * @return
	 * @throws IOException
	 */
	private boolean putS3(MultipartFile file, String key) throws IOException {
		boolean uploadSuccess = true;
		try {
			PutObjectRequest objectRequest = getPutObjectRequest(key);
			RequestBody rb = getFileRequestBody(file);
			s3Client.putObject(objectRequest, rb);
			logger.debug("S3Upload Success :: " + this.findUploadKeyUrl(key));
		} catch (AwsServiceException ase) {
			logger.error("============= AmazonServiceException =============");
			logger.error("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("===================================================");
			uploadSuccess = false;
		} catch (Exception e) {
			logger.error("FileService.putS3 :: ", e);
			uploadSuccess = false;
		}
		
		return uploadSuccess;
	}
	
	/**
	 * File을 S3에 업로드
	 * @param file
	 * @param key
	 * @return
	 * @throws IOException
	 */
	private boolean putS3(File file, String key) throws IOException {
		boolean uploadSuccess = true;
		InputStream is = null;
		try {
			PutObjectRequest objectRequest = getPutObjectRequest(key);
			
			is = new FileInputStream(file);
			
			RequestBody rb = RequestBody.fromInputStream(is, file.length());
			s3Client.putObject(objectRequest, rb);
			
			logger.debug("S3Upload Success :: " + this.findUploadKeyUrl(key));
		} catch (AwsServiceException ase) {
			logger.error("============= AmazonServiceException =============");
			logger.error("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("===================================================");
			uploadSuccess = false;
		} catch (Exception e) {
			logger.error("FileService.putS3 :: ", e);
			uploadSuccess = false;
		} finally {
			if(is != null) {
				try{is.close();} catch(Exception ex) {logger.error("", ex);}
			}
		}
		
		return uploadSuccess;
	}
	
	/**
	 * BufferedImage를 S3에 업로드
	 * @param file
	 * @param key
	 * @return
	 * @throws IOException
	 */
	private boolean putS3(BufferedImage image, String ext, String key) throws IOException {
		boolean uploadSuccess = true;
		try {
			PutObjectRequest objectRequest = getPutObjectRequest(key);
			RequestBody rb = getFileRequestBody(image, ext);
			s3Client.putObject(objectRequest, rb);
			
			logger.debug("S3Upload Success :: " + this.findUploadKeyUrl(key));
		} catch (AwsServiceException ase) {
			logger.error("============= AmazonServiceException =============");
			logger.error("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("===================================================");
			uploadSuccess = false;
		} catch (Exception e) {
			logger.error("FileService.putS3 :: ", e);
			uploadSuccess = false;
		}
		
		return uploadSuccess;
	}
	
	
	/**
	 * S3파일 파일삭제
	 * @param fileIdx
	 * @return
	 * @throws Exception
	 */
	public void delete(int fileIdx) throws Exception {
		SOMap params = new SOMap();
		params.put("idx", fileIdx);
		
		this.delete(params);
	}
	
	/**
	 * 상품이미지 S3파일 파일삭제
	 * @param fileIdx
	 * @return
	 * @throws Exception
	 */
	public void deleteGoodsImage(int fileIdx) throws Exception {
		SOMap params = new SOMap();
		params.put("idx", fileIdx);
		
		// 원본파일 삭제
		this.deleteGoodsImageFile(params);
		
		// 썸네일 삭제
		SOMap params2 = new SOMap();
		params2.put("parentidx", params.getDbStr("idx"));
		List<SOMap> childFiles = fileMapper.selectFileList(params2);
		for (SOMap soMap : childFiles) {
			this.deleteGoodsImageFile(soMap);
		}
	}
	
	/**
	 * S3파일 파일삭제
	 * @param fileIdx
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Throwable.class})
	public void delete(SOMap params) throws Exception {
		// 원보파일 삭제
		this.deleteFile(params);
		
		// 썸네일 삭제
		SOMap params2 = new SOMap();
		params2.put("parentidx", params.getDbStr("idx"));
		List<SOMap> childFiles = fileMapper.selectFileList(params2);
		for (SOMap soMap : childFiles) {
			this.deleteFile(soMap);
		}
	}
	
	/**
	 * 상품이미지 S3파일 파일삭제
	 * - S3 이미지 그대로 두고 T_FILE 에서만 삭제처리
	 * @param fileIdx
	 * @return
	 * @throws Exception
	 */
	private void deleteGoodsImageFile(SOMap params) throws Exception {
		SOMap file = fileMapper.selectFile(params);
		if(file == null) {
			throw new Exception("파일이 존재하지 않습니다.");
		}
		
		if("T".equals(file.getDbStr("istrash"))) {
			throw new Exception("이미 삭제처리된 파일입니다.");
		}
		
		fileMapper.deleteFile(params);
	}
	
	/**
	 * S3파일 파일삭제
	 * @param fileIdx
	 * @return
	 * @throws Exception
	 */
	private void deleteFile(SOMap params) throws Exception {
		SOMap file = fileMapper.selectFile(params);
		if(file == null) {
			throw new Exception("파일이 존재하지 않습니다.");
		}
		
		if("T".equals(file.getDbStr("istrash"))) {
			throw new Exception("이미 삭제처리된 파일입니다.");
		}
		
		String keyName = "";
		
		if(file != null) {
			keyName = file.getDbStr("imgpath") + file.getDbStr("imgfname");
		}
		
		if(!this.deleteS3(keyName)){
			throw new Exception("파일삭제 중 에러가 발생하였습니다.");
		}
		
		fileMapper.deleteFile(params);
	}
	
	/**
	 * S3 파일삭제
	 * @param keyName
	 * @return
	 * @throws IOException
	 */
	private boolean deleteS3(String keyName) throws IOException {
		boolean result = false;
		try {
			s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(keyName).build());
			
			result = true;
		} catch (AwsServiceException ase) {
			logger.error("============= AmazonServiceException =============");
			logger.error("Caught an AmazonServiceException from DELETE requests, rejected reasons:");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("===================================================");
		} catch (Exception e) {
			logger.error("FileService.result :: ", e);
		}
		
		return result;
	}
	
	private PutObjectRequest getPutObjectRequest(String key) {
		return PutObjectRequest.builder().bucket(bucketName).key(key).build();
	}

	private RequestBody getFileRequestBody(File file) throws IOException {
		return RequestBody.fromFile(file);
	}
	
	private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
		return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
	}
	
	private RequestBody getFileRequestBody(BufferedImage image, String format) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.setUseCache(false);
		ImageIO.write(image, format, os);
		return RequestBody.fromBytes(os.toByteArray());
	}
	
	private String findUploadKeyUrl(String key) {
		S3Utilities s3Utilities = s3Client.utilities();
		GetUrlRequest request = GetUrlRequest.builder().bucket(bucketName).key(key).build();
		URL url = s3Utilities.getUrl(request);
		return url.toString();
	}
	
	/**
	 * 이미지 리사이즈
	 * @param bufferedImage
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 * @throws IOException
	 */
	private BufferedImage resizeImage(BufferedImage bufferedImage, int targetWidth, int targetHeight) throws IOException {
		return Thumbnails.of(bufferedImage)
			.size(targetWidth, targetHeight)
			.keepAspectRatio(true)
			.allowOverwrite(true)
			.useOriginalFormat()
			.outputQuality(1.0f)
			.asBufferedImage();
	}
}
