
export const RoleName= {
    0:"普通用户",
    1:"管理员"
}

{/**这应该是向后台调取服务的地址**/}

//本地开发环境
// export const BASE_URL = 'http://localhost:8080';
//部署开发环境
export const BASE_URL ='http://localhost:8088/v1'
// export const BASE_URL ='http://localhost:18888'
//export const BASE_URL = 'http://10.176.36.7:8088/v1'

// 解决跨域问题临时使用
//export const BASE_URL = "http://:80/v1";
// export const BASE_URL = "http://localhost/v1";
// export const BASE_URL = "http://fd.foxbank.cn";
// export const BASE_URL = "http://fd.foxzz.cn";

/**
 * MNG_: 管理员
 * U_: 普通用户
 */

//登录、用户管理
export const USER_INFO = BASE_URL + "/u/user/info";
export const LOGIN_URL = BASE_URL + "/u/user/login";
export const RESET_PASSWORD_URL = BASE_URL + "/u/user/reset";
export const MNG_RESET_PASSWORD_URL =BASE_URL + "/mng/user/reset";
export const ADD_USER_URL = BASE_URL + "/mng/user/add";
export const MNG_UPDATE_USER_URL = BASE_URL + "/mng/user/update";
export const MNG_UPDATE_USERGROUP_URL =BASE_URL+"/mng/user/updateGroup";
export const U_UPDATE_USER_URL = BASE_URL + "/u/user/update";
export const DELETE_USER_URL = BASE_URL + "/mng/user/delete";
export const GET_ALL_USER_URL = BASE_URL + "/mng/user/paging";
export const MNG_GET_USER_DETAIL = BASE_URL + "/mng/user/detail";
export const U_GET_USER_DETAIL = BASE_URL + "/u/user/detail";
export const MNG_GET_ALL_USER=BASE_URL+"/mng/user/list";
export const MNG_GET_ONE_STUDENT_DETAIL_URL=BASE_URL+"/mng/user/oneStudent";
export const MNG_GET_STUDENT_List = BASE_URL + "/mng/user/studentList";

//会议安排
export const ADD_SEMINAR_URL = BASE_URL + "/seminar/add";
export const UPDATE_SEMINAR_URL = BASE_URL + "/seminar/update";
export const DELETE_SEMINAR_URL = BASE_URL + "/seminar/delete";
export const GET_ALL_SEMINAR_URL = BASE_URL + "/seminar/paging";
export const ADD_SEMINAR_LINK_URL = BASE_URL + "/seminar/addlink";
export const UPLOAD_SEMINAR_URL = BASE_URL + "/seminar/upload";
export const DOWNLOAD_SEMINAR_URL = BASE_URL + "/seminar/download"

//专题安排
export const GET_ALL_SECTION_URL = BASE_URL + "/section/paging";
export const ADD_SECTION_URL = BASE_URL + "/section/add";
export const DELETE_SECTION_URL = BASE_URL + "/section/delete";
export const UPDATE_SECTION_URL = BASE_URL + "/section/update";
export const UPLOAD_SECTION_URL = BASE_URL + "/section/upload";
export const DOWNLOAD_SECTION_URL = BASE_URL + "/section/download";

//辅读安排
export const ADD_RECORDER_URL = BASE_URL + "/recorder/add";
export const GET_ALL_RECORDER_URL = BASE_URL + "/recorder/paging";
export const DELETE_RECORDER_URL = BASE_URL + "/recorder/delete";
export const UPDATE_BATTLE__URL = BASE_URL + "/recorder/updateBattle";

export const UPLOAD_RECORDER_URL = BASE_URL + "/recorder/recorderUpload";
export const MNG_RESET_GROUPID = BASE_URL + "/mng/user/resetGroup"
export const MNG_UPDATE_RECORDER = BASE_URL + "/recorder/update"

