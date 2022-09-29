import React, { useContext } from "react";
import { useHistory } from "react-router-dom";
import clsx from "clsx";
import { useFormik } from "formik";
import { postFetch } from "src/base";
import { UserContext } from "src/layouts/Context";
import alertBox from "src/components/AlertBox";
import { Box, Button, TextField, makeStyles } from "@material-ui/core";
import { MNG_UPDATE_USER_URL, U_UPDATE_USER_URL } from "src/settings";
const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
    flexWrap: "wrap",
  },
  margin: {
    margin: theme.spacing(1.5),
  },
  textField: {
    width: "47.9%",
  },
}));
const types = [
  {
    value: 10,
    label: "学术型",
  },
  {
    value: 20,
    label: "结合型",
  },
  {
    value: 30,
    label: "技术型",
  },
];


export default function UpdateUserInfoForm(props) {
    const {userDetail, handleRefreshS}=props
  const classes = useStyles();
  const history = useHistory();
  const {userInfo} = useContext(UserContext);

  const handleOnRefresh=()=>{
      handleRefreshS();
  }

    const userRoles = [
        {
          right:true,
          label:"无",
          value: 0
        },
        {
          right: false,
          label: "无",
          value: 0
        },
        {
            right: userDetail.isTalkMng==1,
            label: "讨论班管理员",
            value:1
        },
        {
            right: userDetail.isLabMng==1,
            label: "Lab管理员",
            value: 2
        },
        {
            right: userDetail.isPaperMng==1,
            label: "推荐论文管理员",
            value: 3
        },
        {
            right: userDetail.isEducateMng>0,
            label: "培养方案管理员",
            value: 4
        },
        {
            right: userDetail.isDeviceMng==1,
            label: "设备管理员",
            value: 5
        },
    ];

  const formik = useFormik({
    initialValues: userDetail,
    onSubmit: (values) => {
      postFetch({
        url: userInfo.isSystemMng ==1 ? MNG_UPDATE_USER_URL : U_UPDATE_USER_URL,
        values,
        successCallback: () => {
          alertBox({ text: "修改成功", severity: "success" });
          history.replace("/app/userManagement")
        },
      });
    },
  });

  const {
    errors,
    handleBlur,
    handleChange,
    handleSubmit,
    isSubmitting,
    touched,
    values,
  } = formik;
  return (
    <form onSubmit={handleSubmit} className={classes.root}>
      <TextField
        label="用户姓名"
        fullWidth
        name="name"
        value={values.name}
        variant="outlined"
        InputProps={{
          readOnly: true,
        }}
        className={classes.margin}
      />
      <TextField
        label="学/工号"
        fullWidth
        name="stuId"
        value={values.stuId}
        variant="outlined"
        InputProps={{
          readOnly: true,
        }}
        className={classes.margin}
      />
        {userInfo.isSystemMng==1 &&(
          <TextField
            error={Boolean(touched.name && errors.name)}
            select
            label="增加权限"
            name="addRole"
            className={clsx(classes.margin, classes.textField)}
            onBlur={handleBlur}
            onChange={handleChange}
            variant="outlined"
            value={values.addRole}
            SelectProps={{
                native: true,
            }}>
              {userRoles.map((role)=> {
                  if(!role.right){
                      return(
                          <option key={role.value} value={role.value}>
                              {role.label}
                          </option>
                      )
                  }
              })}
          </TextField>)}

        {userInfo.isSystemMng==1 &&(
            <TextField
                error={Boolean(touched.name && errors.name)}
                select
                label="删除权限"
                name="deleteRole"
                className={clsx(classes.margin, classes.textField)}
                onBlur={handleBlur}
                onChange={handleChange}
                variant="outlined"
                value={values.deleteRole}
                SelectProps={{
                    native: true,
                }}>
                {userRoles.map((role)=>{
                    if(role.right){
                        return(
                            <option key={role.value} value={role.value}>
                                {role.label}
                            </option>
                        )
                    }
                }
                )}
            </TextField>)

        }

      <TextField
        error={Boolean(touched.name && errors.name)}
        label="手机"
        name="telephone"
        className={clsx(classes.margin, classes.textField)}
        onBlur={handleBlur}
        onChange={handleChange}
        variant="outlined"
        value={values.telephone}
        max={11}
        type="tel"
      />
      <TextField
        error={Boolean(touched.name && errors.name)}
        label="邮箱"
        name="email"
        className={clsx(classes.margin, classes.textField)}
        onBlur={handleBlur}
        onChange={handleChange}
        variant="outlined"
        value={values.email}
        type="email"
      />

      <Box my={2} className={classes.margin}>
        <Button
          color="primary"
          // disabled={isSubmitting}
          fullWidth
          size="large"
          type="submit"
          variant="contained"
        >
          提交
        </Button>
      </Box>
    </form>
  );
}
