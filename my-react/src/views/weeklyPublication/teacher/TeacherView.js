import ViewStyle from "../../../components/Style/ViewStyle";
import React, {useContext, useState} from "react";
import {UserContext} from "../../../layouts/Context";
import Page from "../../../components/Page";
import {Container} from "@material-ui/core";
import WeeklyPublicationForTea from "./WeeklyPublicationForTea";
import {ToggleButton, ToggleButtonGroup} from "@mui/material";
import WeeklyPublicationForManager from "../manager/WeeklyPublicationForManager";

export default function TeacherView(){
    const classes=ViewStyle()
    const {userInfo}=useContext(UserContext)
    const [value, setValue]=useState(0)

    return (
        <Page className={classes.root} title="学生周报模块">
            {userInfo.isEducateMng == 1? (
                <>
                <Container maxWidth={false}>
                    <ToggleButtonGroup
                        color="primary"
                        exclusive
                        onChange={(event, value)=>{
                            setValue(value)
                        }}
                        aria-label="text alignment">
                        <ToggleButton value={0} aria-label="left aligned">
                            管理员身份
                        </ToggleButton>
                        <ToggleButton value={1} aria-label="right aligned">
                            非管理员身份
                        </ToggleButton>
                    </ToggleButtonGroup>
                </Container>
                    {value===0 ?(
                        <Container maxWidth={false}>
                            <WeeklyPublicationForManager/>
                        </Container>
                    ):(
                        <Container maxWidth={false}>
                            <WeeklyPublicationForTea userInfo={userInfo}/>
                        </Container>
                    )}
                </>
            ):(
                <Container maxWidth={false}>
                    <WeeklyPublicationForTea userInfo={userInfo}/>
                </Container>
            )}
        </Page>
    )
}
