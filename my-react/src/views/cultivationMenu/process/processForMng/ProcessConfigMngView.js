import {Container, makeStyles} from "@material-ui/core";
import {useHistory, useParams} from "react-router-dom";
import ViewStyle from "../../../../components/Style/ViewStyle";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {MNG_GET_USER_DETAIL, U_GET_USER_DETAIL} from "../../../../settings";
import Page from "../../../../components/Page";
import ProcessConfigForStu from "../ProcessConfigForStu";

const defaultStyles=makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3)
    },
}));

export default function ProcessConfigMngView(){
    const {stuId}=useParams()
    const classes=ViewStyle()
    const {userInfo}=useContext(UserContext)
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
                {userDetail.stuId>0 && <ProcessConfigForStu userDetail={userDetail}/>}
            </Container>
        </Page>
    )
}
