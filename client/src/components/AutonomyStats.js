import { Card, CardContent, Typography } from '@material-ui/core'
import React from 'react'
import "../style/AutonomyStats.css"

function AutonomyStats({title, cases, recovered, deaths}) {
    return (
        <Card className="autonomy-container">
            <CardContent>
                <Typography className="autonomy-title" color="textSecondary">
                    {title}
                </Typography>
                <div className="table-autonomy">
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

export default AutonomyStats