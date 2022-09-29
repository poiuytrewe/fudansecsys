import React, { useState, useEffect } from "react";
import {
  Box,
  Divider,
  Drawer,
  Hidden,
  List,
  Typography,
  makeStyles,
  Collapse,
} from "@material-ui/core";
import {
  Settings as SettingsIcon,
  Users as UsersIcon,
  Coffee as CoffeeIcon,
  Server as ServerIcon,
  BookOpen as BookOpenIcon,
  Book as Book
} from "react-feather";
import ExpandLess from "@material-ui/icons/ExpandLess";
import ExpandMore from "@material-ui/icons/ExpandMore";
import NavItem from "./NavItem";
import {getUserInfo} from "../../../service/userService";

const useStyles = makeStyles((theme) => ({
  mobileDrawer: {
    width: 256,
  },
  desktopDrawer: {
    width: 256,
    top: 64,
    height: "calc(100% - 64px)",
  },
  avatar: {
    cursor: "pointer",
    width: 64,
    height: 64,
  },
  nested: {
    paddingLeft: theme.spacing(4),
  },
}));

const groupLeaders=[ "20110240046","20110240048"]

const NavBar = ({ onMobileClose, openMobile }) => {
  const classes = useStyles();
  const [userInfo,setUserInfo]=useState({})
  const [seminarOpen, setSeminarOpen] = useState(false);
  const [talkOpen,setTalkOpen]=useState(false)
  const [userRoles, setUserRoles]=useState([])

  useEffect(() => {
    if (openMobile && onMobileClose) {
      onMobileClose();
    }
    getUserInfo().then((res)=>{
      if(res?.success){
        setUserInfo(res?.data)
        setUserRoles([
          {
            right: res?.data?.roleId==0,
            label: "普通用户",
          },
          {
            right: res?.data?.isSystemMng==1,
            label: "系统管理员",
          },
          {
            right: res?.data?.isTalkMng==1,
            label: "讨论班管理员",
          },
          {
            right: res?.data?.isLabMng==1,
            label: "Lab管理员",
          },
          {
            right: res?.data?.isPaperMng==1,
            label: "推荐论文管理员",
          },
          {
            right: res?.data?.isEducateMng==1,
            label: "培养方案管理员",
          },
          {
            right: res?.data?.isDeviceMng==1,
            label: "设备管理员",
          },
        ]);
      }
    })
  }, []);

  const content = (
    <Box height="100%" display="flex" flexDirection="column">
      <Box alignItems="center" display="flex" flexDirection="column" p={2}>
        <Typography className={classes.name} color="textPrimary" variant="h5">
          {userInfo.name}
        </Typography>

        <Typography color="textSecondary" variant="body2">
          {userInfo.stuId}
        </Typography>
        {
          userRoles.map((userRole)=>{
            if(userRole.right){
              return(
                  <Typography color="textSecondary" variant="body2">
                    {userRole.label}
                  </Typography>
              )
            }
          })
        }
      </Box>
      <Divider />
      <Box p={2}>
        <List>
          <NavItem
            href="/app/userManagement"
            title="权限管理"
            icon={UsersIcon}
          />

          {userInfo.type===20? (
              userInfo.stuId == "07175" ? (
                  <NavItem
                    href="/app/weeklyPublication/boss"
                    title="周报管理"
                    icon={Book}/>
                  ):(
                  <NavItem
                      href="/app/weeklyPublication/mentor"
                      title="周报管理"
                      icon={Book}/>
                  )
          ):(
              groupLeaders.indexOf(userInfo.stuId) == -1 ? (//等于-1，说明不是带组博士
                  <NavItem
                      href="/app/weeklyPublication/student"
                      title="周报管理"
                      icon={Book}
                  />): (//否则，就是带组博士
                      <NavItem
                        href="/app/weeklyPublication/groupLeader"
                        title="周报管理"
                        icon={Book}/>)
          )}


          {userInfo.stuId == "20110240048" ? (
              <NavItem
                href="/app/lab/eliteStudent"
                title="Lab管理"
                icon={ServerIcon}
                isToOutLink={true}/>
              ):(
              <NavItem
                  href="/app/lab/normalStudent"
                  title="Lab管理"
                  icon={ServerIcon}
                  isToOutLink={true}
              />
              )}

          <NavItem
              onClick={()=>setSeminarOpen(!seminarOpen)}
              title="讨论班管理"
              icon={CoffeeIcon}
              endIcon={seminarOpen? <ExpandLess/> : <ExpandMore/>}
              nestedItem
          />
          <Collapse in={seminarOpen} timeout="auto" unmountOnExit>
            <List component="div" disablePadding>
              <NavItem className={classes.nested} href="/app/recorderGroup" title="分组安排" icon={CoffeeIcon}/>
              <NavItem className={classes.nested} href="/app/recorder" title="导读安排" icon={CoffeeIcon} />
              <NavItem className={classes.nested} href="/app/seminar" title="演讲安排" icon={CoffeeIcon} />
              <NavItem className={classes.nested} href="/app/essayRecommendation" title="推荐论文管理" icon={CoffeeIcon}/>
              <NavItem className={classes.nested} href="/app/battle" title="论文擂台赛" icon={CoffeeIcon}/>
            </List>
          </Collapse>

          <NavItem
              onClick={()=>setTalkOpen(!talkOpen)}
              title="培养方案管理"
              icon={BookOpenIcon}
              endIcon={talkOpen? <ExpandLess/> : <ExpandMore/>}
              nestedItem
          />
          <Collapse in={talkOpen} timeout="auto" unmountOnExit>
            <List component="div" disablePadding>
              {
                (userInfo.type<20 ) && (//只有学生才能看到这个
                    <NavItem className={classes.nested} href={`/app/updateStudentInfo/${userInfo.stuId}`} title="个人信息" icon={BookOpenIcon}/>
                  )
              }

              {
                (userInfo.type==20 ?(
                    <NavItem className={classes.nested} href="/app/studyPlan/score" title="学生季度评分" icon={BookOpenIcon}/>
                ):(
                    <NavItem className={classes.nested} href={`/app/studyPlan/stuScore/${userInfo.stuId}`} title="季度评分" icon={BookOpenIcon}/>
                ))
              }
              {
                (userInfo.type===20 ?( //应该判断三种身份，老师，培养过程管理员和学生
                    userInfo.isEducateMng===1?(
                        <NavItem className={classes.nested} href="/app/studyPlan/baseInformation/mng" title="学生培养过程" icon={BookOpenIcon}/>
                        ):(
                        <NavItem className={classes.nested} href="/app/studyPlan/process" title="学生培养过程" icon={BookOpenIcon}/>
                        )
                ):(//学生
                    <NavItem className={classes.nested} href={`/app/studyPlan/stuProcess/${userInfo.stuId}`} title="培养过程" icon={BookOpenIcon}/>
                ))
              }

            </List>
          </Collapse>

          <NavItem href="/app/device" title="资源管理" icon={SettingsIcon} />
        </List>
      </Box>
      <Box flexGrow={1} />
    </Box>
  );

  return (
    <>
      <Hidden lgUp>
        <Drawer
          anchor="left"
          classes={{ paper: classes.mobileDrawer }}
          onClose={onMobileClose}
          open={openMobile}
          variant="temporary"
        >
          {content}
        </Drawer>
      </Hidden>
      <Hidden mdDown>
        <Drawer
          anchor="left"
          classes={{ paper: classes.desktopDrawer }}
          open
          variant="persistent"
        >
          {content}
        </Drawer>
      </Hidden>
    </>
  );
};

NavBar.defaultProps = {
  onMobileClose: () => {},
  openMobile: false,
};

export default NavBar;
