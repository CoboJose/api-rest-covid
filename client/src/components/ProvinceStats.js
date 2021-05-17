import { Card, CardContent, Typography } from '@material-ui/core'
import React from 'react'
import "../style/ProvinceStats.css"

function ProvinceStats({dataDate, province}) {
    
    const getProvinceData = (data, province) => {
        let res = null;
        data.autonomies.map((autonomy) => {
            autonomy.provinces.map((element) => {
                if(element.name == province){
                    res = element
                }
            })
        })
        return res;
    }
    const date = dataDate.date;
    const provinceData = getProvinceData(dataDate, province);
    return (
        <Card className="province-container">
            <CardContent>
                <Typography className="province-title">
                    {provinceData.name} {date}
                </Typography>
                <div className="table-province">
                    <tr>
                        <td>
                            Nuevos casos
                        </td>
                        <td>
                            <strong>{provinceData.newConfirmed == null ? "No data" : provinceData.newConfirmed}</strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Recuperados
                        </td>
                        <td>
                        <strong>{provinceData.newRecovered == null ? "No data" : provinceData.newRecovered}</strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        Muertes
                        </td>
                        <td>
                        <strong>{provinceData.newDeaths == null ? "No data" : provinceData.newDeaths}</strong>
                        </td>
                    </tr>
                </div>
            </CardContent>
        </Card>
    )
}

export default ProvinceStats