export const UPLOAD_RECORDER1_URL = BASE_URL + "/recorder/uploadRecorder1";
export const UPLOAD_RECORDER2_URL = BASE_URL + "/recorder/uploadRecorder2";
export const UPLOAD_SUMMARY_URL = BASE_URL + "/recorder/uploadSummary";
export const DOWNLOAD_RECORDER1_URL = BASE_URL + "/recorder/downloadRecorder1";
export const DOWNLOAD_RECORDER2_URL = BASE_URL + "/recorder/downloadRecorder2";
export const DOWNLOAD_SUMMARY_URL = BASE_URL + "/recorder/downloadSummary";
export const DOWNLOAD_RECORDER_URL = BASE_URL + "/recorder/downloadRecorder";
export const DOWNLOAD_SUMMARIZER_URL = BASE_URL + "/recorder/downloadSummarizer";

//推荐论文
export const GET_ALL_CLASS_URL = BASE_URL + "/article/category/list";
export const EDIT_CLASS_URL =BASE_URL+ "/article/category/edit";
export const ADD_CLASS_URL = BASE_URL + "/article/category/add";
export const DELETE_CLASS_URL = BASE_URL + "/article/category/delete";

export const GET_ALL_ARTICLE_URL = BASE_URL + "/article/paging";
export const GET_ARTICLE_URL = BASE_URL + "/article/";
export const ADD_ARTICLE_URL = BASE_URL + "/article/add";
export const EDIT_ARTICLE_URL = BASE_URL + "/article/edit";
export const DELETE_ARTICLE_URL = BASE_URL + "/article/delete";

//周报
export const STU_GET_PUBLICATION = BASE_URL+"/mng/weeklyPublication/paging";
export const UPLOAD_WEEKLYPUBLICATION = BASE_URL+"/mng/weeklyPublication/upload";
export const DELETE_WEEKLYPUBLICATION = BASE_URL+"/mng/weeklyPublication/delete";
export const DOWNLOAD_WEEKLYPUBLICATION = BASE_URL+"/mng/weeklyPublication/download";
export const MENTOR_GET_PUBLICATION=BASE_URL+"/mng/weeklyPublication/mentor/paging";
export const MENTOR_STATISTICS_PUBLICATION = BASE_URL+"/mng/weeklyPublication/mentor/statistics"
export const UPDATE_WEEKLYPUBLICATION=BASE_URL +"/mng/weeklyPublication/update";
export const GROUPLEADER_GET_PUBLICATION = BASE_URL +　"/mng/weeklyPublication/groupLeader/paging";
export const GROUPLEADER_STATISTICS_PUBLICATION=BASE_URL+"/mng/weeklyPublication/groupLeader/statistics"
export const BOSS_GET_PUBLICATION=BASE_URL+"/mng/weeklyPublication/boss/paging";
export const MANAGER_GET_PUBLICATION=BASE_URL+"/mng/weeklyPublication/manager/paging";
export const MANAGER_STATISTICS_PUBLICATION=BASE_URL+"/mng/weeklyPublication/manager/statistics";

//Lab
export const GET_LAB_DATA = BASE_URL+"/lab/labData"
export const GET_LAB_NAME = BASE_URL+"/lab/labName"
export const DELETE_LAB_DATA = BASE_URL+"/lab/deleteLabData"
export const UPLOAD_LAB_DATA = BASE_URL+"/lab/uploadLabData"

//培养计划 管理员
export const MNG_GET_ALL_PLAN_URL = BASE_URL + "/mng/study/plan/paging";
export const MNG_ADD_PLAN_URL = BASE_URL + "/mng/study/plan/add";
export const MNG_UPDATE_PLAN_URL = BASE_URL + "/mng/study/plan/update";
export const MNG_DELETE_PLAN_URL = BASE_URL + "/mng/study/plan/delete";
export const MNG_GET_PLAN_DETAIL_URL = BASE_URL + "/mng/study/plan/overview";
export const MNG_ADD_WORK_URL = BASE_URL + "/mng/study/plan/work/add";
export const MNG_UPDATE_WORK_URL = BASE_URL + "/mng/study/plan/work/update";
export const MNG_DELETE_WORK_URL = BASE_URL + "/mng/study/plan/work/delete";
export const MNG_ADD_STAGE_URL = BASE_URL + "/mng/study/plan/stage/add";
export const MNG_UPDATE_STAGE_URL = BASE_URL + "/mng/study/plan/stage/update";
export const MNG_DELETE_STAGE_URL = BASE_URL + "/mng/study/plan/stage/delete";
export const GET_ALL_STUDENT_URL = BASE_URL + "/mng/user/student";
export const GET_ALL_STUDENT_FROMMENTOR_URL = BASE_URL+ "/mng/user/mentorStudent";
export const GET_MASTER_FROMMENTOR_URL = BASE_URL+"/mng/user/master";
export const MNG_GET_ALLOCATION_URL =
  BASE_URL + "/mng/study/plan/allocation/info/list";
