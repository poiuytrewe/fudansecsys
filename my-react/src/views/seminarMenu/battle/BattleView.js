import ViewStyle from "../../../components/Style/ViewStyle";
import {useContext} from "react";
import {UserContext} from "../../../layouts/Context";
import Page from "../../../components/Page";
import {Container} from "@material-ui/core";
import Battle from "./Battle";

const BattleView=()=>{
    const classes=ViewStyle()
    const {userInfo}=useContext(UserContext)

    return (
        <Page
            className={classes.root}
            title="battle计分"
        >
            <Container maxWidth={false}>
                <Battle/>
            </Container>
        </Page>
    )
}

export default BattleView
