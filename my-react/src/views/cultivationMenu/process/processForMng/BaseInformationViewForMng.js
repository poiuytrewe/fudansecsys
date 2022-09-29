import ViewStyle from "../../../../components/Style/ViewStyle";
import Page from "../../../../components/Page";
import {Container} from "@material-ui/core";
import React, {useState} from "react";
import BaseInformationForMng from "./BaseInformationForMng";
import {ToggleButton, ToggleButtonGroup} from "@mui/material";
import ProcessForTeaView from "../processForTeacher/ProcessForTeaView";

export default function BaseInformationViewForMng(){
    const classes=ViewStyle()
    const [value, setValue]=useState(0)

    return (
        <Page className={classes.root} title="学生培养过程（管理员模式）">
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
            {value===0 ? (
                <Container maxWidth={false}>
                    <BaseInformationForMng/>
                </Container>
            ):(
                <ProcessForTeaView/>
            )}
        </Page>
    )
}
