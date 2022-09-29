import ViewStyle from "../../../components/Style/ViewStyle";
import Page from "../../../components/Page";
import {Container} from "@material-ui/core";
import WeeklyPublicationForManager from "./WeeklyPublicationForManager";

export default function ManagerView(){
    const classes=ViewStyle()

    return (
        <Page className={classes.root} title="周报模块">
            <Container maxWidth={false}>
                <WeeklyPublicationForManager/>
            </Container>
        </Page>
    )
}
