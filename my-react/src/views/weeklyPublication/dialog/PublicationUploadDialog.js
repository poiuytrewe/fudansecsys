import {
    AppBar, Box,
    Button,
    ButtonGroup,
    Dialog,
    IconButton,
    makeStyles,
    TextField,
    Toolbar,
    Typography
} from "@material-ui/core";
import {Transition} from "../../device/components/DeviceApplyHistory";
import CloseIcon from "@material-ui/icons/Close";
import {Formik} from "formik";
import confirmModal from "../../../components/ConfirmModal";
import {postFetch} from "../../../base";
import {UPDATE_WEEKLYPUBLICATION, UPLOAD_WEEKLYPUBLICATION} from "../../../settings";
import * as Yup from "yup";
import {useState} from "react";
import SelectWeekDialog from "./SelectWeekDialog";

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

export default function PublicationUploadDialog(props){
    const {open, detail, onClose}=props
    const classes=useStyles()
    const classesS=useStylesS()
    const [publicationValues, setPublicationValues]=useState({})
    const [selectOpen, setSelectOpen]=useState(false)

    return (
        <>
        <Dialog fullScreen open={open} TransitionComponent={Transition}>
            <AppBar className={classes.appBar}>
                <Toolbar>
                    <Typography variant="h3" className={classes.title}>
                        周报上传
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

            <Formik initialValues={detail}
                    validationSchema={Yup.object().shape({
                        target: Yup.string().required("Target的内容不能为空"),
                        outlinedAchievements: Yup.string().required("OutlinedAchievements的内容不能为空"),
                        achievements: Yup.string().required("Achievements的内容不能为空"),
                        plan: Yup.string().required("Plan的内容不能为空")
                    })}
                    onSubmit={(values)=>{
                        if (detail.id){// detail.id存在，说明是修改，那么week不用改变
                            const cor = confirmModal({
                                title:"确定要修改此次周报吗？",
                                handleCorfirm:()=>{
                                    cor.close()
                                    postFetch({
                                        url: UPDATE_WEEKLYPUBLICATION,
                                        values:{
                                            id: detail.id,
                                            target: values.target,
                                            outlinedAchievements: values.outlinedAchievements,
                                            achievements: values.achievements,
                                            plan: values.plan
                                        },
                                        successCallback:()=>{
                                            onClose()
                                        }
                                    })
                                }
                            })
                        }
                        else {
                            setSelectOpen(true)
                            setPublicationValues({
                                stuName:values.stuName,
                                stuId:values.stuId,
                                target:values.target,
                                outlinedAchievements:values.outlinedAchievements,
                                achievements:values.achievements,
                                plan:values.plan
                            })
                        }
            }}
            >{({handleSubmit, handleChange, handleBlur, values, errors, touched })=>(
                <form onSubmit={handleSubmit} className={classesS.root}>

                    <TextField
                        error={Boolean(touched.target && errors.target)}
                        helperText={touched.target && errors.target}
                        className={classesS.margin}
                        multiline
                        rows={3}
                        label="Target"
                        fullWidth
                        margin="normal"
                        name="target"
                        value={values.target}
                        variant="outlined"
                        onBlur={handleBlur}
                        onChange={handleChange}/>
                    <TextField
                        error={Boolean(touched.outlinedAchievements && errors.outlinedAchievements)}
                        helperText={touched.outlinedAchievements && errors.outlinedAchievements}
                        className={classesS.margin}
                        multiline
                        rows={4}
                        label="Outlined Achievements"
                        fullWidth
                        margin="normal"
                        name="outlinedAchievements"
                        value={values.outlinedAchievements}
                        variant="outlined"
                        onBlur={handleBlur}
                        onChange={handleChange}/>
                    <TextField
                        error={Boolean(touched.achievements && errors.achievements)}
                        helperText={touched.achievements && errors.achievements}
                        className={classesS.margin}
                        multiline
                        rows={20}
                        label="Achievements"
                        fullWidth
                        margin="normal"
                        name="achievements"
                        value={values.achievements}
                        variant="outlined"
                        onBlur={handleBlur}
                        onChange={handleChange}/>
                    <TextField
                        error={Boolean(touched.plan && errors.plan)}
                        helperText={touched.plan && errors.plan}
                        className={classesS.margin}
                        multiline
                        rows={5}
                        label="Plan"
                        fullWidth
                        margin="normal"
                        name="plan"
                        value={values.plan}
                        variant="outlined"
                        onBlur={handleBlur}
                        onChange={handleChange}/>

                    <Box my={2}>
                        <Button
                            className={classesS.margin}
                            fullWidth
                            color="primary"
                            size="large"
                            type="submit"
                            variant="contained"
                        >提交</Button>
                    </Box>
                </form>
            )}</Formik>
        </Dialog>

        <SelectWeekDialog open={selectOpen}
                          publicationValues={publicationValues}
                          onClose={()=>{
                              setSelectOpen(false)
                              setPublicationValues({})

                          }}
                          allClose={()=>{
                              setSelectOpen(false)
                              setPublicationValues({})
                              onClose()
                          }}
        />
        </>
    )
}