export const MNG_ASSGIN_STUDY = BASE_URL + "/mng/study/plan/assign";
export const MNG_DELETE_ALLOCATION = BASE_URL + "/mng/study/plan/allocation/delete";
// export const MNG_GET_ALLOCATION_INfO = BASE_URL + "/mng/study/plan/allocation/info";
export const MNG_GET_ALLOCATION_INfO =
  BASE_URL + "/mng/study/plan/allocation/user/overview";
export const MNG_EDIT_ALLOCATION= BASE_URL + "/mng/study/plan/allocation/edit";
export const MNG_GET_USER_LIST = BASE_URL + "/mng/user/allocation/list";
export const MNG_ADD_SCORE =BASE_URL+ "/mng/study/score/add";
export const MNG_UPDATE_SCORE =BASE_URL+"/mng/study/score/update";
export const MNG_RETURN_SCORE=BASE_URL+"/mng/study/score/return";
export const MNG_RETURN_SCORE_VITAL=BASE_URL +"/mng/study/score/vital"
export const MNG_RETURN_SINGLESCORE=BASE_URL+"/mng/study/score/returnSingle"
export const MNG_DELETE_SCORE=BASE_URL+"/mng/study/score/delete";
export const MNG_GET_SCORE_DATE=BASE_URL+"/mng/study/score/getDate";
export const GET_SCORE_UNDONELIST = BASE_URL+"/mng/study/score/mentorStatics"
export const MNG_GET_UNDONE_SCORE=BASE_URL+"/mng/study/score/unDone";
export const MNG_GET_PROCESS_LIST=BASE_URL+ "/mng/study/process/paging";
export const MNG_ESSENTIAL_PROCESS_INIT=BASE_URL+ "/mng/study/process/essential/init";
//初始化必要任务
export const MNG_BASE_PROCESS_INIT=BASE_URL+"/mng/study/process/base/init";
export const MNG_ADV_PROCESS_INIT=BASE_URL+"/mng/study/process/adv/init";
export const MNG_PROCESS_ADD=BASE_URL+ "/mng/study/process/add";
export const MNG_PROCESS_RESET=BASE_URL +"/mng/study/process/reset";
export const MNG_PROCESS_UPDATE=BASE_URL+"/mng/study/process/update";
export const MNG_PROCESS_GROUPDONE=BASE_URL+"/mng/study/process/groupDone"
export const MNG_PROCESS_GROUPDELAY=BASE_URL+"/mng/study/process/groupDelay"
export const MNG_PROCESS_GROUP_APPROVE=BASE_URL+"/mng/study/process/groupApprove"
export const MNG_PROCESS_GROUP_APPROBE_DELAY=BASE_URL+"/mng/study/process/groupApproveDelay"
export const MNG_PROCESS_DELETE=BASE_URL+ "/mng/study/process/delete";
export const MNG_RECONFIG_PROCESS=BASE_URL+"/mng/study/process/reConfig"
export const MNG_PROCESS_GROUP_UPDATE=BASE_URL+"/mng/study/process/groupUpdate"
export const MNG_PROCESS_GET_STUMISSIONSTATUS=BASE_URL+"/mng/study/process/getMissionStatus"
export const MNG_PROCESS_GET_COUNT=BASE_URL+"/mng/study/process/count"
export const MNG_PROCESS_GET_STUDENT = BASE_URL+"/mng/study/process/student"
export const PROCESS_GET_MASTERFROMMENTOR_TOTAL = BASE_URL+"/mng/study/process/total"
export const MNG_GET_ALL_PROCESS_APPROVE = BASE_URL+"/mng/study/process/getAllApprove"
export const MNG_GET_PROCESS_APPLY_COUNT = BASE_URL+"/mng/study/process/getAllApplyCount"
export const MNG_GET_ALL_PROCESS_DELAY= BASE_URL+"/mng/study/process/getAllDelay"
export const MNG_GET_ALL_PROCESS_CATEGORY=BASE_URL+"/mng/study/process/category/paging"
export const MNG_ADD_PROCESS_CATEGORY=BASE_URL+"/mng/study/process/category/add"
export const MNG_RESET_PROCESS_CATEGORY=BASE_URL+"/mng/study/process/category/reset"
export const MNG_DELETE_PROCESS_CATEGORY=BASE_URL+"/mng/study/process/category/delete"
export const MNG_UPDATE_PROCESS_CATEGORY=BASE_URL+"/mng/study/process/category/update"
export const MNG_ADD_TINY_PROCESS_CATEGORY=BASE_URL+"/mng/study/process/tinyCategory/add"
export const MNG_GET_TINY_PROCESS_CATEGORY=BASE_URL+"/mng/study/process/tinyCategory/paging"
export const PROCESS_GET_GRADUATE_INFORMATION = BASE_URL+"/mng/study/process/graduate"

