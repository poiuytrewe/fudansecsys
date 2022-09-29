import React from "react";
import {
  Button,
  Table,
  TableCell,
  TableBody,
  TableRow,
  TableHead,
  makeStyles,
} from "@material-ui/core";
import {
  U_RETURN_DEVICE_URL,
} from "src/settings";
import { postFetch } from "src/base";
import alertBox from "src/components/AlertBox";
const useStyles = makeStyles((theme) => ({
  root: {
    // flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    padding: theme.spacing(2),
  },
}));
const TYPES = [
  {
    id: 0,
    name: "无详细指明",
  },
  {
    id: 10,
    name: "显示器",
  },
  {
    id: 20,
    name: "移动设备",
  },
  {
    id: 30,
    name: "台式主机",
  },
  {
    id: 50,
    name: "服务器",
  },
  {
    id: 40,
    name: "其它设备",
  },
];
export default function HistoryTable(props) {
  const { refresh, refreshHistory, histories } = props;
  const classes = useStyles();
  const returnDevice = (allocationId) => {//归还设备
    postFetch({
      url: U_RETURN_DEVICE_URL,
      values: {
        allocationId,
      },
      successCallback: () => {
        alertBox({ text: "归还成功", severity: "success" });
        refreshHistory();
        refresh();
      },
    });
  };
  return (
    <div className={classes.root}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>设备类型</TableCell>
            <TableCell>设备品牌</TableCell>
            <TableCell>设备型号</TableCell>
            <TableCell align="center">操作</TableCell>
          </TableRow>
        </TableHead>
        {/* type;设备类型 model;设备型号 name;设备名称 principal;负责人姓名
          inventory;库存 inventoryUnit;库存单位 */}
        <TableBody>
          {histories?.map((history) => (
            <TableRow hover key={history.id}>
              <TableCell>
                {TYPES.find((type) => type.id === history.deviceType)?.name}
              </TableCell>
              <TableCell>{history.deviceName}</TableCell>
              <TableCell>{history.deviceModel}</TableCell>
              <TableCell align="center">
                <Button
                  color={history.status === 10 ? "primary" : "default"}
                  size="small"
                  variant="text"
                  onClick={() => {
                    if (history.status === 10) returnDevice(history.id);
                  }}
                >
                  {history.status === 10 ? "归还设备" : "已归还"}
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}

