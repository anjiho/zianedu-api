package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.BbsMasterKeyType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.mapper.TeacherMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getTeacherHomeInfo(int teacherKey, int listLimit, int device, int menuCtgKey) {
        int resultCode = OK.value();

        TeacherHomeVO teacherHomeInfo = null;
        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //강사기본정보
            TTeacherVO teacherInfo = this.getTeacherInfo(teacherKey);
            //강사 상세설명 정보
            teacherInfo.setIntroduce(teacherMapper.selectTeacherIntroduceInfo(teacherKey, device, menuCtgKey));
            //학습자료실
            List<TBbsDataVO> referenceRoom = boardMapper.selectTBbsDataList(
                    BbsMasterKeyType.LEARNING_REFERENCE_ROOM.getBbsMasterKey(), teacherKey, ZianApiUtils.LIMIT,0, listLimit
            );
            //학습 QNA
            List<TBbsDataVO> learningQna = boardMapper.selectTBbsDataList(
                    BbsMasterKeyType.LEARNING_QNA.getBbsMasterKey(), teacherKey,ZianApiUtils.LIMIT,0, listLimit
            );
            //수강후기
            List<GoodsReviewVO> lectureReview = boardMapper.selectGoodsReviewList(teacherKey, ZianApiUtils.LIMIT, 0, listLimit);
            //동영상강좌정보
            List<GoodsListVO> videoLecture = teacherMapper.selectGoodsListAtTeacherHome(teacherKey, 1);
            //학원강좌정보
            List<GoodsListVO> academyLecture = teacherMapper.selectGoodsListAtTeacherHome(teacherKey, 2);
            //도서정보
            List<BannerBookVO> teacherBook = teacherMapper.selectTeacherBookList(teacherKey);

            teacherHomeInfo = new TeacherHomeVO(teacherInfo, referenceRoom, learningQna, lectureReview, videoLecture, academyLecture, teacherBook);
        }
        return new ApiResultObjectDTO(teacherHomeInfo, resultCode);
    }

    /**
     * 강사정보 가져오기
     * @param teacherKey
     * @return
     */
    @Transactional(readOnly = true)
    public TTeacherVO getTeacherInfo(int teacherKey) {
        if (teacherKey == 0) return null;

        TTeacherVO teacherInfo = teacherMapper.selectTeacherInfo(teacherKey);
        if (teacherInfo != null) {
            teacherInfo.setImageListUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherInfo.getImageList()));
            teacherInfo.setImageTeacherListUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherInfo.getImageTeacherList()));
            teacherInfo.setImageTeacherViewUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherInfo.getImageTeacherView()));
        }
        return teacherInfo;
    }
}
