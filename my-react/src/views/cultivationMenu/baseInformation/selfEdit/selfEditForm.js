import React, {useContext, useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import clsx from "clsx";
import { useFormik } from "formik";
import { postFetch } from "src/base";
import alertBox from "src/components/AlertBox";
import { Box, Button, TextField, makeStyles } from "@material-ui/core";
import {MNG_GET_ALL_USER, MNG_UPDATE_USER_URL, U_UPDATE_USER_URL} from "src/settings";
import {forceGetAllUser} from "../../../../service/userService";
import {UserContext} from "../../../../layouts/Context";

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

export default function SelfEditForm(props) {
    const {userDetail, handleOnRefresh}=props;
    const classes = useStyles();
    const history = useHistory();
    const [teachers,setTeachers] = useState([]);
    const {userInfo}=useContext(UserContext);
    const [refresh, setRefresh]=useState(false);

    const handleOnRefreshS=()=>{
        handleOnRefresh();
    }

    useEffect(()=>{
        fetch(`${MNG_GET_ALL_USER}?type=${20}`,{})
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setTeachers(res?.data || [])
            })
    },[refresh]);

    const formik = useFormik({
        initialValues: userDetail,
        onSubmit: (values) => {
            postFetch({
                url: userInfo.isEducateMng>0 ? MNG_UPDATE_USER_URL : U_UPDATE_USER_URL,
                values,
                successCallback: () => {
                    alertBox({ text: "修改成功", severity: "success" });
                    if(userInfo.isEducateMng>0){
                        history.replace("/app/studyPlan/baseInformation/mng")
                    }
                    handleOnRefreshS()
                },
            });
        },
    });

    const {
        errors,
        handleBlur,
        handleChange,
        handleSubmit,
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
                className={classes.margin}
            />
            {userInfo.isEducateMng>0 && (
                <TextField
                    error={Boolean(touched.name && errors.name)}
                    select
                    label="导师"
                    name="mentor"
                    className={clsx(classes.margin, classes.textField)}
                    onBlur={handleBlur}
                    onChange={handleChange}
                    variant="outlined"
                    value={values.mentor}
                    SelectProps={{
                        native: true,
                    }}
                >
                    <option >
                        无
                    </option>
                    {teachers.map((user)=>(
                        <option value={user.name}>
                            {user.name}
                        </option>
                    ))}
                </TextField>
            )}

            <TextField
              error={Boolean(touched.keshuo && errors.keshuo)}
              select
              disabled={(userInfo.type!==20 || userDetail.type===10)? true: false}
              label="硕士类型"
              name="keshuo"
              className={clsx(classes.margin, classes.textField)}
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
              value={values.keshuo}
              SelectProps={{
                native: true,
              }}
            >
              <option value={0}>无</option>
              <option value={1}>学硕</option>
              <option value={2}>专硕</option>
              <option value={3}>学工</option>
            </TextField>
            <TextField
              error={Boolean(touched.name && errors.name)}
              multiline
              fullWidth
              rows={4}
              label="论文"
              name="papers"
              className={classes.margin}
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
              value={values.papers}
            />
            <TextField
              error={Boolean(touched.name && errors.name)}
              multiline
              fullWidth
              rows={4}
              label="专利"
              name="patents"
              className={classes.margin}
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
              value={values.patents}
            />
            <TextField
              error={Boolean(touched.name && errors.name)}
              multiline
              fullWidth
              rows={4}
              label="服务"
              name="services"
              className={classes.margin}
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
              value={values.services}
            />
            <TextField
              error={Boolean(touched.name && errors.name)}
              multiline
              fullWidth
              rows={4}
              label="项目"
              name="projects"
              className={classes.margin}
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
              value={values.projects}
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
