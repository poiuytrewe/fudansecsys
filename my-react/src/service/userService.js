import cookie from 'react-cookies'

//设置cookie，第三个参数的意思是所有页面都能用这个cookie

// 加载名为cookieName的cookie信息
// cookie.load(cookieName)
// // 删除名为cookieName的cookie信息
// cookie.remove(cookieName)
import {
  GET_ALL_STUDENT_FROMMENTOR_URL,
  GET_ALL_STUDENT_URL,
  GET_ALL_USER_URL,
  USER_INFO,
  MNG_GET_ALL_USER,
  GET_ALL_RECORDER_URL, MNG_GET_PROCESS_LIST, MNG_PROCESS_UPDATE
} from 'src/settings'
// export const getAllUser = (setUsers) => {
//   return fetch(GET_ALL_USER_URL, {
//       method: 'GET',
//     //   headers: new Headers({
//     //       'token': cookie.load("userInfo").token
//     //   })
//       });
// }
import {UserContext} from "../layouts/Context";
import {useContext, useState} from "react";
import {Box, Button, Dialog, DialogTitle, makeStyles, Typography} from "@material-ui/core";
import {postFetch} from "../base";

const useStyles=makeStyles((theme)=>({
  root:{
    padding:theme.spacing(3),
  }
}))

export const getAllUser = async ({ page = 1, limit = 10 ,name}) => {//根据page进行返回用户，page为1，返回前10个用户，page为2，返回11-20的用户
  try {
    let response = await fetch(
      `${GET_ALL_USER_URL}?limit=${limit}&offset=${(page - 1) * limit}&name=${name}`
    );
    return await response.json();
  } catch (error) {
    console.log("Request Failed", error);
  }
};

export const getAllRecorder = async ({page=1,limit})=>{
  try{
    let response = await  fetch(
        `${GET_ALL_RECORDER_URL}?limit=${limit}&offset=${(page-1)*limit}`
    );
    return await response.json();
  }catch (error){
    console.log(error)
  }
}

export const getAllStudent = async ({ page=1,limit=10, groupId, name=""})=>{
  try {
    let response=await  fetch(
        `${GET_ALL_STUDENT_URL}?limit=${limit}&offset=${(page-1)*limit}&name=${name}&groupId=${groupId}`
    );
    return await response.json();
  }catch (error){
    console.log("Request Failed",error)
  }
}


export const getAllStudentFromMentor = async ({name="", page=1,limit=10})=>{
  try{
    let response=await fetch(
        `${GET_ALL_STUDENT_FROMMENTOR_URL}?limit=${limit}&offset=${(page-1)*limit}&name=${name}`
    );
    return await response.json();
  }catch (error){
    console.log("Request Failed",error)
  }
}

export const getUserInfo = async () => {
  try {
    let response = await fetch(USER_INFO)
    return await response.json()
  } catch (error) {
    console.log('Request Failed', error);
  }
}

export const forceGetAllUser = async()=>{
  try {
    let respone = await fetch(
        `${MNG_GET_ALL_USER}?limit=7999`
    )
    return await respone.json()
  }catch (error) {
    console.log("Request Failed", error);
  }
}

export const getAllLeader=async ()=>{ //获取所有组长的信息
  try{
    let response=await fetch(
        `${MNG_GET_ALL_USER}?isTalkMng=${2}`
    )
    return await  response.json()
  }catch (error){
    console.error(error)
  }
}


export default function ProcessAlert(props){
  const classes=useStyles()
  const {processDetail, open, onClose}=props

  return (
      <Dialog open={open} className={classes.root}>
        <DialogTitle>任务提醒</DialogTitle>
        <form className="dialogForm">
          <Typography align="center">
            {processDetail.missionStatus===0? `你的${processDetail.category}任务已经逾期，请提交延期申请` :
            `你的${processDetail.category}任务这个月需要完成，请注意完成时间`}
          </Typography>

          <Box my={2}>
            <Button
              fullWidth
              color="primary"
              size="medium"
              onClick={()=>{
                postFetch({
                  url:MNG_PROCESS_UPDATE,
                  values:{
                    id:processDetail.id,
                    alert:0
                  },
                  successCallback:()=>{
                    onClose()
                  }
                })
              }}
              variant="contained">
              我知道了
            </Button>
          </Box>
        </form>
      </Dialog>
  )
}
