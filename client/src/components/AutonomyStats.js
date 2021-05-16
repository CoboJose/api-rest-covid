import { Card, CardContent, Typography } from '@material-ui/core'
import React from 'react'
import "../style/AutonomyStats.css"

function AutonomyStats({dataDate, province}) {
    const getAutonomyData = (data, province) => {
        let res = null;
        data.autonomies.map((autonomy) => {
            autonomy.provinces.map((element) => {
                if(element.name == province){
                    res = autonomy
                }
            })
        })
        return res;
    }
    const date = dataDate.date;
    const autonomyData = getAutonomyData(dataDate, province);
    console.log(autonomyData)
    return (
        <Card className="autonomy-container">
            <CardContent>
                <Typography className="autonomy-title" color="textSecondary">
                    {autonomyData.name} {date}
                </Typography>
                <div className="table-autonomy">
                    <tr>
                        <td>
                            Casos activos
                        </td>
                        <td>
                            <strong>{autonomyData.newConfirmed == null ? "No data" : autonomyData.newConfirmed }</strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Recuperados
                        </td>
                        <td>
                        <strong>{autonomyData.newRecovered == null ? "No data" : autonomyData.newRecovered}</strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        Muertes
                        </td>
                        <td>
                        <strong>{autonomyData.newDeaths == null ? "No data" : autonomyData.newDeaths}</strong>
                        </td>
                    </tr>
                </div>
            </CardContent>
        </Card>
    )
}

export default AutonomyStats