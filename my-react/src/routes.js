import MainLayout from './layouts/MainLayout';
import DashboardLayout from './layouts/DashoardLayout'
import LoginView from './views/LoginView';
import ResetPasswordView from "./views/ResetPasswordView";
import UpdateUserInfoView from "./views/updateUserInfo/UpdateUserInfoView";
import NotFoundView from './views/NotFoundView';
import SeminarView from './views/seminarMenu/seminar/SeminarView';
import RecorderView from './views/seminarMenu/recorder/RecorderView';
import UserManagementView from './views/userManagemet/UserManagementView';
import EssayRecommendationView from "./views/seminarMenu/essayRecommendation/EssayRecommendationView";
import TrainingSchemeView from './views/trainingScheme/TrainingSchemeView';
import PlanAllocationView from "./views/studyPlan/planAllocation";
import StudyPlanDetailView from "./views/studyPlan/detail";
import BulletinManageView from "./views/bulletin/BulletinManageView";
import BulletinManagementView from "./views/bulletin/BulletinManagementView";
import BulletinListView from './views/bulletin/BulletinListView';
import DeviceView from './views/device/DeviceView';
import SelfEditView from "./views/cultivationMenu/baseInformation/selfEdit/selfEditView";
import RecorderGroupView from "./views/seminarMenu/recorderGroup/RecorderGroupView";
import BattleView from "./views/seminarMenu/battle/BattleView";
import ScoreView from "./views/cultivationMenu/score/scoreForTeacher/ScoreView";
import BaseInformationViewForMng from "./views/cultivationMenu/process/processForMng/BaseInformationViewForMng";
import StudentScoreView from "./views/cultivationMenu/score/scoreForStudent/StudentScoreView";
import ProcessForStuView from "./views/cultivationMenu/process/processForStudent/ProcessForStuView";
import ProcessForTeaView from "./views/cultivationMenu/process/processForTeacher/ProcessForTeaView";
import ProcessForMngView from "./views/cultivationMenu/process/processForMng/ProcessForMngView";
import ProcessConfigView from "./views/cultivationMenu/process/processForMng/ProcessConfigView";
import ProcessDoubleForStuView from "./views/cultivationMenu/process/processForTeacher/ProcessDoubleForStuView";
import ProcessConfigMngView from "./views/cultivationMenu/process/processForMng/ProcessConfigMngView";
import StudentView from "./views/weeklyPublication/student/StudentView";
import TeacherView from "./views/weeklyPublication/teacher/TeacherView";
import GroupLeaderView from "./views/weeklyPublication/groupLeader/GroupLeaderView";
import BossView from "./views/weeklyPublication/boss/BossView";
import NormalStudentView from "./views/lab/normalStudent/NormalStudentView";
import EliteStudentView from "./views/lab/eliteStudent/EliteStudentView";


const routes = [
  {//路径以main开头的
    path: "/main",
    component: MainLayout,
    routes: [
      { path: "/main/login", component: LoginView },
      { path: "/main/reset", component: ResetPasswordView },
    ],
  },
  {//路径以app开头的
    path: "/app",
    component: DashboardLayout,
    auth: true,//auth是啥意思？
    routes: [
      { path: "/app/updateUserInfo/:id", component: UpdateUserInfoView }, //更新用户信息


      { path: "/app/weeklyPublication/student", component: StudentView},
      { path: "/app/weeklyPublication/mentor", component: TeacherView},
      { path: "/app/weeklyPublication/groupLeader", component: GroupLeaderView},
      { path: "/app/weeklyPublication/boss", component: BossView},
      { path: "/app/weeklyPublication/:stuId", component: StudentView},

      { path: "/app/lab/normalStudent", component: NormalStudentView},
      { path: "/app/lab/eliteStudent", component: EliteStudentView},

        /**下面的是讨论班模块的相关路径**/
      { path: "/app/seminar", component: SeminarView }, //演讲安排
      { path: "/app/recorder", component: RecorderView }, //辅读安排
      { path: "/app/recorderGroup", component: RecorderGroupView}, //分组安排
      { path: "/app/battle", component: BattleView},//battle模块
      { path: "/app/essayRecommendation", component: EssayRecommendationView }, //推荐论文

        /**下面的是培养方案模块的相关路径**/
      { path: "/app/updateStudentInfo/:id", component: SelfEditView},//培养计划编辑信息
      { path: "/app/studyPlan/baseInformation/mng", component:BaseInformationViewForMng}, //基本信息培养方案管理员界面
      { path: "/app/studyPlan/score", component:ScoreView},//季度评分教师界面
      { path: "/app/studyPlan/stuScore/:stuId", component:StudentScoreView}, //季度评分学生界面
      { path: "/app/studyPlan/process", component:ProcessForTeaView},
      { path: "/app/studyPlan/mngProcess/category", component:ProcessForMngView},
      { path: "/app/studyPlan/stuProcess/:stuId", component:ProcessForStuView},
      { path: "/app/studyPlan/mngProcess/Config/:stuId", component:ProcessConfigView},
      { path: "/app/studyPlan/mngStuConfig/:stuId", component:ProcessConfigMngView},
      { path: "/app/studyPlan/teacherProcess/:stuId", component:ProcessDoubleForStuView},

      { path: "/app/userManagement", component: UserManagementView }, //用户管理
      {path: "/app/studyPlan/planAllocation/:planId", component: PlanAllocationView,}, //任务分配
      { path: "/app/studyPlan/detail/:id", component: StudyPlanDetailView }, //培养计划详情

      { path: "/app/trainingScheme", component: TrainingSchemeView }, //培养方案
      { path: "/app/bulletinManage", component: BulletinManageView }, // 通告管理
      { path: "/app/bulletinManagement", component: BulletinManagementView }, // 通告管理
      { path: "/app/bulletinList", component: BulletinListView }, //通知列表
      { path: "/app/device", component: DeviceView }, //IT资源管理
      { path: "*", component: NotFoundView },
    ],
  },
  {//这个应该是进行错误处理的
    path: "/",
    component: MainLayout,
    routes: [
      { path: "/404", component: NotFoundView },
      { path: "/*", component: LoginView },
    ],
  },
];

export default routes;
