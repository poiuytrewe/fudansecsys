import React, {useEffect,useState} from "react";
import { Formik } from "formik";
import * as Yup from "yup";
import {
  Box,
  Button,
  TextField,
  Dialog,
  makeStyles,
  DialogTitle,
} from "@material-ui/core";
import alertBox from "src/components/AlertBox";
import { MNG_ADD_DEVICE_URL, MNG_UPDATE_DEVICE_URL, MNG_GET_ALL_USER,GET_ALL_USER_URL} from "src/settings";
import { postFetch } from "src/base";
import {forceGetAllUser} from "../../../service/userService";

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(3)
  },
}));

const Type=[
    {
        id: 0,
        value: "无"
    },
    {
        id: 10,
        value: "显示器"
    },
    {
        id: 20,
        value: "移动设备"
    },
    {
        id: 30,
        value: "台式主机"
    },
    {
        id:50,
        value: "服务器",
    },
    {
        id: 40,
        value: "其他"
    }
]

export default function EditDevice(props) {
  const { onClose, open, deviceDetail } = props;
  const [users,getUsers]=useState([])
  const [refresh,setRefresh]=useState(false);
  const classes = useStyles();

  useEffect(()=>{
      forceGetAllUser().then((res)=>{
          getUsers(res.data || [])
      })
  },[refresh]);


  const handleClose = () => {
    onClose();
    setRefresh((prev) => !prev)
  };

  return (
    <Dialog onClose={handleClose} open={open} className={classes.root}>
      <DialogTitle onClose={handleClose}>
        {deviceDetail?.id ? "编辑设备" : "新增设备"}{/**deviceID是否存在，如果存在，说明是对设备的编辑，如果不存在，说明是新增的设备**/}
      </DialogTitle>
      <Formik
        initialValues={deviceDetail}
        // validationSchema={Yup.object().shape({
        //   type: Yup.number().required("设备类型必填"),
        // })}
        onSubmit={(values) => {
          postFetch({
            url:
              deviceDetail?.id? MNG_UPDATE_DEVICE_URL : MNG_ADD_DEVICE_URL,
            values: {
                type: values?.type || 0,
                model: values?.model || "",
                name: values?.name || "",
                principal: values?.principal || "",
                id:values?.id ||0
              //...values,
            },
            successCallback: () => {
              alertBox({ text: "操作成功", severity: "success" });
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
          isSubmitting,
          touched,
          values,
        }) => (
          <form onSubmit={handleSubmit} className="dialogForm">
            <TextField
              select
              fullWidth
              label="设备类型"
              name="type"
              margin="normal"
              onChange={handleChange}
              variant="outlined"
              SelectProps={{
                native: true,
              }}
              value={values.type}
            >{
                Type.map((type)=>(
                    <option value={type.id}>
                        {type.value}
                    </option>
                ))
            }
            </TextField>
            <TextField
              label="设备型号"
              fullWidth
              margin="normal"
              name="model"
              value={values.model}
              variant="outlined"
              onBlur={handleBlur}
              onChange={handleChange}
            />
            <TextField
              label="设备品牌"
              fullWidth
              margin="normal"
              name="name"
              value={values.name}
              variant="outlined"
              onBlur={handleBlur}
              onChange={handleChange}
            />
              <TextField
              select
              fullWidth
              label="负责人姓名"
              name="principal"
              margin="normal"
              onChange={handleChange}
              value={values.principal}
              variant="outlined"
              SelectProps={{
              native: true,
          }}>
                  <option >
                      无
                  </option>
              {
                  users.map((user)=>{
                      if(user.isDeviceMng==1){
                          return(
                              <option value={user.name}>
                                  {user.name}
                              </option>
                          )
                      }
                  })
              }
            </TextField>
            {/*<TextField*/}
            {/*  label="库存"*/}
            {/*  margin="normal"*/}
            {/*  fullWidth*/}
            {/*  name="inventory"*/}
            {/*  value={values.inventory}*/}
            {/*  variant="outlined"*/}
            {/*  onBlur={handleBlur}*/}
            {/*  onChange={handleChange}*/}
            {/*/>*/}
            {/*<TextField*/}
            {/*  label="库存单位"*/}
            {/*  margin="normal"*/}
            {/*  fullWidth*/}
            {/*  name="inventoryUnit"*/}
            {/*  value={values.inventoryUnit}*/}
            {/*  variant="outlined"*/}
            {/*  onBlur={handleBlur}*/}
            {/*  onChange={handleChange}*/}
            {/*/>*/}

            <Box my={2}>
              <Button
                fullWidth
                color="primary"
                // disabled={isSubmitting}
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
                    onClick={()=>{onClose()}}
                >取消</Button>
            </Box>
          </form>
        )}
      </Formik>
    </Dialog>
  );
}
