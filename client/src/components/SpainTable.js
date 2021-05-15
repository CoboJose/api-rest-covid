import React from 'react'
import "../style/SpainTable.css"

function SpainTable() {
    const autonomies = [
        {
            "name": "Madrid",
            "cases": 1548756
        },
        {
            "name": "Cataluña",
            "cases": 1548756
        },
        {
            "name": "Andalucia",
            "cases": 1548756
        },
        {
            "name": "Castilla y Leon",
            "cases": 1548756
        },
        {
            "name": "Extremadura",
            "cases": 1548756
        },
        {
            "name": "Ceuta",
            "cases": 1548756
        },
        {
            "name": "Melilla",
            "cases": 1548756
        },
    ]
    return (
        <div className = "table">
            {autonomies.map(autonomy => (
                <tr>
                    <td>
                        {autonomy.name}
                    </td>
                    <td>
                        <strong>{autonomy.cases}</strong>
                    </td>
                </tr>
            ))}
        </div>
    )
}

export default SpainTable