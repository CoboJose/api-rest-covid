import { Card, CardContent, Typography } from '@material-ui/core'
import React from 'react'
import "../style/GlobalInfo.css"

function GlobalInfo({title, cases, total, type}) {
    return (
        
        <Card className={`global-info-box ${type}`}>
            <CardContent>
                <Typography className="global-info-title" color = "textSecondary">
                    {title}
                </Typography>
                <h2 className="global-info-cases">{cases}</h2>
                <Typography className="global-info-total" color="textSecondary">
                    {total} Total
                </Typography>
            </CardContent>
        </Card>
    )
}

export default GlobalInfo