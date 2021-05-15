import { Card, CardContent, Typography } from '@material-ui/core'
import React from 'react'
import "../style/ProvinceStats.css"

function ProvinceStats({title, cases, recovered, deaths}) {
    return (
        <Card className="province-container">
            <CardContent>
                <Typography className="province-title">
                    {title}
                </Typography>
                <div className="table-province">
                    <tr>
                        <td>
                            Casos activos
                        </td>
                        <td>
                            <strong>{cases}</strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Recuperados
                        </td>
                        <td>
                        <strong>{recovered}</strong>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        Muertes
                        </td>
                        <td>
                        <strong>{deaths}</strong>
                        </td>
                    </tr>
                </div>
            </CardContent>
        </Card>
    )
}

export default ProvinceStats
