import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import * as Yup from "yup";
import confirmModal from "../../../components/ConfirmModal";
import {postFetch} from "../../../base";
import {UPLOAD_WEEKLYPUBLICATION} from "../../../settings";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function SelectWeekDialog(props){
    const {open, onClose, publicationValues, allClose}=props
    const classes=useStyles()

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle onClose={onClose}>周数选择</DialogTitle>
            <Formik initialValues={publicationValues}
                    validationSchema={Yup.object().shape({
                        week: Yup.string().required("请选择对应的周数")
                    })}
                    onSubmit={(values)=>{
                        let year=values.week.slice(0,4)
                        let week=values.week.slice(6)
                        let yearNum=parseInt(year)
                        let weekNum=parseInt(week)
                        const cor=confirmModal({
                            title:"请确认好周报的内容和周报对应的周数",
                            handleCorfirm:()=>{
                                cor.close()
                                postFetch({
                                    url:UPLOAD_WEEKLYPUBLICATION,
                                    values:{
                                        stuName: publicationValues.stuName,
                                        stuId: publicationValues.stuId,
                                        target: publicationValues.target,
                                        outlinedAchievements: publicationValues.outlinedAchievements,
                                        achievements: publicationValues.achievements,
                                        plan: publicationValues.plan,
                                        isRead: 0,
                                        isComment: 0,
                                        week: "第"+yearNum+"年第"+weekNum+"周"
                                    },
                                    successCallback:()=>{
                                        allClose()
                                    }
                                })
                            }
                        })
                    }}
            >{({handleSubmit, handleChange, handleBlur, values,touched, errors})=>(
                <form onSubmit={handleSubmit} className="dialogForm">
                    <TextField
                        error={Boolean(touched.week && errors.week)}
                        helperText={touched.week && errors.week}
                        InputLabelProps={{shrink:true}}
                        fullWidth
                        label="请选择与周报对应的周数"
                        margin="normal"
                        name="week"
                        value={values.week}
                        onBlur={handleBlur}
                        onChange={handleChange}
                        variant="outlined"
                        type="week"
                        />
                    <Box my={2}>
                        <Button
                            fullWidth
                            color="primary"
                            size="large"
                            type="submit"
                            variant="contained">提交</Button>
                        <Button
                            fullWidth
                            color="secondary"
                            size="large"
                            onClick={()=>{
                                onClose()
                            }}
                            variant="contained">关闭</Button>
                    </Box>
                </form>
            )}</Formik>
        </Dialog>
    )
}
