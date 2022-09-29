import React, { useEffect, useState} from "react";
import { Formik } from "formik";
import * as Yup from "yup";
import { postFetch } from "src/base";
import { Box, Button, TextField, Typography, Dialog } from "@material-ui/core";
import {ADD_USER_URL, MNG_GET_ALL_USER} from "src/settings";
import alertBox from "../../components/AlertBox";
//添加用户


const userTypes=[
    {
        value:null,
        type:"无"
    },
    {
        value:0,
        type:"硕士研究生"
    },
    {
        value:10,
        type:"博士研究生"
    },
    {
        value:20,
        type:"教师"
    }
]

function UserManageForm(props) {
  const { onClose, open } = props;
  const [allTeacher,setAllTeacher]=useState([]);

  const handleClose = () => {
    onClose();
  };

    useEffect(()=>{
        fetch(`${MNG_GET_ALL_USER}?type=20`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setAllTeacher(res?.data || [])
            })
        },[])

  return (
    <Dialog onClose={handleClose} open={open}>
      <Formik
        initialValues={{
          name:"",
          stuId: ""
        }}
        onSubmit={(values) => {
            if(!values?.name || !values?.stuId || !values?.type){
                alertBox({text:"请填写必要的字段",severity:"error"})
                return
            }
            if(parseInt(values?.type)!==20){//说明不是教师
                if(!Number.isInteger(parseInt(values?.enrollYear))){
                    alertBox({text:"请输入正确的入学年份",severity:"error"})
                    return
                }
            }
          postFetch({
            url: ADD_USER_URL,
            values:{
                stuId:values?.stuId,
                name:values?.name,
                type:parseInt(values?.type),
                enrollYear:parseInt(values?.enrollYear),
                keshuo:values?.keshuo,
                mentor:values?.mentor
            },
            successCallback: () => {
              console.log("添加用户成功");
              handleClose();
            },
          });
        }}
      >
        {({
          errors,
          handleBlur,
          handleChange,
          handleSubmit,
          touched,
          values,
        }) => (
          <form onSubmit={handleSubmit} className="dialogForm">
            <Box mb={1}>
              <Typography color="textPrimary" variant="h4">
                添加新用户
              </Typography>
            </Box>
            <TextField
              error={Boolean(touched.name && errors.name)}
              fullWidth
              label="用户姓名"
              margin="normal"
              name="name"
              placeholder="必填"
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
            />
            <TextField
              error={Boolean(touched.stuId && errors.stuId)}
              fullWidth
              placeholder="必填"
              label="学/工号"
              margin="normal"
              name="stuId"
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
            />
              <TextField
                  error={Boolean(touched.userType && errors.userType)}
                  fullWidth
                  select
                  margin="normal"
                  label="身份类型"
                  name="type"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  variant="outlined"
                  value={values.type}
                  SelectProps={{
                      native: true,
                  }}>
                  {userTypes.map((userType)=>(
                      <option key={userType.value} value={userType.value}>
                          {userType.type}
                      </option>
                  ))
                  }
              </TextField>
              {values.type==0 && (
                  <TextField
                      select
                      margin="normal"
                      fullWidth
                      label="硕士类型"
                      name="keshuo"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      variant="outlined"
                      values={values.keshuo}
                      SelectProps={{
                          native:true
                      }}>
                      <option value={0}>无</option>
                      <option value={1}>学硕</option>
                      <option value={2}>专硕</option>
                      <option value={3}>学工</option>
                  </TextField>
              )}
              {(values.type==0 || values.type==10) &&
                  (<TextField
                      error={Boolean(touched.enrollYear && errors.enrollYear)}
                      fullWidth
                      label="入学年份"
                      margin="normal"
                      placeholder="必填"
                      name="enrollYear"
                      value={values.enrollYear}
                      onBlur={handleBlur}
                      onChange={handleChange}
                      variant="outlined"
                  />)}
              {(values.type==0 || values.type==10) &&
                  (<TextField
                          error={Boolean(touched.mentor && errors.mentor)}
                          fullWidth
                          label="导师"
                          margin="normal"
                          name="mentor"
                          onBlur={handleBlur}
                          onChange={handleChange}
                          variant="outlined"
                          select
                          value={values.mentor}
                          SelectProps={{
                              native:true
                          }}>
                          <option>无</option>
                          {
                              allTeacher.map((user)=>{
                                  return (
                                      <option value={user.name}>
                                          {user.name}
                                      </option>
                                  )
                              })
                          }
                      </TextField>


                  )}
            <Box my={2}>
              <Button
                  fullWidth
                  color="primary"
                  size="large"
                  type="submit"
                  variant="contained"
              >
                提交
              </Button>

                <Button
                    fullWidth
                    color="secondary"
                    size="large"
                    variant="contained"
                    onClick={()=>{
                        onClose()
                    }}
                >取消</Button>
            </Box>
          </form>
        )}
      </Formik>
    </Dialog>
  );
}
export default UserManageForm;
