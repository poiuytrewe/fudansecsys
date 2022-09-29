import {Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function UploadLabWork(props){
    const classes = useStyles()
    const {open, onClose, labName} = props

    return (
        <Dialog open={open} className={classes.root}>
            <DialogTitle>
                提交Lab作业
            </DialogTitle>

            <Formik initialValues onSubmit={(values)=>{

            }}>{({handleBlur, handleChange, handleSubmit, values})=>(
                <form onSubmit={handleSubmit} className="dialogForm">
                    <TextField
                        select
                        label="请选择Lab作业对应的Lab"
                        size="small"
                        fullWidth
                        SelectProps={{
                            native:false
                        }}
                        value={values?.labName}
                        variant="outlined"
                        margin="normal"
                        onChange={(event)=>{
                            values.labName=event.target.value
                        }}>
                        {labName.map((singleLabName)=>(
                            <option key={singleLabName.name} value={singleLabName.name()}>
                                {singleLabName.name}
                            </option>
                        ))}
                    </TextField>

                </form>
            )}</Formik>
        </Dialog>
    )
}
