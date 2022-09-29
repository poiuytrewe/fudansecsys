import {useHistory, useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {MNG_GET_USER_DETAIL, U_GET_USER_DETAIL} from "../../../../settings";
import Page from "../../../../components/Page";
import {AppBar, Box, Button, Container, makeStyles, Paper, Tab, Tabs, Typography} from "@material-ui/core";
import PageView from "../../../../components/Style/PageView";
import PropTypes from "prop-types";
import ProcessEssentialForStu from "../ProcessEssentialForStu";
import ProcessBaseForStu from "../ProcessBaseForStu";
import ViewStyle from "../../../../components/Style/ViewStyle";
import ProcessForStu from "../ProcessForStu";
import ProcessConfig from "../processForMng/ProcessConfig";
import theme from "../../../../theme";
import ProcessConfigForStu from "../ProcessConfigForStu";
const defaultStyles=makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3)
    },
}));

export default function ProcessForStuView(){
    const {stuId}=useParams()
    const classes=ViewStyle()
    const {userInfo}=useContext(UserContext)
    const history=useHistory()
    const [userDetail, setUserDetail]=useState({})

    useEffect(()=>{
        fetch(
            `${userInfo.type==20 ? MNG_GET_USER_DETAIL:U_GET_USER_DETAIL }?stuId=${stuId}`)
            .then((res)=>res.json())
            .then((res)=>{
                setUserDetail(res?.data || {})
            })
    },[])

    return (
        <Page classes={classes.root} title="学生培养过程">
            <Container maxWidth={false}>
                {userDetail.id>0 && (
                    (userDetail.keshuo===0 ? (
                        <Paper>
                            <div style={{
                                backgroundColor:theme.palette.background.paper,
                                padding: theme.spacing(2),
                            }}>
                                <div style={{
                                    display: "flex",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                    paddingBottom: theme.spacing(2),
                                    "& .MuiTextField-root": {
                                        width: "150px",
                                    },
                                    "& .MuiButton-root": {
                                        width: "150px",
                                        height: "40px",
                                    },
                                }}>
                                    <Typography color="textPrimary" size="small">
                                        {userDetail.stuId===userInfo.stuId?
                                            "您的身份不是硕士，或者您的硕士类型未被确认，请联系管理员修改您的硕士类型。" :
                                            "该学生的身份不是硕士，或者他的硕士类型未被确认，请修改他的硕士类型。"
                                        }
                                    </Typography>
                                    <Button
                                        color="primary"
                                        size="small"
                                        variant="outlined"
                                        onClick={()=>{
                                            history.goBack()
                                        }}>返回</Button>
                                </div>
                            </div>
                        </Paper>
                    ):(
                        userDetail.studyType===0? (
                            userDetail.stuId===userInfo.stuId ? (
                                <ProcessConfigForStu userDetail={userDetail}/>
                            ):(
                                <Paper>
                                    <div style={{
                                        backgroundColor:theme.palette.background.paper,
                                        padding: theme.spacing(2),
                                    }}>
                                        <div style={{
                                            display: "flex",
                                            alignItems: "center",
                                            justifyContent: "space-between",
                                            paddingBottom: theme.spacing(2),
                                            "& .MuiTextField-root": {
                                                width: "150px",
                                            },
                                            "& .MuiButton-root": {
                                                width: "150px",
                                                height: "40px",
                                            },
                                        }}>
                                            <Typography color="textPrimary" size="small">
                                                该学生还没有选择对应的培养过程
                                            </Typography>
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="outlined"
                                                onClick={()=>{
                                                    history.goBack()
                                                }}>返回</Button>
                                        </div>
                                    </div>
                                </Paper>
                            )
                        ):(
                            <ProcessForStu userDetail={userDetail}/>
                        )
                    )))}
            </Container>
        </Page>
    )
}
