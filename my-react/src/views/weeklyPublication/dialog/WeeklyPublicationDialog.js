import {AppBar, Dialog, IconButton, makeStyles, TextField, Toolbar, Typography} from "@material-ui/core";
import {Transition} from "../../device/components/DeviceApplyHistory";
import CloseIcon from "@material-ui/icons/Close";
import {Formik} from "formik";

const useStyles = makeStyles((theme) => ({
    appBar: {
        position: "relative",
    },
    title: {
        marginLeft: theme.spacing(2),
        flex: 1,
    },
}));

const useStylesS = makeStyles((theme) => ({
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

export default function WeeklyPublicationDialog(props){
    const {open, weeklyPublication, onClose}=props
    const classes=useStyles()
    const classesS=useStylesS()

    return (
        <Dialog fullScreen open={open} TransitionComponent={Transition}>
            <AppBar className={classes.appBar}>
                <Toolbar>
                    <Typography variant="h3" className={classes.title}>
                        {`${weeklyPublication.stuName}${weeklyPublication.date}提交的周报`}
                    </Typography>

                    <IconButton
                        edge="start"
                        color="inherit"
                        onClick={onClose}
                        aria-label="close">
                        <CloseIcon/>
                    </IconButton>
                </Toolbar>
            </AppBar>

            <form className={classesS.root}>
                <TextField
                    className={classesS.margin}
                    InputProps={{readOnly:true}}
                    multiline
                    rows={3}
                    label="Target"
                    fullWidth
                    margin="normal"
                    name="target"
                    value={weeklyPublication.plan}
                    variant="outlined"/>
                <TextField
                    className={classesS.margin}
                    InputProps={{readOnly:true}}
                    multiline
                    rows={4}
                    label="Outlined Achievements"
                    fullWidth
                    margin="normal"
                    name="outlinedAchievements"
                    value={weeklyPublication.outlinedAchievements}
                    variant="outlined"/>
                <TextField
                    className={classesS.margin}
                    InputProps={{readOnly:true}}
                    multiline
                    rows={20}
                    label="Achievements"
                    fullWidth
                    margin="normal"
                    name="achievements"
                    value={weeklyPublication.achievements}
                    variant="outlined"/>
                <TextField
                    className={classesS.margin}
                    InputProps={{readOnly:true}}
                    multiline
                    rows={5}
                    label="Plan"
                    fullWidth
                    margin="normal"
                    name="plan"
                    value={weeklyPublication.plan}
                    variant="outlined"/>
            </form>
        </Dialog>
    )
}
