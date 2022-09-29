import React, { useState, useEffect, useContext } from "react";
import {
  Box,
  Button,
  Card,
  Divider,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography, TextField,
} from "@material-ui/core";
import Pagination from "@material-ui/lab/Pagination";
import corfirmModal from "src/components/ConfirmModal";
import {RoleName, DELETE_USER_URL, MNG_RESET_PASSWORD_URL, MNG_GET_PROCESS_LIST} from "src/settings";
import ProcessAlert, {getAllUser, getProcessAlert} from "src/service/userService";
import { UserContext } from "src/layouts/Context";
import {deleteFetch, formFetch} from "src/base";
import UserManageForm from "./UserManageForm";
import ContentStyle from "../../components/Style/ContentStyle";

const UserManage = () => {
  const [users, setUsers] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const classes = ContentStyle()
  const [open, setOpen] = React.useState(false);
  const { userInfo } = useContext(UserContext);
  const [page, setPage] = useState(1);
  const [pageNo, setPageNo] = useState(0);
  const [editOpen, setEditOpen]=useState(false);
  const [stuId, setStuId]=useState("");
  const [name, setName]=useState("");
  const [alertOpen,setAlertOpen]=useState(false)
  const [processDetail,setProcessDetail]=useState({})

  const handleOpen = () => {//打开一个窗口
    setOpen(true);
  };


  const handleClose = () => {
    setOpen(false);
    setRefresh(prev => !prev);
  };

  const handleResetPassWd=(id,name)=>{//重置密码
    const cor=corfirmModal({
      title: `确定要重置[${name}]的密码吗？`,
      handleCorfirm:()=>{
        cor.close();
        formFetch({
          url: `${MNG_RESET_PASSWORD_URL}?id=${id}`,
          successCallback:()=>{
            setRefresh((prev) => !prev);
          }
        })
      }
    })
  }

  const handleDeleteUser = (id, name) => {//删除用户
    const cor = corfirmModal({
      title: `确定要删除[${name}]吗？`,
      handleCorfirm: () => {
        cor.close();
        deleteFetch({
          url: `${DELETE_USER_URL}?id=${id}`,
          values: { id },
          successCallback: () => {
            setRefresh((prev) => !prev);
          },
        });
      },
    });
  };

  // useEffect(getAllUser,[]);
  useEffect(() => {
    fetch(`${MNG_GET_PROCESS_LIST}?stuId=${userInfo.stuId}`)//获得提示
        .then((res)=>res.json())
        .catch((error)=>console.log(error))
        .then((res)=>{
          res?.data.map((re)=> {
            if(re.alert===1){//这个月有需要完成的任务
              setProcessDetail(re)
              setAlertOpen(true)
            }
            if(re.missionStatus===0){//已经逾期
              setProcessDetail(re)
              setAlertOpen(true)
            }
          })
        })
    getAllUser({ page, name }).then((res) => {
      setUsers(res.data || []);
      setPageNo(Math.ceil(res?.paging?.total / 10) || 0);
    });
  }, [refresh, page, name]);
  return (
    <div>
      <Card>
        <Box className={classes.header}>
          <Typography color="textPrimary" size="small">
            用户管理
          </Typography>

          {(userInfo.isSystemMng==1) && //检验是否拥有系统管理员权限

              (<TextField
                  label="搜索用户"
                  size="small"
                  onChange={(e)=>{
                    setPage(1)
                    setName(e.target.value)}}
              />)}

          {(userInfo.isSystemMng==1) && (
              <Button
                  color="primary"
                  size="small"
                  variant="outlined"
                  onClick={handleOpen}
              >
                添加新用户
              </Button>
          )}

        </Box>

        <Divider />
        {/* <PerfectScrollbar> */}
        <Box minWidth={800}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>姓名</TableCell>
                <TableCell>学/工号</TableCell>
                <TableCell>角色</TableCell>
                {(userInfo.isSystemMng==1)&& (//依然是检验权限
                  <TableCell align="center">操作</TableCell>
                )}
              </TableRow>
            </TableHead>

            <TableBody>
              {users.map((user) => (
                <TableRow hover key={user.id}>
                  <TableCell>{user.name}</TableCell>
                  <TableCell>{user.stuId}</TableCell>
                  <TableCell>{RoleName[user.roleId]}</TableCell>
                  {(userInfo.isSystemMng==1) && (
                    <TableCell align="center">
                      <Button
                        color="primary"
                        size="small"
                        variant="text"
                        href={`/app/updateUserInfo/${user.stuId}`}
                      >
                        编辑
                      </Button>
                      <Button
                        color="primary"
                        size="small"
                        variant="text"
                        onClick={(e) => handleDeleteUser(user.id, user.name)}
                      >
                        删除
                      </Button>
                      <Button
                        color="primary"
                        size="size"
                        variant="text"
                        onClick={(e)=>handleResetPassWd(user.id,user.name)}
                        >
                        重置密码
                      </Button>
                    </TableCell>
                  )}
                </TableRow>
              ))}
            </TableBody>
          </Table>
          {pageNo > 1 && (
            <Pagination
              className={classes.Pagination}
              count={pageNo}
              color="primary"
              onChange={(e, i) => setPage(i)}
            />
          )}
        </Box>
        {/* </PerfectScrollbar> */}
      </Card>
      <UserManageForm open={open} onClose={handleClose} />

      <ProcessAlert processDetail={processDetail} open={alertOpen} onClose={()=>{
        setAlertOpen(false)
        setRefresh((p)=>!p)
      }}/>
    </div>
  );
};

export default UserManage;
