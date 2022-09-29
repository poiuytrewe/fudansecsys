import ViewStyle from "../../../components/Style/ViewStyle";
import Page from "../../../components/Page";
import {Container} from "@material-ui/core";
import WeeklyPublicationForBoss from "./WeeklyPublicationForBoss";

export default function BossView(){
    const classes=ViewStyle()

    return (
        <Page className={classes.root} title="周报模块">
            <Container maxWidth={false}>
                <WeeklyPublicationForBoss/>
            </Container>
        </Page>
    )
}