//培养计划 普通用户
export const U_GET_ALL_PLAN_URL = BASE_URL + "/u/study/plan/allocation/list";
export const U_GET_PLAN_DETAIL_URL = BASE_URL + "/u/study/plan/allocation/overview";
export const U_EDIT_ALLOCATION = BASE_URL + "/u/study/plan/allocation/edit";

//通告管理
export const MNG_ADD_BULLETIN_URL = BASE_URL + "/mng/bulletin/add";
export const MNG_UPDATE_BULLETIN_URL = BASE_URL + "/mng/bulletin/update";
export const MNG_DELETE_BULLETIN_URL = BASE_URL + "/mng/bulletin/delete";
export const MNG_GET_ALL_BULLETIN_URL = BASE_URL + "/mng/bulletin/paging";
export const GET_ALL_BULLETIN_URL = BASE_URL + "/u/bulletin/paging";
export const GET_NEW_BULLETIN_NUMBER_URL =
  BASE_URL + "/u/bulletin/newBulletinNumber";
export const MARK_AS_READ_URL = BASE_URL + "/u/bulletin/read";
export const MARK_READ_ALL = BASE_URL + "/u/bulletin/read/all";
export const GET_BULLETIN_STATE = BASE_URL + "/u/bulletin/state";

//设备管理
export const MNG_ADD_DEVICE_URL = BASE_URL + "/mng/device/add";
export const MNG_UPDATE_DEVICE_URL = BASE_URL + "/mng/device/update";
export const U_GET_ALL_DEVICE_URL = BASE_URL + "/u/device/paging";
export const MNG_GET_ALL_DEVICE_URL = BASE_URL + "/mng/device/paging";
export const MNG_DELETE_DEVICE_URL = BASE_URL + "/mng/device/delete";
export const U_APPLY_DEVICE_URL = BASE_URL + "/u/device/allocation/apply";
export const U_RETURN_DEVICE_URL = BASE_URL + "/u/device/allocation/return";
export const MNG_RETURN_DEVICE_URL = BASE_URL + "/mng/device/allocation/return";
export const U_DEVICE_USAGE_URL = BASE_URL + "/u/device/usage/paging";
export const MNG_DEVICE_USAGE_URL = BASE_URL + "/mng/device/usage/paging";
export const U_DEVICE_ALLOCATION_URL = BASE_URL + "/u/device/allocation/paging";
export const MNG_DEVICE_ALLOCATION_URL = BASE_URL + "/mng/device/allocation/paging";
