import ViewStyle from "../../../components/Style/ViewStyle";
import Page from "../../../components/Page";
import {Container} from "@material-ui/core";
import {useContext} from "react";
import {UserContext} from "../../../layouts/Context";
import RecorderGroupForMng from "./eachRecorderGroup/RecorderGroupForMng";
import RecorderGroupForStu from "./eachRecorderGroup/RecorderGroupForStu";

const RecorderGroupView=()=>{
    const classes=ViewStyle()
    const {userInfo}=useContext(UserContext)

    return　(
        <Page
            className={classes.root}
            title="学生分组"
        >
            <Container maxWidth={false}>
                {(userInfo.isTalkMng === 1 || userInfo.type===20)? <RecorderGroupForMng/> : <RecorderGroupForStu/> }
            </Container>
        </Page>
    )

}

export default RecorderGroupView
