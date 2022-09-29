import ViewStyle from "../../../../components/Style/ViewStyle";
import React, {useContext, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import Page from "../../../../components/Page";
import {Container} from "@material-ui/core";
import ScoreForTea from "./ScoreForTea";
import ScoreForBoss from "./ScoreForBoss";
import ScoreForMng from "../scoreForMng/ScoreForMng";
import {ToggleButton, ToggleButtonGroup} from "@mui/material";

export default function ScoreView(){ //能看到这里的，type一定是20
    const classes=ViewStyle()
    const {userInfo}=useContext(UserContext)
    const [value,setValue]=useState(0)

    return (
        <Page className={classes.root} title="季度评分">
            {userInfo.isEducateMng===1 ?(
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
                            <ScoreForMng/>
                        </Container>
                        ):(
                        <Container maxWidth={false}>
                            <ScoreForTea/>
                        </Container>
                    )}
                    </>
            ):(
                <Container maxWidth={false}>
                    {userInfo.stuId==="07175" ? <ScoreForBoss/> : <ScoreForTea/>}
                </Container>
            )}
        </Page>
    )
}
